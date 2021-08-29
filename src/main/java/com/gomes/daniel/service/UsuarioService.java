package com.gomes.daniel.service;

import com.gomes.daniel.domain.exception.UsuarioNaoEncontradoException;
import com.gomes.daniel.domain.model.Usuario;
import com.gomes.daniel.domain.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario SalvarUsuario(Usuario usuario) throws UsuarioNaoEncontradoException {

       Usuario persistido = usuarioRepository.save(usuario);

       if(persistido != null){
           return persistido;
       }
        throw new UsuarioNaoEncontradoException("Usuario não encontrado");

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
        ids.forEach(id ->
        usuarios.add(usuarioRepository.findById(id).get()));
            return usuarios;

    }





}
