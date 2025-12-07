package com.example.tricycle_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PayoutAdapter extends RecyclerView.Adapter<PayoutAdapter.ViewHolder> {

    private List<Payout> payoutList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onActionClick(Payout payout);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PayoutAdapter(Context context, List<Payout> payoutList) {
        this.context = context;
        this.payoutList = payoutList;
    }

    public void updateList(List<Payout> list) {
        this.payoutList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payout p = payoutList.get(position);
        holder.tvName.setText("Driver: " + p.getDriverName());
        holder.tvAmount.setText("Amount Requested: ₱" + p.getAmount());

        if (p.getStatus().equalsIgnoreCase("Paid")) {
            holder.tvAction.setText("Paid");
            holder.tvAction.setTextColor(Color.parseColor("#088738")); // Green
            holder.btnAction.setBackgroundResource(R.drawable.bg_pill_grey);
            holder.btnAction.setClickable(false); // Disable click if paid
        } else {
            holder.tvAction.setText("Mark as Paid");
            holder.tvAction.setTextColor(Color.parseColor("#0D141C"));
            holder.btnAction.setClickable(true);
            holder.btnAction.setOnClickListener(v -> {
                if (listener != null) listener.onActionClick(p);
            });
        }
    }

    @Override
    public int getItemCount() { return payoutList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAmount, tvAction;
        LinearLayout btnAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDriverName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvAction = itemView.findViewById(R.id.tvAction);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}