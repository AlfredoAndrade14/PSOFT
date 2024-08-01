package com.PitsA.service;

import com.PitsA.dto.ClienteDTO;
import com.PitsA.dto.PedidoDTO;
import com.PitsA.dto.PedidoFormDTO;
import com.PitsA.dto.PizzaPedidoFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.cliente.ClienteNotFoundException;
import com.PitsA.exception.entregador.EntregadorNotFoundException;
import com.PitsA.exception.entregador.NotApprovedEntregador;
import com.PitsA.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.PitsA.exception.pedido.*;
import com.PitsA.exception.saborPizza.SaborPizzaNotFoundException;
import com.PitsA.exception.saborPizza.SaborPizzaUnavailableException;
import com.PitsA.model.*;
import com.PitsA.repository.PedidoRepository;
import com.PitsA.repository.PedidoStatusRepository;
import com.PitsA.util.ENUM.EstadoAprovacao;
import com.PitsA.util.ENUM.TipoPagamento;
import com.PitsA.util.PedidoStatus.PedidoRecebido;
import com.PitsA.util.PedidoStatus.PedidoStatus;
import com.PitsA.util.TipoPagamento.PagamentoCredito;
import com.PitsA.util.TipoPagamento.PagamentoDebito;
import com.PitsA.util.TipoPagamento.PagamentoPix;
import com.PitsA.util.TipoPagamento.TipoPagamentoStrategy;
import com.PitsA.util.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private PizzaPedidoService pizzaPedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoStatusRepository pedidoStatusRepository;

    @Autowired
    private PedidoStatusRepository statusRepository;

    @Autowired
    private EntregadorService entregadorService;

    public PedidoDTO criarPedido(Long clienteId, Long estabelecimentoId, PedidoFormDTO pedidoDTO, Integer codigoAcesso) throws ClienteNotFoundException, EstabelecimentoNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, MustExistAtLeastOneFlavorException, InvalidQuantityInOrderException, SaborPizzaNotFoundException, TheFlavorSizeMustBeGrandeForHalfPizzaException, SaborPizzaUnavailableException {
        ClienteDTO cliente = this.clienteService.getClienteById(clienteId);

        Validacoes.autentica(cliente.getCodigoAcesso(), codigoAcesso);

        this.validaPedido(pedidoDTO);

        Pedido pedido = this.criaPedido(pedidoDTO, cliente, estabelecimentoId);
        this.pedidoRepository.save(pedido);
        PedidoStatus status = new PedidoRecebido(pedido);
        statusRepository.save(status);
        pedido.setStatus(status);

        this.pedidoRepository.save(pedido);

        return new PedidoDTO(pedido);
    }

    private Pedido criaPedido(PedidoFormDTO pedidoDTO, ClienteDTO cliente, Long estabelecimentoId) throws SaborPizzaNotFoundException, TheFlavorSizeMustBeGrandeForHalfPizzaException, EstabelecimentoNotFoundException, MustExistAtLeastOneFlavorException, SaborPizzaUnavailableException {
        String endereco = pedidoDTO.getEndereco() != null ? pedidoDTO.getEndereco() : cliente.getEnderecoPrincipal();
        Set<PizzaPedido> pizzas = this.montaPedido(pedidoDTO.getPizzas());
        return new Pedido(null,
                pizzas,
                endereco,
                this.calculaPreco(pizzas),
                null,
                cliente.convert(),
                this.estabelecimentoService.pegaEstabelecimento(estabelecimentoId).convert(),
                TipoPagamento.EM_AGUARDO,
                new Date(),
                null);
    }

    private Set<PizzaPedido> montaPedido(Set<PizzaPedidoFormDTO> pizzas) throws SaborPizzaNotFoundException, SaborPizzaUnavailableException {
        Set<PizzaPedido> pizzasMontadas = new HashSet<>();


        for (PizzaPedidoFormDTO pizzaPedidoFormDTO : pizzas) {
            pizzasMontadas.add(this.pizzaPedidoService.criar(pizzaPedidoFormDTO));
        }

        return pizzasMontadas;
    }


    private Double calculaPreco(Set<PizzaPedido> pizzas) throws TheFlavorSizeMustBeGrandeForHalfPizzaException, MustExistAtLeastOneFlavorException {

        double preco = 0;

        for (PizzaPedido pizza : pizzas) {
            SaborPizza saborUm = pizza.getSaborPizzaUm();
            SaborPizza saborDois = pizza.getSaborPizzaDois();

            preco += this.calculaPrecoUnitario(saborUm, saborDois) * pizza.getQuantidade();
        }

        return preco;
    }

    private double calculaPrecoUnitario(SaborPizza saborUm, SaborPizza saborDois) throws TheFlavorSizeMustBeGrandeForHalfPizzaException, MustExistAtLeastOneFlavorException {
        if (saborUm != null && saborDois != null) {
            if (!saborUm.getTamanhoPizza().getTamanho().equals("Grande") ||
                    !saborUm.getTamanhoPizza().getTamanho().equals(saborDois.getTamanhoPizza().getTamanho()))
                throw new TheFlavorSizeMustBeGrandeForHalfPizzaException();
            return (saborUm.getValor() + saborDois.getValor()) / 2;
        } else if (saborUm != null && saborDois == null) {
            return saborUm.getValor();
        } else if (saborDois != null && saborUm == null) {
            return saborDois.getValor();
        } else throw new MustExistAtLeastOneFlavorException();
    }

    private void validaPedido(PedidoFormDTO pedido) throws MustExistAtLeastOneFlavorException, InvalidQuantityInOrderException {
        for (PizzaPedidoFormDTO pizza : pedido.getPizzas()) {
            if (pizza.getQuantidade() <= 0) {
                throw new InvalidQuantityInOrderException();
            }
            if ((pizza.getSabor1Id() == null || pizza.getSabor1Id() <= 0) && (pizza.getSabor2Id() == null || pizza.getSabor2Id() <= 0)) {
                throw new MustExistAtLeastOneFlavorException();
            }
        }
    }

    public PedidoDTO confirmaPedido(Long idCliente, Long idPedido, TipoPagamento tipoPagamento, Integer codigoAcesso) throws PedidoNotFoundException, InvalidTipoPagamentoException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, ClienteNotFoundException, TheAccessCodeMustHaveSixDigitsException, InvalidStatusTransition, EstabelecimentoNotFoundException, NotApprovedEntregador, EntregadorNotFoundException {
        Pedido pedido = this.getById(idPedido);

        this.clienteService.confirmaPedido(idCliente, codigoAcesso);
        TipoPagamentoStrategy estrategiaPagamento;

        switch (tipoPagamento) {
            case PIX -> estrategiaPagamento = new PagamentoPix();
            case CARTAO_CREDITO -> estrategiaPagamento = new PagamentoCredito();
            case CARTAO_DEBITO -> estrategiaPagamento = new PagamentoDebito();
            default -> throw new InvalidTipoPagamentoException();
        }

        pedido.setTipoPagamento(tipoPagamento);
        pedido.setPreco(estrategiaPagamento.realizaPagamento(pedido.getPreco()));

        PedidoStatus status = pedido.getStatus();
        if (status.toString() == "Pedido Recebido") status.mudaStatus(pedidoRepository, pedidoStatusRepository);
        else throw new InvalidStatusTransition();

        this.pedidoRepository.save(pedido);

        return new PedidoDTO(pedido);
    }

    private void buscaEntregador(Pedido pedido) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, NotApprovedEntregador, PedidoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        Entregador entregadorFinal = null;

        for (Entregador entregador : pedido.getEstabelecimento().getEntregadores()){
            if (entregador.getAceito().toString().equalsIgnoreCase("aceito") && entregador.getDisponibilidade().toString().equalsIgnoreCase("ativo")){
                if (entregadorFinal == null || entregador.getTempoEspera().before(entregadorFinal.getTempoEspera())){
                    entregadorFinal = entregador;
                }
            }
        }

        if (entregadorFinal != null) this.realizaEntrega(pedido.getEstabelecimento().getId(), pedido.getId(), entregadorFinal.getId(), pedido.getEstabelecimento().getCodigoAcesso());

    }

    private Pedido getById(Long idPedido) throws PedidoNotFoundException {
        Pedido pedido = this.pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNotFoundException());
        return pedido;
    }

    public PedidoDTO finalizaPreparo(Long estabelecimentoId, Long pedidoId, Integer codigoAcesso) throws PedidoNotFoundException, MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, InvalidStatusTransition, NotApprovedEntregador, EntregadorNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        Pedido pedido = this.getById(pedidoId);
        estabelecimentoService.validaAcaoEstabelecimento(estabelecimentoId, codigoAcesso);
        PedidoStatus status = pedido.getStatus();
        if (status.toString() == "Pedido Em Preparo") status.mudaStatus(pedidoRepository, pedidoStatusRepository);
        else throw new InvalidStatusTransition();

        this.buscaEntregador(pedido);
        this.pedidoRepository.save(pedido);

        return new PedidoDTO(pedido);
    }

    public PedidoDTO confirmaRecebimento(Long clienteId, Long pedidoId, Integer codigoAcesso) throws PedidoNotFoundException, InvalidStatusTransition, MustHaveAnAccessCodeException, AccessCodeIncorrectException, ClienteNotFoundException, TheAccessCodeMustHaveSixDigitsException {
        Pedido pedido = this.getById(pedidoId);
        clienteService.confirmaPedido(clienteId, codigoAcesso);
        PedidoStatus status = pedido.getStatus();
        if (status.toString() == "Pedido Em Rota") status.mudaStatus(pedidoRepository, pedidoStatusRepository);
        else throw new InvalidStatusTransition();

        return new PedidoDTO(pedido);
    }

    public PedidoDTO getPedido(Long clienteId, Long pedidoId, Integer codigoAcesso) throws PedidoNotFoundException {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNotFoundException());

        if (pedido.getCliente().getId() == clienteId) return  new PedidoDTO(pedido);

        throw  new PedidoNotFoundException();
    }

    public List<PedidoDTO> getPedidos(Long clienteId, String status, Integer codigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, ClienteNotFoundException {
        List<Pedido> pedidos;

        clienteService.validaAcaoCliente(clienteId, codigoAcesso);

        pedidos = (List<Pedido>) pedidoRepository.findByClienteId(clienteId);

        pedidos = pedidosFiltrar(pedidos, status);

        return pedidos.stream().map(pedido -> new PedidoDTO(pedido)).collect(Collectors.toList());
    }
    
    private List<Pedido> ordenarPedidoPorData(List<Pedido> pedidos){
        for (int i = 0; i < pedidos.size(); i++) {
            for (int j = 0; j < pedidos.size() - 1; j++) {
                if (pedidos.get(j).getDataPedido().getTime() < pedidos.get(j + 1).getDataPedido().getTime()) {
                    Pedido aux = pedidos.get(j);
                    pedidos.set(j, pedidos.get(j + 1));
                    pedidos.set(j + 1, aux);
                }
            }
        }
        return pedidos;
    }

    private List<Pedido> filtrarPedidosPorStatus(List<Pedido> pedidos, String status){
        List<Pedido> listaAux = new ArrayList<>();

        for (Pedido pedidoAdd : pedidos) {
            if (pedidoAdd.getStatus().toString().equals(status)) {
                listaAux.add(pedidoAdd);
            }
        }
        return listaAux;
    }

    private List<Pedido> pedidosFiltrar(List<Pedido> pedidos, String status) {
        if (!pedidos.isEmpty()) {
            if (status != null) {
                return filtrarPedidosPorStatus(pedidos, status);
            }
            pedidos = ordenarPedidoPorData(pedidos);
        }
        return pedidos;
    }

    public PedidoDTO realizaEntrega(Long estabelecimentoId, Long pedidoId, Long entregadorId, Integer codigoAcesso) throws PedidoNotFoundException, EstabelecimentoNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, NotApprovedEntregador, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        this.entregadorService.validaSituacaoParaEntrega(entregadorId);
        Pedido pedido = this.getById(pedidoId);
        Estabelecimento estabelecimento = estabelecimentoService.pegaEstabelecimento(estabelecimentoId).convert();
        Entregador entregador = entregadorService.getById(entregadorId);

        estabelecimentoService.validaAcaoEstabelecimento(estabelecimentoId, codigoAcesso);

        if (entregador.getAceito() != EstadoAprovacao.ACEITO) throw new NotApprovedEntregador();
        pedido.setEntregador(entregador);

        if (pedido.getStatus().toString().equals("Pedido Pronto"))
            pedido.getStatus().mudaStatus(pedidoRepository, pedidoStatusRepository);
        this.pedidoRepository.save(pedido);

        return new PedidoDTO(pedido);
    }

    public void cancelaPedido(ClienteDTO cliente, Long idPedido) throws ClientMustBeThePedidosOwnerException, PedidoNotFoundException, ImpossibleToCancelAReadyPedido {
        Pedido pedido = this.getById(idPedido);
        if (pedido.getCliente().getId().equals(cliente.getId())) {
            if (!(pedido.getStatus().toString().equalsIgnoreCase("pedido recebido")
                  || pedido.getStatus().toString().equalsIgnoreCase("pedido em preparo"))) throw new ImpossibleToCancelAReadyPedido();
            this.pedidoRepository.delete(pedido);
        }else throw new ClientMustBeThePedidosOwnerException();
    }

    public void buscaEntregaDisponveis(Long idEntregador, Long idEstabelecimento, Integer codigoAcesso) throws MustHaveAnAccessCodeException, EstabelecimentoNotFoundException, NotApprovedEntregador, PedidoNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, EntregadorNotFoundException, EntregadorMustBeAcceptedException, EntregadorMustBeAtivoException {
        for (Pedido pedido : this.pedidoRepository.findByEstabelecimentoId(idEstabelecimento)) {
            if (pedido.getStatus().toString().equalsIgnoreCase("Pedido pronto")) {
                this.realizaEntrega(idEstabelecimento, pedido.getId(), idEntregador, codigoAcesso);
            }
        }
    }
}