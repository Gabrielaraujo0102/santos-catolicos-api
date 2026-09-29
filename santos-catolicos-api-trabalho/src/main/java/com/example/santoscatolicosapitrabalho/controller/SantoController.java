package com.example.santoscatolicosapitrabalho.controller;

import com.example.santoscatolicosapitrabalho.model.Santo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/santos")
public class SantoController {

    private final List<Santo> santos = new ArrayList<>();
    private int proximoId = 1;

    @GetMapping
    public ResponseEntity<List<Santo>> listarSantos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) Integer seculo,
            @RequestParam(required = false) Boolean canonizado) {

        List<Santo> resultado = santos.stream()
                .filter(santo -> nome == null || santo.getNome().toLowerCase().contains(nome.toLowerCase()))
                .filter(santo -> pais == null || santo.getPais().equalsIgnoreCase(pais))
                .filter(santo -> seculo == null || santo.getSeculo() == seculo)
                .filter(santo -> canonizado == null || santo.isCanonizado() == canonizado)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Santo> buscarPorId(@PathVariable int id) {
        return santos.stream()
                .filter(santo -> santo.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Santo> cadastrarSanto(@RequestBody Santo santo) {
        santo.setId(proximoId);
        proximoId++;
        santos.add(santo);
        return ResponseEntity.status(HttpStatus.CREATED).body(santo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Santo> atualizarSanto(@PathVariable int id, @RequestBody Santo santoAtualizado) {
        for (Santo santo : santos) {
            if (santo.getId() == id) {
                santo.setNome(santoAtualizado.getNome());
                santo.setPais(santoAtualizado.getPais());
                santo.setSeculo(santoAtualizado.getSeculo());
                santo.setPadroeiroDe(santoAtualizado.getPadroeiroDe());
                santo.setCanonizado(santoAtualizado.isCanonizado());
                return ResponseEntity.ok(santo);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirSanto(@PathVariable int id) {
        boolean removido = santos.removeIf(santo -> santo.getId() == id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
