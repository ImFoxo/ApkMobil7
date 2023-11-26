package com.example.zad7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView sensorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        sensorTextView = findViewById(R.id.sensor_label);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        int type = getIntent().getIntExtra(SensorActivity.KEY_EXTRA_SENSOR_TYPE, -1);
        switch (type)
        {
            case Sensor.TYPE_LIGHT:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                Intent intent = new Intent(SensorDetailsActivity.this, LocationActivity.class);
                startActivity(intent);
                break;
            default:
                sensorTextView.setText(R.string.missing_sensor);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sensor != null)
        {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float curretValue = event.values[0];

        switch (sensorType)
        {
            case Sensor.TYPE_LIGHT:
                sensorTextView.setText(getResources().getString(R.string.light_sensor_label, curretValue));
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                sensorTextView.setText("Asdads");
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}