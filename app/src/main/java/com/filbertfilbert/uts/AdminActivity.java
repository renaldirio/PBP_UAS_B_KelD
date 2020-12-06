package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.filbertfilbert.uts.ui.CreateFasilitasActivity;
import com.filbertfilbert.uts.ui.CreateWahanaActivity;
import com.filbertfilbert.uts.ui.ShowListFasilitasActivity;
import com.filbertfilbert.uts.ui.ShowListWahanaAcivity;

public class AdminActivity extends AppCompatActivity {
    private CardView cvCreateWahana, cvShowListWahana,cvCreateFasilitas, cvShowListFasilitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        cvCreateWahana = findViewById(R.id.cvCreateWahana);
        cvShowListWahana = findViewById(R.id.cvShowListWahana);
        cvCreateFasilitas = findViewById(R.id.cvCreateFasilitas);
        cvShowListFasilitas = findViewById(R.id.cvShowListFasilitas);

        cvCreateWahana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, CreateWahanaActivity.class);
                startActivity(i);
            }
        });

        cvShowListWahana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, ShowListWahanaAcivity.class);
                startActivity(i);
            }
        });
        cvCreateFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, CreateFasilitasActivity.class);
                startActivity(i);
            }
        });

        cvShowListFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, ShowListFasilitasActivity.class);
                startActivity(i);
            }
        });
    }
}