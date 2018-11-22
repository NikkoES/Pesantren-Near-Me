package com.pesantren.boardingschool.model.response;

import com.google.gson.annotations.SerializedName;
import com.pesantren.boardingschool.model.data.Berita;
import com.pesantren.boardingschool.model.data.Pesantren;

import java.io.Serializable;
import java.util.List;

public class ResponseBerita implements Serializable {

    @SerializedName("statust")
    private boolean status;
    @SerializedName("berita1")
    private List<Berita> listBerita;

    public boolean isStatus() {
        return status;
    }

    public List<Berita> getListBerita() {
        return listBerita;
    }
}
