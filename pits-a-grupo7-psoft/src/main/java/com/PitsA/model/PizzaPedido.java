package com.PitsA.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PizzaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SaborPizza saborPizzaUm;

    @ManyToOne
    private SaborPizza saborPizzaDois;

    private Integer quantidade;
}
