package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Tenemos que cambiar @RestController por @Controller ya que esta especifica una respuesta con contenido HTML y no REST
@Controller
public class MarksController {

    //Inyectar el servicio
    @Autowired //Necesitamos la anotacion autowired para inyectar el bean del servicio
    private MarksService marksService;

    @RequestMapping("/mark/list")
    public String getList(Model model){
        model.addAttribute("markList",marksService.getMarks());
        return "mark/list";
    }

    /**
     * Para que un metodo pueda responder a peticiones POST tenemos que indicarlo en el @RequestMapping de tal manera.
     *
     * Como sabemos en las peticiones POST los datos vienen en el body en vez de en la URL. Para definir que parametros queremos en la peticion
     * tendremos que usar la anotacion @RequestParam
     *
     * Ahora que tenemos la entidad Mark podemos cambiar las anotaciones @RequestParam por @ModelAttribute, de esta manera podemos recibir un objeto,
     * mark en este caso
     * @param mark la nota que queremos añadir
     * @return devuelve un Ok al añadirse la nota de manera correcta.
     */
    @RequestMapping(value = "/mark/add", method = RequestMethod.POST )
    public String setMark(@ModelAttribute Mark mark){
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/add")
    public String getMark(){
        return "mark/add";
    }

    /**
     * Metodo que devuelve los detallers. Este comentario es para ir apuntando las cosas que salen en el guión para poder acordarme yo de ello bien.
     * Puesto que el getDetails va a responder a una peticion GET esto significa que va a recibir un parametro en la URL de la petición por lo que
     * tenemos que poner la anotacíón @RequestParam para indicarle al método que el parametro que pide va a recibirlo de la URL.
     * Entonces el controlador (esta clase) va a buscar ese parametro en toda la URL con clave id.
     *
     * Para hacer que el dato no tenga que venir en la URL (?id=x) se puede utilizar la anotacion @PathVariable, pero para que esto funcione necesitamos
     * cambiar la url de la peticion a la que el metodo va a responder, añadiendo /{parametro} al final de la URL. Esto hace que el parametro sea recibido
     * por su posicion en el path en vez de formar parte de la URL
     * @param id el id de la nota que queremos obtener los detalles
     * @return devuelve los datos de la string
     */
    @RequestMapping("/mark/details/{id}")
    public String getDetails(Model model, @PathVariable Long id) {
        model.addAttribute("mark",marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value="/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark",marksService.getMark(id));
        return "mark/edit";
    }

    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        mark.setId(id);
        marksService.addMark(mark);
        return "redirect:/mark/details/"+id;
    }
}
