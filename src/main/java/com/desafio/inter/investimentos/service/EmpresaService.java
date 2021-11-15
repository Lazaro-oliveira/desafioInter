package com.desafio.inter.investimentos.service;

import com.desafio.inter.investimentos.entity.Empresa;
import com.desafio.inter.investimentos.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa salvar(Empresa empresa){
        empresa.getTicker().forEach(t -> t.setEmpresa(empresa));
        return empresaRepository.save(empresa);
    }

    public List<Empresa> listaEmpresa(){return empresaRepository.findAll();}

    public List<Empresa> listaEmpresaAtiva(Boolean ativa){return empresaRepository.findByAtiva(ativa);
    }

    public Optional<Empresa> buscarPorId(Long id){
        return empresaRepository.findById(id);
    }

    public void removerPorId(Long id){
        empresaRepository.deleteById(id);
    }
}
