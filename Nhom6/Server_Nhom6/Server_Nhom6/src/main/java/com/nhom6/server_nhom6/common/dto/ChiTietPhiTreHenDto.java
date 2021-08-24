package com.nhom6.server_nhom6.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhom6.server_nhom6.domain.ChiTietPhiTreHenID;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.PhiTreHen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhiTreHenDto {

    private long maDia;


    private long maphiTreHen;

    private double phiTre;

    private LocalDate ngayTra;


    private int soNgayTreHen;



}
