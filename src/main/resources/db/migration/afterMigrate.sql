
INSERT INTO `ecopool`.`parceiro`
(`id`,
`cnpj`,
`nome`)
VALUES
(null,
'07722779000106',
'UFABC');


INSERT INTO `ecopool`.`destino`
(`id`,
`endereco`,
`nome`,
`parceiro_id`)
VALUES
(null,
'Av. dos Estados, 5001',
'Campus SA',
1);


INSERT INTO `ecopool`.`usuario`
(`id`,
`email`,
`nome`,
`_tipo_usuario`,
`_tolerancia_distancia`,
`_tolerancia_tempo`,
`sexo`,
`sobrenome`,
`telefone`,
`parceiro_id`)
VALUES
(null,	'daniel.djgomes@outlook.com',	'Daniel',	'MOTORISTA',	0,	0,	0,	'Gomes',	11987654321,	1);

INSERT INTO `ecopool`.`parceiro_usuarios`
(`parceiro_id`,
`usuarios_id`)
VALUES
(1,
1);

INSERT INTO `ecopool`.`parametro_servico`
(`id`,
`descricao`,
`valor`)
VALUES
(1,
'Fuso Horario Brasileiro',
'-3');

INSERT INTO `ecopool`.`parametro_servico`
(`id`,
`descricao`,
`valor`)
VALUES
(2,
'Query de busca de usuários com percursos proximos',
'select distinct outros.usuario_id as usuarioPerto from (select * from usuario_percursos t1 inner join percurso_pontos t2 inner join coordinate t3 where t1.usuario_id = :usuarioID and t1.percursos_id = t2.percurso_id and t2.pontos_id_coord = t3.id_coord) as usuario inner join (select * from usuario_percursos t4 inner join percurso_pontos t5 inner join coordinate t6 where t4.usuario_id <> :usuarioID and t4.percursos_id = t5.percurso_id and t5.pontos_id_coord = t6.id_coord) as outros WHERE (6371 * acos(cos(radians(usuario.lat)) * cos(radians(outros.lat)) * cos(radians(outros.lng) - radians(usuario.lng)) + sin(radians(usuario.lat)) * sin(radians(outros.lat)))) < 2.5 and usuario.percursos_id = :percursoID'
);

