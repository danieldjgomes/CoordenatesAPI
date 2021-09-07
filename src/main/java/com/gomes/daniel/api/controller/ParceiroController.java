package com.gomes.daniel.api.controller;

import com.gomes.daniel.domain.exception.EntidadeDuplicadaException;
import com.gomes.daniel.domain.exception.ParceiroNaoEncontradoExpection;
import com.gomes.daniel.domain.exception.RecursoMalInseridoException;
import com.gomes.daniel.domain.model.Destino;
import com.gomes.daniel.domain.model.Parceiro;

import com.gomes.daniel.service.DestinoService;
import com.gomes.daniel.service.ParceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parceiros")
public class ParceiroController {

    @Autowired
    private ParceiroService parceiroService;

    @Autowired DestinoService destinoService;

    @PostMapping("")
    public ResponseEntity<Parceiro> SalvarUsuario(@RequestBody Parceiro usuario) throws RecursoMalInseridoException, EntidadeDuplicadaException {

        Parceiro parceiroPersistido = parceiroService.SalvarParceiro(usuario);
            return ResponseEntity.ok(parceiroPersistido);
    }

    @PostMapping("/destinos/{parceiroId}")
    public Destino SalvarDestino(@RequestBody Destino destino, @PathVariable Long parceiroId) throws ParceiroNaoEncontradoExpection {
        return destinoService.SalvarDestino(destino, parceiroId);
    }

    @GetMapping("destinos/{parceiroId}")
    public List<Destino> ListarDestino(@PathVariable Long parceiroId) throws ParceiroNaoEncontradoExpection {
        return destinoService.ListarDestino(parceiroId);
    }
}
