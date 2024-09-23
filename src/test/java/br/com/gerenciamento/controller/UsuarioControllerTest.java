package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void deveBuscarUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos");
        usuario.setEmail("carlos@gmail.com");
        serviceUsuario.save(usuario);

        mockMvc.perform((org.springframework.test.web.servlet.RequestBuilder) get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("Carlos"));
    }

    private RequestBuilder get(String path) {
        return null;
    }

    @Test
    public void deveCriarNovoUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Ana");
        usuario.setEmail("ana@gmail.com");

        mockMvc.perform((org.springframework.test.web.servlet.RequestBuilder) (org.springframework.test.web.servlet.RequestBuilder) post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(usuario))))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("Ana"));
    }

    @Test
    public void deveAtualizarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNome("Roberto");
        usuario.setEmail("roberto@gmail.com");
        serviceUsuario.save(usuario);

        usuario.setEmail("roberto.novo@gmail.com");

        mockMvc.perform((RequestBuilder) put("/usuarios/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(usuario))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.email").value("roberto.novo@gmail.com"));
    }

    @Test
    public void deveDeletarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setNome("Paula");
        usuario.setEmail("paula@gmail.com");
        serviceUsuario.save(usuario);

        mockMvc.perform(delete("/usuarios/3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/usuarios/3"))
                .andExpect(status().isNotFound());
    }

}
