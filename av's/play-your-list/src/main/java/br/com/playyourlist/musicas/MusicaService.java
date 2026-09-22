package br.com.playyourlist.musicas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.playyourlist.erros.RecursoNaoEncontradoException;
import br.com.playyourlist.playlists.PlaylistMusicaRepository;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final PlaylistMusicaRepository playlistMusicaRepository;

    public MusicaService(MusicaRepository musicaRepository,
                         PlaylistMusicaRepository playlistMusicaRepository) {
        this.musicaRepository = musicaRepository;
        this.playlistMusicaRepository = playlistMusicaRepository;
    }

    public Iterable<Musica> listar() {
        return musicaRepository.findAll();
    }

    public Musica buscar(Integer id) {
        return musicaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Musica com id " + id + " nao localizada"));
    }

    public Musica criar(Musica musica) {
        musica.setId(null);
        return musicaRepository.save(musica);
    }

    public Musica atualizar(Integer id, Musica musica) {
        buscar(id);
        musica.setId(id);
        return musicaRepository.save(musica);
    }

    @Transactional
    public void excluir(Integer id) {
        buscar(id);
        playlistMusicaRepository.deleteByMusicaid(id);
        musicaRepository.deleteById(id);
    }
}
