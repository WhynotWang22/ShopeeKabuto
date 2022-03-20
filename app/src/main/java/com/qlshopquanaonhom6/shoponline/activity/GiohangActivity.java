package com.qlshopquanaonhom6.shoponline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.adapter.GiohangAdapter;
import com.qlshopquanaonhom6.shoponline.model.Giohang;
import com.qlshopquanaonhom6.shoponline.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {
ListView lvgiohang;
TextView txtthongbao;
static TextView txttongtien;
Button btnthanhtoan,btntieptucmua;
Toolbar toolbargiohang;
GiohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        CheckData();
        EvenUltil();
         ActionToolBar();
         CatchOnItemListview();
         EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() >0){
                    Intent intent = new Intent(getApplicationContext(),ThongtinkhachhangActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.showToast_Short(getApplicationContext(),"Giỏ hàng của bạn đang chống");
                }
            }
        });
    }

    private void CatchOnItemListview() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {// dialog xoa gio hang
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.manggiohang.size() <=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if (MainActivity.manggiohang.size() <=0){
                                txtthongbao.setVisibility(View.VISIBLE);//thay doi nut cong tru
                            }else{
                                txtthongbao.setVisibility(View.VISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static void EvenUltil() {
        long tongtien = 0;
        for (int i = 0;i<MainActivity.manggiohang.size();i++){
            tongtien +=MainActivity.manggiohang.get(i).getGiasp();///neu them san pham thi tien tu tang theo

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <=0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);//nut tru san pham ko hien
            lvgiohang.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }



    private void Anhxa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.txtThongbao);
        txttongtien = findViewById(R.id.txttongtien);
        btnthanhtoan = findViewById(R.id.btnthanhtoangiohang);
        btntieptucmua = findViewById(R.id.btntieptucmuahang);
       toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}