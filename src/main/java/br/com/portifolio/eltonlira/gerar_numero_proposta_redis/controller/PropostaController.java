package br.com.portifolio.eltonlira.gerar_numero_proposta_redis.controller;


import br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto.Data;
import br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto.PropostaDTORequest;
import br.com.portifolio.eltonlira.gerar_numero_proposta_redis.dto.PropostaDTOResponse;
import br.com.portifolio.eltonlira.gerar_numero_proposta_redis.service.PropostaService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaService propostaService;
    private final RedisTemplate<String, Long> redisTemplate;

    public PropostaController(PropostaService propostaService,
                              RedisTemplate<String, Long> redisTemplate) {
        this.propostaService = propostaService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping
    public ResponseEntity<Data<PropostaDTOResponse>> postProposta(
            @RequestBody PropostaDTORequest propostaDTORequest) {

        PropostaDTOResponse proposta = propostaService.gerarId(propostaDTORequest);

        // salva no redis


        return ResponseEntity.ok(new Data<>(proposta));
    }

    @GetMapping("/{idJornada}")
    public ResponseEntity<Data<PropostaDTOResponse>> getProposta(@PathVariable UUID idJornada) {

        var proposta = (PropostaDTOResponse)
                redisTemplate.opsForHash().get("mapa-proposta", idJornada.toString());

        if (proposta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new Data<>(proposta));
    }

    @GetMapping
    public ResponseEntity<Data<List<PropostaDTOResponse>>> getPropostas() {

        var valores = redisTemplate.opsForHash().values("mapa-proposta");

        List<PropostaDTOResponse> lista = new ArrayList<>();

        for (Object obj : valores) {
            lista.add((PropostaDTOResponse) obj);
        }

        lista.sort(Comparator.comparing(PropostaDTOResponse::getNumeroProposta).reversed());

        return ResponseEntity.ok(new Data<>(lista));
    }

    @GetMapping("/sequencia-atual")
    public ResponseEntity<Data<Map<String, Long>>> getSequenciaAtual() {

        Long valor =  redisTemplate.opsForValue().get("sequencia:proposta");
        Map<String, Long> dado = new HashMap<>();
        dado.put("sequencia",valor);

        return ResponseEntity.ok(new Data<>(dado));
    }
}
