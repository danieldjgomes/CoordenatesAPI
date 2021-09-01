package com.gomes.daniel.controller;

import com.gomes.daniel.domain.exception.ModoPercursoInvalidoException;
import com.gomes.daniel.domain.exception.SentidoPercursoException;
import com.gomes.daniel.domain.exception.UsuarioNaoEncontradoException;
import com.gomes.daniel.domain.model.ModoPercurso;
import com.gomes.daniel.domain.model.Percurso;
import com.gomes.daniel.domain.model.SentidoPercurso;
import com.gomes.daniel.domain.model.Usuario;
import com.gomes.daniel.service.CoordinateService;
import com.gomes.daniel.service.UsuarioService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalTime;
import java.util.List;

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
    public ResponseEntity<List<Usuario>> ListarUsuario(){
        return usuarioService.ListarUsuarios();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> SalvarUsuario(@RequestBody Usuario usuario){

        try {
            Usuario usuarioPersistido = usuarioService.SalvarUsuario(usuario);
                return ResponseEntity.ok(usuarioPersistido);
                
        } catch (UsuarioNaoEncontradoException e) {
            e.printStackTrace();
                return ResponseEntity.badRequest().build();
                
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PostMapping("usuarios/{playerID}")
    public ResponseEntity<Usuario> SalvarPercurso(
            @PathVariable Long playerID,
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String mode,
            @RequestParam String sentido,
            @RequestParam LocalTime horario
           ) {

        try {
            return ResponseEntity.ok(usuarioService.SalvarPercurso(playerID, origin, destination, mode, sentido, horario, SCHEME, HOST, PATH, MAPS_KEY));

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (UsuarioNaoEncontradoException | SentidoPercursoException | ModoPercursoInvalidoException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("usuarios/proximos/{usuarioID}/{percursoID}")
    public List<Usuario> BuscarUsuariosProximos(@PathVariable Long usuarioID, @PathVariable Long percursoID){
        return usuarioService.BuscaProximos(usuarioID, percursoID);
    }







}
