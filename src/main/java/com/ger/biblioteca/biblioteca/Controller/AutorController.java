package com.ger.biblioteca.biblioteca.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ger.biblioteca.biblioteca.Model.Autor;
import com.ger.biblioteca.biblioteca.Repository.AutoresRepository;


@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutoresRepository repository;

    public AutorController(AutoresRepository repository) {
        this.repository = repository;
    }

    @GetMapping 
    public String inicio(Model model){
        model.addAttribute("autores", repository.findAll());
        return "autores/inicio";
    }

    @GetMapping("/novo")
    public String novoAutor(Model model) {
        model.addAttribute("autores", new Autor());
        return "autores/form";
    }

    @PostMapping 
    public String cadastrarAutor(@ModelAttribute Autor autor){
        repository.save(autor);
        return "redirect:/autores";
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