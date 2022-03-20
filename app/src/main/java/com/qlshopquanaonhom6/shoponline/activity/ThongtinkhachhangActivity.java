package com.qlshopquanaonhom6.shoponline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.ultil.CheckConnection;
import com.qlshopquanaonhom6.shoponline.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class ThongtinkhachhangActivity extends AppCompatActivity {
EditText edtenkhachhang,edemail,edtsdt;
Button btnxacnhan,btnttrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        btnttrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetwordConnection(getApplicationContext())){
            EvenButton();
        }else
        {
            CheckConnection.showToast_Short(getApplicationContext(),"Kiem tra ket noi");
        }
    }

    private void EvenButton() {//send data  custome to server
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ten = edtenkhachhang.getText().toString().trim();
                String sdt = edtsdt.getText().toString().trim();
                String email = edemail.getText().toString().trim();
                if (ten.length() >0 && sdt.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("madonhang",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.showToast_Short(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }

            }

        });
    }

    private void Anhxa() {
        edtenkhachhang = findViewById(R.id.edittxttenkhachhang);
        edemail = findViewById(R.id.edittxtemail);
        edtsdt = findViewById(R.id.edittxtsodienthoai);
        btnxacnhan = findViewById(R.id.btnxacnhan);
        btnttrove = findViewById(R.id.btntrove);
    }
}