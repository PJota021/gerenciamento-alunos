package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void buscarPorNome() {
        Optional<Usuario> usuario = usuarioRepository.findByNome("Carlos");
        Assert.assertTrue(usuario.isPresent());
    }

    @Test
    public void buscarPorEmail() {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByEmail("carlos@gmail.com"));
        Assert.assertTrue(usuario.isPresent());
    }

    @Test
    public void buscarPorId() {
        Optional<Usuario> usuario = usuarioRepository.findById(1L);
        Assert.assertTrue(usuario.isPresent());
    }

    @Test
    public void deletarUsuario() {
        usuarioRepository.deleteById(4L);
        Optional<Usuario> usuarioDeletado = usuarioRepository.findById(4L);
        Assert.assertFalse(usuarioDeletado.isPresent());
    }

}
