package com.example.tricycle_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder> {

    private List<Passenger> passengerList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PassengerAdapter(Context context, List<Passenger> passengerList) {
        this.context = context;
        this.passengerList = passengerList;
    }

    // --- NEW METHOD TO UPDATE LIST ---
    public void updateList(List<Passenger> newList) {
        this.passengerList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_passenger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Passenger p = passengerList.get(position);
        holder.tvName.setText(p.getName());
        holder.tvPhone.setText(p.getPhone());

        if (p.isSuspended()) {
            holder.viewStatus.setBackgroundResource(R.drawable.bg_circle_red);
        } else {
            holder.viewStatus.setBackgroundResource(R.drawable.bg_circle_green);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() { return passengerList.size(); }

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