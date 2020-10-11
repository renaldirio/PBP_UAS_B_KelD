package com.filbertfilbert.uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText txtEmailUser,txtPasswordUser;
    private Button btnAdmin,btnLogin;
    FirebaseAuth fauth;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmailUser = findViewById(R.id.input_email);
        txtPasswordUser = findViewById(R.id.input_password);
        txtRegister = findViewById(R.id.txt_register);
        fauth = FirebaseAuth.getInstance();

        if(fauth.getCurrentUser() != null){
            //Jika ada, maka halaman akan langsung berpidah pada MainActivity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnAdmin = findViewById(R.id.btn_admin);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminIntent;
                adminIntent = new Intent(LoginActivity.this,AdminActivity.class);
                startActivity(adminIntent);
            }
        });
        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmailUser.getText().toString();
                String password = txtPasswordUser.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(LoginActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LoginActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}