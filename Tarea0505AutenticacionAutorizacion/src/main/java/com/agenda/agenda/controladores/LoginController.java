package com.agenda.agenda.controladores;

import com.agenda.agenda.entidades.Usuario;
import com.agenda.agenda.repositorios.UsuarioRepository;
import com.agenda.agenda.seguridad.JWTAuthenticationConfig;
import com.agenda.agenda.seguridad.PasswordEncryptor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("login")
    public String login(
            @RequestParam("user") String username,
            @RequestParam("encryptedPass") String encryptedPass) throws BadRequestException {

        // 1. Obtenemos todos los usuarios del repositorio
        List<Usuario> usuarios = usuarioRepository.getUsuarios();
        Usuario usuarioEncontrado = null;

        // 2. Buscamos si existe el usuario y si la contraseña coincide
        for (Usuario usuario : usuarios) {
            // Comparamos nombre de usuario Y contraseña (desencriptando la almacenada)
            if (usuario.getUsername().equals(username) &&
                    PasswordEncryptor.decrypt(usuario.getEncryptedPass()).equals(encryptedPass)) {
                usuarioEncontrado = usuario;
                break;
            }
        }

        // 3. Si no lo encontramos, lanzamos error
        if (usuarioEncontrado == null) {
            throw new BadRequestException();
        }

        // 4. Si todo es correcto, generamos el token (Pasando ahora el ROL)
        String token = jwtAuthenticationConfig.getJWTToken(
                usuarioEncontrado.getUsername(),
                usuarioEncontrado.getRol()
        );

        return token;
    }
}