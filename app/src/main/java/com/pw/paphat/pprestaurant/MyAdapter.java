package com.pw.paphat.pprestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by paphat on 4/10/2016 AD.
 */
public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] foodStrings, priceStrings, iconStrings;

    public MyAdapter(Context context,
                     String[] foodStrings,
                     String[] priceStrings,
                     String[] iconStrings) {//constructor , setter
        this.context = context;
        this.foodStrings = foodStrings;
        this.priceStrings = priceStrings;
        this.iconStrings = iconStrings;



    }   //Constructor


    @Override
    public int getCount() {
        return foodStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {//customize listview!!!

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.my_listview, viewGroup, false);

        //For TextView
        TextView foodTextView = (TextView) view1.findViewById(R.id.foodnametextview);
        foodTextView.setText(foodStrings[i]);

        TextView priceTextView = (TextView) view1.findViewById(R.id.foodpricetextview);
        priceTextView.setText(priceStrings[i]);

        //For Image View
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.iconimageview);

        Picasso.with(context).load(iconStrings[i]).resize(130,130).into(iconImageView);

        return view1;
    }
}   //Main Class
