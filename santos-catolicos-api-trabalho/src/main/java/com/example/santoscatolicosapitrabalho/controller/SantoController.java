package com.example.santoscatolicosapitrabalho.controller;

import com.example.santoscatolicosapitrabalho.model.Santo;
import com.example.santoscatolicosapitrabalho.repository.SantoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/santos")
public class SantoController {

    private final SantoRepository repository;

    public SantoController(SantoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Santo>> listarSantos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) Integer seculo,
            @RequestParam(required = false) Boolean canonizado) {

        return ResponseEntity.ok(repository.filtrar(nome, pais, seculo, canonizado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Santo> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Santo> cadastrarSanto(@RequestBody Santo santo) {
        santo.setId(null); // ignora qualquer id enviado: quem gera é o banco
        Santo salvo = repository.save(santo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Santo> atualizarSanto(@PathVariable Long id, @RequestBody Santo santoAtualizado) {
        return repository.findById(id)
                .map(santo -> {
                    santo.setNome(santoAtualizado.getNome());
                    santo.setPais(santoAtualizado.getPais());
                    santo.setSeculo(santoAtualizado.getSeculo());
                    santo.setPadroeiroDe(santoAtualizado.getPadroeiroDe());
                    santo.setCanonizado(santoAtualizado.isCanonizado());
                    return ResponseEntity.ok(repository.save(santo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirSanto(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
