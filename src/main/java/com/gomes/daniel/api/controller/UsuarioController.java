package com.gomes.daniel.api.controller;

import com.gomes.daniel.domain.exception.*;
import com.gomes.daniel.domain.model.ModoPercurso;
import com.gomes.daniel.domain.model.SentidoPercurso;
import com.gomes.daniel.domain.model.Usuario;
import com.gomes.daniel.service.CoordinateService;
import com.gomes.daniel.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Email;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CoordinateService coordinateService;

    private static final String MAPS_KEY = "AIzaSyAzXJTthZ-4MMQfiVSSfM9k1BP_5YjoEzo";
    private static final String SCHEME = "https";
    private static final String HOST = "maps.googleapis.com";
    private static final String PATH = "/maps/api/directions/json";


    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> ListarUsuario() {
        return usuarioService.ListarUsuarios();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> SalvarUsuario(@RequestBody Usuario usuario) throws RecursoMalInseridoException {

            Usuario usuarioPersistido = usuarioService.SalvarUsuario(usuario);
            return ResponseEntity.ok(usuarioPersistido);

    }

    @PostMapping("usuarios/{playerID}")
    public ResponseEntity<Usuario> SalvarPercurso(
            @PathVariable Long playerID,
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam ModoPercurso mode,
            @RequestParam SentidoPercurso sentido,
            @RequestParam LocalTime horario
    ) throws RecursoMalInseridoException, UsuarioNaoEncontradoException {

        Map<String, String> parametros = new HashMap<>();
        parametros.put("Scheme", SCHEME);
        parametros.put("Host", HOST);
        parametros.put("Path", PATH);
        parametros.put("MapsKey", MAPS_KEY);

        return ResponseEntity.ok(usuarioService.SalvarPercurso(playerID, origin, destination, mode, sentido, horario, parametros));
    }

    @GetMapping("usuarios/proximos/{usuarioID}/{percursoID}")
    public List<Usuario> BuscarUsuariosProximos(@PathVariable Long usuarioID, @PathVariable Long percursoID) {
        return usuarioService.BuscaProximos(usuarioID, percursoID);
    }

    @PutMapping("usuarios/{usuarioID}")
    public ResponseEntity<Usuario>  AtualizarEmail (@RequestParam @Email String email, @PathVariable Long usuarioID) throws UsuarioNaoEncontradoException {
            Usuario usuario = usuarioService.AtualizarEmail(usuarioID,email);
                return ResponseEntity.ok(usuario);
    }

}

