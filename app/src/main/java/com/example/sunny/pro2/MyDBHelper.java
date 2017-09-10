package com.example.sunny.pro2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Created by sunny on 2017/7/22.
 */

public class MyDBHelper  extends SQLiteOpenHelper {
    private  static MyDBHelper instance = null;
    Context context;
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }



    public static MyDBHelper getInstace(Context ctx){
        if(instance==null){
            instance = new MyDBHelper(ctx,"expense.db",null,1);
        }
        return  instance;
    }

    //如果db存在 就不會執行onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE main.exp " +
                "(_id INTEGER PRIMARY KEY  NOT NULL , " +
                "cdate DATETIME NOT NULL , " +
                "info VARCHAR, " +
                "amount INTEGER)");

        InputStream inputStream = context.getResources().openRawResource(R.raw.expenses);
        //BufferedReader in = new BufferedReader();
        //json


        try {
            JSONObject json = new JSONObject("aaa");
            JSONArray arry = json.getJSONArray("expense");//快速鍵 option +Enter ->抽取會resource

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
