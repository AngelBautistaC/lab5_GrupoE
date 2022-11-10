package com.example.laboratorio5_grupoe.controller;

import com.example.laboratorio5_grupoe.entity.Employee;
import com.example.laboratorio5_grupoe.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(value = "/login")
    public String login() {
        return "inicio";
    }

    @GetMapping(value = "/redirecRol")
    public String redirecRol(Authentication authentication, HttpSession session, RedirectAttributes redirectAttributes) {
        String rol = "";
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println(grantedAuthority.getAuthority());
            rol = grantedAuthority.getAuthority();
        }

        String username = authentication.getName();
        System.out.println(username);
        System.out.println(rol);
        Employee usuario = employeeRepository.findByEmail(username);
        session.setAttribute("usuario", usuario);

        switch (rol) {
            case "manager" -> {

                return "redirect:/empleado";

            }
            case "empleado" -> {
                return "redirect:/empleado";
            }
            default -> {
                String texto = "Credenciales invalidas";
                redirectAttributes.addFlashAttribute("msgLogin", texto);
                return "redirect:/login";
            }

        }
    }
}
