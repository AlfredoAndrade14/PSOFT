package com.PitsA.service;

import com.PitsA.dto.EntregadorDTO;
import com.PitsA.dto.EntregadorFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.entregador.*;
import com.PitsA.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.PitsA.exception.estabelecimento.ThereIsNoDeliveryPersonAcceptedExcetion;
import com.PitsA.exception.pedido.EntregadorMustBeAcceptedException;
import com.PitsA.exception.pedido.EntregadorMustBeAtivoException;
import com.PitsA.exception.pedido.PedidoNotFoundException;
import com.PitsA.exception.tipoVeiculo.TipoVeiculoNotFoundException;
import com.PitsA.model.Entregador;
import com.PitsA.model.TipoVeiculo;
import com.PitsA.repository.EntregadorRepository;
import com.PitsA.util.ENUM.DisponibilidadeEntregador;
import com.PitsA.util.ENUM.EstadoAprovacao;
import com.PitsA.util.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EntregadorService {
    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    public EntregadorDTO criarEntregador(Long estabelecimentoId, EntregadorFormDTO entregadorDTO) throws MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, EstabelecimentoNotFoundException, EntregadorMustHaveAValidNameException, EntregadorMustHaveAValidVehicleColorException, EntregadorMustHaveAValidVehicleTypeException, EntregadorMustHaveAValidVehiclePlateException, TipoVeiculoNotFoundException {
        this.validaEntregador(entregadorDTO);
        Validacoes.validaCodigo(entregadorDTO.getCodigoAcesso());

        estabelecimentoService.pegaEstabelecimento(estabelecimentoId);

        TipoVeiculo tipoVeiculo = tipoVeiculoService.pegaTipoVeiculo(entregadorDTO.getTipoVeiculoId()).convert();

        Entregador entregador = new Entregador(null, entregadorDTO.getNome().trim(), entregadorDTO.getPlacaVeiculo().trim(), tipoVeiculo, entregadorDTO.getCorDoVeiculo().trim(), entregadorDTO.getCodigoAcesso(), EstadoAprovacao.EM_APROVACAO, DisponibilidadeEntregador.DESCANSO, null);
        this.entregadorRepository.save(entregador);

        this.estabelecimentoService.addEntregador(estabelecimentoId, entregador);

        return new EntregadorDTO(entregador);
    }

    public Set<EntregadorDTO> getEntregadoresEstabelecimento(Long idEstabelecimento) throws EstabelecimentoNotFoundException, ThereIsNoDeliveryPersonAcceptedExcetion {
        return estabelecimentoService.getEntregadoresEstabelecimento(idEstabelecimento).stream().filter(entregador -> entregador.getEstadoAprovacao().equals(EstadoAprovacao.ACEITO)).collect(Collectors.toSet());
    }

    public void atualizarSenha(Long id, Integer codigoAcesso, Integer novoCodigoAcesso) throws EntregadorNotFoundException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, AccessCodeIncorrectException {
        Entregador entregador = getById(id);

        Validacoes.autentica(entregador.getCodigoAcesso(),codigoAcesso);
        Validacoes.validaCodigo(novoCodigoAcesso);

        entregador.setCodigoAcesso(novoCodigoAcesso);
        entregadorRepository.save(entregador);
    }

    public EntregadorDTO atualizarEntregador(Long id, Integer codigoAcesso, EntregadorFormDTO entregadorDTO) throws EntregadorNotFoundException, AccessCodeIncorrectException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, EntregadorMustHaveAValidVehicleColorException, EntregadorMustHaveAValidVehicleTypeException, EntregadorMustHaveAValidVehiclePlateException, EntregadorMustHaveAValidNameException, TipoVeiculoNotFoundException {
        validaEntregador(entregadorDTO);
        Entregador entregador = getById(id);
        Validacoes.autentica(entregador.getCodigoAcesso(),codigoAcesso);
        TipoVeiculo tipoVeiculo = tipoVeiculoService.pegaTipoVeiculo(entregadorDTO.getTipoVeiculoId()).convert();

        this.atualizaEntregador(entregador, entregadorDTO, tipoVeiculo);

        entregadorRepository.save(entregador);
        return new EntregadorDTO(entregador);
    }

    private void atualizaEntregador(Entregador entregador, EntregadorFormDTO entregadorDTO, TipoVeiculo tipoVeiculo) {
        entregador.setNome(entregadorDTO.getNome());
        entregador.setCorDoVeiculo(entregadorDTO.getCorDoVeiculo());
        entregador.setPlacaVeiculo(entregadorDTO.getPlacaVeiculo());
        entregador.setTipoDoVeiculo(tipoVeiculo);
    }

    public Entregador getById(Long id) throws EntregadorNotFoundException {
        Entregador entregador = this.entregadorRepository.findById(id)
                .orElseThrow(() -> new EntregadorNotFoundException());

        return entregador;
    }

    public void removeEntregador(Long id, Integer codigoAcesso) throws EntregadorNotFoundException, AccessCodeIncorrectException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException {
        Entregador entregador = getById(id);
        Validacoes.autentica(entregador.getCodigoAcesso(),codigoAcesso);
        this.entregadorRepository.delete(entregador);
    }

    private void validaEntregador(EntregadorFormDTO entregadorDTO) throws EntregadorMustHaveAValidNameException, EntregadorMustHaveAValidVehicleColorException, EntregadorMustHaveAValidVehiclePlateException, EntregadorMustHaveAValidVehicleTypeException {
        if (entregadorDTO.getNome() == null || entregadorDTO.getNome().trim().equals("")) throw new EntregadorMustHaveAValidNameException();
        if (entregadorDTO.getCorDoVeiculo() == null || entregadorDTO.getCorDoVeiculo().trim().equals("")) throw new EntregadorMustHaveAValidVehicleColorException();
        if (entregadorDTO.getPlacaVeiculo() == null || entregadorDTO.getPlacaVeiculo().trim().equals("") || entregadorDTO.getPlacaVeiculo().length() > 7) throw new EntregadorMustHaveAValidVehiclePlateException();
        if (entregadorDTO.getTipoVeiculoId() == null) throw new EntregadorMustHaveAValidVehicleTypeException();
    }

    public void setAprovado(Long entregadorId, boolean estadoAprovacao) throws EntregadorNotFoundException {
        Entregador entregador = getById(entregadorId);
        if (estadoAprovacao) {
            entregador.setAceito(EstadoAprovacao.ACEITO);
            entregador.setDisponibilidade(DisponibilidadeEntregador.DESCANSO);
        } else entregador.setAceito(EstadoAprovacao.RECUSADO);
        this.entregadorRepository.save(entregador);
    }

    public EntregadorDTO atualizarDisponibilidade(Long id, Integer codigoAcesso, DisponibilidadeEntregador disponibilidade) throws MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, EstabelecimentoNotFoundException, NotApprovedEntregador, PedidoNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        Entregador entregador = getById(id);
        Validacoes.autentica(entregador.getCodigoAcesso(),codigoAcesso);
        if(entregador.getAceito() != EstadoAprovacao.ACEITO) throw new NotApprovedEntregador();
        entregador.setDisponibilidade(disponibilidade);
        entregadorRepository.save(entregador);

        if (disponibilidade.toString().equalsIgnoreCase("Ativo")) this.estabelecimentoService.buscaEntregasDisponiveis(id);
        return new EntregadorDTO(entregador);
    }

    public void validaSituacaoParaEntrega(Long entregadorId) throws EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException, EntregadorNotFoundException {
        Entregador entregador = this.getById(entregadorId);
        if (!entregador.getAceito().toString().equalsIgnoreCase("aceito")) throw new EntregadorMustBeAcceptedException();
        if (entregador.getDisponibilidade().toString().equalsIgnoreCase("descanso")) throw new EntregadorMustBeAtivoException();
    }
}