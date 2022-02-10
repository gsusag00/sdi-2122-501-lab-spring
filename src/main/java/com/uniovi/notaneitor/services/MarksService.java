package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * La anotacion service indica que esta clase es una se servicio.
 */
@Service
public class MarksService {

    private List<Mark> marksList = new LinkedList<Mark>();


    /**
     * Es necesario añadir esta anotacion si de verdad queremos que una funcion actue como inicializador.
     */
    @PostConstruct
    public void init() {
        marksList.add(new Mark(1L,"Ejercicio 1", 10.0));
        marksList.add(new Mark(2L,"Ejercicio2",9.0));
    }

    public List<Mark> getMarks() {
        return marksList;
    }

    /**
     * Un poco de lamda funcion aqui, el comentario es para acordarme de como iba la vaina.
     *
     * Primero utilizamos el metodo filter al cual le pasamos un predicate (mark.getId().equals(mark)
     * Despues de esto cojemos solo el primer item del stream filtrado.
     * @return
     */
    public Mark getMark(Long id) {
        return marksList.stream().filter(mark -> mark.getId().equals(id)).findFirst().get();
    }

    public void addMark(Mark mark) {
        //Si el id es null le asignamos el último + 1 de la lista.
        if(mark.getId() == null){
            mark.setId(marksList.get(marksList.size() - 1).getId() + 1);
        }
        marksList.add(mark);
    }

    public void deleteMark(Long id) {
        marksList.removeIf(mark->mark.getId().equals(id));
    }

}
