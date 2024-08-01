package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.facades.Facade;

import java.util.Date;

class LoteTest {
    private Facade mercadoFacade = new Facade();
    private String idP1;
    private String idL1;

    @BeforeEach
    public void createLote() {
        String jsonP1 = "{\"nome\":\"Leite integral\", \"fabricante\":\"Parmalat\", \"preco\":10.5}";
        this.idP1 = mercadoFacade.criaProduto(jsonP1);
        String jsonL1 = "{\"idProduto\":\"" + idP1 + "\", \"quantidade\":10}";
        this.idL1 = mercadoFacade.criaLote(jsonL1);
    }

    @Test
    public void createLoteComParametros() {
        String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
        mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10,new Date(2020,01,10));
        assertEquals(2,mercadoFacade.listaLotes().size());
    }

    @Test
    public void createLoteComProdutoNulo() {
        String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
        Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.criaLote(null,10,new Date(2020,01,10)));
    }

    @Test
    public void createLoteComDataDeFabricacaoNula() {
        String id = mercadoFacade.criaProduto("Bicicleta", "Caloi", 1580.50);
        Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.criaLote(mercadoFacade.listaProdutoId(id),10, null));
    }

    @Test
    public void removeLotePeloId() {
        mercadoFacade.removeLote(this.idL1);
        assertEquals(0,mercadoFacade.listaLotes().size());
    }

    @Test
    public void removeLoteIdInexistente() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.removeLote("1515"));
    }

    @Test
    public void removeLoteIdNulo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.removeLote(null));
    }

    @Test
    public void removeLoteIdVazio() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> mercadoFacade.removeLote(""));
    }
}
