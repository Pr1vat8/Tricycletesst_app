package com.example.tricycle_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.FareLocation;

import java.util.List;

public class FareAdapter extends RecyclerView.Adapter<FareAdapter.ViewHolder> {

    private List<FareLocation> fareList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FareLocation fare);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FareAdapter(Context context, List<FareLocation> fareList) {
        this.context = context;
        this.fareList = fareList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fare, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FareLocation f = fareList.get(position);
        holder.tvName.setText(f.getName());
        holder.tvDesc.setText(f.getDescription());
        holder.tvFare.setText("₱" + f.getBaseFare());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(f);
        });
    }

    @Override
    public int getItemCount() { return fareList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvFare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvLocationName);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            tvFare = itemView.findViewById(R.id.tvCurrentFare);
        }
    }
}