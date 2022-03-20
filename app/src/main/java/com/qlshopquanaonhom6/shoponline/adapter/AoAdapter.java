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

public class AoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydienthoai;

    public AoAdapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arraydienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
     public class ViewHolder{
           public TextView txttendienthoai,txtgiadienthoai,txtmotadienthoai;
           public ImageView imagemotadienthoai;
     }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txttendienthoai = view.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = view.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imagemotadienthoai = view.findViewById(R.id.imagedienthoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Gía:" +decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
           viewHolder.txtmotadienthoai.setMaxLines(2);
           viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
           viewHolder.txtmotadienthoai.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.laod)
                .error(R.drawable.loadq)
                .into(viewHolder.imagemotadienthoai);
        return view;
    }
}
