package com.example.cuahangthietbonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    ArrayList<Sanpham> arraylaptop;
    Context context;

    public LaptopAdapter(ArrayList<Sanpham> arraydienthoai, Context context) {
        this.arraylaptop = arraydienthoai;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

        public class ViewHolder {
            TextView txttenlaptop, txtgialaptop, txtmotalaptop;
            ImageView imglaptop;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LaptopAdapter.ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_laptop, null);
                viewHolder.txttenlaptop = view.findViewById(R.id.textviewtenlaptop);
                viewHolder.txtgialaptop = view.findViewById(R.id.textviewgialaptop);
                viewHolder.txtmotalaptop = view.findViewById(R.id.textviewmotalaptop);
                viewHolder.imglaptop = view.findViewById(R.id.imgeviewlaptop);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Sanpham sanpham = (Sanpham) getItem(position);
            viewHolder.txttenlaptop.setText(sanpham.getTensanpham());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            viewHolder.txtgialaptop.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
            viewHolder.txtmotalaptop.setMaxLines(2);
            viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
            viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());
            Picasso.get().load(sanpham.getHinhanhsanpham()).into(viewHolder.imglaptop);
            return view;

        }
    }