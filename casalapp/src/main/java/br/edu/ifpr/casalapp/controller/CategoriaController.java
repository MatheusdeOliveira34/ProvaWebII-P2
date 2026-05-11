package br.edu.ifpr.casalapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriaController {

    record Categoria(int id, String nome, String icone) {}

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return List.of(
            new Categoria(1, "Alimentação", "prato"),
            new Categoria(2, "Transporte", "carro"),
            new Categoria(3, "Saúde", "coração")
        );
    }
}