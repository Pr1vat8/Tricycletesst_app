package com.example.tricycle_app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Ride;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {

    private List<Ride> rideList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ride ride);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RideAdapter(Context context, List<Ride> rideList) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_ride, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride r = rideList.get(position);

        // --- 1. Format Date ---
        String formattedDate = r.getDate();
        try {
            // Parse "July 26 2024" -> "26/07/2024"
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
            Date date = inputFormat.parse(r.getDate());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set Date Header
        holder.tvDate.setText(formattedDate + " • " + r.getTime());

        // --- 2. Format Details (Multi-line) ---
        // Using \n ensures it doesn't get cut off
        String details = "ID: " + r.getRideId() + "\n" +
                "Passenger: " + r.getPassenger() + "\n" +
                "Driver: " + r.getDriver();

        holder.tvDetails.setText(details);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(r);
        });
    }

    @Override
    public int getItemCount() { return rideList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDetails = itemView.findViewById(R.id.tvDetails);
        }
    }
}