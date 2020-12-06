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
import com.filbertfilbert.uts.response.WahanaResponse;
import com.filbertfilbert.uts.ui.EditWahanaActivity;
import com.filbertfilbert.uts.ui.ShowListWahanaAcivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WahanaFragment extends DialogFragment {

    private TextView twNama_wahana, twLokasi, twRating, twDeskripsi;
    private String sNama_wahana, sLokasi, sRating, sFoto, sDeskripsi,isAdmin;
    private int sIdWahana;
    private ImageView twFoto;
    private ImageButton ibClose;
    private Button btnDelete,btnEdit;
    private ProgressDialog progressDialog;
    private LinearLayout panelEditDelete;

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
        btnDelete = view.findViewById(R.id.btnDelete);
        btnEdit = view.findViewById(R.id.btnEdit);
        panelEditDelete = view.findViewById(R.id.panelEditDelete);

        sIdWahana = getArguments().getInt("id", 0);
        isAdmin = getArguments().getString("isAdmin");
        if(isAdmin.equalsIgnoreCase("false")){
            panelEditDelete.setVisibility(View.GONE);
        }
        loadWahanaById(sIdWahana);

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
                Intent i = new Intent(getActivity(), EditWahanaActivity.class);
                i.putExtra("id",String.valueOf(sIdWahana));
                i.putExtra("namaWahana",sNama_wahana);
                i.putExtra("lokasi",sLokasi);
                i.putExtra("rating",sRating);
                i.putExtra("deskripsi",sDeskripsi);
                startActivity(i);
            }
        });
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
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WahanaResponse> call, Throwable t) {
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
                        Call<WahanaResponse> delete = apiService.deleteWahana(sIdWahana);

                        delete.enqueue(new Callback<WahanaResponse>() {
                            @Override
                            public void onResponse(Call<WahanaResponse> call, Response<WahanaResponse> response) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), ShowListWahanaAcivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<WahanaResponse> call, Throwable t) {
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