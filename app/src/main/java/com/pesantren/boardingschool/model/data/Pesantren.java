package com.pesantren.boardingschool.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pesantren implements Serializable {

    @SerializedName("kode")
    private String kode;
    @SerializedName("nama")
    private String nama;
    @SerializedName("tlp")
    private String tlp;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("website")
    private String website;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;
    @SerializedName("gambar")
    private String gambar;

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getTlp() {
        return tlp;
    }

    public String getKategori() {
        return kategori;
    }

    public String getWebsite() {
        return website;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getGambar() {
        return gambar;
    }
}
