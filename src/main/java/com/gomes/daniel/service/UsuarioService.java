package com.gomes.daniel.service;

import com.gomes.daniel.domain.exception.*;
import com.gomes.daniel.domain.model.*;
import com.gomes.daniel.domain.repository.ParametroRepository;
import com.gomes.daniel.domain.repository.ParceiroRepository;
import com.gomes.daniel.domain.repository.UsuarioRepository;

import com.gomes.daniel.service.commons.ParameterLoader;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CoordenadaService coordenadaService;

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Autowired
    private ParameterLoader parameterLoader;


    public ResponseEntity<List<Usuario>> ListarUsuarios() {

        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    public Usuario SalvarUsuario(Usuario usuario, Long parceiroId) throws RecursoMalInseridoException, EntidadeDuplicadaException, ParceiroNaoEncontradoExpection {

        Usuario usuarioPersistido = usuarioRepository.findByEmail(usuario.getEmail());
        Parceiro parceiroPersistido = parceiroRepository.findById(parceiroId).get();


//        if(usuarioPersistido != null){
//            String mensagem = String.format("O usuario inserido com o email %s, já consta no banco de dados.",usuario.getEmail());
//            throw new EntidadeDuplicadaException(mensagem);
//        }

        if(parceiroPersistido == null){
            String mensagem = String.format("O parceiro inserido com o id %d, não foi encontrado",parceiroId);
            throw new ParceiroNaoEncontradoExpection(mensagem);
        }


        try {
            usuario.setParceiro(parceiroPersistido);
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

    public Usuario SalvarPercurso(Long usuarioId, Long parceiroId, Long destinoId, String endereco, ModoPercurso mode, SentidoPercurso sentido, LocalTime horario, Map<String, String> parametros) throws NegocioException, PersistenceException,DestinoNaoEncontradoException {

        String enderecoOrigem;
        String enderecoDestino;
        AtomicReference<Destino> destino = new AtomicReference<>();


        Parceiro parceiro = parceiroRepository.findById(parceiroId).orElseThrow(() -> new ParceiroNaoEncontradoExpection("ID do parceiro inserido não foi localizado"));
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException("ID do usuario inserido não foi localizado"));

        parceiro.getDestino().forEach(d ->{
            if (d.getId().equals(destinoId)) {
                destino.set(d);
            }});


        if(destino.get() == null){
            throw new DestinoNaoEncontradoException("ID do destino inserido não foi localizado");
        }

        String destinoEndereco = Objects.requireNonNull(destino).get().getEndereco();

            if(sentido == SentidoPercurso.DESTINO){
                enderecoOrigem = endereco;
                enderecoDestino = destinoEndereco;
            }
            else {
                enderecoOrigem = destinoEndereco;
                enderecoDestino = endereco;
            }


        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(parametros.get("Scheme"))
                .host(parametros.get("Host"))
                .path(parametros.get("Path"))
                    .query("origin={origin}")
                    .query("destination={destination}")
                    .query("mode={mode}")
                    .query("horario={horario}")
//                    .query("sentido={sentido}")
                    .query("key={key}")
                        .buildAndExpand(enderecoOrigem, enderecoDestino, ModoPercurso.toGoogleString(mode), horario,
//                                sentido.toString(),
                                parametros.get("MapsKey"));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String polyline = StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");

            if (mode != null) {
                Percurso percurso = new Percurso(coordenadaService.listCoord(polyline),
                        mode,
                        sentido,
                        endereco,
                        destino.get(),
                        horario.plusHours(Long.parseLong(parameterLoader.loadById(1L)))
                        );

                usuario.getPreferencias().getPercursos().add(percurso);
                return SalvarUsuario(usuario,parceiroId);
            }
        }
        throw new IllegalStateException("Erro ocorrido durante o consumo da API do Google");

    }

    public Usuario AtualizarEmail (Long id, String email) throws UsuarioNaoEncontradoException {
        Usuario usuarioAtual = usuarioRepository.findById(id).orElse(null);

        if (usuarioAtual != null) {
            usuarioAtual.setEmail(email);
            return usuarioRepository.save(usuarioAtual);
        }
        else {
            throw new UsuarioNaoEncontradoException("Usuario não encontrado!");
        }

    }
}
