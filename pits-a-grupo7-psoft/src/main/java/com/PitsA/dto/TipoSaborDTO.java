package com.PitsA.dto;

import com.PitsA.model.TipoSabor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TipoSaborDTO  {

    private Long id;
    private String tipo;

    public TipoSaborDTO(TipoSabor tipoSabor) {
        this.id = tipoSabor.getId();
        this.tipo = tipoSabor.getTipo();
    }

    public TipoSabor convert() {
        return new TipoSabor(this.id, this.tipo);
    }

}
