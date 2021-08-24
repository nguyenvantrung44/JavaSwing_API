package service;

import dto.LoaiDiaDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;

public interface LoaiDiaService {
    @Headers("Accept: application/json; charset=utf-8")

    @GET("api/v1/nhom6/loaidia")
    Call<List<LoaiDiaDto>> getDanhSachLoaiDia();

    @GET("api/v1/nhom6/loaidia/{id}")
    Call<LoaiDiaDto> getLoaiDiaById(@Path("id") int id);

}
