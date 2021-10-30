package com.bethaCode.alunos.rest;

import com.bethaCode.alunos.model.entity.Aluno;
import com.bethaCode.alunos.model.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    @Autowired
    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno salvar(@Valid @RequestBody Aluno aluno){
        return repository.save(aluno);
    }

    @GetMapping("{id}")
    public Aluno acharPorId(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno "+id+" não cadastrado!"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id){
        repository.findById(id).map(aluno -> {
            repository.delete(aluno); return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno "+id+" não cadastrado!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @Valid @RequestBody Aluno alunoAtualizado){
        repository.findById(id).map(aluno -> {
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setNumero(alunoAtualizado.getNumero());
            aluno.setIdade(alunoAtualizado.getIdade());
            aluno.setRua(alunoAtualizado.getRua());
            aluno.setCep(alunoAtualizado.getCep());
            aluno.setBairro(alunoAtualizado.getBairro());
            aluno.setCidade(alunoAtualizado.getCidade());
            aluno.setUf(alunoAtualizado.getUf());
            return repository.save(aluno);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno "+id+" não cadastrado!"));
    }

}
