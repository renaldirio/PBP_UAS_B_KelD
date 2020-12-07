package com.filbertfilbert.uts.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.response.FasilitasResponse;
import com.filbertfilbert.uts.ui.EditFasilitasActivity;
import com.filbertfilbert.uts.ui.ShowListFasilitasActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FasilitasFragment extends DialogFragment {

    private TextView twNama_fasilitas, twLokasi, twDeskripsi;
    private String sNama_fasilitas, sLokasi, sFoto, sDeskripsi,isAdmin;
    private int sIdFasilitas;
    private ImageView twFoto;
    private ImageButton ibClose;
    private Button btnDelete,btnEdit;
    private ProgressDialog progressDialog;
    private LinearLayout panelEditDelete;

    public static FasilitasFragment newInstance() {return new FasilitasFragment();}

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_fasilitas, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = view.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        twNama_fasilitas = view.findViewById(R.id.twNama_fasilitas);
        twLokasi = view.findViewById(R.id.twLokasi);
        twDeskripsi = view.findViewById(R.id.twDeskripsi);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnEdit = view.findViewById(R.id.btnEdit);
        panelEditDelete = view.findViewById(R.id.panelEditDelete);

        sIdFasilitas = getArguments().getInt("id", 0);
        isAdmin = getArguments().getString("isAdmin");
        if(isAdmin.equalsIgnoreCase("false")){
            panelEditDelete.setVisibility(View.GONE);
        }
        loadFasilitasById(sIdFasilitas);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = KonfirmasiHapus();
                diaBox.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditFasilitasActivity.class);


                i.putExtra("id",String.valueOf(sIdFasilitas));
                i.putExtra("namaFasilitas",sNama_fasilitas);
                i.putExtra("lokasi",sLokasi);
                i.putExtra("deskripsi",sDeskripsi);
                startActivity(i);
            }
        });
        return view;
    }

    private void loadFasilitasById(int id){
        ApiInterface apiServiceFasilitasId = ApiClient.getClient().create(ApiInterface.class);

        Call<FasilitasResponse> getFasilitas = apiServiceFasilitasId.getFasilitasById(id, "data");

        getFasilitas.enqueue(new Callback<FasilitasResponse>() {
            @Override
            public void onResponse(Call<FasilitasResponse> call, Response<FasilitasResponse> response) {
                sNama_fasilitas = response.body().getFasilitas().get(0).getNama_fasilitas();
                sLokasi = response.body().getFasilitas().get(0).getLokasi();

                sDeskripsi = response.body().getFasilitas().get(0).getDeskripsi();

                twNama_fasilitas.setText(sNama_fasilitas);
                twLokasi.setText(sLokasi);
                twDeskripsi.setText(sDeskripsi);
//                Glide.with(twFoto.getContext())
//                        .load("https://uaspbp.000webhostapp.com/public_html"+sFoto)
//                        .apply(new RequestOptions().centerCrop())
//                        .into(twFoto);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<FasilitasResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private AlertDialog KonfirmasiHapus()
    {
        AlertDialog diaBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Konfirmasi")
                .setMessage("Yakin ingin menghapus data ini?")

                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<FasilitasResponse> delete = apiService.deleteFasilitas(sIdFasilitas);

                        delete.enqueue(new Callback<FasilitasResponse>() {
                            @Override
                            public void onResponse(Call<FasilitasResponse> call, Response<FasilitasResponse> response) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), ShowListFasilitasActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<FasilitasResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Gagal menghapus user", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return diaBox;
    }
}