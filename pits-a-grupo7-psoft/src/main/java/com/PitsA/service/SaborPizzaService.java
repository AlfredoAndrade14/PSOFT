package com.PitsA.service;

import com.PitsA.dto.EstabelecimentoDTO;
import com.PitsA.dto.SaborPizzaDTO;
import com.PitsA.dto.SaborPizzaFormDTO;
import com.PitsA.exception.accessCode.AccessCodeIncorrectException;
import com.PitsA.exception.accessCode.MustHaveAnAccessCodeException;
import com.PitsA.exception.accessCode.TheAccessCodeMustHaveSixDigitsException;
import com.PitsA.exception.estabelecimento.EstabelecimentoNotFoundException;
import com.PitsA.exception.estabelecimento.ThereIsNoPizzaFlavorWithTypeException;
import com.PitsA.exception.saborPizza.*;
import com.PitsA.exception.tamanhoPizza.TamanhoPizzaNotFoundException;
import com.PitsA.exception.tipoSabor.TipoSaborNotFoundException;
import com.PitsA.model.Cliente;
import com.PitsA.model.SaborPizza;
import com.PitsA.model.TamanhoPizza;
import com.PitsA.model.TipoSabor;
import com.PitsA.repository.SaborPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaborPizzaService {

    @Autowired
    private SaborPizzaRepository saborPizzaRepository;

    @Autowired
    private TamanhoPizzaService tamanhoPizzaService;

    @Autowired
    private TipoSaborService tipoSaborService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    public SaborPizzaDTO pegaPeloId(Long id) throws SaborPizzaNotFoundException {
        return new SaborPizzaDTO(this.getSaborPizza(id));
    }

    public SaborPizza getSaborPizza(Long id) throws SaborPizzaNotFoundException{
        SaborPizza saborPizza = this.saborPizzaRepository
                .findById(id).orElseThrow(() -> new SaborPizzaNotFoundException());

        return saborPizza;
    }

    public SaborPizzaDTO criaSabor(Long estabelecimentoId, SaborPizzaFormDTO saborPizzaDTO, Integer codigoAcesso) throws SaborPizzaMustHaveAValidNameException, SaborPizzaMustHaveAValidValorException, SaborPizzaMustHaveAValidTipoSaborException, SaborPizzaMustHaveAValidTamanhoException, EstabelecimentoNotFoundException, TipoSaborNotFoundException, TamanhoPizzaNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
        this.validaSaborPizza(saborPizzaDTO);

        estabelecimentoService.validaAcaoEstabelecimento(estabelecimentoId,codigoAcesso);

        TipoSabor tipoSabor = tipoSaborService.pegaTipoSabor(saborPizzaDTO.getTipoSaborId()).convert();
        TamanhoPizza tamanhoPizza = tamanhoPizzaService.pegaTamanhoPizza(saborPizzaDTO.getTamanhoPizzaId()).convert();
        SaborPizza saborPizza = new SaborPizza(null,saborPizzaDTO.getNome(),saborPizzaDTO.getValor(),
                tipoSabor,tamanhoPizza, true);

        this.saborPizzaRepository.save(saborPizza);
        this.estabelecimentoService.adicionaSaborPizza(estabelecimentoId, saborPizza);

        return new SaborPizzaDTO(saborPizza);
    }

    public SaborPizzaDTO editaSabor(Long id, SaborPizzaFormDTO saborPizzaDTO) throws SaborPizzaMustHaveAValidNameException, SaborPizzaMustHaveAValidValorException, SaborPizzaNotFoundException, SaborPizzaMustHaveAValidTipoSaborException, SaborPizzaMustHaveAValidTamanhoException, TipoSaborNotFoundException, TamanhoPizzaNotFoundException {
        this.validaSaborPizza(saborPizzaDTO);
        
        SaborPizza saborPizza = this.getSaborPizza(id);
        saborPizza.setTipoSabor(this.tipoSaborService.pegaTipoSabor(saborPizzaDTO.getTipoSaborId()).convert());
        saborPizza.setTamanhoPizza(this.tamanhoPizzaService.pegaTamanhoPizza(saborPizzaDTO.getTamanhoPizzaId()).convert());
        saborPizza.setNome(saborPizzaDTO.getNome());
        saborPizza.setValor(saborPizzaDTO.getValor());

        this.saborPizzaRepository.save(saborPizza);

        return new SaborPizzaDTO(saborPizza);
    }

    public void deletaSabor(Long id) throws SaborPizzaNotFoundException {
        SaborPizza saborPizza= this.getSaborPizza(id);
        this.saborPizzaRepository.delete(saborPizza);
    }

    private void validaSaborPizza(SaborPizzaFormDTO saborPizzaDTO) throws SaborPizzaMustHaveAValidNameException, SaborPizzaMustHaveAValidValorException, SaborPizzaMustHaveAValidTipoSaborException, SaborPizzaMustHaveAValidTamanhoException {
        if (saborPizzaDTO.getNome() == null || saborPizzaDTO.getNome().trim().equals("")) throw new SaborPizzaMustHaveAValidNameException();
        if (saborPizzaDTO.getValor() < 0) throw new SaborPizzaMustHaveAValidValorException();
    }

    public void atualizaDisponibilidade(Long idSaborPizza, Boolean disponibilidade) throws SaborPizzaNotFoundException {
        SaborPizza saborPizza = this.getSaborPizza(idSaborPizza);
        saborPizza.setDisponivel(disponibilidade);
        this.saborPizzaRepository.save(saborPizza);
    }

    public void adicionaInteressado(Cliente cliente, Long idSaborPizza) throws SaborPizzaNotFoundException, SaborPizzaIsAvailableException {
        SaborPizza saborPizza = this.getSaborPizza(idSaborPizza);
        if (saborPizza.getDisponivel()) throw new SaborPizzaIsAvailableException();
        saborPizza.registerObserver(cliente);

        this.saborPizzaRepository.save(saborPizza);
    }

    public void atualizaDisponibilidadeSaborPizza(Long estabelecimentoId, Long saborPizzaId, Boolean disponibilidade, Integer codigoAcesso) throws EstabelecimentoNotFoundException, SaborPizzaNotFoundException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException, InvalidFlavorForStore {
        estabelecimentoService.validaAcaoEstabelecimento(estabelecimentoId,codigoAcesso);
        EstabelecimentoDTO estabelecimento = estabelecimentoService.pegaEstabelecimento(estabelecimentoId);
        verificaSaborEstabelecimento(estabelecimento, saborPizzaId);
        this.atualizaDisponibilidade(saborPizzaId, disponibilidade);
    }

    private boolean verificaSaborEstabelecimento(EstabelecimentoDTO estabelecimento, Long saborPizzaId) throws InvalidFlavorForStore {
        for(SaborPizzaDTO sabor: estabelecimento.getSaboresPizzas()) {
            if(sabor.getId() == saborPizzaId) return true;
        }
        throw new InvalidFlavorForStore();
    }

    public List<SaborPizzaDTO> pegaPorEstabelecimento(Long estabelecimentoId, String tipoSabor, Integer codigoAcesso) throws EstabelecimentoNotFoundException, ThereIsNoPizzaFlavorWithTypeException, MustHaveAnAccessCodeException, AccessCodeIncorrectException, TheAccessCodeMustHaveSixDigitsException {
        estabelecimentoService.validaAcaoEstabelecimento(estabelecimentoId, codigoAcesso);
        EstabelecimentoDTO estabelecimento = estabelecimentoService.pegaEstabelecimento(estabelecimentoId);
        Set<SaborPizzaDTO> saborPizzaDTOSet = estabelecimento.getSaboresPizzas();
        List<SaborPizzaDTO> cardapio = new ArrayList<>();

        if (tipoSabor == null) cardapio = estabelecimento.getSaboresPizzas().stream().collect(Collectors.toList());
        else filtraSaboresPorTipo(saborPizzaDTOSet, cardapio, tipoSabor);

        if (cardapio.isEmpty()) throw new ThereIsNoPizzaFlavorWithTypeException();

        return cardapio.stream()
                .sorted(Comparator.comparing(SaborPizzaDTO::getDisponivel, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private void filtraSaboresPorTipo(Set<SaborPizzaDTO> saborPizzaDTOSet, List<SaborPizzaDTO> saborPizzaDTOFiltro, String tipoSabor) {
        for (SaborPizzaDTO saborPizzaDTO : saborPizzaDTOSet) {
            if (saborPizzaDTO.getTipoSabor().getTipo().toLowerCase().equals(tipoSabor.toLowerCase())) {
                saborPizzaDTOFiltro.add(saborPizzaDTO);
            }
        }
    }
}