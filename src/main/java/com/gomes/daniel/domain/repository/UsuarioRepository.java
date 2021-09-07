package com.gomes.daniel.domain.repository;

import com.gomes.daniel.domain.model.Usuario;
import com.gomes.daniel.service.commons.ParameterLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {



        String query = "select distinct outros.usuario_id as usuarioPerto from (select * from usuario_percursos t1 inner join percurso_pontos t2 inner join coordinate t3 where t1.usuario_id = :usuarioID and t1.percursos_id = t2.percurso_id and t2.pontos_id_coord = t3.id_coord) as usuario inner join (select * from usuario_percursos t4 inner join percurso_pontos t5 inner join coordinate t6 where t4.usuario_id <> :usuarioID and t4.percursos_id = t5.percurso_id and t5.pontos_id_coord = t6.id_coord) as outros WHERE (6371 * acos(cos(radians(usuario.lat)) * cos(radians(outros.lat)) * cos(radians(outros.lng) - radians(usuario.lng)) + sin(radians(usuario.lat)) * sin(radians(outros.lat)))) < 2.5 and usuario.percursos_id = :percursoID";
            @Query(nativeQuery = true, value = query)
        List<Long> usuariosProximos(@Param("usuarioID") Long usuarioID, @Param("percursoID") Long percursoID);

        Usuario findByEmail(String string);

}
