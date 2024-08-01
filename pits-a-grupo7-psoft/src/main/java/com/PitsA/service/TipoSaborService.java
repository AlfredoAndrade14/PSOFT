package com.PitsA.service;

import com.PitsA.dto.TipoSaborDTO;
import com.PitsA.exception.tipoSabor.TipoSaborMustHaveAValidTipoException;
import com.PitsA.exception.tipoSabor.TipoSaborNotFoundException;
import com.PitsA.model.TipoSabor;
import com.PitsA.repository.TipoSaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoSaborService {

    @Autowired
    private TipoSaborRepository tipoSaborRepository;

    public TipoSaborDTO pegaTipoSabor(Long id) throws TipoSaborNotFoundException {
        TipoSabor tipoSabor = this.getTipoSabor(id);
        
        return new TipoSaborDTO(tipoSabor);
    }

    private TipoSabor getTipoSabor(Long id) throws TipoSaborNotFoundException {
        TipoSabor tipoSabor = this.tipoSaborRepository.findById(id)
                .orElseThrow(() -> new TipoSaborNotFoundException());

        return tipoSabor;
    }

    public TipoSaborDTO criaTipoSabor(TipoSaborDTO tipoSaborDTO) throws TipoSaborMustHaveAValidTipoException {
        this.validaTipoSabor(tipoSaborDTO);

        TipoSabor tipoSabor = new TipoSabor(null, tipoSaborDTO.getTipo());

        this.salvaTipoSabor(tipoSabor);

        return new TipoSaborDTO(tipoSabor);
    }

    private void salvaTipoSabor(TipoSabor tipoSabor) {
        this.tipoSaborRepository.save(tipoSabor);
    }

    private void validaTipoSabor(TipoSaborDTO tipoSaborDTO) throws TipoSaborMustHaveAValidTipoException {
        if (tipoSaborDTO.getTipo().trim().equals("")) throw new TipoSaborMustHaveAValidTipoException();
    }

    public TipoSaborDTO editaTipoSabor(Long id, TipoSaborDTO tipoSaborDTO) throws TipoSaborNotFoundException, TipoSaborMustHaveAValidTipoException {
        this.validaTipoSabor(tipoSaborDTO);

        TipoSabor tipoSabor = this.getTipoSabor(id);
        tipoSabor.setTipo(tipoSaborDTO.getTipo());

        this.salvaTipoSabor(tipoSabor);

        return new TipoSaborDTO(tipoSabor);
    }

    public void deletaTipoSabor(Long id) throws TipoSaborNotFoundException {
        TipoSabor tipoSabor = this.getTipoSabor(id);
        this.tipoSaborRepository.delete(tipoSabor);
    }
}
