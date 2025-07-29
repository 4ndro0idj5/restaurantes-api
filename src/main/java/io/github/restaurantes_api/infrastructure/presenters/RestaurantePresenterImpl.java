package io.github.restaurantes_api.infrastructure.presenters;

import io.github.restaurantes_api.core.domain.entities.Endereco;
import io.github.restaurantes_api.core.domain.entities.Restaurante;
import io.github.restaurantes_api.core.dtos.EnderecoRequest;
import io.github.restaurantes_api.core.dtos.RestauranteRequest;
import io.github.restaurantes_api.core.dtos.RestauranteResponse;
import io.github.restaurantes_api.core.interfaces.RestaurantePresenter;
import org.springframework.stereotype.Component;

@Component
public class RestaurantePresenterImpl implements RestaurantePresenter {
    public Restaurante fromDTO(RestauranteRequest dto){

        return Restaurante.builder()
                .nome(dto.getNome())
                .categoria(dto.getCategoria())
                .horarioFuncionamento(dto.getHorarioFuncionamento())
                .proprietarioId(dto.getProprietarioId())
                .endereco(Endereco.builder()
                        .rua(dto.getEnderecoRequest().getRua())
                        .numero(dto.getEnderecoRequest().getNumero())
                        .bairro(dto.getEnderecoRequest().getBairro())
                        .cidade(dto.getEnderecoRequest().getCidade())
                        .cep(dto.getEnderecoRequest().getCep())
                        .build())
                .build();
    }

    public RestauranteResponse toResponseDTO(Restaurante restaurante) {

        return RestauranteResponse.builder()
                .nome(restaurante.getNome())
                .categoria(restaurante.getCategoria())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .enderecoRequest(toEnderecoRequest(restaurante.getEndereco()))
                .build();
    }


    public EnderecoRequest toEnderecoRequest(Endereco endereco) {
        if (endereco == null) return null;
        return EnderecoRequest.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .build();
    }
}