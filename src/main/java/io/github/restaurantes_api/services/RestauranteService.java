package io.github.restaurantes_api.services;


import io.github.restaurantes_api.dto.RestauranteResponse;
import io.github.restaurantes_api.dto.RestauranteUpdateDTO;
import io.github.restaurantes_api.entities.Endereco;
import io.github.restaurantes_api.entities.Restaurante;
import io.github.restaurantes_api.mapper.RestauranteMapper;
import io.github.restaurantes_api.dto.RestauranteRequest;
import io.github.restaurantes_api.respositories.EnderecoRepository;
import io.github.restaurantes_api.respositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteMapper restauranteMapper;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioService usuarioService;
    private final EnderecoRepository enderecoRepository;

    public Restaurante cadastrar(RestauranteRequest request) {
        usuarioService.validarUsuarioAutenticadoEProprietario(request.getProprietarioId());
        Restaurante restaurante = restauranteRepository.save(restauranteMapper.fromDTO(request));
        return restaurante;
    }

    public List<RestauranteResponse> listarTodos() {

        return restauranteRepository.findAll().stream()
                .map(restauranteMapper::toResponseDTO)
                .toList();
    }

    public RestauranteResponse buscarPorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        return restauranteMapper.toResponseDTO(restaurante);
    }

    public void excluir(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        restauranteRepository.delete(restaurante);
    }

    public RestauranteResponse atualizar(RestauranteUpdateDTO dto, Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Endereco endereco = enderecoRepository.findById(restaurante.getEndereco().getId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        endereco.setRua(dto.getEnderecoRequest().getRua());
        endereco.setNumero(dto.getEnderecoRequest().getNumero());
        endereco.setBairro(dto.getEnderecoRequest().getBairro());
        endereco.setCidade(dto.getEnderecoRequest().getCidade());
        endereco.setCep(dto.getEnderecoRequest().getCep());
        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        restaurante.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        restaurante.setEndereco(endereco);


        Restaurante salvo = restauranteRepository.save(restaurante);

        return restauranteMapper.toResponseDTO(salvo);
    }

}

