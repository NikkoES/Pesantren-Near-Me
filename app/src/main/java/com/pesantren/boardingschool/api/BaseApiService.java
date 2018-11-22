package com.pesantren.boardingschool.api;

import com.pesantren.boardingschool.model.response.ResponseBerita;
import com.pesantren.boardingschool.model.response.ResponsePesantren;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BaseApiService {

    @GET("tampil_pondokpesantren.php")
    Call<ResponsePesantren> getAllPesantren();

    @GET("tampil_berita.php")
    Call<ResponseBerita> getAllBerita();
}