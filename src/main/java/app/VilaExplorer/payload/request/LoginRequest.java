package app.VilaExplorer.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@NotBlank
  private String email;

	@NotBlank
	private String password;
}