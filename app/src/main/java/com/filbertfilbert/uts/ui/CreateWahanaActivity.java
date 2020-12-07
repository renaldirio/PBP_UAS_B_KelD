package com.filbertfilbert.uts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.UnitTestWahana.ActivityUtil;
import com.filbertfilbert.uts.UnitTestWahana.WahanaPresenter;
import com.filbertfilbert.uts.UnitTestWahana.WahanaService;
import com.filbertfilbert.uts.UnitTestWahana.WahanaView;
import com.filbertfilbert.uts.response.WahanaResponse;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateWahanaActivity extends AppCompatActivity implements WahanaView {
    private ImageButton ibBack;
    private EditText etNama_wahana, etLokasi, etRating, etDeskripsi, etFoto;
    private MaterialButton btnCancel, btnCreate;
    private ProgressDialog progressDialog;
    private WahanaPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wahana);



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
        presenter=new WahanaPresenter(this, new WahanaService());
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
                    presenter.onLoginClicked();


            }
        });
    }

    private void saveWahana() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WahanaResponse> add = apiService.createWahana(etNama_wahana.getText().toString(), etLokasi.getText().toString(),
                etRating.getText().toString(), etDeskripsi.getText().toString());

        add.enqueue(new Callback<WahanaResponse>() {
            @Override
            public void onResponse(Call<WahanaResponse> call, Response<WahanaResponse> response) {
                Toast.makeText(CreateWahanaActivity.this, "Berhasil menambah user", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }

            @Override
            public void onFailure(Call<WahanaResponse> call, Throwable t) {
                Toast.makeText(CreateWahanaActivity.this, "Gagal menambah user", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public String getNamaWahana() {
        return etNama_wahana.getText().toString();
    }

    @Override
    public String getLokasi() {
        return etLokasi.getText().toString();
    }

    @Override
    public String getRating() {
        return etRating.getText().toString();
    }

    @Override
    public String getDeskripsi() {
        return etDeskripsi.getText().toString();
    }

    @Override
    public void showNamaWahanaError(String message) {
        etNama_wahana.setError(message);
    }

    @Override
    public void showLokasiError(String message) {
        etLokasi.setError(message);
    }

    @Override
    public void showRatingError(String message) {
        etRating.setError(message);
    }

    @Override
    public void showDeskripsiError(String message) {
        etDeskripsi.setError(message);
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showLoginError(String message) {

    }

    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}