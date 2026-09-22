package br.com.playyourlist.reproducoes;

import jakarta.validation.constraints.NotNull;

public record ReproducaoRequest(
        @NotNull(message = "O playlistid e obrigatorio") Integer playlistid) {
}
