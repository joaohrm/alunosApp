package com.bethaCode.alunos.rest;

import com.bethaCode.alunos.model.entity.Disciplina;
import com.bethaCode.alunos.model.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    private final DisciplinaRepository repository;

    @Autowired
    public DisciplinaController(DisciplinaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina salvar(@Valid @RequestBody Disciplina disciplina){
        return repository.save(disciplina);
    }

    @GetMapping("{id}")
    public Disciplina acharPorId(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina "+id+" não encontrada")
        );
    }

    @GetMapping
    public List<Disciplina> acharPorId(){
        return repository.findAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void atualizar(@PathVariable Integer id, @Valid @RequestBody Disciplina dadoAtualizado){
        repository.findById(id).map(
                disciplina -> {
                    disciplina.setDescricao(dadoAtualizado.getDescricao());
                    disciplina.setNumeroHoras(dadoAtualizado.getNumeroHoras());
                    return repository.save(disciplina);

                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina "+id+" não encontrada!"));
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Integer id){
        repository.findById(id).map(
                disciplina -> {
                    repository.delete(disciplina);
                    return Void.TYPE;
                }).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina "+id+" não encontrada")
                );
    }

}
