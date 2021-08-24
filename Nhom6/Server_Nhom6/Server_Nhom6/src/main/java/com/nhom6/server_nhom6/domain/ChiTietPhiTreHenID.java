package com.nhom6.server_nhom6.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class ChiTietPhiTreHenID implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "maPhiTreHen")
	private long maPhiTreHen;
	@Column(name = "maDia")
	private long maDia;
	

}
