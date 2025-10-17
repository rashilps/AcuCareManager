package com.example.acucaremanager.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.acucaremanager.R;
import com.example.acucaremanager.pojo.FeedbackPojo;
import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private ArrayList<FeedbackPojo> feedbackList;

    public FeedbackAdapter(ArrayList<FeedbackPojo> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackPojo feedback = feedbackList.get(position);
        holder.tvFeedbackClientName.setText("Name of client: "+feedback.getClientName());
        holder.tvFeedbackTimestamp.setText("Time of feedback: "+feedback.getTimestamp());
        holder.tvFeedbackText.setText("Feedback: "+feedback.getFeedbackText());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView tvFeedbackClientName, tvFeedbackTimestamp, tvFeedbackText;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeedbackClientName = itemView.findViewById(R.id.tvFeedbackClientName);
            tvFeedbackTimestamp = itemView.findViewById(R.id.tvFeedbackTimestamp);
            tvFeedbackText = itemView.findViewById(R.id.tvFeedbackText);
        }
    }
}
