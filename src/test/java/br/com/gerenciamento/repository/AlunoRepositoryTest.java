package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void buscarPorNome() {
        List<Aluno> alunos = alunoRepository.findByNome("Vinicius");
        Assert.assertFalse(alunos.isEmpty());
    }

    @Test
    public void buscarPorStatus() {
        List<Aluno> alunosAtivos = alunoRepository.findByStatusAtivo(Status.ATIVO);
        Assert.assertFalse(alunosAtivos.isEmpty());
    }

    @Test
    public void buscarPorCurso() {
        List<Aluno> alunosCurso;
        alunosCurso = findBy(Curso.ADMINISTRACAO);
        Assert.assertFalse(alunosCurso.isEmpty());
    }

    private List<Aluno> findBy(Curso curso) {

        return List.of();
    }

    @Test
    public void buscarPorMatricula() {
        Aluno aluno = alunoRepository.findByMatricula("123456");
        Assert.assertNotNull(aluno);
    }

}
