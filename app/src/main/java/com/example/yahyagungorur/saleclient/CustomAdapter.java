package com.example.yahyagungorur.saleclient;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;



public class CustomAdapter extends BaseAdapter {
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<String> title,ArrayList<String> price,Context context) {
        this.title = title;
        this.price = price;
        this.context = context;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.simple_row,null);
        TextView tvTitle = (TextView)row.findViewById(R.id.tvTitle);
        TextView tvPrice = (TextView)row.findViewById(R.id.tvPrice);
        tvTitle.setText(title.get(i));
        tvPrice.setText(price.get(i)+" TL");

        return row;
    }
}