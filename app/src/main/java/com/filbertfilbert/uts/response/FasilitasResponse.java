package com.filbertfilbert.uts.response;

import com.filbertfilbert.uts.DAO.FasilitasDAO;
import com.filbertfilbert.uts.DAO.WahanaDAO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FasilitasResponse {
    @SerializedName("data")
    @Expose
    private List<FasilitasDAO> fasilitas;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FasilitasDAO> getFasilitas() { return fasilitas; }

    public void setFasilitas(List<FasilitasDAO> wahana) { this.fasilitas = fasilitas; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }


}
