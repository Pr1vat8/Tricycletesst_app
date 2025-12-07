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

public class DriverRideAdapter extends RecyclerView.Adapter<DriverRideAdapter.ViewHolder> {
    private List<Ride> list;
    private Context context;
    private OnItemClickListener listener; // Listener reference

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(Ride ride);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DriverRideAdapter(Context context, List<Ride> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(List<Ride> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver_ride, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride r = list.get(position);
        holder.tvDate.setText(r.getDate() + " • " + r.getTime());
        holder.tvDetails.setText(r.getFromLocation() + " -> " + r.getToLocation());
        holder.tvPrice.setText("₱" + r.getTotalFare());

        if(r.getStatus().equalsIgnoreCase("Cancelled")) {
            holder.tvPrice.setTextColor(Color.RED);
        } else {
            holder.tvPrice.setTextColor(Color.parseColor("#0A73D9"));
        }

        // Handle Item Click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(r);
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDetails, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}