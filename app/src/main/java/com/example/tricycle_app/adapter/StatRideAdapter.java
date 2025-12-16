package com.example.tricycle_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Ride;

import java.util.List;

public class StatRideAdapter extends RecyclerView.Adapter<StatRideAdapter.ViewHolder> {

    private List<Ride> rideList;
    private Context context;

    public StatRideAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    public void updateList(List<Ride> list) {
        this.rideList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CHANGED: Now inflates 'item_admin_stat_ride' to show Driver info
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_stat_ride, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride r = rideList.get(position);

        holder.tvDate.setText(r.getDate() + " • " + r.getTime());
        holder.tvPassenger.setText(r.getPassenger());

        // NEW: Bind Driver Name
        holder.tvDriver.setText(r.getDriver());

        holder.tvFare.setText("₱" + r.getTotalFare());
        holder.tvStatus.setText(r.getStatus());

        // Color Logic for Status Pill
        if ("Completed".equalsIgnoreCase(r.getStatus())) {
            holder.tvStatus.setBackgroundResource(R.drawable.bg_pill_green);
            holder.tvStatus.setTextColor(Color.WHITE);
        }
        else if ("Cancelled".equalsIgnoreCase(r.getStatus())) {
            holder.tvStatus.setBackgroundResource(R.drawable.bg_pill_red);
            holder.tvStatus.setTextColor(Color.WHITE);
        }
        else {
            holder.tvStatus.setBackgroundResource(R.drawable.bg_pill_grey);
            holder.tvStatus.setTextColor(Color.parseColor("#61768A"));
        }
    }

    @Override
    public int getItemCount() { return rideList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Added tvDriver
        TextView tvDate, tvPassenger, tvDriver, tvFare, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPassenger = itemView.findViewById(R.id.tvPassenger);
            tvDriver = itemView.findViewById(R.id.tvDriver); // NEW
            tvFare = itemView.findViewById(R.id.tvFare);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}