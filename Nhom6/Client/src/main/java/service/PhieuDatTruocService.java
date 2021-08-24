package service;

import dto.Page.PhieuDatTruocPage;
import dto.Post.PostPhieuDatTruoc;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PhieuDatTruocService {

    @POST("api/v1/nhom6/phieudattruoc")
    Call<Long> addPhieuDatTruoc(@Body PostPhieuDatTruoc postPhieuDatTruoc);

    @GET("api/v1/nhom6/phieudattruoc/kh/{id}")
    Call<PhieuDatTruocPage> getPhieuDatTruocByIdKhachHang(@Path("id")long id);

    @GET("api/v1/nhom6/phieudattruoc")
    Call<PhieuDatTruocPage> getAllPhieuDatTruoc();
}
