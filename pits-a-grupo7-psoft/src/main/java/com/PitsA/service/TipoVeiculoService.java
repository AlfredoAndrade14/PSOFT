package com.PitsA.service;

import com.PitsA.dto.TipoVeiculoDTO;
import com.PitsA.exception.entregador.EntregadorMustHaveAValidVehicleTypeException;
import com.PitsA.exception.tipoVeiculo.TipoVeiculoNotFoundException;
import com.PitsA.model.TipoVeiculo;
import com.PitsA.repository.TipoVeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoVeiculoService {

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

    public TipoVeiculoDTO pegaTipoVeiculo(Long id) throws TipoVeiculoNotFoundException {
        return new TipoVeiculoDTO(this.getById(id));
    }

    private TipoVeiculo getById(Long id) throws TipoVeiculoNotFoundException {
        TipoVeiculo tipoVeiculo = this.tipoVeiculoRepository.findById(id).orElseThrow(() -> new TipoVeiculoNotFoundException());

        return tipoVeiculo;
    }

    public TipoVeiculoDTO getTipoByName(String tipoDoVeiculoStr) throws EntregadorMustHaveAValidVehicleTypeException {
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findAllByTipo(tipoDoVeiculoStr).orElseThrow(() -> new EntregadorMustHaveAValidVehicleTypeException());
        return new TipoVeiculoDTO(tipoVeiculo);
    }
}
