package io.github.restaurantes_api.respositories;

import io.github.restaurantes_api.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
