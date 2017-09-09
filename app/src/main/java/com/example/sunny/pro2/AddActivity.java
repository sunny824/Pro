package com.example.sunny.pro2;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import static java.util.Calendar.DATE;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    static MyDBHelper helper;
    private EditText editAmount;
    private EditText editDate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editDate = (EditText) findViewById(R.id.edit_date);
        editAmount = (EditText) findViewById(R.id.edit_amount);
        final Calendar now = Calendar.getInstance();
        helper = MyDBHelper.getInstace(this);
        editDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new DatePickerDialog(AddActivity.this,AddActivity.this, now.get(Calendar.YEAR), now.get(DATE), now.get(Calendar.MONTH)).show();
            }
        }
        );
    }


    public void add(View v){
        String date       = editDate.getText().toString();
        String amountStr    = editAmount.getText().toString();
        int amount   = Integer.parseInt(amountStr);
        ContentValues values = new ContentValues();
        values.put("cdate", date);
        values.put("amount",amount);
        //用map不用 組inser 語法
        long id =helper.getWritableDatabase().insert("exp",null,values);
        Log.d("ADD",id+"");



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDate.setText(year+"/"+month+"/"+dayOfMonth);
    }
}
