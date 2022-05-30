package com.qlshopquanaonhom6.shoponline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.adapter.LoaispAdapter;
import com.qlshopquanaonhom6.shoponline.adapter.SanphamAdapter;
import com.qlshopquanaonhom6.shoponline.model.Giohang;
import com.qlshopquanaonhom6.shoponline.model.Loaisp;
import com.qlshopquanaonhom6.shoponline.model.Sanpham;
import com.qlshopquanaonhom6.shoponline.ultil.CheckConnection;
import com.qlshopquanaonhom6.shoponline.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
      int id = 0;
      String tenloaisp = "";
      String hinhanhloaisp = "";
      ArrayList<Sanpham> mangsanpham;
      SanphamAdapter sanphamAdapter;
      public static ArrayList<Giohang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println( );
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(MainActivity.this,"your device is" + token,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        Anhxa();
        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuSPMoiNhat();
            CatchOnItemListView();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(),"Kiểm Tra kết nối của bạn");
            finish();
        }

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

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i){
                    case 0:
                        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, AoActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, VayActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHectivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongtinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

            }
        });
    }

    private void GetDuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            if (response !=null){
                int ID = 0;
                String Tensanpham = "";
                Integer Giasanpham = 0;
                String Hinhanhsanpham = "";
                String Motasanpham = "";
                int IDsanpham = 0;
                for (int i = 0; i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ID = jsonObject.getInt("id");
                        Tensanpham = jsonObject.getString("tensp");
                        Giasanpham = jsonObject.getInt("giasp");
                        Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                        Motasanpham = jsonObject.getString("motasp");
                        IDsanpham = jsonObject.getInt("idsanpham");
                         mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                         sanphamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        //api doc du lieu tu server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                  for (int i = 0; i <response.length();i++){
                      try {
                          JSONObject jsonObject = response.getJSONObject(i);
                          id = jsonObject.getInt("id");
                          tenloaisp = jsonObject.getString("tenloaisp");
                          hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                          mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                          loaispAdapter.notifyDataSetChanged();
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
                  mangloaisp.add(3,new Loaisp(0,"Liên hệ","https://png.pngtree.com/png-vector/20190804/ourlarge/pngtree-call-contact-delete-png-image_1650527.jpg"));
                    mangloaisp.add(4,new Loaisp(0,"Thông tin","https://png.pngtree.com/png-vector/20190411/ourlarge/pngtree-vector-information-icon-png-image_925431.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://vn-live-05.slatic.net/p/77b9e5e43404a0885ceeed3d2b6e26e1.jpg_400x400q90.jpg_.webp");
        mangquangcao.add("https://vn-live-05.slatic.net/p/77b9e5e43404a0885ceeed3d2b6e26e1j.pg_400x400q90.jpg_.webp");
        mangquangcao.add("https://vn-live-05.slatic.net/p/241c7bed6a453a9e76a97b06b272e81a.jpg_400x400q90.jpg_.webp");
        mangquangcao.add("https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8bW9kZWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60");
        mangquangcao.add("https://media.istockphoto.com/photos/fashionable-young-woman-in-autumn-outfit-and-boots-looking-at-camera-picture-id1280050698?b=1&k=20&m=1280050698&s=170667a&w=0&h=baSLSkPxycuQJr2gxvdNBOUKhdRYNMUFEfx-ckT37FI=");
        mangquangcao.add("https://image.thanhnien.vn/800x1200/Uploaded/2021/kpcvovbs/2021_09_19/1000_zixc.jpg");
        mangquangcao.add("https://img.yes24.vn/Upload/ProductImage/anhduong201605/2040703_L.jpg");
        for (int i = 0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
         Picasso.get().load(mangquangcao.get(i)).into(imageView);
         imageView.setScaleType(ImageView.ScaleType.FIT_XY);
         viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlippert);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chủ","https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_960_720.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        if (manggiohang !=null){

        }else {
               manggiohang = new ArrayList<>();
        }
    }
}