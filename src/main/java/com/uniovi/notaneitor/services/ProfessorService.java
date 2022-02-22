package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.repositories.ProfessorRepository;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;


/*    @PostConstruct
    public void init(){
        professorRepository.save(new Professor(1L, "123456789", "Nombre 1","Apellido 1","categoria 1"));
        professorRepository.save(new Professor(2L, "987654321", "Nombre 2","Apellido 2","categoria 2"));
    }*/

    public List<Professor> getProfessors(){
        List<Professor> professorsList = new LinkedList<>();
        professorRepository.findAll().forEach(professorsList::add);
        return professorsList;
    }

    public Professor getProfessor(Long id){
        return professorRepository.findById(id).get();
    }

    public void addProfessor(Professor professor){
        professorRepository.save(professor);
    }

    public void deleteProfessor(Long id){
        professorRepository.deleteById(id);
    }

    public Professor getProfessorByDni(String dni) {
        return professorRepository.findByDni(dni);
    }
}
