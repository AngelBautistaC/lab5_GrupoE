package com.example.laboratorio5_grupoe.controller;

import com.example.laboratorio5_grupoe.entity.Department;
import com.example.laboratorio5_grupoe.entity.Employee;
import com.example.laboratorio5_grupoe.repository.DepartmentRepository;
import com.example.laboratorio5_grupoe.repository.EmployeeRepository;
import com.example.laboratorio5_grupoe.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    JobRepository jobRepository;

    @Autowired
    DepartmentRepository departmentRepository;

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
    @GetMapping("/info")
    public String informEmployee(@ModelAttribute("employee") Employee employee, @RequestParam("id") int id, Model model) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if(optEmployee.isPresent()){
            employee = optEmployee.get();
            model.addAttribute("listaDepartamentos",departmentRepository.findAll());
            model.addAttribute("listaTrabajos",jobRepository.findAll());
            model.addAttribute("employee", employee);
            return "employee/datos";
        }else{
            return "redirect:employee/lista";
        }
    }

    //Nuevo Empleado
    @GetMapping("/newEmployee")
    public String newEmployee(@ModelAttribute("employee") Employee employee,Model model, RedirectAttributes attr) {
        model.addAttribute("listJob", jobRepository.findAll());
        model.addAttribute("listEmployee", employeeRepository.findAll());
        model.addAttribute("listDepartment", departmentRepository.findAll());
        return "/employee/datos";
    }


    @GetMapping({"empleado/lista", "empleado"})



    //Guardar empleado
    @PostMapping ("/saveEmployee")
    public String saveEmployee(Model model, @ModelAttribute("employee") @Valid Employee employee,
                               BindingResult bindingResult, RedirectAttributes attr) {
        if(bindingResult.hasErrors()){
            model.addAttribute("listaEmpleados",employeeRepository.findAll());
            return "employee/list";
        }else {
            employeeRepository.save(employee);
            return "redirect:employee/lista";
        }
    }
}


