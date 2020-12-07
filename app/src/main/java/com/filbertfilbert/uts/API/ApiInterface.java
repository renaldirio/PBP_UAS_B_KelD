package com.filbertfilbert.uts.API;

import com.filbertfilbert.uts.DAO.FasilitasDAO;
import com.filbertfilbert.uts.DAO.WahanaDAO;
import com.filbertfilbert.uts.response.FasilitasResponse;
import com.filbertfilbert.uts.response.WahanaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("wahana")
    Call<List<WahanaDAO>> getAllWahana();

    @GET("wahana/{id}")
    Call<WahanaResponse> getWahanaById(@Path("id")int id,
                                       @Query("data")String data);

    @POST("wahana/add")
    @FormUrlEncoded
    Call<WahanaResponse> createWahana(@Field("nama_wahana")String nama_wahana, @Field("lokasi")String lokasi,
                                      @Field("rating")String rating, @Field("deskripsi")String deskripsi);

    @POST("wahana/update/{id}")
    @FormUrlEncoded
    Call<WahanaResponse> updateWahana(@Path("id") int id,@Field("nama_wahana")String nama_wahana, @Field("lokasi")String lokasi,
                                      @Field("rating")String rating, @Field("deskripsi")String deskripsi);

    @POST("wahana/delete/{id}")
    Call<WahanaResponse> deleteWahana(@Path("id")int id);

/////////////////////////////////////////////////// FASILITAS

    @GET("fasilitas")
    Call<List<FasilitasDAO>> getAllFasilitas();

    @GET("fasilitas/{id}")
    Call<FasilitasResponse> getFasilitasById(@Path("id")int id,
                                             @Query("data")String data);

    @POST("fasilitas/add")
    @FormUrlEncoded
    Call<FasilitasResponse> createFasilitas(@Field("nama_fasilitas")String nama_fasilitas, @Field("lokasi")String lokasi,
                                      @Field("deskripsi")String deskripsi);

    @POST("fasilitas/update/{id}")
    @FormUrlEncoded
    Call<FasilitasResponse> updateFasilitas(@Path("id") int id,@Field("nama_fasilitas")String nama_fasilitas, @Field("lokasi")String lokasi,
                                      @Field("deskripsi")String deskripsi);

    @POST("fasilitas/delete/{id}")
    Call<FasilitasResponse> deleteFasilitas(@Path("id")int id);
}
