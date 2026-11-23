package cl.duoc.pasteleria.repository;

import cl.duoc.pasteleria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigo(String codigo);
    List<Producto> findByDisponibleTrue();
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByDisponibleTrueAndCategoriaId(Long categoriaId);
}
