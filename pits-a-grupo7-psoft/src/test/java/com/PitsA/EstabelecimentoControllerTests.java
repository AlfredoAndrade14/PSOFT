package com.PitsA;

import com.PitsA.dto.EstabelecimentoDTO;
import com.PitsA.dto.EstabelecimentoFormDTO;
import com.PitsA.dto.SaborPizzaDTO;
import com.PitsA.dto.SaborPizzaFormDTO;
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
public class EstabelecimentoControllerTests {
	@LocalServerPort
	private int serverPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testcriarEstabelecimentoComSucesso() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	public void testcriarEstabelecimentoSemNome() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO(null, "Rua dos bobos nº 0", 123456);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void testcriarEstabelecimentoComNomeEmBranco() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("", "Rua dos bobos nº 0", 123456);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void testcriarEstabelecimentoSemEndereco() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", null, 123456);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void testcriarEstabelecimentoComEnderecoEmBranco() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "", 123456);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void testcriarEstabelecimentoComSenhaComMenosDe6Digitos() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 12345);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void testcriarEstabelecimentoSemSenha() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		URI uri = new URI(baseUrl + "/estabelecimento/");
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", null);

		HttpEntity<EstabelecimentoFormDTO> request = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> response = this.restTemplate.postForEntity(uri, request, EstabelecimentoDTO.class);

		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void testModificaDisponibilidadeDeSabor() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		// CRIA ESTABELECIMENTO
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento X", "Rua do Estabelecimento", 123456);
		HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento", requestEstabelecimento, EstabelecimentoDTO.class);
		assertThat(responseEstabelecimento.getStatusCodeValue()).isEqualTo(201);

		// CRIA SABOR DE PIZZA
		SaborPizzaFormDTO saborDePizzaDTO = new SaborPizzaFormDTO("Sabor1",20.00,1L,1L);
		HttpEntity<SaborPizzaFormDTO> requestSaborDePizza = new HttpEntity<>(saborDePizzaDTO);
		ResponseEntity<SaborPizzaDTO> responseSaborPizza = this.restTemplate.postForEntity(baseUrl+"/saborPizza/estabelecimento/1?codigoAcesso=123456", requestSaborDePizza, SaborPizzaDTO.class);
		assertThat(responseSaborPizza.getStatusCodeValue()).isEqualTo(201);

		URI uriGetSaborDePizza = new URI(baseUrl + "/saborPizza/2");
		URI uriModificaDisponibilidadeParaFalse = new URI(baseUrl + "/saborPizza/2/disponibilidadeSabor/1?disponibilidade=false&codigoAcesso=123456");
		URI uriModificaDisponibilidadeParaTrue = new URI(baseUrl + "/saborPizza/2/disponibilidadeSabor/1?disponibilidade=true&codigoAcesso=123456");

		// dá GET no SaborPizza e verifica sua disponibilidade, a padrão é true.
		SaborPizzaDTO responseGetSaborSemAlteracao = this.restTemplate.getForObject(uriGetSaborDePizza, SaborPizzaDTO.class);
		assertThat(responseGetSaborSemAlteracao.getDisponivel()).isEqualTo(true);	// Verifica a disponibilidade padrão == true

		// altera a Disponibilidade de True para False
		ResponseEntity<JSONObject> responseAlteracaoParaFalse = this.restTemplate.exchange(uriModificaDisponibilidadeParaFalse, HttpMethod.PUT, null, JSONObject.class);
		assertThat(responseAlteracaoParaFalse.getStatusCodeValue()).isEqualTo(200);

		// dá GET no SaborPizza pós mudança de disponibilidade para false, verificando se foi alterado para false
		SaborPizzaDTO responseGetSaborAlterouParaFalse = this.restTemplate.getForObject(uriGetSaborDePizza, SaborPizzaDTO.class);
		assertThat(responseGetSaborAlterouParaFalse.getDisponivel()).isEqualTo(false);	// Verifica a disponibilidade == false

		// faz o processo inverso, alterando a Disponibilidade de False para True
		ResponseEntity<JSONObject> responseAlteracaoParaTrue = this.restTemplate.exchange(uriModificaDisponibilidadeParaTrue, HttpMethod.PUT, null, JSONObject.class);
		assertThat(responseAlteracaoParaTrue.getStatusCodeValue()).isEqualTo(200);

		// dá GET no SaborPizza pós mudança de disponibilidade para true, verificando se foi alterado para true
		SaborPizzaDTO responseGetSaborAlterouParaTrue = this.restTemplate.getForObject(uriGetSaborDePizza, SaborPizzaDTO.class);
		assertThat(responseGetSaborAlterouParaTrue.getDisponivel()).isEqualTo(true);	// Verifica a disponibilidade == true
	}

	@Test // Entra em conflito com outros, se executa-lo sozinho ele funciona perfeitamente .
	public void testMostraDisponibilidadeNoCardapio() throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/api";

		// CRIA ESTABELECIMENTO
		EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento X", "Rua do Estabelecimento", 123456);
		HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
		ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento", requestEstabelecimento, EstabelecimentoDTO.class);
		assertThat(responseEstabelecimento.getStatusCodeValue()).isEqualTo(201); // verifica se estabelecimento foi criado

		// CRIA SABOR DE PIZZA 1
		SaborPizzaFormDTO saborDePizzaDTO1 = new SaborPizzaFormDTO("Sabor1",10.00,1L,1L);
		HttpEntity<SaborPizzaFormDTO> requestSaborDePizza1 = new HttpEntity<>(saborDePizzaDTO1);
		ResponseEntity<SaborPizzaDTO> responseSaborPizza1 = this.restTemplate.postForEntity(baseUrl+"/saborPizza/estabelecimento/1?codigoAcesso=123456", requestSaborDePizza1, SaborPizzaDTO.class);
		assertThat(responseSaborPizza1.getStatusCodeValue()).isEqualTo(201); // verifica se sabor foi criado

		// CRIA SABOR DE PIZZA 2
		SaborPizzaFormDTO saborDePizzaDTO2 = new SaborPizzaFormDTO("Sabor2",20.00,1L,1L);
		HttpEntity<SaborPizzaFormDTO> requestSaborDePizza2 = new HttpEntity<>(saborDePizzaDTO2);
		ResponseEntity<SaborPizzaDTO> responseSaborPizza2 = this.restTemplate.postForEntity(baseUrl+"/saborPizza/estabelecimento/1?codigoAcesso=123456", requestSaborDePizza2, SaborPizzaDTO.class);
		assertThat(responseSaborPizza2.getStatusCodeValue()).isEqualTo(201); // verifica se sabor foi criado

		// Modifica a disponibilidade da PIZZA 1 (id=2) para False, fazendo ela ficar sempre, no fim do cardápio
		URI uriModificaDisponibilidadeParaFalse = new URI(baseUrl + "/saborPizza/2/disponibilidadeSabor/1?disponibilidade=false&codigoAcesso=123456");
		ResponseEntity<JSONObject> responseAlteracaoParaFalse = this.restTemplate.exchange(uriModificaDisponibilidadeParaFalse, HttpMethod.PUT, null, JSONObject.class);
		assertThat(responseAlteracaoParaFalse.getStatusCodeValue()).isEqualTo(200); // verifica se foi alterado

		String cardapioEsperado = "[{\"id\":3,\"nome\":\"Sabor2\",\"valor\":20.0,\"tipoSabor\":{\"id\":1,\"tipo\":\"Salgado\"},\"tamanhoPizza\":{\"id\":1,\"tamanho\":\"Médio\"},\"disponivel\":true},{\"id\":2,\"nome\":\"Sabor1\",\"valor\":10.0,\"tipoSabor\":{\"id\":1,\"tipo\":\"Salgado\"},\"tamanhoPizza\":{\"id\":1,\"tamanho\":\"Médio\"},\"disponivel\":false}]";

		// GET no cardápio
		String cardapio = this.restTemplate.getForObject(baseUrl + "/saborPizza/cardapio/1?codigoAcesso=123456", String.class);
		assertThat(cardapio).isEqualTo(cardapioEsperado);
	}
}
