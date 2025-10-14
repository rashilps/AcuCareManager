package com.example.acucaremanager.adapters;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.ClientPojo;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private ArrayList<ClientPojo> clientList;

    public ClientAdapter(ArrayList<ClientPojo> clientList) {
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        ClientPojo client = clientList.get(position);
        holder.tvName.setText("Name: "+client.getName());
        holder.tvContact.setText("Phone no: "+client.getContact());
        holder.tvNotes.setText("Notes: "+client.getNotes());
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvContact, tvNotes;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvClientName);
            tvContact = itemView.findViewById(R.id.tvClientContact);
            tvNotes = itemView.findViewById(R.id.tvClientNotes);
        }
    }
}
