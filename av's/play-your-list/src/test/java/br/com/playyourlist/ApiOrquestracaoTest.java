package br.com.playyourlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import br.com.playyourlist.api.ApiOrquestradorService;
import br.com.playyourlist.api.ExecucaoResponse;
import br.com.playyourlist.api.MensagemResponse;
import br.com.playyourlist.playlists.PlaylistMusicaRepository;
import br.com.playyourlist.reproducoes.ReproducaoRepository;

@DirtiesContext
@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=8181",
                "app.base-url=http://localhost:8181",
                "spring.datasource.url=jdbc:h2:mem:playyourlist_api"
        })
class ApiOrquestracaoTest {

    @Autowired
    private ApiOrquestradorService orquestradorService;

    @Autowired
    private PlaylistMusicaRepository playlistMusicaRepository;

    @Autowired
    private ReproducaoRepository reproducaoRepository;

    @Test
    void deveOrquestrarEndpointsComOpenFeign() {
        MensagemResponse inclusao = orquestradorService.adicionarMusica(1, 2);

        assertEquals(
                "Música Billie Jean adicionada com sucesso à playlist Clássicos do Rock",
                inclusao.mensagem());
        assertTrue(playlistMusicaRepository
                .existsByPlaylistidAndMusicaid(1, 2));

        long antes = reproducaoRepository.countByPlaylistid(3);
        ExecucaoResponse execucao = orquestradorService.executarPlaylist(3);

        assertEquals(antes + 1, reproducaoRepository.countByPlaylistid(3));
        assertEquals(3, execucao.reproducao().getPlaylistid());
    }
}
