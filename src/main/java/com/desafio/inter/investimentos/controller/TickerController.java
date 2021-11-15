package com.desafio.inter.investimentos.controller;


import com.desafio.inter.investimentos.entity.Empresa;
import com.desafio.inter.investimentos.entity.Ticker;
import com.desafio.inter.investimentos.service.EmpresaService;
import com.desafio.inter.investimentos.service.TickerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ticker")
public class TickerController {

    @Autowired
    private TickerService tickerService;

    @Autowired
    private ModelMapper modelMapper;

    //Não consegui fazer funcionar
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Ticker> listaTicker(){
        return tickerService.listaTicker();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ticker buscarTickerPorId(@PathVariable("id") Long id){
        return tickerService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ação não encontrada"));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerTicker(@PathVariable("id") Long id){
        tickerService.buscarPorId(id)
                .map(ticker -> {
                    tickerService.removerPorId(ticker.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ação não encontrada"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarTicker(@PathVariable("id") Long id, @RequestBody Ticker ticker){
        tickerService.buscarPorId(id)
                .map(tickerBase -> {
                    modelMapper.map(ticker, tickerBase);
                    tickerService.salvar(tickerBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ação não encontrada"));
    }


}
