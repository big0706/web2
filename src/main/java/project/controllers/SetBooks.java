package project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.models.Person;
import project.services.BookService;

@Controller
@RequestMapping("/set")
public class SetBooks {


    private final BookService bookService;

    public SetBooks(BookService bookService) {
        this.bookService = bookService;
    }


    @PatchMapping("/{id}")
    public String setBook(@PathVariable("id") int id, @ModelAttribute("person")Person person) {
        bookService.lockBook(person.getId(),id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/unlock/{id}")
    public String unlockBook(@PathVariable("id") int id){
        bookService.unlockBook(id);
        return "redirect:/books/"+id;
    }

}
