package br.com.playyourlist.reproducoes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class StatisticController {

    private final ReproducaoService reproducaoService;

    public StatisticController(ReproducaoService reproducaoService) {
        this.reproducaoService = reproducaoService;
    }

    // Alias exigido no texto do endpoint PUT /api/executar/{playlistId}.
    @PostMapping("/statistic")
    public ResponseEntity<Reproducao> criar(
            @Valid @RequestBody ReproducaoRequest request) {
        return ResponseEntity.status(201)
                .body(reproducaoService.criar(request.playlistid()));
    }
}
