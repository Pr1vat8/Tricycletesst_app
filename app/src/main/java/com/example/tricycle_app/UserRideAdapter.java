package com.example.tricycle_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserRideAdapter extends RecyclerView.Adapter<UserRideAdapter.ViewHolder> {

    private List<Ride> rideList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Ride ride);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserRideAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reuse the existing layout, but we will populate it differently
        View view = LayoutInflater.from(context).inflate(R.layout.item_ride, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride r = rideList.get(position);

        // Format Date
        String formattedDate = r.getDate();
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.US);
            Date date = inputFormat.parse(r.getDate());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            formattedDate = outputFormat.format(date);
        } catch (Exception e) { e.printStackTrace(); }

        holder.tvDate.setText(formattedDate + " • " + r.getTime());

        // --- HIDE ID HERE ---
        // Only show Passenger, Driver, and Status
        String details = "Driver: " + r.getDriver() + "\n" +
                "Status: " + r.getStatus();

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