package ru.tikskit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tikskit.mygson.MyGson;
import ru.tikskit.testobjects.Object1;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyGsonTest {
    private MyGson myGson;
//    private Gson gson;

    @BeforeEach
    public void setUp() {
        myGson = new MyGson();
//        gson = new Gson();
    }

    @DisplayName("Проверяем, что Gson восстанавливает эквивалентный исходному объект из строки JSON")
    @Test
    public void checkEquals() {
        var gson = new Gson();
        Object1 obj = new Object1();
        obj.setValues();
        String json = myGson.toJson(obj);
        Object1 obj2 = gson.fromJson(json, Object1.class);
        assertTrue(obj.equals(obj2));
    }

    @Test
    public void test() {
        var gson = new Gson();
        assertEquals(gson.toJson(null), myGson.toJson(null));
        assertEquals(gson.toJson((byte)1), myGson.toJson((byte)1));
        assertEquals(gson.toJson((short)1f), myGson.toJson((short)1f));
        assertEquals(gson.toJson(1), myGson.toJson(1));
        assertEquals(gson.toJson(1L), myGson.toJson(1L));
        assertEquals(gson.toJson(1f), myGson.toJson(1f));
        assertEquals(gson.toJson(1d), myGson.toJson(1d));
        assertEquals(gson.toJson("aaa"), myGson.toJson("aaa"));
        assertEquals(gson.toJson('a'), myGson.toJson('a'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), myGson.toJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2 ,3)), myGson.toJson(List.of(1, 2 ,3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), myGson.toJson(Collections.singletonList(1)));
    }

}
