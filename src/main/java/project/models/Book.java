package project.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Books")
public class Book {
    @Column(name = "name")
    @Size(min = 2,max = 50,message = "Допустимое значение от 2 до 30 символов")
    @NotEmpty(message = "Название не может быть пустым!")
    private String name;
    @Column(name = "author")
    @Size(min = 2,max = 30,message = "Допустимое значение от 2 до 30 символов")
    @NotEmpty(message = "Автор не может быть пустым!")
    private String author;
    @Column(name = "year")
    @Min(value = 0, message = "Настолько древних книг не держим!")
    private int year;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private boolean expired;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;


    public Book() {
    }



    public Book(String name, String author, int year, Date date) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.date = date;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int age) {
        this.year = age;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isExpired() {
        if (date!=null) {
            Date compareDate = new Date(date.getTime() + (10 * 24 * 60 * 60 * 1000));
            Date toDay = new Date();
            if (toDay.after(compareDate)) {
                return true;
            } else {
                return false;
            }
        } else {
             return false;
        }

    }


}
