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
import org.springframework.stereotype.Service;


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

    public List<RestauranteResponse> listarTodos(Long idUsuario) {
        usuarioService.validarUsuarioAutenticado(idUsuario);
        return restauranteRepository.findAll().stream()
                .map(restauranteMapper::toResponseDTO)
                .toList();
    }

    public RestauranteResponse buscarPorId(Long id, Long idUsuario) {
        usuarioService.validarUsuarioAutenticado(idUsuario);
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        return restauranteMapper.toResponseDTO(restaurante);
    }

    public void excluir(Long id, Long idUsuario) {

        usuarioService.validarUsuarioAutenticado(idUsuario);

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        if (!restaurante.getProprietarioId().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para excluir este restaurante");
        }


        restauranteRepository.delete(restaurante);
    }

    public RestauranteResponse atualizar(RestauranteUpdateDTO dto, Long id, Long idUsuario){

        usuarioService.validarUsuarioAutenticado(idUsuario);

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        if (!restaurante.getProprietarioId().equals(idUsuario)) {
            throw new RuntimeException("Usuário não tem permissão para atualizar este restaurante");
        }

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

