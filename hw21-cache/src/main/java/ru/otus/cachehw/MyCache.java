package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final Map<K, V> cache = new WeakHashMap<>();
    private final Set<WeakReference<HwListener<K, V>>> listeners = new HashSet<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyAction(key, value, "put");
    }


    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        if (value != null) {
            notifyAction(key, value, "remove");
        }
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            notifyAction(key, value, "get");
        }
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        var iterator = listeners.iterator();
        while (iterator.hasNext()) {
            var ref = iterator.next();

            if (ref.get() == null) {
                iterator.remove();
            } else if (ref.get() == listener) { // Если тут ref.get() будет равно null - не страшно
                iterator.remove();
                return;
            }
        }
    }

    private void notifyAction(K key, V value, String action) {
        var iterator = listeners.iterator();

        while (iterator.hasNext()) {
            var ref = iterator.next();
            var listener = ref.get();

            if (listener == null) {
                iterator.remove();
            } else {
                listener.notify(key, value, action);
            }
        }
    }

}
