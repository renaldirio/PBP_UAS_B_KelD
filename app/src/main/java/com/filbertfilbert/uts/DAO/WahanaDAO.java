package com.filbertfilbert.uts.DAO;

import com.google.gson.annotations.SerializedName;

public class WahanaDAO {

    @SerializedName("id")
    private int id;

    @SerializedName("nama_wahana")
    private String nama_wahana;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("rating")
    private String rating;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("foto")
    private String foto;

    public WahanaDAO(int id, String nama_wahana, String lokasi, String rating, String deskripsi, String foto) {
        this.id = id;
        this.nama_wahana = nama_wahana;
        this.lokasi = lokasi;
        this.rating = rating;
        this.deskripsi = deskripsi;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_wahana() {
        return nama_wahana;
    }

    public void setNama_wahana(String nama_wahana) {
        this.nama_wahana = nama_wahana;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
