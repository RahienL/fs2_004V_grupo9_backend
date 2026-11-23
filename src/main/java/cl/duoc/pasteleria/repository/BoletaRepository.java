package cl.duoc.pasteleria.repository;

import cl.duoc.pasteleria.model.Boleta;
import cl.duoc.pasteleria.model.EstadoBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioId(Long usuarioId);
    List<Boleta> findByEstado(EstadoBoleta estado);
    List<Boleta> findByUsuarioIdAndEstado(Long usuarioId, EstadoBoleta estado);
}
