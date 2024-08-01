package com.PitsA;

import com.PitsA.dto.*;
import net.minidev.json.JSONObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PitsAApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class ClienteControllerTests {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testcriarClienteComSucesso() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente", "Rua dos bobos nº 0",123456);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void testcriarClienteComNomeEmBranco() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("", "Rua dos bobos nº 0",123456);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarClienteSemNome() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO(null, "Rua dos bobos nº 0",123456);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarClienteComEndereçoeEmBranco() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente", "",123456);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarClienteSemEndereço() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente", null,123456);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarClienteComSenhaComMenosDe6Digitos() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente", "Rua dos bobos nº 0",12345);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarClienteSemSenha() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        URI uri = new URI(baseUrl + "/cliente/");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente", "Rua dos bobos nº 0",null);

        HttpEntity<ClienteFormDTO> request = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(uri, request, ClienteDTO.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void adicionarSaborDeInteresseSaborDisponivel() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        // CRIA ESTABELECIMENTO
        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento X", "Rua do Estabelecimento", 123456);
        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento", requestEstabelecimento, EstabelecimentoDTO.class);
        assertThat(responseEstabelecimento.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CRIA CLIENTE
        URI uriCliente = new URI(baseUrl + "/cliente");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente Y", "Rua do Cliente",654321);

        HttpEntity<ClienteFormDTO> requestCliente = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> responseCliente = this.restTemplate.postForEntity(uriCliente, requestCliente, ClienteDTO.class);
        assertThat(responseCliente.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CRIA SABOR DE PIZZA
        SaborPizzaFormDTO saborDePizzaDTO = new SaborPizzaFormDTO("Sabor1",20.00,1L,1L);
        HttpEntity<SaborPizzaFormDTO> requestSaborDePizza = new HttpEntity<>(saborDePizzaDTO);
        ResponseEntity<SaborPizzaDTO> responseSaborPizza = this.restTemplate.postForEntity(baseUrl+"/saborPizza/estabelecimento/1?codigoAcesso=123456", requestSaborDePizza, SaborPizzaDTO.class);
        assertThat(responseSaborPizza.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CLIENTE DEMONSTRA INTERESSE EM SABOR
        URI uriDemonstraInteresse = new URI(baseUrl + "/cliente/2/saborPizza/interesse/3");

        ResponseEntity<JSONObject> response = this.restTemplate.exchange(uriDemonstraInteresse, HttpMethod.PUT, null,JSONObject.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400); // BAD REQUEST pois não é possível demonstrar interesse por um sabor disponível
    }

    @Test
    public void adicionarSaborDeInteresseSaborIndisponivel() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        // CRIA ESTABELECIMENTO
        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento X", "Rua do Estabelecimento", 123456);
        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento", requestEstabelecimento, EstabelecimentoDTO.class);
        assertThat(responseEstabelecimento.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CRIA CLIENTE
        URI uriCliente = new URI(baseUrl + "/cliente");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente Y", "Rua do Cliente",654321);

        HttpEntity<ClienteFormDTO> requestCliente = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> responseCliente = this.restTemplate.postForEntity(uriCliente, requestCliente, ClienteDTO.class);
        assertThat(responseCliente.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CRIA SABOR DE PIZZA
        SaborPizzaFormDTO saborDePizzaDTO = new SaborPizzaFormDTO("Sabor1",20.00,1L,1L);
        HttpEntity<SaborPizzaFormDTO> requestSaborDePizza = new HttpEntity<>(saborDePizzaDTO);
        ResponseEntity<SaborPizzaDTO> responseSaborPizza = this.restTemplate.postForEntity(baseUrl+"/saborPizza/estabelecimento/1?codigoAcesso=123456", requestSaborDePizza, SaborPizzaDTO.class);
        assertThat(responseSaborPizza.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // ATUALIZA A DISPONIBILIDADE DA PIZZA PARA INDISPONIVEL == FALSE
        this.restTemplate.exchange(baseUrl+"/saborPizza/3/disponibilidadeSabor/1?disponibilidade=false&codigoAcesso=123456", HttpMethod.PUT, null, JSONObject.class);

        // CLIENTE DEMONSTRA INTERESSE EM SABOR
        URI uriDemonstraInteresse = new URI(baseUrl + "/cliente/2/saborPizza/interesse/3");

        ResponseEntity<JSONObject> response = this.restTemplate.exchange(uriDemonstraInteresse, HttpMethod.PUT, null,JSONObject.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void adicionarSaborDeInteresseSaborQueNaoExiste() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        // CRIA ESTABELECIMENTO
        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento X", "Rua do Estabelecimento", 123456);
        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento", requestEstabelecimento, EstabelecimentoDTO.class);
        assertThat(responseEstabelecimento.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CRIA CLIENTE
        URI uriCliente = new URI(baseUrl + "/cliente");
        ClienteFormDTO clienteDTO = new ClienteFormDTO("Cliente Y", "Rua do Cliente",654321);

        HttpEntity<ClienteFormDTO> requestCliente = new HttpEntity<>(clienteDTO);
        ResponseEntity<ClienteDTO> responseCliente = this.restTemplate.postForEntity(uriCliente, requestCliente, ClienteDTO.class);
        assertThat(responseCliente.getStatusCodeValue()).isEqualTo(201); // Verifica criação

        // CLIENTE DEMONSTRA INTERESSE EM SABOR QUE NÃO EXISTE (NÃO FOI CRIADO)
        URI uriDemonstraInteresse = new URI(baseUrl + "/cliente/2/saborPizza/interesse/300");

        ResponseEntity<JSONObject> response = this.restTemplate.exchange(uriDemonstraInteresse, HttpMethod.PUT, null,JSONObject.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
