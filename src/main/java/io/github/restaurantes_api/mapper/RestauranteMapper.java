package io.github.restaurantes_api.mapper;

import io.github.restaurantes_api.dto.EnderecoRequest;
import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.entities.Endereco;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.dto.RestauranteRequest;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {
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

    private EnderecoRequest toEnderecoRequest(Endereco endereco) {
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
