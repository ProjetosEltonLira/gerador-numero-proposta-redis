package br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class PropostaDTOResponse implements Serializable {

    UUID idJornada;
    Long numeroProposta;
    LocalDateTime dataCriacao;

    public PropostaDTOResponse(){};
    public PropostaDTOResponse(UUID idJornada, Long numeroProposta) {
        this.idJornada = idJornada;
        this.numeroProposta = numeroProposta;
        this.dataCriacao = LocalDateTime.now();
    }

    public UUID getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(UUID idJornada) {
        this.idJornada = idJornada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getNumeroProposta() {
        return numeroProposta;
    }

    public void setNumeroProposta(Long numeroProposta) {
        this.numeroProposta = numeroProposta;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "idJornada=" + idJornada +
                ", numeroProposta=" + numeroProposta +
                '}';
    }
}
