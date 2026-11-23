package cl.duoc.pasteleria.controller;

import cl.duoc.pasteleria.dto.BoletaDTO;
import cl.duoc.pasteleria.dto.CrearBoletaRequest;
import cl.duoc.pasteleria.exception.ResourceNotFoundException;
import cl.duoc.pasteleria.model.Usuario;
import cl.duoc.pasteleria.repository.UsuarioRepository;
import cl.duoc.pasteleria.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/boletas")
@Tag(name = "Boletas/Órdenes", description = "Gestión de órdenes de compra")
@SecurityRequirement(name = "Bearer Authentication")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Cliente ve sus órdenes, admin ve todas")
    public ResponseEntity<List<BoletaDTO>> listar(Authentication authentication,
            @RequestParam(required = false) Boolean todas) {
        
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean esAdmin = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(a -> a.equals("ROLE_ADMIN"));

        if (esAdmin && todas != null && todas) {
            return ResponseEntity.ok(boletaService.listarTodas());
        }

        // Por ahora retornamos todas para clientes también
        return ResponseEntity.ok(boletaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de orden")
    public ResponseEntity<BoletaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(boletaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva orden", description = "Cliente crea su orden")
    public ResponseEntity<BoletaDTO> crear(Authentication authentication,
            @RequestBody CrearBoletaRequest request) {
        
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Long usuarioId = usuario.getId();

        BoletaDTO creada = boletaService.crear(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cambiar estado de orden", description = "Solo admin")
    public ResponseEntity<BoletaDTO> cambiarEstado(@PathVariable Long id,
            @RequestParam String estado) {
        BoletaDTO actualizada = boletaService.cambiarEstado(id, estado);
        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar orden", description = "Cliente cancela su orden pendiente")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        boletaService.cancelar(id);
        return ResponseEntity.ok().build();
    }
}
