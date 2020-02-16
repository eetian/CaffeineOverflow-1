package com.caffeineoverflow.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;

import java.util.List;


public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {

    private List<Result> results;

    public ResultListAdapter(List<Result> results) {

        this.results = results;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvTitle;
        TextView tvMinutes;
        TextView tvServings;


        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvId = itemView.findViewById(R.id.tvId);
            tvMinutes = itemView.findViewById(R.id.tvMinutes);
            tvServings = itemView.findViewById(R.id.tvServings);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.tvTitle.setText(result.getTitle());
        holder.tvId.setText(result.getId());
        holder.tvMinutes.setText(String.valueOf(result.getReadyInMinutes()));
        holder.tvServings.setText(result.getServings());

    }

    @Override
    public int getItemCount() {
        if (results != null) {
            return results.size();
        } else {
            return 0;
        }
    }
}
