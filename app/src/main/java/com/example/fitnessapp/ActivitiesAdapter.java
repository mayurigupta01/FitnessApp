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
        holder.activityName.setText(myActivities.get(position).getActivity());
        holder.activityDate.setText(myActivities.get(position).getActivityDate());
        holder.activityTime.setText(myActivities.get(position).getActivityTime());
    }

    @Override
    public int getItemCount() {
        return myActivities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView activityName, activityDate, activityTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activityName);
            activityDate = itemView.findViewById(R.id.activityDate);
            activityTime = itemView.findViewById(R.id.activityTime);
        }
    }
}
