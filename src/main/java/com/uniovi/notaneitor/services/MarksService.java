package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * La anotacion service indica que esta clase es una se servicio.
 */
@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    private final HttpSession httpSession;

    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    /**
     * Un poco de lamda funcion aqui, el comentario es para acordarme de como iba la vaina.
     *
     * Primero utilizamos el metodo filter al cual le pasamos un predicate (mark.getId().equals(mark)
     * Despues de esto cojemos solo el primer item del stream filtrado.
     * @return
     */
    public Mark getMark(Long id) {
        Set<Mark> consultedList = (Set<Mark>) httpSession.getAttribute("consultedList");
        if(consultedList == null) {
            consultedList = new HashSet<Mark>();
        }
        Mark obtainedMark = marksRepository.findById(id).get();
        consultedList.add(obtainedMark);
        httpSession.setAttribute("consultedList",consultedList);
        return marksRepository.findById(id).get();
    }

    public void addMark(Mark mark) {
        //Si el id es null le asignamos el último + 1 de la lista.
        marksRepository.save(mark);
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }

}
