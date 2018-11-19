package com.pesantren.boardingschool.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pesantren.boardingschool.model.data.Pesantren;

import java.io.Serializable;
import java.util.List;

public class ResponsePesantren implements Serializable {

    @SerializedName("statust")
    private boolean status;
    @SerializedName("pondok_pesantren")
    private List<Pesantren> pondokPesantren;

    public boolean isStatus() {
        return status;
    }

    public List<Pesantren> getPondokPesantren() {
        return pondokPesantren;
    }
}
