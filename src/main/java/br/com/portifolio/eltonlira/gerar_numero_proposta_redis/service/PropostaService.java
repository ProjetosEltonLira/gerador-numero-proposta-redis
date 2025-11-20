package br.com.portifolio.eltonlira.gerar_numero_proposta_redis.service;


import br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto.PropostaDTORequest;
import br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto.PropostaDTOResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class PropostaService {

    private static final String KEY_CONTADOR = "sequencia:proposta";
    private final RedisTemplate<String, Long> redisTemplate;
    private final PropostaDBService propostaDBService;

    public PropostaService(RedisTemplate<String, Long> redisTemplate,
                           PropostaDBService propostaDBService) {
        this.redisTemplate = redisTemplate;
        this.propostaDBService = propostaDBService;
    }

    public PropostaDTOResponse gerarId(PropostaDTORequest request) {

        // 1. Verifica se existe contador no Redis
        Long value = redisTemplate.opsForValue().get(KEY_CONTADOR);

        if (value == null) {
            // 2. Busca do banco
            Long inicial = propostaDBService.BuscarPropostaMaisRecenteNoDB();

            // 3. Atualiza Redis com o valor inicial
            redisTemplate.opsForValue().set(KEY_CONTADOR, inicial);

            value = inicial;
        }

        // 4. Incremento atômico (incremento sempre do valor atual)
        Long novoValor = redisTemplate.opsForValue().increment(KEY_CONTADOR, 1L);

        var dadosProposta =  new PropostaDTOResponse(request.idJornada(), novoValor);

        redisTemplate.opsForHash().put("mapa-proposta",
                dadosProposta.getIdJornada().toString(),
                dadosProposta);

        return dadosProposta;
    }
}









