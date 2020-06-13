package ru.tikskit;

import com.google.gson.Gson;
import ru.tikskit.mygson.MyGson;
import ru.tikskit.testobjects.Object1;

import java.util.Collections;

public class MyGSONMainClass {
    public static void main(String[] args) {
        Object1 obj = new Object1();
        obj.setValues();

        MyGson myGson = new MyGson();
//        String json = myGson.toJson(Collections.singletonList(1));
//        String json = myGson.toJson(Collections.singletonList(1));
        String json = myGson.toJson(obj);


        System.out.println(json);


        var gson = new Gson();
        Object obj2 = gson.fromJson(json, Object1.class);
        System.out.println(obj2);

    }


}
