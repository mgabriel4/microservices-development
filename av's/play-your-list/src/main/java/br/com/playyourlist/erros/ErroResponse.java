package br.com.playyourlist.erros;

import java.time.LocalDateTime;

public record ErroResponse(LocalDateTime datahora, int status, String erro) {
}
