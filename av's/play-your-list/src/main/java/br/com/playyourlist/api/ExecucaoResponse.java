package br.com.playyourlist.api;

import br.com.playyourlist.reproducoes.Reproducao;

public record ExecucaoResponse(String mensagem, Reproducao reproducao) {
}
