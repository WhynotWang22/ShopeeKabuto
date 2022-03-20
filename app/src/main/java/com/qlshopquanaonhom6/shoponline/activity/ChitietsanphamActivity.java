package com.qlshopquanaonhom6.shoponline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.model.Giohang;
import com.qlshopquanaonhom6.shoponline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChitietsanphamActivity extends AppCompatActivity {

    Toolbar toolbarchitiet;
    ImageView imageViewChitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id = 0;
    String TenChitiet = "";
    int Giachitiet = 0;
    String Hinhanhchitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEvenSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() >0){
                 int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                 boolean exists = false;
                 for (int i = 0; i <MainActivity.manggiohang.size(); i++){
                     if (MainActivity.manggiohang.get(i).getIdsp() ==id){
                         MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+ sl);
                         if (MainActivity.manggiohang.get(i).getSoluongsp() >=20){
                             MainActivity.manggiohang.get(i).setSoluongsp(20);
                         }
                         MainActivity.manggiohang.get(i).setGiasp(Giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                         exists = true;

                     }
                 }
                 if (exists == false){
                     int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                     long Giamoi = soluong * Giachitiet;
                     MainActivity.manggiohang.add(new Giohang(id,TenChitiet,Giamoi,Hinhanhchitiet,soluong));

                 }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                   long Giamoi = soluong * Giachitiet;
                   MainActivity.manggiohang.add(new Giohang(id,TenChitiet,Giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEvenSpinner() {//
        Integer [] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        TenChitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhanhsanpham();
        MotaChitiet = sanpham.getMotasanpham();
        Idsanpham = sanpham.getIDSanpham();
        txtten.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá :" +decimalFormat.format(Giachitiet)+ "Đ");
        txtmota.setText(MotaChitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.laod)
                .error(R.drawable.loadq)
                .into(imageViewChitiet);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsanpham);
        imageViewChitiet = findViewById(R.id.imagechitietsanpham);
        txtten = findViewById(R.id.txttenchitietsanpham);
        txtgia =findViewById(R.id.txtgiachitietsanpham);
        txtmota = findViewById(R.id.txtviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttondatmua);
    }
}