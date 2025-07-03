package io.github.restaurantes_api.services;

import io.github.restaurantes_api.dto.ItemDTO;
import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.dto.RestauranteUpdateDTO;
import io.github.restaurantes_api.entities.Endereco;
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


        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        Item item = Item.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .foto(dto.getFoto())
                .consumoLocal(dto.isConsumoLocal())
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

    public ItemDTO atualizar(ItemDTO dto, Long id, Long idUsuario){

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        if (!restaurante.getProprietarioId().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para atualizar este prato.");
        }

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado."));

        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setConsumoLocal(dto.isConsumoLocal());
        item.setFoto(dto.getFoto());


        Item salvo = itemRepository.save(item);

        return itemMapper.toItemDTO(salvo);
    }

    public void excluir(Long idRestaurante, Long id, Long idUsuario) {

        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        usuarioService.validarUsuarioAutenticado(restaurante.getProprietarioId());

        if (!restaurante.getProprietarioId().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para excluir este prato.");
        }

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));


        itemRepository.delete(item);
    }

}