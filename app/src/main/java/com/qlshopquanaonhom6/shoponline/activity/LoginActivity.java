package com.qlshopquanaonhom6.shoponline.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qlshopquanaonhom6.shoponline.DAO.NguoiDungDAO;
import com.qlshopquanaonhom6.shoponline.R;

public class LoginActivity extends AppCompatActivity {
EditText edUsernameLogin,edPasswordLogin;
Button btnLogin,btnCancelLogin;
CheckBox chkRememberPassLogin;
NguoiDungDAO nguoiDungDAO;
String strUSer,strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsernameLogin=findViewById(R.id.edUserNameLogin);
        edPasswordLogin=findViewById(R.id.edPassLogin);
        btnLogin=findViewById(R.id.btnLogin);
        btnCancelLogin=findViewById(R.id.btnCancelLogin);
        chkRememberPassLogin=findViewById(R.id.chkRememberPassLogin);
        nguoiDungDAO =new NguoiDungDAO(this);
        SharedPreferences preferences =
                getSharedPreferences("USER_FILE",MODE_PRIVATE);
       String user =preferences.getString("USERNAME","");
       String pass =preferences.getString("PASSWORD","");
       Boolean rem =preferences.getBoolean("REMEMBER",false);

       edUsernameLogin.setText(user);
       edPasswordLogin.setText(pass);
       chkRememberPassLogin.setChecked(rem);
       findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(LoginActivity.this,ThemUserActivity.class);
               startActivity(i);
               finish();
           }
       });

       btnCancelLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               edUsernameLogin.setText("");
               edPasswordLogin.setText("");
           }
       });
       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
       checkLogin();
           }
       });
    }
    public void rememberUser(String u,String p,boolean status){
        SharedPreferences preferences =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }
    public void checkLogin(){
        strUSer=edUsernameLogin.getText().toString();
        strPass=edPasswordLogin.getText().toString();
        if (strUSer.isEmpty()||strPass.isEmpty()){
            Toast.makeText(getApplicationContext(),"khong duoc de trong",Toast.LENGTH_SHORT).show();
        }else {
            if (nguoiDungDAO.checkLogin(strUSer,strPass)>0){
                Toast.makeText(getApplicationContext(),"Login thanh cong",Toast.LENGTH_SHORT).show();
            rememberUser(strUSer,strPass,chkRememberPassLogin.isChecked());
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"thong tin dang nhap sai",Toast.LENGTH_SHORT).show();
            }
        }
    }
}