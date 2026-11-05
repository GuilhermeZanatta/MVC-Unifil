package com.ger.biblioteca.biblioteca.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ger.biblioteca.biblioteca.Model.Livro;
import com.ger.biblioteca.biblioteca.Service.BibliotecaDados;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final BibliotecaDados dados;

    public LivroController(BibliotecaDados dados) {
        this.dados = dados;
    }

    @GetMapping
    public String inicio(Model model) {
        model.addAttribute("livros", dados.getLivros());
        return "livros/inicio";
    }

    @GetMapping("/novo")
    public String novoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/form";
    }

    @PostMapping
    public String cadastrarLivro(@ModelAttribute Livro livro) {
        livro.setId(dados.proximoIdLivro());
        dados.getLivros().add(livro);
        return "redirect:/livros";
    }

    @GetMapping("/editar/{id}")
    public String editarLivro(@PathVariable Long id, Model model) {
        for (Livro livro : dados.getLivros()) {
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
        for (Livro livro : dados.getLivros()) {
            if (livro.getId().equals(id)) {
                livro.setTitulo(livroAtualizado.getTitulo());
                livro.setAutor(livroAtualizado.getAutor());
                livro.setIsbn(livroAtualizado.getIsbn());
                break;
            }
        }
        return "redirect:/livros";
    }

    @PostMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable Long id) {
        dados.getLivros().removeIf(livro -> livro.getId().equals(id));
        dados.getAutores().forEach((com.ger.biblioteca.biblioteca.Model.Autor autor) ->
            autor.getLivrosPublicados().remove(id));
        return "redirect:/livros";
    }
}