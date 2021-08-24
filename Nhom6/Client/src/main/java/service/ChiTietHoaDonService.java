package service;

import dto.Post.PostChiTietHoaDon;
import dto.Post.PostHoaDon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChiTietHoaDonService {
    @POST("api/v1/nhom6/chitiethoadon")
    Call<Long> addChiTietHoaDon(@Body PostChiTietHoaDon postChiTietHoaDon);
}
