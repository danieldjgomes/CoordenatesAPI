package com.gomes.daniel.service;

import com.gomes.daniel.domain.exception.*;
import com.gomes.daniel.domain.model.*;
import com.gomes.daniel.domain.repository.UsuarioRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceException;
import java.time.LocalTime;
import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CoordinateService coordinateService;

    public ResponseEntity<List<Usuario>> ListarUsuarios() {

        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    public Usuario SalvarUsuario(Usuario usuario) throws RecursoMalInseridoException {
        try {
            return usuarioRepository.save(usuario);
        }
        catch (DataIntegrityViolationException e){
            throw new RecursoMalInseridoException();
        }

    }

    public Usuario BuscarUsuario(Long id) throws UsuarioNaoEncontradoException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new UsuarioNaoEncontradoException("Usuario não encontrado");
    }

    public List<Usuario> BuscaProximos(Long usuarioID, Long percursoID) {
        List<Long> ids = usuarioRepository.usuariosProximos(usuarioID, percursoID);
        List<Usuario> usuarios = new ArrayList<>();
        ids.forEach(id -> {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            usuario.ifPresent(usuarios::add);
        });
        return usuarios;
    }

    public Usuario SalvarPercurso(Long playerID, String origin, String destination, ModoPercurso mode, SentidoPercurso sentido, LocalTime horario, Map<String, String> parametros) throws RecursoMalInseridoException, PersistenceException, UsuarioNaoEncontradoException {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(parametros.get("Scheme"))
                .host(parametros.get("Host"))
                .path(parametros.get("Path"))
                .query("origin={origin}")
                .query("destination={destination}")
                .query("mode={mode}")
                .query("horario={horario}")
                .query("sentido={sentido}")
                .query("key={key}")
                .buildAndExpand(origin, destination, ModoPercurso.toGoogleString(mode), horario, sentido.toString(), parametros.get("MapsKey"));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Usuario usuario = BuscarUsuario(playerID);
            String polyline = StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");

            if (mode != null) {
                Percurso percurso = new Percurso(coordinateService.listCoord(polyline),
                        mode,
                        origin,
                        destination,
                        usuario,
                        horario,
                        sentido);
                usuario.getPreferencias().getPercursos().add(percurso);
                return SalvarUsuario(usuario);
            }
        }
        throw new IllegalStateException("Erro ocorrido durante o consumo da API do Google");

    }

    public Usuario AtualizarEmail (Long id, String email) throws UsuarioNaoEncontradoException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isPresent()){
            usuario.get().setEmail(email);
                return usuario.get();
        }
        else {
            throw new UsuarioNaoEncontradoException("Usuario não encontrado!");
        }


    }
}
