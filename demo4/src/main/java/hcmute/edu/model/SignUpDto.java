package hcmute.edu.model;

import lombok.Data;

@Data
public class SignUpDto {

	private String username;
	private String password;
	private String email;
	private String fullName;
	private boolean enabled;
}
