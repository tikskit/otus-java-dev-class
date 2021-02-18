package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private class BeanContext {
        private final Method beanInstantiator;
        private final String name;
        private Object bean;

        public BeanContext(String name, Method beanInstantiator) {
            this.name = name;
            this.beanInstantiator = beanInstantiator;
        }

        public Method getBeanInstantiator() {
            return beanInstantiator;
        }

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

        public String getName() {
            return name;
        }

    }

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object o : appComponents) {
            if (componentClass.isAssignableFrom(o.getClass())) {
                return (C) o;
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            instantiateComponents(configClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }


    private void instantiateComponents(Class<?> configClass) throws Exception {
        List<BeanContext> orderedBeans = getOrderedBeanMethods(configClass);
        Object configObject = instantiateConfigObject(configClass);
        for (BeanContext beanContext : orderedBeans) {
            instantiateComponent(configObject, beanContext);
            appComponents.add(beanContext.getBean());
            appComponentsByName.put(beanContext.getName(), beanContext.getBean());
        }
    }

    private Object instantiateConfigObject(Class<?> configClass) {
        try {
            Constructor<?> constructor = configClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Exception on instantiation config class object", e);
        }
    }

    private List<BeanContext> getOrderedBeanMethods(Class<?> configClass) {

        Method[] methods = configClass.getMethods();
        SortedMap<Integer, Set<BeanContext>> order = new TreeMap<>();
        for (Method method: methods) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                AppComponent appComponent = method.getDeclaredAnnotation(AppComponent.class);
                Set<BeanContext> orderMethods = order.get(appComponent.order());
                if (orderMethods == null) { //@todo разобраться с computeIfAbsent
                    orderMethods = new HashSet<>();
                    order.put(appComponent.order(), orderMethods);
                }
                orderMethods.add(new BeanContext(appComponent.name(), method));
            }
        }
        List<BeanContext> res = new ArrayList<>();
        for(Integer key: order.keySet()) {
            Set<BeanContext> methodSet = order.get(key);
            res.addAll(methodSet);
        }

        return res;
    }

    private void instantiateComponent(Object configObj, BeanContext beanContext) throws Exception {
        Parameter[] parameters = beanContext.getBeanInstantiator().getParameters();
        Object[] values = new Object[parameters.length];
        for (int i = 0; i< parameters.length; i++) {
            values[i] = getAppComponent(parameters[i].getType());
            if (values[i] == null) {
                throw new RuntimeException(String.format("Bean %s not found", parameters[i].getName()));
            }

        }

        Object bean = beanContext.getBeanInstantiator().invoke(configObj, values);
        beanContext.setBean(bean);
    }
}
