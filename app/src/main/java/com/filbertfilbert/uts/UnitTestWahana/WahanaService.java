package com.filbertfilbert.uts.UnitTestWahana;

import android.widget.Toast;

import com.filbertfilbert.uts.API.ApiClient;
import com.filbertfilbert.uts.API.ApiInterface;
import com.filbertfilbert.uts.response.WahanaResponse;
import com.filbertfilbert.uts.ui.CreateWahanaActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WahanaService {

    public void login(final WahanaView view, String nama_wahana, String lokasi, String rating,
                      String deskripsi,final WahanaCallback callback){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<WahanaResponse> userDAOCall = apiService.createWahana(nama_wahana, lokasi,rating,deskripsi);
        userDAOCall.enqueue(new Callback<WahanaResponse>() {
            @Override
            public void onResponse(Call<WahanaResponse> call,
                                   Response<WahanaResponse> response) {

                callback.onSuccess(true);}

            @Override
            public void onFailure(Call<WahanaResponse> call, Throwable t) {
                view.showErrorResponse(t.getMessage());
                callback.onError();
            }
        });
    }
    public Boolean getValid(final WahanaView view, String nama_wahana, String lokasi, String rating,
                            String deskripsi) {
        final Boolean[] bool = new Boolean[1];
        login(view, nama_wahana, lokasi, rating, deskripsi, new WahanaCallback() {
            @Override
            public void onSuccess(boolean value) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }}

    //CREATE WAHANA ACTIVITY
