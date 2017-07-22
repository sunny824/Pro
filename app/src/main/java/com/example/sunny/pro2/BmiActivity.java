package com.example.sunny.pro2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BmiActivity extends AppCompatActivity {

    private Button btncal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        btncal = (Button) findViewById(R.id.btn_cal);
        btncal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText w = (EditText) findViewById(R.id.weight);
                String wstr = w.getText().toString();
                EditText h = (EditText)findViewById(R.id.hight);
                String hstr= w.getText().toString();

                float hf = Integer.parseInt(wstr);
                float wf = Integer.parseInt(hstr);
                float bmi = wf/(hf*hf);
                String bmistr= String.valueOf(bmi);
                TextView result = (TextView)findViewById(R.id.result);
                result.setText(bmistr);

            }
        });

    }


}
