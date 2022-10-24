package project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.models.Person;
import project.services.PersonService;

@Component
public class PersonNameValidator implements Validator {
    private PersonService personService;

    @Autowired
    public PersonNameValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personService.show(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName","","Этот читатель уже существует!");
        }

    }
}
