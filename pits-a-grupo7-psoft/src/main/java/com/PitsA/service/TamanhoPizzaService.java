package com.PitsA.service;

import com.PitsA.dto.TamanhoPizzaDTO;
import com.PitsA.exception.tamanhoPizza.TamanhoPizzaMustHaveAValidTamanhoException;
import com.PitsA.exception.tamanhoPizza.TamanhoPizzaNotFoundException;
import com.PitsA.model.TamanhoPizza;
import com.PitsA.repository.TamanhoPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TamanhoPizzaService {

    @Autowired
    private TamanhoPizzaRepository tamanhoPizzaRepository;

    public TamanhoPizzaDTO pegaTamanhoPizza(Long id) throws TamanhoPizzaNotFoundException {
        return new TamanhoPizzaDTO(this.getTamanhoPizza(id));
    }

    private TamanhoPizza getTamanhoPizza(Long id) throws TamanhoPizzaNotFoundException {
        TamanhoPizza tamanhoPizza = this.tamanhoPizzaRepository.findById(id)
                .orElseThrow(() -> new TamanhoPizzaNotFoundException());

        return tamanhoPizza;
    }

    public TamanhoPizzaDTO criaTamanhoPizza(TamanhoPizzaDTO tamanhoPizzaDTO) throws TamanhoPizzaMustHaveAValidTamanhoException {
        this.validaTamanhoPizza(tamanhoPizzaDTO);

        TamanhoPizza tamanhoPizza = new TamanhoPizza(null, tamanhoPizzaDTO.getTamanho());
        this.salvaTamanhoPizza(tamanhoPizza);

        return new TamanhoPizzaDTO(tamanhoPizza);
    }

    private void salvaTamanhoPizza(TamanhoPizza tamanhoPizza) {
        this.tamanhoPizzaRepository.save(tamanhoPizza);
    }

    private void validaTamanhoPizza(TamanhoPizzaDTO tamanhoPizzaDTO) throws TamanhoPizzaMustHaveAValidTamanhoException {
        if (tamanhoPizzaDTO.getTamanho().trim().equals("")) throw new TamanhoPizzaMustHaveAValidTamanhoException();
    }

    public TamanhoPizzaDTO editaTamanhoPizza(Long id, TamanhoPizzaDTO tamanhoPizzaDTO) throws TamanhoPizzaMustHaveAValidTamanhoException, TamanhoPizzaNotFoundException {
        this.validaTamanhoPizza(tamanhoPizzaDTO);

        TamanhoPizza tamanhoPizza = this.getTamanhoPizza(id);
        tamanhoPizza.setTamanho(tamanhoPizzaDTO.getTamanho());

        this.salvaTamanhoPizza(tamanhoPizza);

        return new TamanhoPizzaDTO(tamanhoPizza);
    }


    public void deletaTamanhoPizza(Long id) throws TamanhoPizzaNotFoundException {
        TamanhoPizza tamanhoPizza = this.getTamanhoPizza(id);
        this.tamanhoPizzaRepository.delete(tamanhoPizza);
    }
}
