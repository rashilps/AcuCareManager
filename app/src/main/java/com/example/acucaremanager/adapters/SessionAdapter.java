package com.example.acucaremanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.SessionPojo;

import java.util.ArrayList;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {

    private ArrayList<SessionPojo> sessionList;

    public SessionAdapter(ArrayList<SessionPojo> sessionList) {
        this.sessionList = sessionList;
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        SessionPojo session = sessionList.get(position);
        holder.tvSessionClientName.setText("Client: " + session.getClientName());
        holder.tvSessionDate.setText("Date: " + session.getDate());
        holder.tvSessionTime.setText("Time: " + session.getTime());
        holder.tvSessionFee.setText("Fee: ₹" + session.getFee());
        holder.tvSessionNotes.setText("Notes: " + (session.getNotes().isEmpty() ? "None" : session.getNotes()));
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public static class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView tvSessionClientName, tvSessionDate, tvSessionTime, tvSessionFee, tvSessionNotes;

        public SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSessionClientName = itemView.findViewById(R.id.tvSessionClientName);
            tvSessionDate = itemView.findViewById(R.id.tvSessionDate);
            tvSessionTime = itemView.findViewById(R.id.tvSessionTime);
            tvSessionFee = itemView.findViewById(R.id.tvSessionFee);
            tvSessionNotes = itemView.findViewById(R.id.tvSessionNotes);
        }
    }
}
