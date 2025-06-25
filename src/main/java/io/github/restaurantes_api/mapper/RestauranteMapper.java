package io.github.restaurantes_api.mapper;

import io.github.restaurantes_api.entities.Endereco;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.dto.RestauranteRequest;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {
    public Restaurante fromDTO(RestauranteRequest dto){

        return Restaurante.builder()
                .nome(dto.getNome())
                .telefone(dto.getTelefone())
                .categoria(dto.getCategoria())
                .cnpj(dto.getCnpj())
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
}
