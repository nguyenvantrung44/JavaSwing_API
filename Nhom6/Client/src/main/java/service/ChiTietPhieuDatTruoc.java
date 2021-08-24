package service;

import dto.CTPDTByTieuDeId;
import dto.Page.ChiTietPhieuDatTruocPage;
import retrofit2.Call;
import retrofit2.http.*;
import status.TrangThaiDatTruoc;

import java.util.List;

public interface ChiTietPhieuDatTruoc {
    @POST("api/v1/nhom6/ctpdt/{idPhieuDatTruoc}/{idTieuDe}")
    Call<Long> addChiTietPhieuDatTruoc(@Path("idPhieuDatTruoc") Long idPhieuDatTruoc, @Path("idTieuDe") Long idTieuDe);

    @GET("api/v1/nhom6/ctpdt/{id}")
    Call<ChiTietPhieuDatTruocPage> getChiTietPhieuDatTruocById(@Path("id") long id);

    @GET("api/v1/nhom6/ctpdt/getAllChitietPhieuDatTruocByTieuDeId/{tieuDeId}")
    Call<List<CTPDTByTieuDeId>> getAllChitietPhieuDatTruocByTieuDeId(@Path("tieuDeId") Long tieuDeId);

    @PUT("api/v1/nhom6/ctpdt/thay-doi-trang-thai/{idPhieuDatTruoc}/{idTieuDe}/{maDia}")
    Call<Long> thayDoiTrangThai(@Path("idPhieuDatTruoc") Long idPhieuDatTruoc
            , @Path("idTieuDe") Long idTieuDe,@Path("maDia") Long maDia, @Query("trangThai") String trangThaiDatTruoc);
}
