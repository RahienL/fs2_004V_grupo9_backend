package cl.duoc.pasteleria.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoletaDTO {
    private Long id;
    private Long usuarioId;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;
    private String observaciones;
    private List<DetalleBoletaDTO> detalles;
}
