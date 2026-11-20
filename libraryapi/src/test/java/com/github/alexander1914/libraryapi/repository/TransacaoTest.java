package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacaoTest {

    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacaoSimples() {
        transacaoService.executar();
    }

    @Test
    void transancaoEstadoManaged(){
        transacaoService.atualizarSemAtualizar();
    }

    @Test
    void rollbackTest() {
        transacaoService.rollback();
    }
}
