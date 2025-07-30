package io.github.restaurantes_api.infrastructure.data.repositories;

import io.github.restaurantes_api.core.domain.entities.Item;
import io.github.restaurantes_api.infrastructure.data.entities.ItemEntity;
import io.github.restaurantes_api.infrastructure.data.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByRestauranteId(Long restauranteId);
    Optional<ItemEntity> findByIdAndRestauranteId(Long itemId, Long restauranteId);
}
