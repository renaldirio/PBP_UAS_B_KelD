package com.filbertfilbert.uts;

import com.filbertfilbert.uts.model.WahanaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


    public interface Service {
        @GET("/wahana")
        Call<List<WahanaModel>> getkelompok();

    }

