package com.filbertfilbert.uts.DAO;

import com.google.gson.annotations.SerializedName;

public class FasilitasDAO {


    @SerializedName("id")
    private int id;

    @SerializedName("nama_fasilitas")
    private String nama_fasilitas;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("foto")
    private String foto;

    public FasilitasDAO(int id, String nama_fasilitas, String lokasi, String deskripsi, String foto) {
        this.id = id;
        this.nama_fasilitas = nama_fasilitas;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_fasilitas() {
        return nama_fasilitas;
    }

    public void setNama_fasilitas(String nama_fasilitas) {
        this.nama_fasilitas = nama_fasilitas;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
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
