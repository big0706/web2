package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.models.Book;
import project.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    //List<Book> findAll();
    Book findById(int id);
    List<Book> findByOwner(Person owner);
    Optional<Book> findByName(String name);

    @Query("select b from  Book b where lower(b.name) like ?1% or b.name like ?1% or upper(b.name) like ?1%")
    List<Book> findBook (String str);


}
