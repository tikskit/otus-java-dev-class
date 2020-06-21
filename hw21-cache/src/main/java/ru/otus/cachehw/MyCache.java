package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
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
        notifyAction(key, value, "put into the cache");
    }


    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        if (value != null) {
            notifyAction(key, value, "removed from the cache");
        }
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            notifyAction(key, value, "got from the cache");
        }
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        for (WeakReference<HwListener<K, V>> l : listeners) {
            if (l.get() != null && l.get() == listener) {
                listeners.remove(l);
                return;
            }
        }
    }

    private void notifyAction(K key, V value, String action) {

        Iterator<WeakReference<HwListener<K, V>>> iterator = listeners.iterator();

        while (iterator.hasNext()) {
            WeakReference<HwListener<K, V>> ref = iterator.next();
            if (ref.get() == null) {
                iterator.remove();
            } else {
                ref.get().notify(key, value, action);
            }
        }
    }

}
