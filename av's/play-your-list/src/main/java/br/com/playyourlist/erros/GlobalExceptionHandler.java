package br.com.playyourlist.erros;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> recursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(LocalDateTime.now(), 404, ex.getMessage()));
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErroResponse> conflito(ConflitoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(LocalDateTime.now(), 409, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> erros.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErroResponse> recursoRemotoNaoEncontrado(FeignException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(LocalDateTime.now(), 404,
                        "Musica ou playlist nao localizada durante a validacao pelo OpenFeign"));
    }

    @ExceptionHandler(FeignException.Conflict.class)
    public ResponseEntity<ErroResponse> conflitoRemoto(FeignException.Conflict ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(LocalDateTime.now(), 409,
                        "A operacao solicitada pelo OpenFeign gerou um conflito"));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErroResponse> falhaDeIntegracao(FeignException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ErroResponse(LocalDateTime.now(), 502,
                        "Falha na comunicacao entre os servicos"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> integridade(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(LocalDateTime.now(), 409,
                        "A operacao viola uma regra de integridade do banco de dados"));
    }
}
