package com.qlshopquanaonhom6.shoponline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.adapter.AoAdapter;
import com.qlshopquanaonhom6.shoponline.model.Sanpham;
import com.qlshopquanaonhom6.shoponline.ultil.CheckConnection;
import com.qlshopquanaonhom6.shoponline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AoActivity extends AppCompatActivity {

    Toolbar toolbardt;
    ListView lvdt;
    AoAdapter aoAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ao);
        Anhxa();
        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(),"Kiểm tra kết nối");
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
    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChitietsanphamActivity.class);// gui du lieu sang chi tiet san pham
                intent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(intent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
        if (firstItem + visibleItem ==totalItem && totalItem !=0 && isLoading == false && limitdata ==false){
           isLoading = true;
           ThreadData threadData = new ThreadData();
           threadData.start();
        }
            }
        });
    }

    private void GetData(int Page) {//server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    int id = 0;
                    String Tendt = "";
                    int Giadt = 0;
                    String Hinhanhdt = "";
                    String Mota = "";
                    int Idspdt = 0;
                    if (response !=null && response.length() != 2){
                        lvdt.removeFooterView(footerview);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i =0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                Tendt = jsonObject.getString("tensp");
                                Giadt = jsonObject.getInt("giasp");
                                Hinhanhdt = jsonObject.getString("hinhanhsp");
                                Mota = jsonObject.getString("motasp");
                                Idspdt = jsonObject.getInt("idsanpham");
                                mangdt.add(new Sanpham(id,Tendt,Giadt,Hinhanhdt,Mota,Idspdt));
                                aoAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                      limitdata = true;
                        lvdt.removeFooterView(footerview);
                        CheckConnection.showToast_Short(getApplicationContext(),"Hết Dữ liệu");
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        iddt = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",iddt+"");
    }

    private void Anhxa() {
        toolbardt = findViewById(R.id.toolbardienthoai);
        lvdt = findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        aoAdapter = new AoAdapter(getApplicationContext(),mangdt);
        lvdt.setAdapter(aoAdapter);
        LayoutInflater inflater  = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{////
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}