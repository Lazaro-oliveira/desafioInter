package com.desafio.inter.investimentos.repository;

import com.desafio.inter.investimentos.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, Long> {
}
