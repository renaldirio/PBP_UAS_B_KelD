package com.filbertfilbert.uts.API;

import com.filbertfilbert.uts.DAO.WahanaDAO;
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
                                      @Field("rating")String rating, @Field("deskripsi")String deskripsi,
                                      @Field("foto")String foto);

    @POST("wahana/{id}")
    @FormUrlEncoded
    Call<WahanaResponse> updateWahana(@Field("nama_wahana")String nama_wahana, @Field("lokasi")String lokasi,
                                      @Field("rating")String rating, @Field("deskripsi")String deskripsi,
                                      @Field("foto")String foto);

    @POST("wahana/{id}")
    Call<WahanaResponse> deleteWahana(@Path("id")String id);

//    Call<WahanaResponse> createWahana(String toString, String toString1, String toString2, String toString3, String toString4);
}
