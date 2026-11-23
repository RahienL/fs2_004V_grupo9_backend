package cl.duoc.pasteleria.service;

import cl.duoc.pasteleria.dto.*;
import cl.duoc.pasteleria.exception.ResourceNotFoundException;
import cl.duoc.pasteleria.model.*;
import cl.duoc.pasteleria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private DetalleBoletaRepository detalleBoletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<BoletaDTO> listarPorUsuario(Long usuarioId) {
        List<Boleta> boletas = boletaRepository.findByUsuarioId(usuarioId);
        return boletas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BoletaDTO> listarTodas() {
        List<Boleta> boletas = boletaRepository.findAll();
        return boletas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BoletaDTO obtenerPorId(Long id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));
        return toDTO(boleta);
    }

    public BoletaDTO crear(Long usuarioId, CrearBoletaRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        BigDecimal total = BigDecimal.ZERO;
        
        Boleta boleta = Boleta.builder()
                .usuario(usuario)
                .total(total)
                .estado(EstadoBoleta.PENDIENTE)
                .observaciones(request.getObservaciones())
                .build();

        Boleta boletaGuardada = boletaRepository.save(boleta);

        BigDecimal totalFinal = BigDecimal.ZERO;
        for (CrearBoletaRequest.DetalleBoletaRequest item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

            BigDecimal subtotal = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
            totalFinal = totalFinal.add(subtotal);

            DetalleBoleta detalle = DetalleBoleta.builder()
                    .boleta(boletaGuardada)
                    .producto(producto)
                    .cantidad(item.getCantidad())
                    .precioUnitario(item.getPrecioUnitario())
                    .subtotal(subtotal)
                    .build();

            detalleBoletaRepository.save(detalle);
        }

        boletaGuardada.setTotal(totalFinal);
        boletaGuardada = boletaRepository.save(boletaGuardada);

        return toDTO(boletaGuardada);
    }

    public BoletaDTO cambiarEstado(Long id, String nuevoEstado) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));
        
        try {
            boleta.setEstado(EstadoBoleta.valueOf(nuevoEstado.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado no válido");
        }

        Boleta actualizada = boletaRepository.save(boleta);
        return toDTO(actualizada);
    }

    public void cancelar(Long id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));
        
        if (!boleta.getEstado().equals(EstadoBoleta.PENDIENTE)) {
            throw new IllegalArgumentException("Solo se pueden cancelar órdenes pendientes");
        }

        boleta.setEstado(EstadoBoleta.CANCELADA);
        boletaRepository.save(boleta);
    }

    private BoletaDTO toDTO(Boleta boleta) {
        List<DetalleBoletaDTO> detalles = boleta.getDetalles().stream()
                .map(d -> DetalleBoletaDTO.builder()
                        .id(d.getId())
                        .productoId(d.getProducto().getId())
                        .productoNombre(d.getProducto().getNombre())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return BoletaDTO.builder()
                .id(boleta.getId())
                .usuarioId(boleta.getUsuario().getId())
                .fecha(boleta.getFecha())
                .total(boleta.getTotal())
                .estado(boleta.getEstado().toString())
                .observaciones(boleta.getObservaciones())
                .detalles(detalles)
                .build();
    }
}
