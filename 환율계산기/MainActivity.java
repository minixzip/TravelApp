package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private String[] currencyList = {"KRW", "USD", "EUR", "CAD","JPY"};
    public TextView et_from;
    public TextView tv_to;
    private Button btn_exchange;
    private String[] fromto = new String[2];

    private TextView tv_test;

    double currencyRate = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);

        et_from = findViewById(R.id.et_from);
        tv_to = findViewById(R.id.tv_to);

        btn_exchange = findViewById(R.id.btn_exchange);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                currencyList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromto[0] = currencyList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromto[1] = currencyList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // doInBackground의 인자로 들어갈 fromto 배열을 execute 에 넣어준다.
                    currencyRate = new Task().execute(fromto).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                double input = Double.parseDouble(et_from.getText().toString());
                double result = Math.round(input * currencyRate * 100.0) / 100.0; // 소수점 두자리에서 자르기 위함

                tv_to.setText(Double.toString(result));


            }
        });
    }
}