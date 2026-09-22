package br.com.playyourlist.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.playyourlist.erros.RecursoNaoEncontradoException;
import br.com.playyourlist.musicas.Musica;
import br.com.playyourlist.playlists.Playlist;
import br.com.playyourlist.reproducoes.Reproducao;
import br.com.playyourlist.reproducoes.ReproducaoRequest;

@Service
public class ApiOrquestradorService {

    private final MusicaClient musicaClient;
    private final PlaylistClient playlistClient;
    private final ReproducaoClient reproducaoClient;

    public ApiOrquestradorService(MusicaClient musicaClient,
                                  PlaylistClient playlistClient,
                                  ReproducaoClient reproducaoClient) {
        this.musicaClient = musicaClient;
        this.playlistClient = playlistClient;
        this.reproducaoClient = reproducaoClient;
    }

    public MensagemResponse adicionarMusica(Integer playlistId, Integer musicaId) {
        Musica musica = corpoOuErro(
                musicaClient.buscar(musicaId), "Musica", musicaId);
        Playlist playlist = corpoOuErro(
                playlistClient.buscar(playlistId), "Playlist", playlistId);

        playlistClient.adicionarMusica(playlistId, musicaId);

        return new MensagemResponse(
                "Música " + musica.getTitulo()
                        + " adicionada com sucesso à playlist " + playlist.getNome());
    }

    public ExecucaoResponse executarPlaylist(Integer playlistId) {
        Playlist playlist = corpoOuErro(
                playlistClient.buscar(playlistId), "Playlist", playlistId);

        Reproducao reproducao = corpoOuErro(
                reproducaoClient.criar(new ReproducaoRequest(playlistId)),
                "Reproducao da playlist", playlistId);

        return new ExecucaoResponse(
                "Playlist " + playlist.getNome() + " executada com sucesso",
                reproducao);
    }

    private <T> T corpoOuErro(ResponseEntity<T> response,
                              String recurso,
                              Integer id) {
        if (response == null || response.getBody() == null) {
            throw new RecursoNaoEncontradoException(
                    recurso + " com id " + id + " nao localizado");
        }
        return response.getBody();
    }
}
