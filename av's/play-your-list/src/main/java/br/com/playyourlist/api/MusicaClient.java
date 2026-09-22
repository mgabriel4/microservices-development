package br.com.playyourlist.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.playyourlist.musicas.Musica;

@FeignClient(name = "musicas-client", url = "${app.base-url}")
public interface MusicaClient {

    @GetMapping("/musicas/{id}")
    ResponseEntity<Musica> buscar(@PathVariable("id") Integer id);
}
