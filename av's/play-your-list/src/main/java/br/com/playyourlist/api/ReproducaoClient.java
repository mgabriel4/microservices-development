package br.com.playyourlist.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.playyourlist.reproducoes.Reproducao;
import br.com.playyourlist.reproducoes.ReproducaoRequest;

@FeignClient(name = "reproducoes-client", url = "${app.base-url}")
public interface ReproducaoClient {

    @PostMapping("/statistic")
    ResponseEntity<Reproducao> criar(
            @RequestBody ReproducaoRequest request);
}
