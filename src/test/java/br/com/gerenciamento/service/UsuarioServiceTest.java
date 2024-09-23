package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos");
        usuario.setEmail("carlos@gmail.com");
        this.serviceUsuario.save(usuario);

        Usuario usuarioRetorno = this.serviceUsuario.getById(1L);
        Assert.assertNotNull(usuarioRetorno);
    }

    @Test
    public void salvarUsuarioSemEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNome("Ana");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceUsuario.save(usuario);
        });
    }

    @Test
    public void atualizarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setNome("Roberto");
        usuario.setEmail("roberto@gmail.com");
        this.serviceUsuario.save(usuario);

        usuario.setEmail("roberto.novo@gmail.com");
        this.serviceUsuario.save(usuario);

        Usuario usuarioAtualizado = this.serviceUsuario.getById(3L);
        Assert.assertEquals("roberto.novo@gmail.com", usuarioAtualizado.getEmail());
    }

    @Test
    public void deletarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(4L);
        usuario.setNome("Paula");
        usuario.setEmail("paula@gmail.com");
        this.serviceUsuario.save(usuario);

        this.serviceUsuario.deleteById(4L);

        Assert.assertNull(this.serviceUsuario.getById(4L));
    }

}
