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
    public String listEmployee(RedirectAttributes attr,@RequestParam(name = "sorted",required = false) String sorted,Model model, @RequestParam(name = "search",required = false) String search, @RequestParam(name = "order", required = false) Integer order, RedirectAttributes attributes){

        System.out.println(sorted);

        if (Objects.equals(sorted, "asc")){
            System.out.println("asc");
            model.addAttribute("listaEmpleados",employeeRepository.findAll(Sort.by(Sort.Order.asc("firstName"))));
            model.addAttribute("sorted","asc");
        }

        if (Objects.equals(sorted, "desc")){
            System.out.println("desc");
            model.addAttribute("listaEmpleados",employeeRepository.findAll(Sort.by(Sort.Order.desc("firstName"))));
            model.addAttribute("sorted",null);
        }
        else if(sorted == null){
            System.out.println("f");
            model.addAttribute("listaEmpleados",employeeRepository.findAll());
        }




        return "employee/list";
    }

    @PostMapping({"sort"})
    public String sortEmployee(Model model,@RequestParam(name = "sorted",required = false) String sorted,RedirectAttributes attr) {
        model.addAttribute("sorted",sorted);



        return "redirect:/empleado/lista";
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
    public String saveEmployee(
                                Model model, @ModelAttribute("employee") @Valid Employee employee,
                               BindingResult bindingResult, RedirectAttributes attr, @RequestParam("nuevo") int nuevo) {


        if (employee.getDepartment() ==null || employee.getJob() ==null){
            System.out.println("########################################");
            attr.addFlashAttribute("error", "Debe seleccionar un departamento y un trabajo");
            return "redirect:/empleado/newEmployee";
        }

        System.out.println("########################################aqui estoy gagagagagagag 2");
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


