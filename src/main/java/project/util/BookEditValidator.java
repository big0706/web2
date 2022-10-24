package project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.models.Book;
import project.services.BookService;

@Component
public class BookEditValidator implements Validator {

    private BookService bookService;

    @Autowired
    public BookEditValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookService.show(book.getName()).isPresent() & bookService.showBook(book.getId()).getId()!=book.getId()) {
            errors.rejectValue("name","","Такая книга существует в списке!");
        }
    }
}
