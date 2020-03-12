package com.onionv2.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        counter = 0;
        final TextView textView = findViewById(R.id.textView2);
        textView.setText("Licznik: 0");

        findViewById(R.id.constr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textView.setText("Licznik: " + counter);

            }
        });
    }
}
