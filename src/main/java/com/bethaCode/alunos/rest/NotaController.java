package com.bethaCode.alunos.rest;

import com.bethaCode.alunos.model.dto.NotaDTO;
import com.bethaCode.alunos.model.entity.Aluno;
import com.bethaCode.alunos.model.entity.Disciplina;
import com.bethaCode.alunos.model.entity.Nota;
import com.bethaCode.alunos.model.repository.AlunoRepository;
import com.bethaCode.alunos.model.repository.DisciplinaRepository;
import com.bethaCode.alunos.model.repository.NotaRepository;
import com.bethaCode.alunos.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("api/Notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final BigDecimalConverter bigDecimalConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Nota salvar(@RequestBody NotaDTO notaDTO){

        LocalDate dataNota = LocalDate.parse(notaDTO.getDataNota(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Integer idAluno = notaDTO.getIdAluno();
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno "+idAluno+" não existe na aplicação!"));

        Integer idDisciplina = notaDTO.getIdDisciplina();
        Disciplina disciplina = disciplinaRepository.findById(idDisciplina).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Disciplina "+idDisciplina+" não encontrada no sistema"));

        Nota nota = new Nota();
        nota.setDataNota(dataNota);
        nota.setAluno(aluno);
        nota.setDisciplina(disciplina);
        nota.setNota(bigDecimalConverter.converter(notaDTO.getNota()));

        return notaRepository.save(nota);

    }

    @GetMapping("{id}")
    public Nota acharPorId(@PathVariable Integer id){
        return notaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota não encontrada!"));

    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Integer id){
        //notaRepository.delete(notaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não encontrada")));

        notaRepository.findById(id).map(
                nota -> {
                    notaRepository.delete(nota);
                    return Void.TYPE;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota não cadastrada!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody NotaDTO dadoAtualizado){

        LocalDate dataNota = LocalDate.parse(
                dadoAtualizado.getDataNota(), DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        //BigDecimal valorDaNota = bigDecimalConverter(dadoAtualizado.getNota());

        Integer idAluno = dadoAtualizado.getIdAluno();
        Aluno alunoDoBanco = alunoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "O aluno não existe!"));

        Integer idDisciplina = dadoAtualizado.getIdDisciplina();
        Disciplina disciplinaDoBanco = disciplinaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A disciplina não existe!"));

        notaRepository.findById(id).map(
                nota -> {
                    nota.setNota(bigDecimalConverter.converter(dadoAtualizado.getNota()));
                    nota.setDataNota(dataNota);
                    nota.setAluno(alunoDoBanco);
                    nota.setDisciplina(disciplinaDoBanco);
                    return notaRepository.save(nota);
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nota não existe em nossa app!"));

    }



}
