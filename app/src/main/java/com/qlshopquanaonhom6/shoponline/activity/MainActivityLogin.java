package com.qlshopquanaonhom6.shoponline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qlshopquanaonhom6.shoponline.R;

import io.paperdb.Paper;

public class MainActivityLogin extends AppCompatActivity implements View.OnClickListener  {

    TextView register,forgotpassword;
    private TextInputEditText editTextEmail,editTextPassword;

    CheckBox box ;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        forgotpassword = findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        box = findViewById(R.id.checkbox);
        Paper.init(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this,RegisterUser.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,Forgot_pass_Activity.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Lam on nhap email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextEmail.setError("vui long nhap password");
            editTextPassword.requestFocus();
            return;

        }
        if (password.length() < 6){
            editTextPassword.setError("Mat khau phai dai hon 6 ky tu");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Toast.makeText(MainActivityLogin.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivityLogin.this,MainActivity.class));


                }

                else{
                    Toast.makeText(MainActivityLogin.this, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}