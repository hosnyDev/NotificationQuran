package com.hosnydev.quran.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.hosnydev.quran.R;
import com.hosnydev.quran.model.Model;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuran extends RecyclerView.Adapter<AdapterQuran.viewHolder> implements Filterable {

    private List<Model> list;
    private final List<Model> listFiltter;
    private int textSize, txtColor, recBack;

    public AdapterQuran(List<Model> list) {
        this.list = list;
        listFiltter = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.format, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);


        final String name = list.get(i).getAyaQuran();
        viewHolder.setName(name);

        viewHolder.aya.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        viewHolder.aya.setTextColor(txtColor);
        viewHolder.cardBack.setCardBackgroundColor(recBack);

    }

    public void setTextSizes(int textSize) {
        this.textSize = textSize;
        notifyDataSetChanged();
    }

    public void setTextColor(int txtColor) {
        this.txtColor = txtColor;
        notifyDataSetChanged();
    }

    public void setBackColor(int backColor) {
        this.recBack = backColor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String pattern = constraint.toString().toLowerCase().trim();
            if (pattern.isEmpty()) {
                list = listFiltter;
            } else {
                List<Model> filteredList = new ArrayList<>();

                for (Model tube : listFiltter) {
                    if (tube.getAyaQuran().toLowerCase().contains(pattern)) {
                        filteredList.add(tube);
                    }
                }
                list = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = list;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<Model>) results.values;
            notifyDataSetChanged();
        }
    };


    class viewHolder extends RecyclerView.ViewHolder {

        private final TextView aya;
        private final CardView cardBack;


        viewHolder(@NonNull View itemView) {
            super(itemView);

            aya = itemView.findViewById(R.id.aya);
            cardBack = itemView.findViewById(R.id.cardBack);

        }

        void setName(String name) {
            aya.setText(name);
        }
    }


}
