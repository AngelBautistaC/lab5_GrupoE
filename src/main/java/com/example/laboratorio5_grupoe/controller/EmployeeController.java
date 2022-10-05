package com.example.laboratorio5_grupoe.controller;

import com.example.laboratorio5_grupoe.entity.Employee;
import com.example.laboratorio5_grupoe.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;



    @GetMapping({"lista", ""})
    public String listEmployee(Model model, @RequestParam(name = "search",required = false) String search, @RequestParam(name = "order", required = false) Integer order, RedirectAttributes attributes){


        model.addAttribute("listaEmpleados",employeeRepository.findAll());
        return "employee/list";
    }


    //Buscar Empleado
    @PostMapping({"search"})
    public String searchEmployee(Model model, @RequestParam(name = "searchField",required = false) String searchField, @RequestParam(name = "order", required = false) Integer order, RedirectAttributes attributes){

        List<Employee> listaEmployees=employeeRepository.buscarEmpleado(searchField);
        model.addAttribute("listaEmpleados",listaEmployees);

        return "employee/list";
    }


    //Editar Empleado
    @GetMapping("/informEmployee")
    public String informEmployee(@ModelAttribute("employee") @Valid Employee employee, @RequestParam("id") int id, Model model) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        return "employee/datos";
    }

    //Guardar Empleado
    //@...Mapping("")
    public String saveEmployee(  ) {
        //        COMPLETAR
        return "XXXXXX";
    }

    //Nuevo Empleado
    public String newEmployee(Model model) {
        //        COMPLETAR
        return "XXXXXX";
    }
}
