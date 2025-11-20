package br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto;

public class Data<T> {
    private T data;

    public Data(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
