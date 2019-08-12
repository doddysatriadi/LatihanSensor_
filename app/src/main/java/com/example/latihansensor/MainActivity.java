package com.example.latihansensor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private boolean color = false;
    private TextView tview;
    private long lastUpdate;
    int i=0;
    String counter ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tview = findViewById(R.id.text);
        tview.setBackgroundColor(Color.GREEN);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            jalankanAccelometer(sensorEvent);
        }
    }

    private void jalankanAccelometer(SensorEvent se) {

        float[] values = se.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualtime = se.timestamp;
        if (accelerationSquareRoot >= 2)//
        {
            i = i +1;
            if (actualtime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualtime;
            Toast.makeText(this, "ada ibu ibu gais", Toast.LENGTH_SHORT)
                    .show();
            if (color) {
                tview.setBackgroundColor(Color.BLUE);
                tview.setText(String.valueOf(i));

            }else {
                tview.setBackgroundColor(Color.DKGRAY);
                tview.setText(String.valueOf(i));
            }
            color = !color;
        }
    }

    @Override
    protected void  onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(counter, i);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //selalu panggil superclass
        super.onRestoreInstanceState(savedInstanceState);

        i = savedInstanceState.getInt(counter);
        tview.setText(String.valueOf(i));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
