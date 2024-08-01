package com.PitsA;

import com.PitsA.dto.EntregadorDTO;
import com.PitsA.dto.EntregadorFormDTO;
import com.PitsA.dto.EstabelecimentoDTO;
import com.PitsA.dto.EstabelecimentoFormDTO;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PitsAApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class EntregadorControllerTests {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testcriarEntregadorComSucesso() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", 2L, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void testcriarEntregadorComNomeEmBranco() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("", "AAA0A00", 2L, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorSemNome() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO(null, "AAA0A00", 2L, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorComPlacaEmBranco() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "", 2L, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorSemPlaca() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", null, 2L, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorTipoDeVeiculoInvalido() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", 3L, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void testcriarEntregadorSemTipoDeVeiculo() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", null, "PRETO", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorComCorDeVeiculoEmBranco() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", 2L, "", 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorSemCorDeVeiculo() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", 2L, null, 123456);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorComSenhaComMenosDe6Digitos() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", 2L, "Preto", 12345);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testcriarEntregadorSemSenha() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        EntregadorFormDTO entregadorDTO = new EntregadorFormDTO("Entregador", "AAA0A00", 2L, "Preto", null);
        HttpEntity<EntregadorFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<EntregadorDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/entregador/estabelecimento/"+estabelecimentoId+"/", requestEntregador, EntregadorDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(400);
    }
}
