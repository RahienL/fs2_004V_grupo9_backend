package cl.duoc.pasteleria.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String correo;
    private String password;
}
