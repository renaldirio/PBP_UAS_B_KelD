package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.filbertfilbert.uts.API.UtilApi;
import com.filbertfilbert.uts.adapter.WahanaAdapter;
import com.filbertfilbert.uts.adapter.WahanaRecyclerViewAdapter;
import com.filbertfilbert.uts.database.DatabaseClient;
import com.filbertfilbert.uts.model.Wahana;
import com.filbertfilbert.uts.model.WahanaModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    Button btnAdd,btnCancel;
    RecyclerView recyclerView;
    ProgressDialog dialog;

    SwipeRefreshLayout refreshLayout;
    SearchView searchView;
    WahanaAdapter adapter;
    private Service service;
    List<WahanaModel> modellist;
    List<WahanaModel> arraymodel = new ArrayList<>();
    private static final String TAG = AdminActivity.class.getSimpleName();

    private FrameLayout frameLayout;
    ProgressDialog progressDoalog;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        context.startActivity(intent);
    }

    public void loadFragment(Fragment fragment) {
        // Clear any existing layout.
        frameLayout.removeAllViews();

        // Load the new fragment to the layout.
        getFragmentManager().beginTransaction()
                .addToBackStack(null) // Go to the previous fragment when clicking the back button.
                .replace(R.id.admin_layout, fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        refreshLayout=findViewById(R.id.swipe_refresh);
        initViews();
        //  progressDoalog = new ProgressDialog(AdminActivity.this);
        //progressDoalog.setMessage("Loading....");
        //progressDoalog.show();


        loadData();
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
                //    getUsers();
                loadData();
                refreshLayout.setRefreshing(false);
            }
        });

        //     getUsers();
      //  searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
                return false;
            }
        });
    }
    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.admin_rv);
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
                // adapter = new WahanaRecyclerViewAdapter(AdminActivity.this, wahana);
                recyclerView.setAdapter(adapter);

                if (wahana.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty List ", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }

    private void loadData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AdminActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        new dapatdata().execute();
        arraymodel.clear();
    }
    private  class dapatdata extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress dialog
            dialog = new ProgressDialog(AdminActivity.this);
            dialog.setMessage("Silahkan Tunggu");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            adapter = new WahanaAdapter(arraymodel,AdminActivity.this);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            service = UtilApi.getApiSerivce();
            recyclerView.invalidate();
            service.getkelompok()
                    .enqueue(new Callback<List<WahanaModel>>() {
                        @Override
                        public void onResponse(Call<List<WahanaModel>> call, Response<List<WahanaModel>> response) {
                            if (response.isSuccessful()){
                                try {
                                    if (response == null){
                                        Toast.makeText(AdminActivity.this,"Hmm...Mungkin Datanya Kosong",Toast.LENGTH_LONG).show();
                                    }else {
                                        dialog.dismiss();

                                        WahanaModel model = new WahanaModel();
                                        List<WahanaModel> models = response.body();
                                        arraymodel.addAll(models);
                                        adapter.notifyDataSetChanged();
                                        //arraymodel.clear();
                                    }
                                }catch (Exception e){
                                    Toast.makeText(AdminActivity.this,"Sepertinya Terjadi Sesuatu"+"\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(AdminActivity.this,"Gagal Merespon ",Toast.LENGTH_LONG).show();

                            }
                        }
                        @Override
                        public void onFailure(Call<List<WahanaModel>> call, Throwable t) {

                            Toast.makeText(AdminActivity.this,"Gagal Menghubungi Server :( ",Toast.LENGTH_LONG).show();
                            refreshLayout.setRefreshing(false);


                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()){
                dialog.dismiss();

            }
            arraymodel.clear();
            recyclerView.setAdapter(adapter);
        }
    }
}