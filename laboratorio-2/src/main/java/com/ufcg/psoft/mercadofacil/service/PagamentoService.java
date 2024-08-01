package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.PagamentoDTO;
import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Pagamento;
import com.ufcg.psoft.mercadofacil.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {
    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    public ModelMapper modelMapper;

    public void salvaPagamento(Pagamento pagamento) {
        pagamentoRepository.save(pagamento);
    }

    public List<PagamentoDTO> getPagamentosById(long id) throws ClienteNotFoundException {
        clienteService.getClienteById(id);

        return pagamentoRepository.getByClienteId(id).stream()
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDTO.class))
                .collect(Collectors.toList());
    }
}
