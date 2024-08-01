package com.PitsA;

import com.PitsA.dto.*;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PitsAApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class SaborPizzaControllerTests {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCriarSaborComSucesso()  {
        final String baseUrl = "http://localhost:" + serverPort + "/api";

        EstabelecimentoFormDTO estabelecimentoDTO = new EstabelecimentoFormDTO("Estabelecimento", "Rua dos bobos nº 0", 123456);

        HttpEntity<EstabelecimentoFormDTO> requestEstabelecimento = new HttpEntity<>(estabelecimentoDTO);
        ResponseEntity<EstabelecimentoDTO> responseEstabelecimento = this.restTemplate.postForEntity(baseUrl + "/estabelecimento/", requestEstabelecimento, EstabelecimentoDTO.class);
        Long estabelecimentoId = responseEstabelecimento.getBody().getId();

        SaborPizzaFormDTO entregadorDTO = new SaborPizzaFormDTO("Portuguesa",45.00,1L,2L);
        HttpEntity<SaborPizzaFormDTO> requestEntregador = new HttpEntity<>(entregadorDTO);
        ResponseEntity<SaborPizzaDTO> responseEntregador = this.restTemplate.postForEntity(baseUrl+"/saborPizza/estabelecimento/"+estabelecimentoId+"?codigoAcesso=123456", requestEntregador, SaborPizzaDTO.class);

        assertThat(responseEntregador.getStatusCodeValue()).isEqualTo(201);
    }
}