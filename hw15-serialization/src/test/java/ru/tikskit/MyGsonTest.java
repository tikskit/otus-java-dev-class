package ru.tikskit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tikskit.mygson.MyGson;
import ru.tikskit.testobjects.Object1;

import static org.junit.jupiter.api.Assertions.*;

public class MyGsonTest {
    private MyGson myGson;
    private Gson gson;

    @BeforeEach
    public void setUp() {
        myGson = new MyGson();
        gson = new Gson();
    }

    @DisplayName("Проверяем, что Gson восстанавливает эквивалентный исходному объект из строки JSON")
    @Test
    public void checkEquals() {
        Object1 obj = new Object1();
        obj.setValues();
        String json = myGson.toJson(obj);
        Object1 obj2 = gson.fromJson(json, Object1.class);
        assertTrue(obj.equals(obj2));
    }

}
