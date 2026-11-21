package com.ger.biblioteca.biblioteca.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ger.biblioteca.biblioteca.Model.Autor;
import com.ger.biblioteca.biblioteca.Model.Categoria;
import com.ger.biblioteca.biblioteca.Model.Livro;
import com.ger.biblioteca.biblioteca.Repository.AutoresRepository;
import com.ger.biblioteca.biblioteca.Repository.CategoriasRepository;
import com.ger.biblioteca.biblioteca.Repository.LivrosRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivrosRepository repository;
    private final AutoresRepository autoresRepository;
    private final CategoriasRepository categoriasRepository;

    public LivroController(LivrosRepository repository, AutoresRepository autoresRepository, CategoriasRepository categoriasRepository) {
        this.repository = repository;
        this.autoresRepository = autoresRepository;
        this.categoriasRepository = categoriasRepository;
    }

    @GetMapping
    public String inicio(Model model) {
        model.addAttribute("livros", repository.findAll());
        Map<Long, String> autoresPorId = new HashMap<>();
        for (Autor autor : autoresRepository.findAll()) {
            autoresPorId.put(autor.getId(), autor.getNome());
        }
        Map<Long, String> categoriasPorId = new HashMap<>();
        for (Categoria categoria : categoriasRepository.findAll()) {
            categoriasPorId.put(categoria.getId(), categoria.getNome());
        }
        model.addAttribute("autoresPorId", autoresPorId);
        model.addAttribute("categoriasPorId", categoriasPorId);
        return "livros/inicio";
    }

    @GetMapping("/novo")
    public String novoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        adicionarOpcoes(model);
        return "livros/form";
    }

    @PostMapping
    public String cadastrarLivro(Livro livro, Model model) {
        if (!associacoesValidas(livro)) {
            model.addAttribute("erro", "Selecione um autor e uma categoria cadastrados.");
            model.addAttribute("livro", livro);
            adicionarOpcoes(model);
            return "livros/form";
        }
        repository.save(livro);
        model.addAttribute("nomeAutor", autoresRepository.findById(livro.getAutorId())
                .orElseThrow().getNome());
        model.addAttribute("nomeCategoria", categoriasRepository.findById(livro.getCategoriaId())
                .orElseThrow().getNome());
        model.addAttribute("livro", livro);
        return "livros/confirmacao";
    }

    @GetMapping("/editar/{id}")
    public String editarLivro(@PathVariable Long id, Model model) {
        for (Livro livro : repository.findAll()) {
            if (livro.getId().equals(id)) {
                model.addAttribute("livro", livro);
                adicionarOpcoes(model);
                return "livros/form";
            }
        }
        return "redirect:/livros";
    }

    @PostMapping("/editar/{id}")
    public String atualizarLivro(@PathVariable Long id, Livro livroAtualizado, Model model) {
        Livro livro = repository.findById(id).orElse(null);
        if (livro == null) {
            return "redirect:/livros";
        }
        livroAtualizado.setId(id);
        if (!associacoesValidas(livroAtualizado)) {
            model.addAttribute("erro", "Selecione um autor e uma categoria cadastrados.");
            model.addAttribute("livro", livroAtualizado);
            adicionarOpcoes(model);
            return "livros/form";
        }
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutorId(livroAtualizado.getAutorId());
        livro.setIsbn(livroAtualizado.getIsbn());
        livro.setCategoriaId(livroAtualizado.getCategoriaId());
        livro.setQuantidade(livroAtualizado.getQuantidade());
        repository.save(livro);
        return "redirect:/livros";
    }

    @PostMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/livros";
    }

    private void adicionarOpcoes(Model model) {
        model.addAttribute("autores", autoresRepository.findAll());
        model.addAttribute("categorias", categoriasRepository.findAll());
    }

    private boolean associacoesValidas(Livro livro) {
        return livro.getAutorId() != null
                && livro.getCategoriaId() != null
                && autoresRepository.existsById(livro.getAutorId())
                && categoriasRepository.existsById(livro.getCategoriaId());
    }
}