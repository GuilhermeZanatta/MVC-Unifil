package com.ger.biblioteca.biblioteca.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ger.biblioteca.biblioteca.Model.Categoria;
import com.ger.biblioteca.biblioteca.Repository.CategoriasRepository;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriasRepository repository;

    public CategoriaController(CategoriasRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String inicio(Model model) {
        model.addAttribute("categorias", repository.findAll());
        return "categorias/inicio";
    }

    @GetMapping("/novo")
    public String novaCategoria(Model model) {
        model.addAttribute("categorias", new Categoria());
        return "categorias/form";
    }

    @PostMapping
    public String cadastrarCategoria(@ModelAttribute Categoria categoria) {
        repository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping ("/categorias")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", repository.findAll());
        return "categorias/inicio";
    }

    @GetMapping ("/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        for (Categoria categoria : repository.findAll()) {
            if (categoria.getId().equals(id)) {
                model.addAttribute("categoria", categoria);
                return "categorias/form";
            }
        }
        return "redirect:/categorias";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCategoria(@PathVariable Long id, @ModelAttribute Categoria categoriaAtualizada) {
        for (Categoria categoria : repository.findAll()) {
            if (categoria.getId().equals(id)){
                categoria.setNome(categoriaAtualizada.getNome());
                break;
            }
        }
        return "redirect:/categorias";
    }

    @PostMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id){
        repository.deleteById(id);
        return "redirect:/categorias";
    }
}