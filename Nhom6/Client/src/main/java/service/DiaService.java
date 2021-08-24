package service;

import dto.DiaDto;
import dto.Page.DiaPage;
import dto.Post.PostDia;
import retrofit2.Call;
import retrofit2.http.*;

public interface DiaService {
    @Headers("Accept: application/json; charset=utf-8")

    @GET("api/v1/nhom6/dia")
    Call<DiaPage> getDanhSachDia();

    @GET("api/v1/nhom6/dia/hoadon/{id}")
    Call<DiaDto> getDiaTra(@Path("id") int id);

    @POST("api/v1/nhom6/dia")
    Call<Void> addDia(@Body PostDia postDia);

    @DELETE("api/v1/nhom6/dia/{id}")
    Call<Void> deleteDia(@Path("id") int id);

    @GET("api/v1/nhom6/dia/{id}")
    Call<DiaDto> getDiaById(@Path("id") int id);

    @PUT("api/v1/nhom6/dia/updateDia/{idDia}")
    Call<Long> updateTrangThaiDia(@Path("idDia")long idDia,@Query("trangThai") String trangThai);

}
