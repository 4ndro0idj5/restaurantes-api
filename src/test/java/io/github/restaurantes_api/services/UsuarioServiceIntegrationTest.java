package io.github.restaurantes_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(UsuarioService.class)
class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MockRestServiceServer server;

    @TestConfiguration
    static class RestTemplateConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Test
    void deveChamarApiUsuariosCorretamente() {
        server.expect(requestTo("http://localhost:8080/api/v1/usuarios/1"))
                .andRespond(withSuccess("{ \"autenticado\": true, \"perfil\": \"PROPRIETARIO\" }",
                        MediaType.APPLICATION_JSON));

        assertDoesNotThrow(() -> usuarioService.validarUsuarioAutenticadoEProprietario(1L));
    }
}
