package io.github.restaurantes_api.services;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.entities.Item;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.restaurantes_api.respositories.ItemRepository;
import io.github.restaurantes_api.respositories.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UsuarioService usuarioService;


    public ItemDTO cadastrarItem(Long restauranteId, ItemDTO dto) {



        usuarioService.validarUsuarioAutenticadoEProprietario(dto.getProprietarioId());

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        Item item = Item.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .foto(dto.getFoto())
                .build();
        item.setRestaurante(restaurante);
        Item salvo = itemRepository.save(item);
        return itemMapper.toItemDTO(salvo);
    }

    public List<ItemDTO> listarItensPorRestaurante(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        return itemRepository.findByRestaurante(restaurante)
                .stream()
                .map(itemMapper::toItemDTO)
                .toList();
    }

    public Optional<ItemDTO> buscarPorId(Long id, Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        return itemRepository.findById(id)
                .map(itemMapper::toItemDTO);
    }
}