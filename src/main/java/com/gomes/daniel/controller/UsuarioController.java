package com.gomes.daniel.controller;

import com.gomes.daniel.domain.exception.ModoPercursoInvalidoException;
import com.gomes.daniel.domain.exception.UsuarioNaoEncontradoException;
import com.gomes.daniel.domain.model.ModoPercurso;
import com.gomes.daniel.domain.model.Percurso;
import com.gomes.daniel.domain.model.Usuario;
import com.gomes.daniel.service.CoordinateService;
import com.gomes.daniel.service.UsuarioService;
//
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;
import java.util.List;

@RestController
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CoordinateService coordinateService;
    
    private static final String MAPS_KEY = "${key}";
    private static final String SCHEME = "https";
    private static final String HOST = "maps.googleapis.com";
    private static final String PATH = "/maps/api/directions/json";

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> SalvarUsuario(@RequestBody Usuario usuario){

        try {
            Usuario usuarioPersistido = usuarioService.SalvarUsuario(usuario);
                return ResponseEntity.ok(usuarioPersistido);
        } catch (UsuarioNaoEncontradoException e) {
            e.printStackTrace();
                return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> ListarUsuario(){
        return usuarioService.ListarUsuarios();
    }

    @PostMapping("usuarios/{playerID}")
    public ResponseEntity<Usuario> SalvarPercurso(
            @PathVariable Long playerID,
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String mode,
            @RequestParam LocalTime horarioOrigem,
            @RequestParam LocalTime horarioDestino)
    {
    	UriComponents uriComponents = UriComponentsBuilder.newInstance()
    			.scheme(SCHEME)
    			.host(HOST)
    			.path(PATH)
    			.query("origin={origin}")
                .query("destination={destination}")
                .query("mode={mode}")
                .query("key={key}")
                .buildAndExpand(origin,destination,mode,MAPS_KEY);
    	
    	 RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);
        
         
         if (responseEntity.getStatusCode() == HttpStatus.OK) {

             try
             {
                 Usuario usuario = usuarioService.BuscarUsuario(playerID);
                 String polyline = StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");
                 ModoPercurso modo = ModoPercurso.stringToModo(mode);

                 if(modo != null){
                     Percurso percurso = new Percurso (coordinateService.listCoord(polyline),
                             modo,
                             origin,
                             destination,
                             usuario,
                             horarioOrigem,
                             horarioDestino);
                     usuario.getPercursos().add(percurso);
                     return ResponseEntity.ok(usuarioService.SalvarUsuario(usuario));
                 }


             }
             catch (ModoPercursoInvalidoException e)
             {
                    return  ResponseEntity.badRequest().build();
             }
             catch (UsuarioNaoEncontradoException e)
             {
                   return ResponseEntity.notFound().build();
             }


         }
         return  ResponseEntity.notFound().build();
    }

    @GetMapping("usuarios/proximos/{usuarioID}/{percursoID}")
    public List<Usuario> BuscarUsuariosProximos(@PathVariable Long usuarioID, @PathVariable Long percursoID){
        return usuarioService.BuscaProximos(usuarioID, percursoID);
    }







}
