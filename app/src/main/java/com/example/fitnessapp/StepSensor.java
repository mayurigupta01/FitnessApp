package com.example.fitnessapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//public class StepSensor extends AppCompatActivity implements SensorEventListener {
public class StepSensor extends AppCompatActivity {

    FloatingActionButton newStepsButton;
    ArrayList<StepModel> myStepValues = new ArrayList<StepModel>();

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = ProfileCreation.customerEmail;
    int userID;

    SensorManager mSensorManager = null;
    Sensor mStepSensor;
    int steps = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_steps);

//        PackageManager manager = getPackageManager();
//        boolean hasStepDetector = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
//        Log.e("Step Detector?", String.valueOf(hasStepDetector));
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
//            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
//        }
//
//        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//        mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_NORMAL);

        newStepsButton = findViewById(R.id.new_steps);
        newStepsButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddSteps.class);
            startActivity(intent);
        });

        for (int i = 0; i < customerData.size(); i++) {
            if (customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                userID = customerData.get(i).user_id;
            }
        }

        Cursor cursor = new SQLhelper(getApplicationContext()).getStepData(userID);
        while (cursor.moveToNext()) {
            StepModel stepModel = new StepModel(cursor.getString(1), cursor.getString(2));
            myStepValues.add(stepModel);
        }

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        if (myStepValues.isEmpty()) {
            System.out.println("No steps data!");
        } else {
            List<DataEntry> data = new ArrayList<>();

            for (int i = 0; i < myStepValues.size(); i++) {
                data.add(new ValueDataEntry(String.valueOf(myStepValues.get(i).stepsDateValue), Integer.parseInt(myStepValues.get(i).stepsValue)));
            }

            Column column = cartesian.column(data);

            column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM)
                    .offsetX(0d)
                    .offsetY(5d)
                    .format("{%Value}{groupsSeparator:}");

            cartesian.animation(true);
            cartesian.title("Your Daily Steps Over Time");

            cartesian.yScale().minimum(0d);

            cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator:}");

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
            cartesian.interactivity().hoverMode(HoverMode.BY_X);

            cartesian.xAxis(0).title("Date");
            cartesian.yAxis(0).title("Number of Steps");

            anyChartView.setChart(cartesian);
        }
    }

    public class ValueDataEntry extends DataEntry {

        public ValueDataEntry(String x, Number value) {
            setValue("x", x);
            setValue("value", value);
        }
    }

//    @Override
//    protected void onResume() {
//        // Register a listener for the sensor.
//        super.onResume();
//        mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        // Be sure to unregister the sensor when the activity pauses.
//        super.onPause();
//        mSensorManager.unregisterListener(this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//        TextView sensorReading = findViewById(R.id.sensorReading);
//        sensorReading.setText(String.format("%s steps", steps));
//        Sensor sensor = sensorEvent.sensor;
//
//        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
//            steps++;
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // Do something here if sensor accuracy changes.
//        if(sensor == mStepSensor) {
//            switch(accuracy) {
//                case 0:
//                    Toast.makeText(getApplicationContext(), "Unreliable", Toast.LENGTH_SHORT).show();
//                    break;
//                case 1:
//                    Toast.makeText(getApplicationContext(), "Low Accuracy", Toast.LENGTH_SHORT).show();
//                    break;
//                case 2:
//                    Toast.makeText(getApplicationContext(), "Medium Accuracy", Toast.LENGTH_SHORT).show();
//                    break;
//                case 3:
//                    Toast.makeText(getApplicationContext(), "High Accuracy", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    }
}
