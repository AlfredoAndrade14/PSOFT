package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.facades.Facade;

import java.util.Date;

class ProdutoTest {

	private Facade mercadoFacade = new Facade(); 
	
	private String jsonP1 = "{\"nome\":\"Leite integral\", \"fabricante\":\"Parmalat\", \"preco\":10.5}";

	private String idP1;

	@BeforeEach
	public void createProduto() {
		this.idP1 = mercadoFacade.criaProduto(jsonP1);
	}
	
	@Test
	public void verifyProdutosIgualUm() {
		assertEquals(1, mercadoFacade.listaProdutos().size());
	}

	@Test
	public void listagemProdutos() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		assertEquals(2, mercadoFacade.listaProdutos().size());
	}

	@Test
	public void createProdutoComParametros() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		assertEquals(2, mercadoFacade.listaProdutos().size());
	}

	@Test
	public void createProdutoComNomeNulo() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.criaProduto(null, "Caloi", 1580.50));
	}

	@Test
	public void createProdutoComNomeVazio() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.criaProduto("", "Caloi", 1580.50));
	}

	@Test
	public void createProdutoComFabricanteNulo() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.criaProduto("Bicicleta", null, 1580.50));
	}

	@Test
	public void createProdutoComFabricanteVazio() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.criaProduto("Bicicleta", "", 1580.50));
	}

	@Test
	public void removeProdutoPeloId() {
		String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		mercadoFacade.removeProduto(id);
		assertEquals(1, mercadoFacade.listaProdutos().size());
	}

	@Test
	public void removeProdutoPeloIdInexitente() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.removeProduto("155"));
	}

	@Test
	public void removeProdutoPorIdNulo() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.removeProduto(null));
	}

	@Test
	public void removeProdutoPorIdVazio() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.removeProduto(""));
	}

	@Test
	public void removerProdutoPeloIdERemoverLoteAssociado() {
		String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10,new Date(2020,01,10));
		mercadoFacade.removeProduto(id);
		assertEquals(1, mercadoFacade.listaProdutos().size());
		assertEquals(0,mercadoFacade.listaLotes().size());
	}

	@Test
	public void listaProdutosPeloNome() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		assertEquals(1, mercadoFacade.listaProdutosPeloNome("leite").size());
	}

	@Test
	public void listaProdutosPeloNomeSemNenhumProduto() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		assertEquals(0, mercadoFacade.listaProdutosPeloNome("bolacha").size());
	}

	@Test
	public void listaProdutosPeloNomeComNomeEmComum() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		assertEquals(2, mercadoFacade.listaProdutosPeloNome("le").size());
	}

	@Test
	public void listaProdutosPeloNomeEmBranco() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.listaProdutosPeloNome(""));
	}

	@Test
	public void listaProdutosPeloNulo() {
		mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.listaProdutosPeloNome(null));
	}

	@Test
	public void listaProdutosComLotePeloNome() {
		String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10,new Date(2020,01,10));
		assertEquals(1, mercadoFacade.listaProdutosComLotePeloNome("bicicleta").size());
	}

	@Test
	public void listaProdutosComLotePeloNomeComNomeEmComum() {
		String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10,new Date(2020,01,10));
		mercadoFacade.criaLote(mercadoFacade.listaProdutoId(this.idP1),10,new Date(2020,01,10));
		assertEquals(2, mercadoFacade.listaProdutosComLotePeloNome("le").size());
	}

	@Test
	public void listaProdutosComLotePeloNomeComNomeNulo() {
		String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10,new Date(2020,01,10));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.listaProdutosComLotePeloNome(null).size());
	}

	@Test
	public void listaProdutosComLotePeloNomeComNomeVazio() {
		String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
		mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10,new Date(2020,01,10));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.listaProdutosComLotePeloNome("").size());
	}
}