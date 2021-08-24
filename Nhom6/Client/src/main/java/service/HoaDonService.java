package service;

import dto.Post.PostHoaDon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HoaDonService {

    @POST("api/v1/nhom6/hoadon")
    Call<Long> addHoaDon(@Body PostHoaDon postHoaDon);
}
