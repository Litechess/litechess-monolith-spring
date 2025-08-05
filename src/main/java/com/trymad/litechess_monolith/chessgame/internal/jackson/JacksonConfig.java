package com.trymad.litechess_monolith.chessgame.internal.jackson;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.trymad.litechess_monolith.chessgame.ChessPiece;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    Module chessPieceModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ChessPiece.class, new ChessPieceDeserializer());
        return module;
    }
}
