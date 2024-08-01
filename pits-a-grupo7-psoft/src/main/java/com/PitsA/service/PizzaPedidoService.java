package com.PitsA.service;

import com.PitsA.dto.PizzaPedidoFormDTO;
import com.PitsA.exception.saborPizza.SaborPizzaNotFoundException;
import com.PitsA.exception.saborPizza.SaborPizzaUnavailableException;
import com.PitsA.model.PizzaPedido;
import com.PitsA.model.SaborPizza;
import com.PitsA.repository.PizzaPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaPedidoService {

    @Autowired
    private PizzaPedidoRepository pizzaPedidoRepository;

    @Autowired
    private SaborPizzaService saborPizzaService;

    public PizzaPedido criar(PizzaPedidoFormDTO pizzaPedidoFormDTO) throws SaborPizzaNotFoundException, SaborPizzaUnavailableException {

        SaborPizza saborUm = pizzaPedidoFormDTO.getSabor1Id()==null? null  : this.saborPizzaService.getSaborPizza(pizzaPedidoFormDTO.getSabor1Id());
        SaborPizza saborDois = pizzaPedidoFormDTO.getSabor2Id()==null? null : this.saborPizzaService.getSaborPizza(pizzaPedidoFormDTO.getSabor2Id());

        if (saborUm !=null && saborPizzaService.getSaborPizza(pizzaPedidoFormDTO.getSabor1Id()).getDisponivel() ||
                saborDois!=null && saborPizzaService.getSaborPizza(pizzaPedidoFormDTO.getSabor2Id()).getDisponivel()){

            PizzaPedido pizzaPedido = new PizzaPedido(null, saborUm, saborDois, pizzaPedidoFormDTO.getQuantidade());
            this.pizzaPedidoRepository.save(pizzaPedido);
            return pizzaPedido;
        }else{
                throw new SaborPizzaUnavailableException();
        }
    }

}
