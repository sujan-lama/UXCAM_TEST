package com.sujan.uxcam.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sujan.uxcam.R;
import com.sujan.uxcam.databinding.ItemRecentSearchBinding;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.DataViewHolder> {


    private List<String> searchList;
    private MainAdapterClickListener listener;

    public MainAdapter(List<String> searchList, MainAdapterClickListener listener) {
        this.searchList = searchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecentSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recent_search, parent, false);
        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bind(searchList.get(position));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void addData(String searchItem) {
        if (!searchList.contains(searchItem))
            searchList.add(0, searchItem);
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return searchList;
    }

    public void clearAll() {
        searchList.clear();
        notifyDataSetChanged();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        ItemRecentSearchBinding binding;

        public DataViewHolder(@NonNull ItemRecentSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String searchText) {
            binding.getRoot().setOnClickListener(v -> listener.onRecentSearchClicked(searchText));
            binding.circleText.setText(searchText.substring(0, 1).toUpperCase());
            binding.searchText.setText(searchText);
        }
    }

    public interface MainAdapterClickListener {
        void onRecentSearchClicked(String query);
    }
}
