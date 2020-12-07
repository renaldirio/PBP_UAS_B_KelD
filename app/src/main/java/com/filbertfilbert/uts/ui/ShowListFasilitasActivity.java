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
import com.filbertfilbert.uts.DAO.FasilitasDAO;
import com.filbertfilbert.uts.DAO.WahanaDAO;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.adapter.FasilitasRecyclerAdapter;
import com.filbertfilbert.uts.adapter.WahanaRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowListFasilitasActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private RecyclerView recyclerView;
    private FasilitasRecyclerAdapter recyclerAdapter;
    private List<FasilitasDAO> fasilitas = new ArrayList<>();
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_fasilitas);

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

        loadFasilitas();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFasilitas();
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

    private void loadFasilitas() {
        ApiInterface apiGetFasilitas = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FasilitasDAO>> callGetFasilitas = apiGetFasilitas.getAllFasilitas();

        callGetFasilitas.enqueue(new Callback<List<FasilitasDAO>>() {
            @Override
            public void onResponse(Call<List<FasilitasDAO>> call, Response<List<FasilitasDAO>> response) {
                generateDataList(response.body());
                swipeRefreshLayout.setRefreshing(false);
                //   System.out.println(response.body().get(0).getLokasi());
            }

            @Override
            public void onFailure(Call<List<FasilitasDAO>> call, Throwable t) {
                Toast.makeText(ShowListFasilitasActivity.this, "Kesalahan Jaringan", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                Log.v("error","message"+t.toString());
            }
        });
    }

    private void generateDataList(List<FasilitasDAO> FasilitasList) {
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerAdapter = new FasilitasRecyclerAdapter(FasilitasList, this);
        recyclerAdapter.isAdmin = "true";
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }
}