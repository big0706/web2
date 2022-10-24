package project.models;



import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullName")
    @Pattern(regexp = "^[A-Z]\\w+\\s([A-Z]\\w+\\s|[A-Z]\\w+\\s[A-Z]\\w+|[A-Z]\\w+)",message = "ФИО должно выглядить так: Ivanov Ivan Ivanovich")
    @Size(min = 2,max = 30,message = "Допустимая значение от 2 до 30 символов!")
    @NotEmpty(message = "ФИО не может быть пустым!")
    private String fullName;

    @Column(name = "year")
    @Min(value = 1900, message = "Ты шо дядя? Некромант?!")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }

    public Person(String nss, int year,int id) {
        this.fullName = nss;
        this.year = year;
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
