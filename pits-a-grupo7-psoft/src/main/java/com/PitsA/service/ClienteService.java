package com.PitsA.service;

import com.PitsA.dto.ClienteDTO;
import com.PitsA.dto.ClienteFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.pedido.ClientMustBeThePedidosOwnerException;
import com.PitsA.exception.cliente.ClienteMustHaveAValidAddressException;
import com.PitsA.exception.cliente.ClienteMustHaveAValidNameException;
import com.PitsA.exception.cliente.ClienteNotFoundException;
import com.PitsA.exception.pedido.ImpossibleToCancelAReadyPedido;
import com.PitsA.exception.pedido.PedidoNotFoundException;
import com.PitsA.exception.saborPizza.SaborPizzaIsAvailableException;
import com.PitsA.exception.saborPizza.SaborPizzaNotFoundException;
import com.PitsA.model.Cliente;
import com.PitsA.repository.ClienteRepository;
import com.PitsA.util.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SaborPizzaService saborPizzaService;

    @Autowired
    private PedidoService pedidoService;

    public ClienteDTO criarCliente(ClienteFormDTO clienteDTO) throws MustHaveAnAccessCodeException, ClienteMustHaveAValidAddressException, ClienteMustHaveAValidNameException, TheAccessCodeMustHaveSixDigitsException {
        Validacoes.validaCodigo(clienteDTO.getCodigoAcesso());
        this.validaCliente(clienteDTO);


        Cliente cliente = new Cliente(null,
                                      clienteDTO.getNome().trim(),
                                      clienteDTO.getEnderecoPrincipal().trim(),
                                      clienteDTO.getCodigoAcesso());


        this.salvarCliente(cliente);

        return new ClienteDTO(cliente);
    }

    public ClienteDTO atualizarCliente(Long id, ClienteFormDTO clienteDTO, Integer codigoAcesso) throws MustHaveAnAccessCodeException, ClienteMustHaveAValidAddressException, ClienteMustHaveAValidNameException, ClienteNotFoundException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
        this.validaCliente(clienteDTO);

        Cliente cliente = this.getById(id);

        Validacoes.autentica(cliente.getCodigoAcesso(), codigoAcesso);

        cliente.setNome(clienteDTO.getNome().trim());
        cliente.setEnderecoPrincipal(clienteDTO.getEnderecoPrincipal().trim());
        this.salvarCliente(cliente);

        return new ClienteDTO(cliente);
    }

    public void removeCliente(Long id, Integer codigoAcesso) throws MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, ClienteNotFoundException, AccessCodeIncorrectException {
        Cliente cliente = this.getById(id);
        Validacoes.autentica(cliente.getCodigoAcesso(), codigoAcesso);
        this.clienteRepository.delete(cliente);
    }

    private Cliente getById(Long id) throws ClienteNotFoundException {

        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException());

        return cliente;
    }

    public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException {
        Cliente cliente = this.getById(id);
        return new ClienteDTO(cliente);
    }

    private void validaCliente(ClienteFormDTO clienteDTO) throws ClienteMustHaveAValidNameException, ClienteMustHaveAValidAddressException, MustHaveAnAccessCodeException{
        if (clienteDTO.getNome() == null || clienteDTO.getNome().trim().equals(""))
            throw new ClienteMustHaveAValidNameException();
        if (clienteDTO.getEnderecoPrincipal() == null || clienteDTO.getEnderecoPrincipal().trim().equals(""))
            throw new ClienteMustHaveAValidAddressException();
    }

    private void salvarCliente(Cliente cliente) {
        this.clienteRepository.save(cliente);
    }

    public void atualizaSenha(Long id, Integer codigoAcesso, Integer novoCodigoAcesso) throws ClienteNotFoundException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, AccessCodeIncorrectException {
        Cliente cliente = this.getById(id);

        Validacoes.autentica(cliente.getCodigoAcesso(),codigoAcesso);
        Validacoes.validaCodigo(novoCodigoAcesso);

        cliente.setCodigoAcesso(novoCodigoAcesso);
        clienteRepository.save(cliente);
    }

    public void confirmaPedido(Long idCliente, Integer codigoAcesso) throws ClienteNotFoundException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, AccessCodeIncorrectException {
        ClienteDTO cliente = getClienteById(idCliente);
        Validacoes.validaCodigo(codigoAcesso);
        Validacoes.autentica(cliente.getCodigoAcesso(), codigoAcesso);
    }

    public void demonstraInteresseSaborPizza(Long id, Long saborPizzaId) throws ClienteNotFoundException, SaborPizzaNotFoundException, SaborPizzaIsAvailableException {
        Cliente cliente = this.getById(id);
        this.saborPizzaService.adicionaInteressado(cliente, saborPizzaId);
    }

    public void validaAcaoCliente(Long clienteId, Integer codigoAcesso) throws MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, AccessCodeIncorrectException, ClienteNotFoundException {
        Cliente cliente = getById(clienteId);
        Validacoes.validaCodigo(codigoAcesso);
        Validacoes.autentica(cliente.getCodigoAcesso(), codigoAcesso);
    }

    public void cancelaPedido(Long id, Long idPedido, Integer codigoAcesso) throws ClienteNotFoundException, MustHaveAnAccessCodeException, TheAccessCodeMustHaveSixDigitsException, AccessCodeIncorrectException, PedidoNotFoundException, ClientMustBeThePedidosOwnerException, ImpossibleToCancelAReadyPedido {
        ClienteDTO clienteDTO = this.getClienteById(id);
        Validacoes.validaCodigo(codigoAcesso);
        Validacoes.autentica(clienteDTO.getCodigoAcesso(), codigoAcesso);
        this.pedidoService.cancelaPedido(clienteDTO, idPedido);

    }
}
