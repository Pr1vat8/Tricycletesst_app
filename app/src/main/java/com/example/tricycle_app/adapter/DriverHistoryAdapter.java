package com.example.tricycle_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Ride;
import java.util.List;

public class DriverHistoryAdapter extends RecyclerView.Adapter<DriverHistoryAdapter.ViewHolder> {

    private List<Ride> rides;

    public DriverHistoryAdapter(List<Ride> rides) {
        this.rides = rides;
    }

    public void updateData(List<Ride> newRides) {
        this.rides = newRides;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Uses your item_stat_ride.xml layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stat_ride, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride ride = rides.get(position);

        // Bind Data
        holder.tvDate.setText(ride.getDate());
        holder.tvStatus.setText(ride.getStatus());

        // --- STATUS COLOR LOGIC ---
        if ("Completed".equalsIgnoreCase(ride.getStatus())) {
            holder.tvStatus.setBackgroundResource(R.drawable.bg_pill_green);
        } else if ("Cancelled".equalsIgnoreCase(ride.getStatus())) {
            // UPDATED: Now uses the red pill
            holder.tvStatus.setBackgroundResource(R.drawable.bg_pill_red);
        } else {
            // Default/Pending uses blue
            holder.tvStatus.setBackgroundResource(R.drawable.bg_pill_blue);
        }

        // Passenger Name
        String pName = (ride.getPassenger() != null) ? ride.getPassenger() : "Unknown";
        holder.tvPassenger.setText(pName);

        // Fare
        holder.tvFare.setText("₱" + ride.getTotalFare());
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvStatus, tvPassenger, tvFare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvPassenger = itemView.findViewById(R.id.tvPassenger);
            tvFare = itemView.findViewById(R.id.tvFare);
        }
    }
}