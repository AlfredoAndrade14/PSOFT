import model.Lote;
import model.Product;

import java.util.Date;

public class main {
    public static void main(String[] args) {
        Facade facade = new Facade();

        Product bicicleta = facade.postProduct("bicicleta","Caloi", 1200.2);
        System.out.println(facade.getProducts().toString());

        Lote lote = facade.postLote(5, new Date(2020,01,20), bicicleta);
        System.out.println(facade.getLotes().toString());
    }
}
