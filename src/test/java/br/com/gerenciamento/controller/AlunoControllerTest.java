package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
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

import static java.lang.reflect.Array.get;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void deveBuscarAlunoPorId() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);

        mockMvc.perform(get("/alunos/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("Vinicius"));
    }

    private RequestBuilder get(String path) {
        return null;
    }

    @Test
    public void deveCriarNovoAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.CONTABILIDADE);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");

        mockMvc.perform((RequestBuilder) post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(aluno))))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("João"));
    }

    @Test
    public void deveAtualizarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setNome("Maria");
        aluno.setTurno(Turno.VESPERTINO);
        aluno.setCurso(Curso.ENGENHARIA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("789456");
        serviceAluno.save(aluno);

        aluno.setNome("Maria Atualizada");

        mockMvc.perform((RequestBuilder) put("/alunos/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.asMediaType(MediaType.valueOf(objectMapper.writeValueAsString(aluno)))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.nome").value("Maria Atualizada"));
    }

    @Test
    public void deveDeletarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setNome("Lucas");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("852963");
        serviceAluno.save(aluno);

        mockMvc.perform(delete("/alunos/3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/alunos/3"))
                .andExpect(status().isNotFound());
    }

}
