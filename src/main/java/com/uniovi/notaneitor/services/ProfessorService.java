package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorService {

    private List<Professor> professorsList = new LinkedList<>();

    @PostConstruct
    public void init(){
        professorsList.add(new Professor(1L, "123456789", "Nombre 1","Apellido 1","categoria 1"));
        professorsList.add(new Professor(2L, "987654321", "Nombre 2","Apellido 2","categoria 2"));
    }

    public List<Professor> getProfessors(){
        return professorsList;
    }

    public Professor getProfessor(Long id){
        return professorsList.stream().filter(mark->mark.getId().equals(id)).findFirst().get();
    }

    public void addProfessor(Professor professor){
        if(professor.getId() == null){
            professor.setId(professorsList.get(professorsList.size() - 1).getId() + 1);
        }
        professorsList.add(professor);
    }

    public void deleteProfessor(Long id){
        professorsList.removeIf(mark->mark.getId().equals(id));
    }
}
