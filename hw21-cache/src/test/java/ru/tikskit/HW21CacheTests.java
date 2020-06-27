package ru.tikskit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.model.User;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HW21CacheTests {


    @DisplayName("Показывает, что если в список слушателей кэша добавляем noname класс имплементирующий HwListener, то он удалятся при сборке мусора")
    @Test
    public void testAnonymousListener() {
        HwCache<String, User> myCache = new MyCache<>();

        myCache.addListener(new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {

            }
        });


        System.gc();

        Set<WeakReference<HwListener<String, User>>> listeners = getCacheListeners(myCache);
        assertNotNull(listeners);

        assertEquals(1, listeners.size());
        var iterator = listeners.iterator();

        assertTrue(iterator.hasNext());
        var ref = iterator.next();
        assertNull(ref.get()); // Анонимный класс был удален из листнеров
    }

    @DisplayName("Показывает, что если в список слушателей кэша добавляем лямбду, то она не удалятся при сборке мусора")
    @Test
    public void testLambdaListener() {
        HwCache<String, User> myCache = new MyCache<>();
        myCache.addListener((key, value, action) -> {

        });


        System.gc();

        Set<WeakReference<HwListener<String, User>>> listeners = getCacheListeners(myCache);
        assertNotNull(listeners);

        assertEquals(1, listeners.size());
        var iterator = listeners.iterator();

        assertTrue(iterator.hasNext());
        var ref = iterator.next();
        assertNotNull(ref.get());  // Лямбда не была удалена из листнеров
    }

    private Set<WeakReference<HwListener<String, User>>> getCacheListeners(HwCache<String, User> cache){
        try {
            Field field = cache.getClass().getDeclaredField("listeners");
            field.setAccessible(true);
            return (Set<WeakReference<HwListener<String, User>>>) field.get(cache);
        } catch (Exception e) {
            return null;
        }

    }
}
