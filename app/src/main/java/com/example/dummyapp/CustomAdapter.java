package com.example.dummyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private String[] dataSet;
    private String[] idSet;
    private List<City> cities;
    private Context context;

    public void setmDataSet(String[] mDataSet) {
        this.dataSet = mDataSet;
    }

    public void setData(List<City> cities) {
        this.cities = cities;
        this.dataSet = cities.stream().map(city -> city.nameFr).collect(Collectors.toList()).toArray(new String[0]);
        this.idSet = cities.stream().map(city -> city.code).collect(Collectors.toList()).toArray(new String[0]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private FrameLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.txt_city);
            layout = (FrameLayout) itemView.findViewById(R.id.itemLayout);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public CustomAdapter(Context ctx) {
        this.context = ctx;
    }

    public CustomAdapter(Context ctx, String[] dataSet) {
        this(ctx);
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextView().setText(dataSet[position]);
        int index = position;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                City city = cities.stream().filter(c -> c.code.equalsIgnoreCase(idSet[index]))
                        .findAny().orElse(null);

                Intent intent = new Intent(context, CityActivity.class);
                intent.putExtra("code", city.code);
                intent.putExtra("nameEn", city.nameEn);
                intent.putExtra("nameFr", city.nameFr);
                intent.putExtra("province", city.province);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
