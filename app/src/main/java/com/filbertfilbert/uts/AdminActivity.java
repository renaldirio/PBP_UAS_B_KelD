package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    Button btnAdd,btnCancel;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    SearchView searchView;
    WahanaRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView=findViewById(R.id.admin_rv);
        refreshLayout=findViewById(R.id.swipe_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd=findViewById(R.id.btn_addWahana);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragment addfragment = new AddFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.admin_layout,addfragment)
                        .commit();
            }

        });
        btnCancel=findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent cancelIntent = new Intent(AdminActivity.this,LoginActivity.class);
               startActivity(cancelIntent);
            }
        });

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
                adapter = new WahanaRecyclerViewAdapter(AdminActivity.this, wahana);
                recyclerView.setAdapter(adapter);

                if (wahana.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty List ", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
}