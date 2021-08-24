package com.nhom6.server_nhom6.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
public class ChiTietPhieuDatTruocID implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "maPhieuDatTruoc")
	private long maPhieuDatTruoc;
	@Column(name = "maTuaDe")
	private long maTuaDe;

}
