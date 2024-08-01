package com.PitsA.dto;
import com.PitsA.model.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClienteDTO {

    private Long id;
    private String nome;
    private String enderecoPrincipal;

    @JsonIgnore
    private Integer codigoAcesso;

    public ClienteDTO(Cliente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.enderecoPrincipal = cliente.getEnderecoPrincipal();
        this.codigoAcesso = cliente.getCodigoAcesso();
    }

    public Cliente convert() {
        return new Cliente(this.id,
                           this.nome,
                           this.enderecoPrincipal,
                           this.codigoAcesso);
    }
}
