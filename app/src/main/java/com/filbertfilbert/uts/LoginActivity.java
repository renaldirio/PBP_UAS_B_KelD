package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class LoginActivity extends AppCompatActivity {

    private Button btnAdmin,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                Intent mainIntent;
                mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}