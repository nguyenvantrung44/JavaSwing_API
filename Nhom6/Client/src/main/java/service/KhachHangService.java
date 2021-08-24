package service;

import dto.KhachHangDto;
import dto.Page.KhachHangPage;
import dto.Page.TieuDePage;
import dto.Post.PostKhachHang;
import dto.Post.PostTieuDe;
import retrofit2.Call;
import retrofit2.http.*;

public interface KhachHangService {
    @Headers("Accept: application/json; charset=utf-8")

    @GET("api/v1/nhom6/khachhang")
    Call<KhachHangPage> getDanhSachKhachHang();

    @GET("api/v1/nhom6/khachhang/{id}")
    Call<KhachHangDto> getKhachHangById(@Path("id") long id);

    @POST("api/v1/nhom6/khachhang")
    Call<Void> addKhacHang(@Body PostKhachHang khachHang);

    @DELETE("api/v1/nhom6/khachhang/{id}")
    Call<Void> deleteKhachHang(@Path("id") long id);

    @PUT("api/v1/nhom6/khachhang/{id}")
    Call<Void> editKhachHang(@Path("id") long id,@Body KhachHangDto khachHangDto);
}
