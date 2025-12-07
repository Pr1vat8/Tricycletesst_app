package com.example.tricycle_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SavedPlaceAdapter extends RecyclerView.Adapter<SavedPlaceAdapter.ViewHolder> {

    private List<SavedPlace> list;
    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = -1; // -1 means no selection

    public interface OnItemClickListener {
        void onItemClick(int position, SavedPlace place);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SavedPlaceAdapter(Context context, List<SavedPlace> list) {
        this.context = context;
        this.list = list;
    }

    public void clearSelection() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedPlace p = list.get(position);
        holder.tvName.setText(p.getName());
        holder.tvAddress.setText(p.getAddress());

        // Highlight selected item
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.bg_rounded_light_grey); // Highlight color
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE); // Default
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged(); // Refresh view to show highlight
            if (listener != null) listener.onItemClick(selectedPosition, p);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPlaceName);
            tvAddress = itemView.findViewById(R.id.tvPlaceAddress);
        }
    }
}