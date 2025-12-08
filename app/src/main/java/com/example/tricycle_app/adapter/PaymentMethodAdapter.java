package com.example.tricycle_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tricycle_app.R;
import com.example.tricycle_app.model.PaymentMethod;
import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {

    private List<PaymentMethod> methods;
    private OnItemActionListener listener;
    private boolean isSelectionMode;
    private int selectedPosition = -1;

    // Updated Interface
    public interface OnItemActionListener {
        void onItemClick(PaymentMethod method);
        void onDeleteClick(PaymentMethod method);
    }

    public PaymentMethodAdapter(List<PaymentMethod> methods, boolean isSelectionMode, OnItemActionListener listener) {
        this.methods = methods;
        this.isSelectionMode = isSelectionMode;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentMethod method = methods.get(position);

        holder.tvProvider.setText(method.getProvider());

        // --- Visual Setup ---
        boolean isDefaultMethod = false;
        if (method.getProvider().equalsIgnoreCase("Cash")) {
            isDefaultMethod = true;
            holder.tvNumber.setText("Default");
            holder.imgIcon.setImageResource(R.drawable.money);
        } else if (method.getProvider().equalsIgnoreCase("Wallet")) {
            isDefaultMethod = true;
            holder.tvNumber.setText(method.getPhoneNumber());
            holder.imgIcon.setImageResource(R.drawable.payout);
        } else {
            String num = method.getPhoneNumber();
            holder.tvNumber.setText(num.length() > 4 ? "**** " + num.substring(num.length() - 4) : num);

            if(method.getProvider().equalsIgnoreCase("GCash")) holder.imgIcon.setImageResource(R.drawable.gcash);
            else if(method.getProvider().equalsIgnoreCase("PayMaya")) holder.imgIcon.setImageResource(R.drawable.maya);
            else holder.imgIcon.setImageResource(R.drawable.card);
        }

        // --- Mode Logic ---
        if (isSelectionMode) {
            // Select Mode: Hide Edit & Delete
            holder.imgEdit.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);

            // Highlight logic
            if (selectedPosition == position) {
                holder.itemContainer.setBackgroundResource(R.drawable.bg_rounded_border);
                holder.itemContainer.setAlpha(1.0f);
            } else {
                holder.itemContainer.setBackgroundResource(R.drawable.bg_rounded_light_grey);
                holder.itemContainer.setAlpha(0.7f);
            }

            holder.itemContainer.setOnClickListener(v -> {
                int previous = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previous);
                notifyItemChanged(selectedPosition);
                listener.onItemClick(method);
            });

        } else {
            // Settings Mode: Show Edit & Delete (unless default)
            holder.itemContainer.setBackgroundResource(R.drawable.bg_rounded_light_grey);
            holder.itemContainer.setAlpha(1.0f);

            if (isDefaultMethod) {
                holder.imgEdit.setVisibility(View.GONE);
                holder.imgDelete.setVisibility(View.GONE);
                holder.itemContainer.setOnClickListener(null);
            } else {
                holder.imgEdit.setVisibility(View.VISIBLE);
                holder.imgDelete.setVisibility(View.VISIBLE);

                // Click main body to Edit
                holder.itemContainer.setOnClickListener(v -> listener.onItemClick(method));

                // Click trash icon to Delete
                holder.imgDelete.setOnClickListener(v -> listener.onDeleteClick(method));
            }
        }
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }

    public PaymentMethod getSelectedMethod() {
        if (selectedPosition != -1 && selectedPosition < methods.size()) {
            return methods.get(selectedPosition);
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProvider, tvNumber;
        ImageView imgIcon, imgEdit, imgDelete;
        LinearLayout itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProvider = itemView.findViewById(R.id.tvProvider);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            itemContainer = itemView.findViewById(R.id.itemContainer);
        }
    }
}