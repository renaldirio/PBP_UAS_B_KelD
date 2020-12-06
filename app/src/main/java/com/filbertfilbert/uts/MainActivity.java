package com.filbertfilbert.uts;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.filbertfilbert.uts.ui.FasilitasActivity;
import com.filbertfilbert.uts.ui.FindMeActivity;
import com.filbertfilbert.uts.ui.WahanaActivity;
import com.filbertfilbert.uts.user.ProfileActivity;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    CardView btnProfil,btnWahana,btnFasilitas,btnMap;
    TextView txtDate;
    String hariIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProfil = findViewById(R.id.btnProfil);
        btnWahana = findViewById(R.id.btnWahana);
        btnFasilitas = findViewById(R.id.btnFasilitas);
        btnMap = findViewById(R.id.btnMap);
        txtDate = findViewById(R.id.txtDate);

        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);
        getToday();

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnWahana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WahanaActivity.class);
                startActivity(i);
            }
        });

        btnFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FasilitasActivity.class);
                startActivity(i);

            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, FindMeActivity.class);
                startActivity(in);

            }
        });
    }

    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d MMMM yyyy", date);
        String formatFix = hariIni + ", " + tanggal;
        txtDate.setText(formatFix);
    }
}