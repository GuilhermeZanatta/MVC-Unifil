package com.ger.biblioteca.biblioteca.Controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ger.biblioteca.biblioteca.Model.Autor;
import com.ger.biblioteca.biblioteca.Model.Livro;
import com.ger.biblioteca.biblioteca.Service.BibliotecaDados;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final BibliotecaDados dados;

    public AutorController(BibliotecaDados dados) {
        this.dados = dados;
    }

    @GetMapping
    public String inicio(Model model) {
        Map<Long, List<Livro>> livrosPorAutor = new LinkedHashMap<>();
        for (Autor autor : dados.getAutores()) {
            List<Livro> livrosDoAutor = new ArrayList<>();
            for (Livro livro : dados.getLivros()) {
                if (autor.getLivrosPublicados().contains(livro.getId())) {
                    livrosDoAutor.add(livro);
                }
            }
            livrosPorAutor.put(autor.getId(), livrosDoAutor);
        }
        model.addAttribute("autores", dados.getAutores());
        model.addAttribute("livrosPorAutor", livrosPorAutor);
        return "autores/inicio";
    }

    @GetMapping("/novo")
    public String novoAutor(Model model) {
        model.addAttribute("autor", new Autor());
        model.addAttribute("livros", dados.getLivros());
        return "autores/form";
    }

    @PostMapping
    public String cadastrarAutor(@ModelAttribute Autor autor) {
        autor.setId(dados.proximoIdAutor());
        autor.setLivrosPublicados(livrosValidos(autor.getLivrosPublicados()));
        dados.getAutores().add(autor);
        return "redirect:/autores";
    }

    @GetMapping("/editar/{id}")
    public String editarAutor(@PathVariable Long id, Model model) {
        for (Autor autor : dados.getAutores()) {
            if (autor.getId().equals(id)) {
                model.addAttribute("autor", autor);
                model.addAttribute("livros", dados.getLivros());
                return "autores/form";
            }
        }
        return "redirect:/autores";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAutor(
            @PathVariable Long id,
            @ModelAttribute Autor autorAtualizado) {
        for (Autor autor : dados.getAutores()) {
            if (autor.getId().equals(id)) {
                autor.setNome(autorAtualizado.getNome());
                autor.setNacionalidade(autorAtualizado.getNacionalidade());
                autor.setLivrosPublicados(livrosValidos(autorAtualizado.getLivrosPublicados()));
                break;
            }
        }
        return "redirect:/autores";
    }

    @PostMapping("/excluir/{id}")
    public String excluirAutor(@PathVariable Long id) {
        dados.getAutores().removeIf(autor -> autor.getId().equals(id));
        return "redirect:/autores";
    }

    private List<Long> livrosValidos(List<Long> ids) {
        List<Long> validos = new ArrayList<>();
        if (ids == null) {
            return validos;
        }
        for (Long id : ids) {
            for (Livro livro : dados.getLivros()) {
                if (livro.getId().equals(id)) {
                    validos.add(id);
                    break;
                }
            }
        }
        return validos;
    }
}