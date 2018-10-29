package com.pesantren.boardingschool.model;

import java.io.Serializable;

public class Berita implements Serializable {

    String judul, konten, tanggal, image;

    public Berita(String judul, String konten, String tanggal, String image) {
        this.judul = judul;
        this.konten = konten;
        this.tanggal = tanggal;
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public String getKonten() {
        return konten;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getImage() {
        return image;
    }
}
