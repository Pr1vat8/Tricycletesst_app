package com.example.tricycle_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tricycle_app.R;
import com.example.tricycle_app.model.Payout;

import java.util.List;

public class PayoutAdapter extends RecyclerView.Adapter<PayoutAdapter.ViewHolder> {

    private Context context;
    private List<Payout> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Payout payout);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PayoutAdapter(Context context, List<Payout> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CHANGED: Uses the specific admin row layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_payout_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payout payout = list.get(position);
        holder.tvName.setText(payout.getDriverName());
        holder.tvAmount.setText("₱" + payout.getAmount());

        // Bind Payment Method
        if (holder.tvPaymentMethod != null) {
            holder.tvPaymentMethod.setText("• " + payout.getPaymentMethod());
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(payout);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAmount, tvPaymentMethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDriverName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
        }
    }
}