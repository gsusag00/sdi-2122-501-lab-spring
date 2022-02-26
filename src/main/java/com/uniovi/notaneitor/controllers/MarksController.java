package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

//Tenemos que cambiar @RestController por @Controller ya que esta especifica una respuesta con contenido HTML y no REST
@Controller
public class MarksController {

    //Inyectar el servicio
    @Autowired //Necesitamos la anotacion autowired para inyectar el bean del servicio
    private MarksService marksService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private HttpSession httpSession;

    /**
     * La variable Principal contiene el nombre de la autenticacion.
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping("/mark/list")
    public String getList(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required=false) String searchText){
        String dni = principal.getName(); //DNI es el name de la autenticacion
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = new PageImpl<>(new LinkedList<Mark>());
        if(searchText != null && !searchText.isEmpty()) {
            marks = marksService.searchMarksByDescriptionAndNameForUser(pageable, searchText, user);
        } else {
            marks = marksService.getMarksForUser(pageable, user);
        }
        model.addAttribute("markList",marks.getContent());
        model.addAttribute("page",marks);
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
    public String getMark(Model model) {
        model.addAttribute("usersList",usersService.getUsers());
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
        model.addAttribute("usersList",usersService.getUsers());
        return "mark/edit";
    }

    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
        // modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/"+id;
    }

    /**
     * Estamos definiendo un endpoint (una url) que devuelve el fragmento correspondiente en vez de retornar la vista completa.
     * @param model modelo de la aplicacion.
     * @return devuelve solo el fragmento tableMarks de la vista mark/list
     */
    @RequestMapping("/mark/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName(); //DNI es el name de la autenticacion
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable,user);
        model.addAttribute("markList",marks.getContent());
        return "mark/list :: tableMarks";
    }

    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResendTrue(Model model, @PathVariable Long id) {
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResendFalse(Model model, @PathVariable Long id) {
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
    }
}
