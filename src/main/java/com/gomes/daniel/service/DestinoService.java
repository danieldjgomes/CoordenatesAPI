package com.gomes.daniel.service;

import com.gomes.daniel.domain.exception.ParceiroNaoEncontradoExpection;
import com.gomes.daniel.domain.model.Destino;
import com.gomes.daniel.domain.model.Parceiro;
import com.gomes.daniel.domain.repository.DestinoRepository;
import com.gomes.daniel.domain.repository.ParceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinoService {

    @Autowired
    ParceiroRepository parceiroRepository;

    @Autowired
    DestinoRepository destinoRepository;

    public Destino SalvarDestino(Destino destino, Long parceiroId) throws ParceiroNaoEncontradoExpection {

        Optional<Parceiro> parceiro = parceiroRepository.findById(parceiroId);

        if(parceiro.isEmpty()){
            throw new ParceiroNaoEncontradoExpection(String.format("O parceiro inserido com o id %d, já consta no banco de dados.",parceiroId));
        }
        Destino destinoPersistido = destinoRepository.save(destino);
        parceiro.get().getDestino().add(destinoPersistido);
        Parceiro parceiroPersistido = parceiroRepository.save(parceiro.get());
            return parceiroPersistido.getDestino().get(parceiroPersistido.getDestino().size()-1);

    }

    public List<Destino> ListarDestino(Long parceiroId) throws ParceiroNaoEncontradoExpection {
        Optional<Parceiro> parceiro = parceiroRepository.findById(parceiroId);
        if(parceiro.isEmpty()){
            throw new ParceiroNaoEncontradoExpection(String.format("O parceiro inserido com o id %d, já consta no banco de dados.",parceiroId));
        }
        return parceiro.get().getDestino();
    }
}
