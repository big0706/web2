package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    Person findByFullName(String fullName);
    Person findById(int id);
    Optional<Person> findPersonByFullName(String fullName);

}
