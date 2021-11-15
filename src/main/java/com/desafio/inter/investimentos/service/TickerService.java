package com.desafio.inter.investimentos.service;

import com.desafio.inter.investimentos.entity.Ticker;
import com.desafio.inter.investimentos.repository.TickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TickerService {

    @Autowired
    private TickerRepository tickerRepository;

    public Ticker salvar(Ticker ticker){
        return tickerRepository.save(ticker);
    }

    public List<Ticker> listaTicker(){
        return tickerRepository.findAll();
    }

    public Optional<Ticker> buscarPorId(Long id){
        return tickerRepository.findById(id);
    }

    public void removerPorId(Long id){
        tickerRepository.deleteById(id);
    }
}
