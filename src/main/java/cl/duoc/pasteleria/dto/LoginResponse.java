package cl.duoc.pasteleria.dto;

import cl.duoc.pasteleria.model.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String nombre;
    private String correo;
    private Rol rol;
    private String token;
}
