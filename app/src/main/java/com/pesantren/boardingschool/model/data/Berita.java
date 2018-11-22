package com.pesantren.boardingschool.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Berita implements Serializable {

    @SerializedName("id_berita")
    private String idBerita;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("judul")
    private String judul;
    @SerializedName("isi")
    private String isi;
    @SerializedName("penulis")
    private String penulis;
    @SerializedName("gambar")
    private String gambar;

    public String getIdBerita() {
        return idBerita;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getPenulis() {
        return penulis;
    }

    public String getGambar() {
        return gambar;
    }
}
