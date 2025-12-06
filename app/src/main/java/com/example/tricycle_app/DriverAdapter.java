package com.example.tricycle_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {

    private List<Driver> driverList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Driver driver);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DriverAdapter(Context context, List<Driver> driverList) {
        this.context = context;
        this.driverList = driverList;
    }

    public void updateList(List<Driver> newList) {
        this.driverList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Driver d = driverList.get(position);
        holder.tvName.setText(d.getName());
        holder.tvPhone.setText(d.getPhone());

        // --- COLOR LOGIC ---
        if (d.getStatus().equalsIgnoreCase("Verified")) {
            holder.viewStatus.setBackgroundResource(R.drawable.bg_circle_green);
        } else if (d.getStatus().equalsIgnoreCase("Rejected")) {
            holder.viewStatus.setBackgroundResource(R.drawable.bg_circle_red);
        } else {
            // Default to Pending
            holder.viewStatus.setBackgroundResource(R.drawable.bg_circle_orange);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(d);
        });
    }

    @Override
    public int getItemCount() { return driverList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        View viewStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            viewStatus = itemView.findViewById(R.id.viewStatus);
        }
    }
}