package com.ger.biblioteca.biblioteca.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ger.biblioteca.biblioteca.Model.Livro;
import com.ger.biblioteca.biblioteca.Repository.LivrosRepository;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivrosRepository repository;

    public LivroController(LivrosRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String inicio(Model model) {
        model.addAttribute("livros", repository.findAll());
        return "livros/inicio";
    }

    @GetMapping("/novo")
    public String novoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/form";
    }

    @PostMapping
    public String cadastrarLivro(@ModelAttribute Livro livro) {
        repository.save(livro);
        return "redirect:/livros";
    }

    @GetMapping ("/livros")
    public String listarLivros(Model model) {
        repository.findAll();
        return "livros/inicio";
    }

    @GetMapping("/editar/{id}")
    public String editarLivro(@PathVariable Long id, Model model) {
        for (Livro livro : repository.findAll()) {
            if (livro.getId().equals(id)) {
                model.addAttribute("livro", livro);
                return "livros/form";
            }
        }
        return "redirect:/livros";
    }

    @PostMapping("/editar/{id}")
    public String atualizarLivro(
            @PathVariable Long id,
            @ModelAttribute Livro livroAtualizado) {
        for (Livro livro : repository.findAll()) {
            if (livro.getId().equals(id)) {
                livro.setTitulo(livroAtualizado.getTitulo());
                livro.setAutorId(livroAtualizado.getAutorId());
                livro.setIsbn(livroAtualizado.getIsbn());
                livro.setCategoriaId(livroAtualizado.getCategoriaId());
                break;
            }
        }
        return "redirect:/livros";
    }

    @PostMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/livros";
    }
}