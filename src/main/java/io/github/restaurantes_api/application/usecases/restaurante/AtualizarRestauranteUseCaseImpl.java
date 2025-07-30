package io.github.restaurantes_api.application.usecases.restaurante;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.domain.usecases.restaurante.AtualizarRestauranteUseCase;
import io.github.restaurantes_api.core.dtos.RestauranteUpdateDTO;
import io.github.restaurantes_api.core.gateways.RestauranteGateway;
import io.github.restaurantes_api.core.gateways.UsuarioServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarRestauranteUseCaseImpl implements AtualizarRestauranteUseCase {

    private final RestauranteGateway gateway;
    private final UsuarioServiceGateway usuarioService;

    @Override
    public Restaurante executar(RestauranteUpdateDTO dto, Long restauranteId, Long usuarioId) {
        usuarioService.validarUsuarioAutenticado(usuarioId);

        var restaurante = gateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        if (!restaurante.getProprietarioId().equals(usuarioId)) {
            throw new RuntimeException("Usuário não tem permissão para atualizar este restaurante");
        }

        restaurante.setNome(dto.getNome() != null ? dto.getNome() : restaurante.getNome());
        restaurante.setCategoria(dto.getCategoria() != null ? dto.getCategoria() : restaurante.getCategoria());
        restaurante.setHorarioFuncionamento(dto.getHorarioFuncionamento() != null ? dto.getHorarioFuncionamento() : restaurante.getHorarioFuncionamento());

        if (dto.getEnderecoRequest() != null) {
            Endereco endereco = restaurante.getEndereco();
            endereco.setRua(dto.getEnderecoRequest().getRua() != null ? dto.getEnderecoRequest().getRua() : endereco.getRua());
            endereco.setNumero(dto.getEnderecoRequest().getNumero() != null ? dto.getEnderecoRequest().getNumero() : endereco.getNumero());
            endereco.setBairro(dto.getEnderecoRequest().getBairro() != null ? dto.getEnderecoRequest().getBairro() : endereco.getBairro());
            endereco.setCidade(dto.getEnderecoRequest().getCidade() != null ? dto.getEnderecoRequest().getCidade() : endereco.getCidade());
            endereco.setCep(dto.getEnderecoRequest().getCep() != null ? dto.getEnderecoRequest().getCep() : endereco.getCep());

            restaurante.setEndereco(endereco);
        }

        return gateway.salvar(restaurante);
    }
}
