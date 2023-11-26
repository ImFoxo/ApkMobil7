package com.example.zad7;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    public static final String KEY_EXTRA_SENSOR_TYPE = "SensorActivity.sensor";
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        builder = new AlertDialog.Builder(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (adapter == null)
        {
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }

    public class SensorHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener
    {
        private Sensor sensor;
        private TextView nameTextView;
        private ImageView sensorImageView;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            nameTextView = itemView.findViewById(R.id.sensor_item_name);
            sensorImageView = itemView.findViewById(R.id.sensor_item_image);

        }

        public void bind(Sensor sensor)
        {
            this.sensor = sensor;
            nameTextView.setText(sensor.getName());
            sensorImageView.setImageResource(R.drawable.ic_sensor_foreground);
        }

        @Override
        public boolean onLongClick(View v) {
                builder.setTitle("Opis sensora")
                        .setMessage("Producent: " + sensor.getVendor() + "\nMaksymalna wartość: " + sensor.getMaximumRange() + "\nTyp: " + sensor.getType())
                        .setCancelable(true)
                        .setNeutralButton("Powrót", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return false;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
            intent.putExtra(KEY_EXTRA_SENSOR_TYPE, sensor.getType());
            startActivity(intent);
        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder>
    {
        private List<Sensor> sensors;
        public SensorAdapter(List<Sensor> sensors)
        {
            this.sensors = sensors;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SensorActivity.this);
            return new SensorHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            Sensor sensor = sensors.get(position);
            holder.bind(sensor);
        }

        @Override
        public int getItemCount() {
            return sensors.size();
        }

    }

}