package io.github.restaurantes_api.respositories;

import io.github.restaurantes_api.entities.Item;
import io.github.restaurantes_api.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByRestaurante(Restaurante restaurante);
}

