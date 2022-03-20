package com.qlshopquanaonhom6.shoponline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.qlshopquanaonhom6.shoponline.DAO.NguoiDungDAO;
import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.model.NguoiDung;

import java.util.regex.Pattern;

public class ThemUserActivity extends AppCompatActivity {
private EditText edUsernameThem,ednameThem,edPassThem,edSDTThem,edRePassThem;
private Button btnSignUpThem,btnCancelThem;
NguoiDungDAO nguoiDungDAO;
NguoiDung nguoiDung;
    private static final Pattern USER_CHECK =
            Pattern.compile("^[A-Z]+\\w*");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_user);
        ednameThem=findViewById(R.id.edNameThem);
        edPassThem=findViewById(R.id.edPassThem);
        edRePassThem=findViewById(R.id.edRePassThem);
        edUsernameThem=findViewById(R.id.edUserThem);
        edSDTThem=findViewById(R.id.edNumberThem);
        btnSignUpThem=findViewById(R.id.btnSignUpThem);
        btnCancelThem=findViewById(R.id.btnCancelThem);
        nguoiDungDAO =new NguoiDungDAO(this);
        btnCancelThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              xoatrang();
            }
        });
        btnSignUpThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nguoiDung =new NguoiDung();
                nguoiDung.setMaND(edUsernameThem.getText().toString());
                nguoiDung.setTenND(ednameThem.getText().toString());
                nguoiDung.setMK(edPassThem.getText().toString());
                nguoiDung.setSDT(edSDTThem.getText().toString());
                if (validate()>0){
                    if (nguoiDungDAO.insert(nguoiDung)>0){
                        Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_SHORT).show();
                    changeacti();
                    }else {
                        Toast.makeText(getApplicationContext(),"Lưu không thành công",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void xoatrang() {
        ednameThem.setText("");
        edPassThem.setText("");
        edRePassThem.setText("");
        edUsernameThem.setText("");
        edSDTThem.setText("");
    }

    public int validate(){
        int check = 1;
        try {
            Integer.parseInt(edSDTThem.getText().toString());
        }catch (Exception ex){
            Toast.makeText(this,"vui long nhap la so",Toast.LENGTH_SHORT).show();
        }
        String hoten =ednameThem.getText().toString().trim();

       

        //doan ko fix
        if (edUsernameThem.getText().length()==0 || ednameThem.getText().length()==0 || edPassThem.getText().length()==0 ||
                edRePassThem.getText().length()==0||edSDTThem.getText().length()==0){
            Toast.makeText(this,"Bạn phải nhập thông tin đầy đủ",Toast.LENGTH_SHORT).show();
            check =-1;
        }
        else {
            String pass =edPassThem.getText().toString();
            String rePass =edRePassThem.getText().toString();
            if (!pass.equals(rePass)){
                Toast.makeText(this,"Mật khẩu không trùng khớp",
                        Toast.LENGTH_SHORT).show();
                check =-1;
            }
        }
        return  check;
    }
public void changeacti(){
    Intent i =new Intent(ThemUserActivity.this,LoginActivity.class);
    startActivity(i);
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        changeacti();

    }
}