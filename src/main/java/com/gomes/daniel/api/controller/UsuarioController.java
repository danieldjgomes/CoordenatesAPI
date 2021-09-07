package com.gomes.daniel.api.controller;

import com.gomes.daniel.domain.exception.*;
import com.gomes.daniel.domain.model.ModoPercurso;
import com.gomes.daniel.domain.model.SentidoPercurso;
import com.gomes.daniel.domain.model.Usuario;
import com.gomes.daniel.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final String MAPS_KEY = "AIzaSyAzXJTthZ-4MMQfiVSSfM9k1BP_5YjoEzo";
    private static final String SCHEME = "https";
    private static final String HOST = "maps.googleapis.com";
    private static final String PATH = "/maps/api/directions/json";


    @GetMapping("")
    public ResponseEntity<List<Usuario>> ListarUsuario() {
        return usuarioService.ListarUsuarios();
    }

    @PostMapping("/{parceiroId}")
    public ResponseEntity<Usuario> SalvarUsuario(@RequestBody Usuario usuario, @PathVariable Long parceiroId) throws RecursoMalInseridoException, EntidadeDuplicadaException, ParceiroNaoEncontradoExpection {
            Usuario usuarioPersistido = usuarioService.SalvarUsuario(usuario, parceiroId);
            return ResponseEntity.ok(usuarioPersistido);
    }

    @PostMapping("/percurso/{parceiroId}/{destinoId}/{usuarioId}")
    public ResponseEntity<Usuario> SalvarPercurso(
            @PathVariable Long parceiroId,
            @PathVariable Long usuarioId,
            @PathVariable Long destinoId,
            @RequestParam String endereco,
            @RequestParam ModoPercurso modo,
            @RequestParam SentidoPercurso sentido,
            @RequestParam LocalTime horario
    ) throws NegocioException {

        Map<String, String> parametros = new HashMap<>();
        parametros.put("Scheme", SCHEME);
        parametros.put("Host", HOST);
        parametros.put("Path", PATH);
        parametros.put("MapsKey", MAPS_KEY);

        return ResponseEntity.ok(usuarioService.SalvarPercurso(usuarioId, parceiroId, destinoId, endereco,modo, sentido, horario, parametros));
    }

    @GetMapping("usuarios/proximos/{usuarioID}/{percursoID}")
    public List<Usuario> BuscarUsuariosProximos(@PathVariable Long usuarioID, @PathVariable Long percursoID) {
        return usuarioService.BuscaProximos(usuarioID, percursoID);
    }

    @PutMapping("/{usuarioID}")
    public ResponseEntity<Usuario>  AtualizarEmail (@RequestParam @Email String email, @PathVariable Long usuarioID) throws UsuarioNaoEncontradoException {
            Usuario usuario = usuarioService.AtualizarEmail(usuarioID,email);
                return ResponseEntity.ok(usuario);
    }

}

