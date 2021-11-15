package com.desafio.inter.investimentos.controller;

import com.desafio.inter.investimentos.entity.Empresa;
import com.desafio.inter.investimentos.service.EmpresaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empresa salvar(@RequestBody Empresa empresa){

        return empresaService.salvar(empresa);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Empresa> listaEmpresa(){
        return empresaService.listaEmpresa();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Empresa buscarEmpresaPorId(@PathVariable("id") Long id){
        return empresaService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada"));

    }

    @GetMapping("/ativa/{ativa}")
    @ResponseStatus(HttpStatus.OK)
    public List<Empresa> buscarEmpresaAtiva(@PathVariable("ativa") Boolean ativa){

        return empresaService.listaEmpresaAtiva(ativa);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerEmpresa(@PathVariable("id") Long id){
        System.out.println("aqui "+ id);
        empresaService.buscarPorId(id)
                .map(empresa -> {
                    empresaService.removerPorId(empresa.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarEmpresa(@PathVariable("id") Long id, @RequestBody Empresa empresa){
        empresaService.buscarPorId(id)
                .map(empresaBase -> {
                    modelMapper.map(empresa, empresaBase);
                    empresaService.salvar(empresaBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

}
