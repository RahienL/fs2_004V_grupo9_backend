package cl.duoc.pasteleria.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearBoletaRequest {
    private List<DetalleBoletaRequest> items;
    private String observaciones;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DetalleBoletaRequest {
        private Long productoId;
        private Integer cantidad;
        private BigDecimal precioUnitario;
    }
}
