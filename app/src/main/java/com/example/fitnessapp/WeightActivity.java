package com.example.fitnessapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.scales.Linear;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class WeightActivity extends AppCompatActivity {

    FloatingActionButton newWeightButton;
    ArrayList<WeightModel> myWeightValues = new ArrayList<WeightModel>();

    private List<CustomerModel> customerData = ProfileCreation.customerData;
    private String customerEmail = ProfileCreation.customerEmail;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_weight);

        newWeightButton = findViewById(R.id.new_weight);
        newWeightButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddWeight.class);
            startActivity(intent);
        });

        for(int i = 0; i < customerData.size(); i++) {
            if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                userID = customerData.get(i).user_id;
            }
        }

        Cursor cursor = new SQLhelper(getApplicationContext()).getWeightData(userID);
        while(cursor.moveToNext()) {
            WeightModel weightModel = new WeightModel(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            myWeightValues.add(weightModel);
        }

//        Log.e("test", String.valueOf(myWeightValues.get(0).weightDateValue));

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();

        Linear scalesLinearCalories = Linear.instantiate();
        //scalesLinearCalories.minimum(0);
        //scalesLinearCalories.maximum(3000);
        scalesLinearCalories.ticks("{ interval: 50 }");

        Linear scalesLinearWeight = Linear.instantiate();
        //scalesLinearWeight.minimum(0);
        //scalesLinearWeight.maximum(300);
        scalesLinearWeight.ticks("{ interval: 10 }");

        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//        cartesian.title("Your Weight Trend");
        cartesian.yAxis(0).title("Weight (Pounds)").scale(scalesLinearWeight);
        cartesian.yAxis(1).title("Calories").orientation("right").scale(scalesLinearCalories);
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        if(myWeightValues.isEmpty()) {
            System.out.println("No weight data!");
        } else {
            List<DataEntry> seriesData = new ArrayList<>();
            List<DataEntry> seriesData2 = new ArrayList<>();

            for (int i = 0; i < myWeightValues.size(); i++) {
                seriesData.add(new CustomDataEntry(String.valueOf(myWeightValues.get(i).weightDateValue), Integer.parseInt(myWeightValues.get(i).weightValue)));
                seriesData2.add(new CustomDataEntry(String.valueOf(myWeightValues.get(i).weightDateValue), Integer.parseInt(myWeightValues.get(i).calorieValue)));
            }

            Set set = Set.instantiate();
            set.data(seriesData);
            Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
            Set set2 = Set.instantiate();
            set2.data(seriesData2);
            Mapping series2Mapping = set2.mapAs("{ x: 'x', value: 'value' }");

            Line series1 = cartesian.line(series1Mapping);
            series1.yScale(scalesLinearWeight);
            series1.name("Weight");
            series1.hovered().markers().enabled(true);
            series1.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series1.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5d)
                    .offsetY(5d);

            Line series2 = cartesian.line(series2Mapping);
            series2.yScale(scalesLinearCalories);
            series2.name("Calories");
            series2.hovered().markers().enabled(true);
            series2.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series2.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5d)
                    .offsetY(5d);

            cartesian.legend().enabled(true);
            cartesian.legend().fontSize(13d);
            cartesian.legend().padding(0d, 0d, 10d, 0d);

            anyChartView.setChart(cartesian);
        }
    }
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }
    }

    public class ValueDataEntry extends DataEntry {

        public ValueDataEntry(String x, Number value) {
            setValue("x", x);
            setValue("value", value);
        }
    }

}
