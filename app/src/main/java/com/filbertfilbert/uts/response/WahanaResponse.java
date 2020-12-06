package com.filbertfilbert.uts.response;

import com.filbertfilbert.uts.DAO.WahanaDAO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WahanaResponse {

    @SerializedName("data")
    @Expose
    private List<WahanaDAO> wahana;
    @SerializedName("message")
    @Expose
    private String message;

    public List<WahanaDAO> getWahana() { return wahana; }

    public void setWahana(List<WahanaDAO> wahana) { this.wahana = wahana; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

}
