package service;
import dto.Page.PhiTreHenPage;
import dto.Page.TieuDePage;
import dto.PhiTreHenDto;
import dto.Post.PostPhiTreHen;
import retrofit2.Call;
import retrofit2.http.*;

public interface PhiTreHenService {

    @GET("api/v1/nhom6/phitrehen")
    Call<PhiTreHenPage> getAllPhiTreHen();

    @POST("api/v1/nhom6/phitrehen")
    Call<Long> addPhiTreHen(@Body PostPhiTreHen postPhiTreHen);

    @GET("api/v1/nhom6/phitrehen/kh/{id}")
    Call<PhiTreHenPage> getPhiTreHenByIdKH(@Path("id") long id);

    @PUT("api/v1/nhom6/phitrehen/{id}")
    Call<Long> updateTienDaTraPhiTre(@Path("id") long id,@Body PhiTreHenDto phiTreHenDto);
}
