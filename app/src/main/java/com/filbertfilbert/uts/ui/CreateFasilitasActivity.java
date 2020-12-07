package com.filbertfilbert.uts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.response.FasilitasResponse;
import com.filbertfilbert.uts.response.WahanaResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFasilitasActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private EditText etNama_fasilitas, etLokasi, etDeskripsi, etFoto;
    private MaterialButton btnCancel, btnCreate;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fasilitas);

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etNama_fasilitas = findViewById(R.id.etNama_fasilitas);
        etLokasi = findViewById(R.id.etLokasi);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNama_fasilitas.getText().toString().isEmpty()) {
                    etNama_fasilitas.setError("Isikan dengan benar");
                    etNama_fasilitas.requestFocus();
                } else if (etLokasi.getText().toString().isEmpty()) {
                    etLokasi.setError("Isikan dengan benar");
                    etLokasi.requestFocus();

                } else if (etDeskripsi.getText().toString().isEmpty()) {
                    etDeskripsi.setError("Isikan dengan benar");
                    etDeskripsi.requestFocus();
                } else {
                    progressDialog.show();
                    saveFasilitas();
                }
            }
        });
    }

    private void saveFasilitas() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<FasilitasResponse> add = apiService.createFasilitas(etNama_fasilitas.getText().toString(), etLokasi.getText().toString(),
                etDeskripsi.getText().toString());

        add.enqueue(new Callback<FasilitasResponse>() {
            @Override
            public void onResponse(Call<FasilitasResponse> call, Response<FasilitasResponse> response) {
                Toast.makeText(CreateFasilitasActivity.this, "Berhasil menambah user", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<FasilitasResponse> call, Throwable t) {
                Toast.makeText(CreateFasilitasActivity.this, "Gagal menambah user", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}