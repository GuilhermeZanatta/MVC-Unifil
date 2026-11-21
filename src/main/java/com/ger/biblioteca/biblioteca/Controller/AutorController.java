package com.ger.biblioteca.biblioteca.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ger.biblioteca.biblioteca.Model.Autor;
import com.ger.biblioteca.biblioteca.Model.Livro;
import com.ger.biblioteca.biblioteca.Repository.AutoresRepository;
import com.ger.biblioteca.biblioteca.Repository.LivrosRepository;


@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutoresRepository repository;
    private final LivrosRepository livrosRepository;

    public AutorController(AutoresRepository repository, LivrosRepository livrosRepository) {
        this.repository = repository;
        this.livrosRepository = livrosRepository;
    }

    @GetMapping 
    public String inicio(Model model){
        List<Autor> autores = repository.findAll();
        Map<Long, List<Livro>> livrosPorAutor = new HashMap<>();
        for (Autor autor : autores) {
            livrosPorAutor.put(autor.getId(), new ArrayList<>());
        }
        for (Livro livro : livrosRepository.findAll()) {
            List<Livro> livrosDoAutor = livrosPorAutor.get(livro.getAutorId());
            if (livrosDoAutor != null) {
                livrosDoAutor.add(livro);
            }
        }
        model.addAttribute("autores", autores);
        model.addAttribute("livrosPorAutor", livrosPorAutor);
        return "autores/inicio";
    }

    @GetMapping("/novo")
    public String novoAutor(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/form";
    }

    @PostMapping 
    public String cadastrarAutor(@ModelAttribute Autor autor, Model model){
        repository.save(autor);
        model.addAttribute("autor", autor);
        return "autores/confirmacao";
    }

    @GetMapping ("/editar/{id}")
    public String editarAutor(@PathVariable Long id, Model model){
        for (Autor autor : repository.findAll()) {
            if (autor.getId().equals(id)) {
                model.addAttribute("autor", autor);
                return "autores/form";
            }
        }
        return "redirect:/autores";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAutor(@PathVariable Long id, @ModelAttribute Autor autorAtualizado){
        for (Autor autor : repository.findAll()){
            if (autor.getId().equals(id)){
                autor.setNome(autorAtualizado.getNome());
                autor.setNacionalidade(autorAtualizado.getNacionalidade());
                repository.save(autor);
                break;
            }
        }
        return "redirect:/autores";
    }

    @PostMapping("/excluir/{id}")
    public String excluirAutor (@PathVariable Long id){
        repository.deleteById(id);
        return "redirect:/autores";
    }
}