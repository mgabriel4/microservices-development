package br.com.playyourlist.reproducoes;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReproducaoRepository extends CrudRepository<Reproducao, Integer> {

    List<Reproducao> findByPlaylistidOrderByDatahoraAsc(Integer playlistid);

    long countByPlaylistid(Integer playlistid);
}
