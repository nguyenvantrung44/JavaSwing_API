package dto.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PostLogin {

	private String email;
	private String password;

	public PostLogin(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
