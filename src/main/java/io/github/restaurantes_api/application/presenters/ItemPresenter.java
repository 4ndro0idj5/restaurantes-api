package io.github.restaurantes_api.application.presenters;

import io.github.restaurantes_api.application.mapper.ItemMapper;
import io.github.restaurantes_api.core.domain.usecases.item.*;
import io.github.restaurantes_api.core.dtos.ItemDTO;
import io.github.restaurantes_api.core.dtos.ItemUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPresenter {

    private final CadastrarItemUseCase cadastrarItemUC;
    private final ListarItensPorRestauranteUseCase listarItensUC;
    private final BuscarItemPorIdUseCase buscarItemPorIdUC;
    private final AtualizarItemUseCase atualizarItemUC;
    private final ExcluirItemUseCase excluirItemUC;
    private final ItemMapper itemMapper;

    public void cadastrar(Long restauranteId, ItemDTO dto, Long usuarioId) {
        cadastrarItemUC.executar(restauranteId, dto, usuarioId);
    }

    public List<ItemDTO> listar(Long restauranteId, Long usuarioId) {
        return listarItensUC.executar(restauranteId, usuarioId)
                .stream()
                .map(itemMapper::toItemDTO)
                .toList();
    }

    public Optional<ItemDTO> buscarPorId(Long id, Long restauranteId, Long usuarioId) {
        return buscarItemPorIdUC.executar(id, restauranteId, usuarioId)
                .map(itemMapper::toItemDTO);
    }

    public void atualizar(Long restauranteId, Long id, ItemUpdateDTO dto, Long usuarioId) {
        atualizarItemUC.executar(restauranteId, dto, id, usuarioId);
    }

    public void excluir(Long restauranteId, Long id, Long usuarioId) {
        excluirItemUC.executar(restauranteId, id, usuarioId);
    }

}
