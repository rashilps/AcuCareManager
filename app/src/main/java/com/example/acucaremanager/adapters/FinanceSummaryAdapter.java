package com.example.acucaremanager.homescreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.acucaremanager.R;
import java.util.ArrayList;

public class FinanceSummaryAdapter extends RecyclerView.Adapter<FinanceSummaryAdapter.FinanceViewHolder> {

    private ArrayList<String> clientNames;
    private ArrayList<Integer> sessionCounts;
    private ArrayList<Integer> totalEarnings;

    public FinanceSummaryAdapter(ArrayList<String> clientNames, ArrayList<Integer> sessionCounts, ArrayList<Integer> totalEarnings) {
        this.clientNames = clientNames;
        this.sessionCounts = sessionCounts;
        this.totalEarnings = totalEarnings;
    }

    @NonNull
    @Override
    public FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finance_summary, parent, false);
        return new FinanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceViewHolder holder, int position) {
        holder.tvClientNameFinance.setText(clientNames.get(position));
        holder.tvSessionCountFinance.setText(sessionCounts.get(position) + " Sessions");
        holder.tvClientTotalAmount.setText("₹" + totalEarnings.get(position));
    }

    @Override
    public int getItemCount() {
        return clientNames.size();
    }

    public static class FinanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvClientNameFinance, tvSessionCountFinance, tvClientTotalAmount;

        public FinanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientNameFinance = itemView.findViewById(R.id.tvClientNameFinance);
            tvSessionCountFinance = itemView.findViewById(R.id.tvSessionCountFinance);
            tvClientTotalAmount = itemView.findViewById(R.id.tvClientTotalAmount);
        }
    }
}
