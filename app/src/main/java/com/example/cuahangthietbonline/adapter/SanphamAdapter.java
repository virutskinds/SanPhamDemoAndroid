package com.example.cuahangthietbonline.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangthietbonline.R;
import com.example.cuahangthietbonline.model.Sanpham;
import com.example.cuahangthietbonline.util.ClickItemSanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanphamAdapter<Contact> extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> implements Filterable {
    ArrayList<Sanpham> arraysanpham;
    ClickItemSanPham clickItemSanPham;
    public SanphamAdapter(ArrayList<Sanpham> arraysanpham, ClickItemSanPham clickItemSanPham) {
        this.arraysanpham = arraysanpham;
        this.clickItemSanPham = clickItemSanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_sanpham,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int i) {
        final Sanpham sanpham = arraysanpham.get(i);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .into(holder.imghinhanhsanpham);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clickItemSanPham.onClickItemSanPham(sanpham);
            }
        });


    }
    @Override
    public int getItemCount()    {
        return arraysanpham.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    arraysanpham = (ArrayList<Sanpham>) arraysanpham;
                } else {
                    List<Sanpham> filteredList = new ArrayList<>();
                    for (Sanpham row : arraysanpham) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTensanpham().toLowerCase().contains(charString.toLowerCase()) || row.getTensanpham().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    arraysanpham= (ArrayList<Sanpham>) filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = arraysanpham;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arraysanpham = (ArrayList<Sanpham>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    public static class ItemHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public ImageView imghinhanhsanpham;
        public TextView txttensanpham,txtgiasanpham;

        public ItemHolder(View itemView)
        {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview);
            imghinhanhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham=itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham=itemView.findViewById(R.id.textviewtensanpham);
        }

    }


    }
