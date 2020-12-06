package com.filbertfilbert.uts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.DAO.WahanaDAO;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.adapter.WahanaRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowListWahanaAcivity extends AppCompatActivity {

    private ImageButton ibBack;
    private RecyclerView recyclerView;
    private WahanaRecyclerAdapter recyclerAdapter;
    private List<WahanaDAO> wahana = new ArrayList<>();
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_wahana_acivity);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        searchView = findViewById(R.id.searchWahana);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
//        swipeRefreshLayout.setRefreshing(true);
//        swipeRefreshLayout.setRefreshing(true);

        loadWahana();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWahana();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(requestCode == RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
        }
    }

    private void loadWahana() {
        ApiInterface apiGetWahana = ApiClient.getClient().create(ApiInterface.class);
        Call<List<WahanaDAO>> callGetWahana = apiGetWahana.getAllWahana();

        callGetWahana.enqueue(new Callback<List<WahanaDAO>>() {
            @Override
            public void onResponse(Call<List<WahanaDAO>> call, Response<List<WahanaDAO>> response) {
                generateDataList(response.body());
                swipeRefreshLayout.setRefreshing(false);
                //   System.out.println(response.body().get(0).getLokasi());
            }

            @Override
            public void onFailure(Call<List<WahanaDAO>> call, Throwable t) {
                Toast.makeText(ShowListWahanaAcivity.this, "Kesalahan Jaringan", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                Log.v("error","message"+t.toString());
            }
        });
    }

        private void generateDataList(List<WahanaDAO> WahanaList) {
            recyclerView = findViewById(R.id.userRecyclerView);
            recyclerAdapter = new WahanaRecyclerAdapter(WahanaList, this);
            recyclerAdapter.isAdmin = "true";
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerAdapter);

            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
}