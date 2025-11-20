package br.com.portifolio.eltonlira.gerar_numero_proposta_redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupLogger.class);

    @Override
    public void run(String... args) {
        logger.info("Programa iniciado");
    }
}