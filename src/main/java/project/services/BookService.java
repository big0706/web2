package project.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.models.Book;
import project.models.Person;
import project.repositories.BookRepository;
import project.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }
    public Optional<Book> show(String name){
        return bookRepository.findByName(name).stream().findAny();
    }


    public List<Book> index(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }
    public List<Book> index(boolean sort) {
        if (sort){
            return bookRepository.findAll(Sort.by("year"));
        } else {
            return bookRepository.findAll();
        }

    }

    public Book showBook(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id,Book book){
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void lockBook(int personId, int bookId){
        Book book = bookRepository.findById(bookId);
        book.setDate(new Date());
        book.setOwner(personRepository.findById(personId));
        bookRepository.save(book);
    }

    public Person getOwner(int bookId) {
        return bookRepository.findById(bookId).getOwner();

   }
    @Transactional
    public void unlockBook(int bookId){
        Book book = bookRepository.findById(bookId);
        book.setDate(null);
        book.setOwner(null);
        bookRepository.save(book);
    }

    public List<Book> lockBookList(int id) {
        List<Book> b = bookRepository.findByOwner(personRepository.findById(id));
        for (Book boo:b) {
            System.out.println(boo.isExpired());
        }
        return b;
//        return bookRepository.findByOwner(personRepository.findById(id));
    }

    public List<Book> search(String value){
        return bookRepository.findBook(value);
    }
}
