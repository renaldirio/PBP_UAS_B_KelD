package com.filbertfilbert.uts.model;

import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Wahana extends BaseObservable implements Serializable{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name="namaWahana")
    public String namaWahana;

    @ColumnInfo(name = "alamatWahana")
    public String alamatWahana;

    @ColumnInfo(name = "ratingWahana")
    public String ratingWahana;

    @ColumnInfo(name = "hargaWahana")
    public Double hargaWahana;

    public Wahana(int id, String namaWahana, String alamatWahana, String ratingWahana, Double hargaWahana) {
        this.id = id;
        this.namaWahana = namaWahana;
        this.alamatWahana = alamatWahana;
        this.ratingWahana = ratingWahana;
        this.hargaWahana = hargaWahana;
    }

    public Double getHargaWahana() {
        return hargaWahana;
    }

    public void setHargaWahana(Double hargaWahana) {
        this.hargaWahana = hargaWahana;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaWahana() {
        return namaWahana;
    }

    public void setNamaWahana(String namaWahana) {
        this.namaWahana = namaWahana;
    }

    public String getAlamatWahana() {
        return alamatWahana;
    }

    public void setAlamatWahana(String alamatWahana) {
        this.alamatWahana = alamatWahana;
    }

    public String getRatingWahana() {
        return ratingWahana;
    }

    public void setRatingWahana(String ratingWahana) {
        this.ratingWahana = ratingWahana;
    }
}