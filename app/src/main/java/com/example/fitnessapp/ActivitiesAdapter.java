package com.example.fitnessapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.MyViewHolder> {

    private ArrayList<ActivitiesModel> myActivities = new ArrayList<ActivitiesModel>();

    public ActivitiesAdapter(ArrayList<ActivitiesModel> myActivities) {
        this.myActivities = myActivities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.health_activity_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.activity.setText(myActivities.get(position).getActivity());
        holder.date.setText(myActivities.get(position).getDate());
        holder.time.setText(myActivities.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return myActivities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView activity, date, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activity = itemView.findViewById(R.id.activityName);
            date = itemView.findViewById(R.id.activityDate);
            time = itemView.findViewById(R.id.activityTime);
        }
    }
}
