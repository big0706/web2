package project.controllers;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.models.Book;
import project.models.Person;
import project.services.BookService;
import project.services.PersonService;
import project.util.BookEditValidator;
import project.util.BookNameValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BookController {


    private final BookService bookService;
    private final PersonService personService;
    private final BookNameValidator bookNameValidator;
    private final BookEditValidator bookEditValidator;

    @Autowired
    public BookController( BookService bookService, PersonService personService, BookNameValidator bookNameValidator, BookEditValidator bookEditValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookNameValidator = bookNameValidator;
        this.bookEditValidator = bookEditValidator;
    }
    @GetMapping
    public String index ( Model model,
                          @RequestParam(name = "page",required = false,defaultValue = "0") int page,
                          @RequestParam(name = "books_per_page", required = false , defaultValue = "10") int booksPerPage,
                          @RequestParam(name = "sort_by_year", required = false, defaultValue = "false") boolean sort) {
        if (page > 0) {
            model.addAttribute("books",bookService.index(page,booksPerPage));
        } else {
        model.addAttribute("books", bookService.index(sort));
        }
        return "books/index";
    }



    @GetMapping("/{id}")
    public String showBook (@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book",bookService.showBook(id));
        model.addAttribute("people",personService.index());
        model.addAttribute("people1",bookService.getOwner(id));
        return "books/show";
    }
    @GetMapping("/add_book")
    public String addNewBook(Model model) {
        model.addAttribute("book",new Book());
        return "books/add_book";
    }
    @PostMapping
    public String createBook(@ModelAttribute() @Valid Book book, BindingResult bindingResult){
        bookNameValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/add_book";
        }
        bookService.save(book);

        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editBook(Model model,@PathVariable("id") int id) {
        model.addAttribute("book",bookService.showBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookEditValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(Model model,
                         @RequestParam(value = "value",required = false) String str) {
        model.addAttribute("books",bookService.search(str));
        return "books/search";
    }
}
