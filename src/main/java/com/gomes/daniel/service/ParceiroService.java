package com.gomes.daniel.service;

import com.gomes.daniel.domain.exception.EntidadeDuplicadaException;
import com.gomes.daniel.domain.exception.RecursoMalInseridoException;
import com.gomes.daniel.domain.model.Parceiro;
import com.gomes.daniel.domain.repository.ParceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;



@Service
public class ParceiroService {

    @Autowired
    ParceiroRepository parceiroRepository;

    public Parceiro SalvarParceiro(Parceiro parceiro) throws RecursoMalInseridoException, EntidadeDuplicadaException {

        Parceiro parceiroPersistido = parceiroRepository.findByCnpj(parceiro.getCnpj());

        if(parceiroPersistido != null){
            String mensagem = String.format("O parceiro inserido com o CNPJ %s, já consta no banco de dados.",parceiro.getCnpj());
            throw new EntidadeDuplicadaException(mensagem);
        }

        try {
            return parceiroRepository.save(parceiro);
        }

        catch (DataIntegrityViolationException e){
            throw new RecursoMalInseridoException();
        }

    }
}
