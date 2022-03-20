package com.qlshopquanaonhom6.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.activity.GiohangActivity;
import com.qlshopquanaonhom6.shoponline.activity.MainActivity;
import com.qlshopquanaonhom6.shoponline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayListgiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arrayListgiohang) {
        this.context = context;
        this.arrayListgiohang = arrayListgiohang;
    }

    @Override
    public int getCount() {
        return arrayListgiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListgiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class Viewholder{
       public TextView txttengiohang,txtgiagiohang;
       public ImageView imggiohang;
       public Button btnminus,btnvalues,btnplus;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Viewholder viewholder = null;
        if (view ==null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewholder.txttengiohang = view.findViewById(R.id.txttengiohang);
            viewholder.txtgiagiohang = view.findViewById(R.id.txtgiagiohang);
            viewholder.imggiohang = view.findViewById(R.id.imagegiohang);
            viewholder.btnminus = view.findViewById(R.id.btnminus);
            viewholder.btnvalues = view.findViewById(R.id.btnvalues);
            viewholder.btnplus = view.findViewById(R.id.btnplus);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewholder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewholder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+"Đ");
        Picasso.get().load(giohang.getHinhsp())
                .placeholder(R.drawable.laod)
                .error(R.drawable.loadq)
                .into(viewholder.imggiohang);
        viewholder.btnvalues.setText(giohang.getSoluongsp()+ "");
        int sl = Integer.parseInt(viewholder.btnvalues.getText().toString());
        if (sl >=20){
            viewholder.btnplus.setVisibility(View.INVISIBLE);//nut mo di
            viewholder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl <=1){
            viewholder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl >=1){
            viewholder.btnminus.setVisibility(View.VISIBLE);
            viewholder.btnplus.setVisibility(View.VISIBLE);
        }
        Viewholder finalViewholder = viewholder;
        viewholder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewholder.btnvalues.getText().toString())+1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht  = MainActivity.manggiohang.get(position).getGiasp();

                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewholder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+"Đ");
                GiohangActivity.EvenUltil();
                if (slmoinhat >19){
                    finalViewholder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewholder.btnminus.setVisibility(View.VISIBLE);
                    finalViewholder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewholder.btnminus.setVisibility(View.VISIBLE);
                    finalViewholder.btnplus.setVisibility(View.VISIBLE);
                    finalViewholder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewholder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewholder.btnvalues.getText().toString())-1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht  = MainActivity.manggiohang.get(position).getGiasp();

                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewholder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+"Đ");
                GiohangActivity.EvenUltil();
                if (slmoinhat <2){
                    finalViewholder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewholder.btnplus.setVisibility(View.VISIBLE);
                    finalViewholder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewholder.btnminus.setVisibility(View.VISIBLE);
                    finalViewholder.btnplus.setVisibility(View.VISIBLE);
                    finalViewholder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return view;
    }
}
