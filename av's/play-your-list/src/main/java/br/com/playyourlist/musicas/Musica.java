package br.com.playyourlist.musicas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O titulo e obrigatorio e nao pode conter apenas espacos")
    @Size(max = 150, message = "O titulo deve ter no maximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "O artista e obrigatorio e nao pode conter apenas espacos")
    @Size(max = 150, message = "O artista deve ter no maximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String artista;

    @Size(max = 150, message = "O album deve ter no maximo 150 caracteres")
    @Column(length = 150)
    private String album;

    @NotNull(message = "A duracao e obrigatoria")
    @Positive(message = "A duracao deve ser maior que zero")
    @Column(nullable = false)
    private Integer duracao;

    @Size(max = 50, message = "O genero deve ter no maximo 50 caracteres")
    @Column(length = 50)
    private String genero;

    public Musica() {
    }

    public Musica(String titulo, String artista, String album, Integer duracao, String genero) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracao = duracao;
        this.genero = genero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
