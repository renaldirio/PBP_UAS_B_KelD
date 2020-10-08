package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import gr.net.maroulis.library.EasySplashScreen;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    SearchView searchView;
    WahanaRecyclerViewAdapter adapter;
    Button btnLogout,btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnProfile = findViewById(R.id.btn_profile);
        btnLogout = findViewById(R.id.btn_logout);
        recyclerView=findViewById(R.id.user_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout=findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                refreshLayout.setRefreshing(false);
            }
        });

        getUsers();
        searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });



        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });
    }

    private  void getUsers() {
        class GetUsers extends AsyncTask<Void, Void, List<Wahana>> {
            @Override
            protected List<Wahana> doInBackground(Void... voids) {
                List<Wahana> wahanaList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .wahanaDao()
                        .getAll();
                return wahanaList;
            }


            @Override
            protected void onPostExecute(List<Wahana> wahana) {
                super.onPostExecute(wahana);
                adapter = new WahanaRecyclerViewAdapter(MainActivity.this, wahana);
                recyclerView.setAdapter(adapter);
                adapter.isClickable=false;

                if (wahana.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty List ", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
}