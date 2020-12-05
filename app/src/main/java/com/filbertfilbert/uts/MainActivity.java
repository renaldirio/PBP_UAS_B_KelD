package com.filbertfilbert.uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.filbertfilbert.uts.databinding.ItemWahanaBinding;
import com.filbertfilbert.uts.adapter.WahanaRecyclerViewAdapter;
import com.filbertfilbert.uts.database.DatabaseClient;
import com.filbertfilbert.uts.model.Wahana;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Wahana> ListWahana;
    SwipeRefreshLayout refreshLayout;
    Button btnProfile,btnFindme;
    private RecyclerView recyclerView;
    private WahanaRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          recyclerView = findViewById(R.id.recycler_view_wahana);
            adapter = new WahanaRecyclerViewAdapter(MainActivity.this, ListWahana);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String CHANNEL_ID = "Channel 1";
                CharSequence name = "Channel 1";
                String description = "This is Channel 1";


                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                FirebaseMessaging.getInstance().subscribeToTopic("news")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String mag = "Successful";
                                if (!task.isSuccessful()) {
                                    mag = "failed";
                                }
                                Toast.makeText(MainActivity.this, mag, Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getUsers();
                    refreshLayout.setRefreshing(false);
                }
            });

            getUsers();


            btnProfile=findViewById(R.id.btn_profile);
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                }
            });
            btnFindme=findViewById(R.id.btn_find_me);
            btnFindme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity( new Intent(MainActivity.this,FindMeActivity.class));
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