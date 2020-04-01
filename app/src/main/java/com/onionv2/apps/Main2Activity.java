package com.onionv2.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;

public class Main2Activity extends AppCompatActivity implements ShakeDetector.Listener  {

    int counter;
    ImageView mada;
    float delta;

    Chronometer simpleChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        counter = 0;
        final TextView textView = findViewById(R.id.textView2);
        textView.setText("Licznik: 0");
        mada = findViewById(R.id.mada);
        delta = 30f;

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

         simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.start();

        findViewById(R.id.constr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                PropertyValuesHolder pvhTranslateX = PropertyValuesHolder
                        .ofKeyframe(View.TRANSLATION_X, Keyframe.ofFloat(0f, 0),
                                Keyframe.ofFloat(.10f, -delta),
                                Keyframe.ofFloat(.26f, delta),
                                Keyframe.ofFloat(.42f, -delta),
                                Keyframe.ofFloat(.58f, delta),
                                Keyframe.ofFloat(.74f, -delta),
                                Keyframe.ofFloat(.90f, delta),
                                Keyframe.ofFloat(1f, 0f));

                ObjectAnimator animation =  ObjectAnimator.ofPropertyValuesHolder(mada, pvhTranslateX);
                animation.setDuration(750);
                animation.start();
                animation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                        Toast.makeText(getApplicationContext(), "upłynelo " + elapsedMillis + " milisekund", Toast.LENGTH_SHORT).show();
                        simpleChronometer.setBase(SystemClock.elapsedRealtime());
                        simpleChronometer.start();
                    }
                });


                counter++;
                textView.setText("Licznik: " + counter);

            }
        });
    }

    @Override
    public void hearShake() {

        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder
                .ofKeyframe(View.TRANSLATION_X, Keyframe.ofFloat(0f, 0),
                        Keyframe.ofFloat(.10f, -delta),
                        Keyframe.ofFloat(.26f, delta),
                        Keyframe.ofFloat(.42f, -delta),
                        Keyframe.ofFloat(.58f, delta),
                        Keyframe.ofFloat(.74f, -delta),
                        Keyframe.ofFloat(.90f, delta),
                        Keyframe.ofFloat(1f, 0f));

        ObjectAnimator animation =  ObjectAnimator.ofPropertyValuesHolder(mada, pvhTranslateX);
        animation.setDuration(750);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                Toast.makeText(getApplicationContext(), "upłynelo " + elapsedMillis + " milisekund", Toast.LENGTH_SHORT).show();
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.start();
            }
        });
    }
}
