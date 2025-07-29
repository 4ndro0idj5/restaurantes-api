package io.github.restaurantes_api.infrastructure.data.repositories;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

}
