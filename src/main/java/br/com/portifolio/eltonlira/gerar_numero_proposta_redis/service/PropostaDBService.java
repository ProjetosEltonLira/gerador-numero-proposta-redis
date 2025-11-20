package br.com.portifolio.eltonlira.gerar_numero_proposta_redis.service;

import org.springframework.stereotype.Service;

@Service
public class PropostaDBService {

    private Long valorDoBanco = null; // simulação

    public Long BuscarPropostaMaisRecenteNoDB() {
        if (valorDoBanco == null) {
            valorDoBanco = 10000100L; // viria do BD real
        }
        return valorDoBanco;
    }

    public void salvarNovoValor(Long novoValor) {
        valorDoBanco = novoValor;
    }
}

