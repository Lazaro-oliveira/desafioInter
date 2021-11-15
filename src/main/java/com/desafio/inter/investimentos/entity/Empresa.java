package com.desafio.inter.investimentos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length = 100)
    private String nome;

    @Column(nullable = false)
    private Boolean ativa;

    @JsonIgnoreProperties("empresa")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa" ,cascade = CascadeType.ALL)
    private List<Ticker> ticker = new ArrayList<>();

}
