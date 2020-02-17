package com.caffeineoverflow.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {

    private List<Result> results;
    private OnItemClickListener listener;


    public ResultListAdapter(List<Result> results, OnItemClickListener listener) {
        this.listener = listener;
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

        public void bind(final Result item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    System.out.println("I am clicked");
                    listener.onItemClick(item);
                }
            });
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
        holder.tvServings.setText(String.valueOf(result.getServings()));
        holder.bind(results.get(position), listener);
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
