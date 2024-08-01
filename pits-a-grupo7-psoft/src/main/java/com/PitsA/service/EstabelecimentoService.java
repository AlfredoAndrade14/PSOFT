package com.PitsA.service;

import com.PitsA.dto.*;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.entregador.EntregadorNotFoundException;
import com.PitsA.exception.entregador.NotApprovedEntregador;
import com.PitsA.exception.estabelecimento.*;
import com.PitsA.exception.pedido.EntregadorMustBeAcceptedException;
import com.PitsA.exception.pedido.EntregadorMustBeAtivoException;
import com.PitsA.exception.pedido.PedidoNotFoundException;
import com.PitsA.model.Entregador;
import com.PitsA.model.Estabelecimento;
import com.PitsA.model.SaborPizza;
import com.PitsA.repository.EstabelecimentoRepository;
import com.PitsA.util.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EstabelecimentoService {
    @Autowired
    private EntregadorService entregadorService;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private PedidoService pedidoService;

    public EstabelecimentoDTO criarEstabelecimento(EstabelecimentoFormDTO estabelecimentoDTO) throws TheAccessCodeMustHaveSixDigitsException, EstabelecimentoMustHaveAValidAddressException, EstabelecimentoMustHaveAValidNameException, MustHaveAnAccessCodeException {
        Validacoes.validaCodigo(estabelecimentoDTO.getCodigoAcesso());
        this.validaEstabelecimento(estabelecimentoDTO);

        Estabelecimento estabelecimento = new Estabelecimento(null, estabelecimentoDTO.getNome().trim(), estabelecimentoDTO.getEndereco().trim(), estabelecimentoDTO.getCodigoAcesso(), new HashSet<>(), new HashSet<>());
        this.salvarEstabelecimento(estabelecimento);

        return new EstabelecimentoDTO(estabelecimento);
    }

    public EstabelecimentoDTO atualizarEstabelecimento(Long id, EstabelecimentoFormDTO estabelecimentoDTO, Integer codigoAcesso) throws TheAccessCodeMustHaveSixDigitsException, EstabelecimentoNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, EstabelecimentoMustHaveAValidAddressException, EstabelecimentoMustHaveAValidNameException {
        this.validaEstabelecimento(estabelecimentoDTO);

        Estabelecimento estabelecimento = this.getById(id);

        Validacoes.autentica(estabelecimento.getCodigoAcesso(), codigoAcesso);

        estabelecimento.setNome(estabelecimentoDTO.getNome().trim());
        estabelecimento.setEndereco(estabelecimentoDTO.getEndereco().trim());
        this.salvarEstabelecimento(estabelecimento);

        return new EstabelecimentoDTO(estabelecimento);
    }

    public void atualizarSenha(Long id, Integer codigoAcesso, Integer novoCodigoAcesso) throws AccessCodeIncorrectException, EstabelecimentoNotFoundException, TheAccessCodeMustHaveSixDigitsException, MustHaveAnAccessCodeException {
        Estabelecimento estabelecimento = this.getById(id);

        Validacoes.autentica(estabelecimento.getCodigoAcesso(), codigoAcesso);
        Validacoes.validaCodigo(novoCodigoAcesso);

        estabelecimento.setCodigoAcesso(novoCodigoAcesso);
        this.salvarEstabelecimento(estabelecimento);
    }

    public EstabelecimentoDTO pegaEstabelecimento(Long id) throws EstabelecimentoNotFoundException {
        return new EstabelecimentoDTO(this.getById(id));
    }

    private Estabelecimento getById(Long id) throws EstabelecimentoNotFoundException {

        Estabelecimento estabelecimento = this.estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EstabelecimentoNotFoundException());

        return estabelecimento;
    }

    public void aprovaEntregador(Long id, Long entregadorId, Boolean aprovado, Integer codigoAcesso) throws EstabelecimentoNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException {
        Estabelecimento estabelecimento = getById(id);
        Validacoes.autentica(estabelecimento.getCodigoAcesso(), codigoAcesso);
        entregadorService.setAprovado(entregadorId, aprovado);
    }

    public void addEntregador(Long estabelecimentoId, Entregador entregador) throws EstabelecimentoNotFoundException {
        Estabelecimento estabelecimento = this.getById(estabelecimentoId);
        estabelecimento.addEntregador(entregador);
        estabelecimentoRepository.save(estabelecimento);
    }

    private void salvarEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimentoRepository.save(estabelecimento);
    }

    private void validaEstabelecimento(EstabelecimentoFormDTO estabelecimentoDTO) throws EstabelecimentoMustHaveAValidNameException, EstabelecimentoMustHaveAValidAddressException {
        if (estabelecimentoDTO.getNome() == null || estabelecimentoDTO.getNome().trim().equals(""))
            throw new EstabelecimentoMustHaveAValidNameException();
        if (estabelecimentoDTO.getEndereco() == null || estabelecimentoDTO.getEndereco().trim().equals(""))
            throw new EstabelecimentoMustHaveAValidAddressException();
    }

    public Set<EntregadorDTO> getEntregadoresEstabelecimento(Long estabelecimentoId) throws EstabelecimentoNotFoundException, ThereIsNoDeliveryPersonAcceptedExcetion {
        Set<EntregadorDTO> entregadores = pegaEstabelecimento(estabelecimentoId).getEntregadores();
        if (entregadores.isEmpty()) throw new ThereIsNoDeliveryPersonAcceptedExcetion();
        return entregadores;
    }

    public void adicionaSaborPizza(Long idEstabelecimento, SaborPizza saborPizza) throws EstabelecimentoNotFoundException {
        Estabelecimento estabelecimento = this.getById(idEstabelecimento);
        estabelecimento.addSaborPizza(saborPizza);
        this.salvarEstabelecimento(estabelecimento);
    }

    public void validaAcaoEstabelecimento(Long estabelecimentoId, Integer codigoAcesso) throws EstabelecimentoNotFoundException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, AccessCodeIncorrectException {
        Estabelecimento estabelecimento = getById(estabelecimentoId);
        Validacoes.validaCodigo(codigoAcesso);
        Validacoes.autentica(estabelecimento.getCodigoAcesso(), codigoAcesso);
    }

    public void buscaEntregasDisponiveis(Long idEntregador) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, NotApprovedEntregador, PedidoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        Estabelecimento estabelecimento = this.estabelecimentoRepository.buscaEstabelecimento(idEntregador);
        this.pedidoService.buscaEntregaDisponveis(idEntregador, estabelecimento.getId(), estabelecimento.getCodigoAcesso());
    }
}