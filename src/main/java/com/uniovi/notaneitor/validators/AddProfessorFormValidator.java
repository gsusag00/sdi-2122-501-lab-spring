package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddProfessorFormValidator implements Validator {

    @Autowired
    private ProfessorService professorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Professor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"dni","Error.empty");
        if (professor.getDni().length() < 5 || professor.getDni().length() > 24) {
            errors.rejectValue("dni", "Error.signup.dni.length");}
        if (professorService.getProfessorByDni(professor.getDni()) != null) {
            errors.rejectValue("dni", "Error.signup.dni.duplicate");}
        if (professor.getName().length() < 5 || professor.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");}
        if (professor.getSurname().length() < 5 || professor.getSurname().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");}

    }
}
