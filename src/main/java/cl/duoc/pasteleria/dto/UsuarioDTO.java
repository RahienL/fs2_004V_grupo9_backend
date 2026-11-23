package cl.duoc.pasteleria.dto;

import cl.duoc.pasteleria.model.Rol;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String region;
    private String comuna;
    private String direccion;
    private LocalDate fechaEntregaPreferida;
    private Rol rol;
    private String estado;
}
