package com.desafio.inter.investimentos.repository;

import com.desafio.inter.investimentos.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
    List<Empresa> findByAtiva(Boolean ativa);

}
