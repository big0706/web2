package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.models.Person;
import project.services.BookService;
import project.services.PersonService;
import project.util.PersonEditValidator;
import project.util.PersonNameValidator;

import javax.validation.Valid;

@RequestMapping("/people")
@Controller
public class PersonController {
    private final PersonService personService;
    private final BookService bookService;
    private final PersonNameValidator personNameValidator;
    private final PersonEditValidator personEditValidator;


    @Autowired
    public PersonController(PersonService personService, BookService bookService, PersonNameValidator personNameValidator, PersonEditValidator personEditValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personNameValidator = personNameValidator;
        this.personEditValidator = personEditValidator;
    }

    @GetMapping
    public String index (Model model){
        model.addAttribute("people",personService.index());
        return "people/index";
    }


    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id, Model model){
        model.addAttribute("person",personService.show(id));
        model.addAttribute("books",bookService.lockBookList(id));
        return "people/show";
    }
    @GetMapping("/addPerson")
    public String addPerson(Model model) {
        model.addAttribute("person",new Person());
        return "people/addPerson";
    }

    @PostMapping
    public String create(@ModelAttribute() @Valid Person person, BindingResult bindingResult){
        personNameValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/addPerson";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id) {
        model.addAttribute("person",personService.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personEditValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }

}
