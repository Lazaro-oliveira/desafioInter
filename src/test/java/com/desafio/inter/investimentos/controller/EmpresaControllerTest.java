
package com.desafio.inter.investimentos.controller;


import com.desafio.inter.investimentos.entity.Empresa;
import com.desafio.inter.investimentos.entity.Ticker;
import com.desafio.inter.investimentos.service.EmpresaService;
import com.desafio.inter.investimentos.service.TickerService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.desafio.inter.investimentos.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class EmpresaControllerTest {

    @Autowired
    private EmpresaController empresaController;

    @Autowired
    private TickerController tickerController;

    @MockBean
    private EmpresaService empresaService;

    @MockBean
    private TickerService tickerService;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup(){
        RestAssuredMockMvc.standaloneSetup(this.empresaController);
        mockMvc = MockMvcBuilders.standaloneSetup(empresaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }


    @Test
    public void RetornarSucesso_QuandoBuscarEmpresaPorId(){

        Empresa empresa= new Empresa();
        empresa.setId(1L);
        empresa.setNome("Inter");
        empresa.setAtiva(true);

        when(this.empresaService.buscarPorId(1L))
                .thenReturn(java.util.Optional.of(empresa));

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .get("/empresa/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void RetornarNaoEncontrado_QuandoBuscarEmpresaPorId(){


        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .get("/empresa/{id}", 5L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void RetornarCreated_QuandoSalvarEmpresa() throws Exception {

        Empresa empresa= new Empresa();
        empresa.setId(1L);
        empresa.setNome("Inter");
        empresa.setAtiva(true);

        mockMvc.perform(post("/empresa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(empresa)))
                .andExpect(status().isCreated());

    }

    @Test
    public void RetornarNoContent_QuandoDeletarEmpresaPorId(){

        Empresa empresa= new Empresa();
        empresa.setId(1L);
        empresa.setNome("Inter");
        empresa.setAtiva(true);


        when(this.empresaService.buscarPorId(1L))
                .thenReturn(java.util.Optional.of(empresa));

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .delete("/empresa/{id}", 1L)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void RetornarNotFound_QuandoDeletarEmpresaPorIdNaoEncontrada(){

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .delete("/empresa/{id}", 1L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }



}
