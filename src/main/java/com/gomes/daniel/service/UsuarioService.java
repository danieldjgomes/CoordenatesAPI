package com.gomes.daniel.service;

import com.gomes.daniel.domain.exception.ModoPercursoInvalidoException;
import com.gomes.daniel.domain.exception.SentidoPercursoException;
import com.gomes.daniel.domain.exception.UsuarioNaoEncontradoException;
import com.gomes.daniel.domain.model.*;
import com.gomes.daniel.domain.repository.UsuarioRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CoordinateService coordinateService;

    public Usuario SalvarUsuario(Usuario usuario) throws UsuarioNaoEncontradoException, DataIntegrityViolationException {
        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<List<Usuario>> ListarUsuarios(){
        var usuarios =  usuarioRepository.findAll();
            return  ResponseEntity.ok(usuarios);
    }
    
    public Usuario BuscarUsuario(Long id) throws UsuarioNaoEncontradoException{
    	Optional<Usuario> usuario =  usuarioRepository.findById(id);
    	
    	if(usuario.isPresent()) {
    		return usuario.get();
    	}
    	
    	throw new UsuarioNaoEncontradoException("Usuario não encontrado"); 
    }

    public List<Usuario> BuscaProximos(Long usuarioID,Long percursoID){
        List<Long> ids = usuarioRepository.usuariosProximos(usuarioID, percursoID);
        List<Usuario> usuarios = new ArrayList<>();
        ids.forEach(id -> {
            usuarios.add(usuarioRepository.findById(id).get());
        });
            return usuarios;

    }

    public Usuario SalvarPercurso(Long playerID, String origin, String destination, String mode, String sentido, LocalTime horario, String SCHEME, String HOST,String PATH, String MAPS_KEY) throws ModoPercursoInvalidoException, PersistenceException, UsuarioNaoEncontradoException, SentidoPercursoException {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(HOST)
                .path(PATH)
                .query("origin={origin}")
                .query("destination={destination}")
                .query("mode={mode}")
                .query("horario={horario}")
                .query("sentido={sentido}")
                .query("key={key}")
                .buildAndExpand(origin, destination, mode, horario, sentido,MAPS_KEY);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {

                Usuario usuario = BuscarUsuario(playerID);
                String polyline = StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");
                ModoPercurso modo = ModoPercurso.stringToModo(mode);
                SentidoPercurso sentidoPercurso = SentidoPercurso.stringToPercurso(sentido);

                if(modo != null){
                    Percurso percurso = new Percurso (coordinateService.listCoord(polyline),
                            modo,
                            origin,
                            destination,
                            usuario,
                            horario,
                            sentidoPercurso);
                    usuario.getPreferencias().getPercursos().add(percurso);
                    return SalvarUsuario(usuario);
                }
            }

        throw new IllegalStateException("Erro ocorrido durante o consumo da API do Google");

    }





}
