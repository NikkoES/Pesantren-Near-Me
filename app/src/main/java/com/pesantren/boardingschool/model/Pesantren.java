package com.pesantren.boardingschool.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pesantren implements Serializable {

    String namaPesantren, aliran, alamat, latitude, longitude, image;

    public Pesantren(String namaPesantren, String aliran, String alamat, String latitude, String longitude, String image) {
        this.namaPesantren = namaPesantren;
        this.aliran = aliran;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public String getNamaPesantren() {
        return namaPesantren;
    }

    public String getAliran() {
        return aliran;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImage() {
        return image;
    }
}
