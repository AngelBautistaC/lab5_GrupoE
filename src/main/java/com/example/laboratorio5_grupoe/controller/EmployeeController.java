package com.example.laboratorio5_grupoe.controller;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.example.laboratorio5_grupoe.entity.Department;
import com.example.laboratorio5_grupoe.entity.Employee;
import com.example.laboratorio5_grupoe.repository.DepartmentRepository;
import com.example.laboratorio5_grupoe.repository.EmployeeRepository;
import com.example.laboratorio5_grupoe.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping({"lista", ""})
    public String listEmployee(Model model, @RequestParam(name = "search",required = false) String search, @RequestParam(name = "order", required = false) Integer order, RedirectAttributes attributes){


        model.addAttribute("listaEmpleados",employeeRepository.findAll());
        return "employee/list";
    }

    @PostMapping({"sort"})
    public String sortEmployee(Model model,@RequestParam(name = "sorted",required = false) String sorted,RedirectAttributes attr) {

        if (sorted == null){
            model.addAttribute("listaEmpleados",employeeRepository.findAll(Sort.by(Sort.Order.asc("firstName"))));
            attr.addFlashAttribute("sorted","xd");
        }


         if (Objects.equals(sorted, "xd")){
            model.addAttribute("listaEmpleados",employeeRepository.findAll(Sort.by(Sort.Order.desc("firstName"))));
        }

        return "employee/list";
    }


    //Buscar Empleado
    @PostMapping({"search"})
    public String searchEmployee(Model model, @RequestParam(name = "searchField",required = false) String searchField,
                                 @RequestParam(name = "order", required = false) Integer order, RedirectAttributes attributes){

        List<Employee> listaEmployees=employeeRepository.buscarEmpleado(searchField);
        model.addAttribute("listaEmpleados",listaEmployees);
        model.addAttribute("searchField",searchField);

        return "employee/list";
    }


    //Editar Empleado
    @GetMapping("/info")
    public String informEmployee(@ModelAttribute("employee") Employee employee, @RequestParam("id") int id, Model model) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if(optEmployee.isPresent()){
            employee = optEmployee.get();
            model.addAttribute("listaDepartamentos",departmentRepository.findAll());
            model.addAttribute("listaTrabajos",jobRepository.findAll());
            model.addAttribute("employee", employee);
            model.addAttribute("nuevo",0);
            return "employee/datos";
        }else{
            return "redirect:employee/lista";
        }
    }

    //Nuevo Empleado
    @GetMapping("/newEmployee")
    public String newEmployee(@ModelAttribute("employee") Employee employee,Model model, RedirectAttributes attr) {
        model.addAttribute("listaDepartamentos",departmentRepository.findAll());
        model.addAttribute("listaTrabajos",jobRepository.findAll());
        model.addAttribute("nuevo",1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("fechaActual",dtf.format(now));
        return "/employee/datos";
    }


    //@GetMapping({"empleado/lista", "empleado"})



    //Guardar empleado
    @PostMapping ("/saveEmployee")
    public String saveEmployee(Model model, @ModelAttribute("employee") @Valid Employee employee,
                               BindingResult bindingResult, RedirectAttributes attr, @RequestParam("nuevo") int nuevo) {
        System.out.println("########################################aqui estoy gagagagagagag");
        if(bindingResult.hasErrors()){
            System.out.println("########################################aqui estoy gagagagagagag 2");
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("nuevo",nuevo);
            model.addAttribute("listaDepartamentos",departmentRepository.findAll());
            model.addAttribute("listaTrabajos",jobRepository.findAll());
            return "/employee/datos";
        }else {
            if (employee.getId() == null) {
                attr.addFlashAttribute("msg", "Empleado registrado correctamente");
            } else {
                attr.addFlashAttribute("msg", "Registro del empleado actualizado");
            }
            employeeRepository.save(employee);
            return "redirect:/empleado";
        }
    }
}


