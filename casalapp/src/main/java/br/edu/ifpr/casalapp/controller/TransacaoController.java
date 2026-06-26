package br.edu.ifpr.casalapp.controller;

import br.edu.ifpr.casalapp.dto.TransacaoRequest;
import br.edu.ifpr.casalapp.dto.TransacaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teste")
@CrossOrigin
public class TransacaoController {

    private int proximoId = 4;

    private final List<TransacaoResponse> transacoes = new ArrayList<>(List.of(
            new TransacaoResponse(1, "Salário", 5000.0, "receita"),
            new TransacaoResponse(2, "Supermercado", 350.50, "despesa"),
            new TransacaoResponse(3, "Aluguel", 1200.0, "despesa")
    ));

    @GetMapping("/find-all-transactions")
    public List<TransacaoResponse> findAllTransactions() {
        return transacoes;
    }

    @GetMapping("/find-transaction/{id}")
    public ResponseEntity<TransacaoResponse> findTransaction(@PathVariable int id) {
        for (TransacaoResponse transacao : transacoes) {
            if (transacao.id() == id) {
                return ResponseEntity.ok(transacao);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-transaction")
    public ResponseEntity<TransacaoResponse> createTransaction(@RequestBody TransacaoRequest request) {
        TransacaoResponse nova = new TransacaoResponse(
                proximoId,
                request.descricao(),
                request.valor(),
                request.tipo()
        );
        proximoId++;
        transacoes.add(nova);
        return ResponseEntity.status(201).body(nova);
    }

    @PutMapping("/update-transaction/{id}")
    public ResponseEntity<TransacaoResponse> updateTransaction(
            @PathVariable int id,
            @RequestBody TransacaoRequest request) {

        for (int i = 0; i < transacoes.size(); i++) {
            if (transacoes.get(i).id() == id) {
                TransacaoResponse atualizada = new TransacaoResponse(
                        id,
                        request.descricao(),
                        request.valor(),
                        request.tipo()
                );
                transacoes.set(i, atualizada);
                return ResponseEntity.ok(atualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update-partial-transaction/{id}")
    public ResponseEntity<TransacaoResponse> updatePartialTransaction(
            @PathVariable int id,
            @RequestBody TransacaoRequest request) {

            for (TransacaoResponse transacaoResponse : transacoes) {
                if (transacaoResponse.id() == id) {

                    String novaDescricao = request.descricao() != null ? request.descricao() : transacaoResponse.descricao();
                    double novoValor = request.valor() != null ? request.valor() : transacaoResponse.valor();
                    String novoTipo = request.tipo() != null ? request.tipo() : transacaoResponse.tipo();
                    
                    TransacaoResponse atualizada = new TransacaoResponse(id, novaDescricao, novoValor, novoTipo);
                    transacoes.set(id, atualizada);

                    return ResponseEntity.ok(atualizada);
                }
            }

            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        for (TransacaoResponse transacao : transacoes) {
            if (transacao.id() == id) {
                transacoes.remove(transacao);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
