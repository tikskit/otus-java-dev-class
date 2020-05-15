package ru.tikskit.instrumentation;

import ru.tikskit.business.Car;
import ru.tikskit.business.Toyota;
import ru.tikskit.instrumentation.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ClassFactory {

    private ClassFactory() {

    }

    public static Car createToyota() {
        InvocationHandler carInvocationHandler = new CarInvocationHandler(new Toyota());
        return (Car) Proxy.newProxyInstance(ClassFactory.class.getClassLoader(), new Class<?>[]{Car.class},
                carInvocationHandler);
    }

    static class CarInvocationHandler implements InvocationHandler {
        private final Car car;
        private final Map<Method, Boolean> knownMethods = new HashMap<>();

        public CarInvocationHandler(Car car) {
            this.car = car;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Boolean isAnnotationPresent = knownMethods.get(method);
            if (isAnnotationPresent == null) {
                try {
                    Method m = car.getClass().getMethod(method.getName(), method.getParameterTypes());
                    isAnnotationPresent = m.isAnnotationPresent(Log.class);
                    knownMethods.put(method, isAnnotationPresent);
                } catch (NoSuchMethodException e) {
                    System.out.println(String.format("Method %s not found!", methodToStr(method, args)));
                }
            }

            if (isAnnotationPresent != null && isAnnotationPresent) {
                System.out.println(String.format("Method %s is about to get called", methodToStr(method, args)));
            }


            return method.invoke(car, args);
        }

        private String methodToStr(Method method, Object[] args) {
            if (method.getParameterTypes().length != args.length) {
                throw new IllegalArgumentException(String.format("Params count %d don't match to args count %d!",
                        method.getParameterTypes().length, args.length));
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(method.getName()).append("(");

                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    sb.append(String.format("%s = %s", method.getParameterTypes()[i].getName(), args[i].toString()));
                    if (i < method.getParameterTypes().length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");

                return sb.toString();
            }
        }


    }
}
