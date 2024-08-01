package com.PitsA.model;

import com.PitsA.util.Abstracts.Observer;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@Entity
public class Cliente extends Observer {

    private String nome;
    private String enderecoPrincipal;
    private Integer codigoAcesso;

    public Cliente(Long id, String nome, String enderecoPrincipal, Integer codigoAcesso) {
        super.id = id;
        this.nome = nome;
        this.enderecoPrincipal = enderecoPrincipal;
        this.codigoAcesso = codigoAcesso;
    }

    public void update(Object mensagemRecebida) {
        String mensagem = String.format("%s, %s",  this.nome, (String) mensagemRecebida);
        System.out.println(mensagem);
    }
}
