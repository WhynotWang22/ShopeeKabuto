package com.qlshopquanaonhom6.shoponline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VayAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraylaptop;

    public VayAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
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
    public class ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imagemotalaptop;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop = view.findViewById(R.id.textviewtenlaptop);
            viewHolder.txtgialaptop = view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imagemotalaptop = view.findViewById(R.id.imagelatop);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);//dinh nghia lai chuoi
        viewHolder.txttenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Gía:" +decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
        viewHolder.txtmotalaptop.setMaxLines(2);//mo ta chi dc 2 dong
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.laod)
                .error(R.drawable.loadq)
                .into(viewHolder.imagemotalaptop);
        return view;
    }
}
