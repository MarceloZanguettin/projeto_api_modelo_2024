package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Questao;
import application.repository.QuestaoRepository;

@RestController
@RequestMapping("/questoes")
public class QuestoesController {
    @Autowired
    private QuestaoRepository questoesRepo;

    @GetMapping
    public Iterable<Questao> getAll() {
        return questoesRepo.findAll();
    }

    @GetMapping("/{id}")
    public Questao getOne(@PathVariable long id) {
        Optional<Questao> result = questoesRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Questão não encontrada"
            );
        }
        return result.get();
    }

    @PostMapping
    public Questao post(@RequestBody Questao questao) {
        return questoesRepo.save(questao);
    }

    @PutMapping("/{id}")
    private Questao put(@RequestBody Questao questao, @PathVariable long id) {
        Optional<Questao> result = questoesRepo.findById(id);

        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Questão não encontrada"
            );
        }

        result.get().setEnunciado(questao.getEnunciado());
        result.get().setCategoria(questao.getCategoria());
        return questoesRepo.save(result.get());
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable long id) {
        if(questoesRepo.existsById(id)) {
            questoesRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Questão não encontrada"
            );
        }
    }
}
