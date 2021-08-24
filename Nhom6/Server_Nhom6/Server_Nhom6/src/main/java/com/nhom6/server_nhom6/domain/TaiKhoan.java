package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity(name = "TaiKhoan")
@NoArgsConstructor
@Getter
@Setter
public class TaiKhoan extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String password;

	private  boolean active = true;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId")
	private Role role;

	public TaiKhoan(int id, String email, String password , Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
