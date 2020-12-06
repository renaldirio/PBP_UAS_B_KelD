package com.filbertfilbert.uts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.response.WahanaResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditWahanaActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private String sIdWahana, sNama_wahana, sLokasi, sRating, sDeskripsi;
    private EditText etNama_wahana, etLokasi, etRating, etDeskripsi, etFoto;
    private MaterialButton btnCancel, btnEdit;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wahana);

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etNama_wahana = findViewById(R.id.etNama_Wahana);
        etLokasi = findViewById(R.id.etLokasi);
        etRating = findViewById(R.id.etRating);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        etFoto = findViewById(R.id.etFoto);
        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);

        Intent i = getIntent();
        sIdWahana = i.getStringExtra("id");
        sNama_wahana = i.getStringExtra("namaWahana");
        sLokasi = i.getStringExtra("lokasi");
        sRating = i.getStringExtra("rating");
        sDeskripsi = i.getStringExtra("deskripsi");

        etNama_wahana.setText(sNama_wahana);
        etLokasi.setText(sLokasi);
        etRating.setText(sRating);
        etDeskripsi.setText(sDeskripsi);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama_wahana.getText().toString().isEmpty())
                {
                    etNama_wahana.setError("Isikan dengan benar");
                    etNama_wahana.requestFocus();
                }
                else if(etLokasi.getText().toString().isEmpty())
                {
                    etLokasi.setError("Isikan dengan benar");
                    etLokasi.requestFocus();
                }
                else if(etRating.getText().toString().isEmpty())
                {
                    etRating.setError("Isikan dengan benar");
                    etRating.requestFocus();
                }
                else if(etDeskripsi.getText().toString().isEmpty())
                {
                    etDeskripsi.setError("Isikan dengan benar");
                    etDeskripsi.requestFocus();
                }

                else if(etFoto.getText().toString().isEmpty())
                {
                    etFoto.setError("Isikan dengan benar");
                    etFoto.requestFocus();
                }
                else
                {
                    progressDialog.show();
                    saveWahana();
                }
            }
        });
    }

    private void saveWahana() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WahanaResponse> update = apiService.updateWahana(Integer.parseInt(sIdWahana),etNama_wahana.getText().toString(), etLokasi.getText().toString(),
                etRating.getText().toString(), etDeskripsi.getText().toString());

        update.enqueue(new Callback<WahanaResponse>() {
            @Override
            public void onResponse(Call<WahanaResponse> call, Response<WahanaResponse> response) {
                Toast.makeText(EditWahanaActivity.this, "Berhasil edit wahana", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent i = new Intent(EditWahanaActivity.this, ShowListWahanaAcivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<WahanaResponse> call, Throwable t) {
                Toast.makeText(EditWahanaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}