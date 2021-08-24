package com.nhom6.server_nhom6.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChiTietHoaDonID implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name = "maHoaDon")
	private long maHoaDon;
	@Column(name = "maDia")
	private long maDia;
}
