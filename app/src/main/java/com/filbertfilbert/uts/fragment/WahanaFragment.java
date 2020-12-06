package com.filbertfilbert.uts.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.response.WahanaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WahanaFragment extends DialogFragment {

    private TextView twNama_wahana, twLokasi, twRating, twDeskripsi;
    private String sNama_wahana, sLokasi, sRating, sFoto, sDeskripsi;
    private int sIdWahana;
    private ImageView twFoto;
    private ImageButton ibClose;
    private ProgressDialog progressDialog;

    public static WahanaFragment newInstance() {return new WahanaFragment();}

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_wahana, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = view.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        twNama_wahana = view.findViewById(R.id.twNama_wahana);
        twLokasi = view.findViewById(R.id.twLokasi);
        twRating = view.findViewById(R.id.twRating);
        twDeskripsi = view.findViewById(R.id.twDeskripsi);
        twFoto = view.findViewById(R.id.twFoto);

        sIdWahana = getArguments().getInt("id", 0);

        loadWahanaById(sIdWahana);

        return view;
    }

    private void loadWahanaById(int id){
        ApiInterface apiServiceWahanaId = ApiClient.getClient().create(ApiInterface.class);

        Call<WahanaResponse> getWahana = apiServiceWahanaId.getWahanaById(id, "data");

        getWahana.enqueue(new Callback<WahanaResponse>() {
            @Override
            public void onResponse(Call<WahanaResponse> call, Response<WahanaResponse> response) {
                sNama_wahana = response.body().getWahana().get(0).getNama_wahana();
                sLokasi = response.body().getWahana().get(0).getLokasi();
                sRating = response.body().getWahana().get(0).getRating();
                sDeskripsi = response.body().getWahana().get(0).getDeskripsi();
                sFoto = response.body().getWahana().get(0).getFoto();

                twNama_wahana.setText(sNama_wahana);
                twLokasi.setText(sLokasi);
                twRating.setText(sRating);
                twDeskripsi.setText(sDeskripsi);
//                Glide.with(twFoto.getContext())
//                        .load("https://uaspbp.000webhostapp.com/public_html"+sFoto)
//                        .apply(new RequestOptions().centerCrop())
//                        .into(twFoto);
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WahanaResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}