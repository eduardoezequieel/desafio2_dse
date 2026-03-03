package com.udb.desafio2.dse.infrastructure.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/")
    public String root() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch("ROLE_ADMIN"::equals);
            return isAdmin ? "redirect:/dashboard" : "redirect:/client/dashboard";
        }
        return "landing";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirect", required = false) String redirect, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch("ROLE_ADMIN"::equals);
            return isAdmin ? "redirect:/dashboard" : "redirect:/client/dashboard";
        }

        if (redirect != null && !redirect.isBlank()) {
            model.addAttribute("redirect", redirect);
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch("ROLE_ADMIN"::equals);
            return isAdmin ? "redirect:/dashboard" : "redirect:/client/dashboard";
        }
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboardIndex(Model model) {
        model.addAttribute("activePage", "index");
        model.addAttribute("pageHeading", "Inicio");
        return "dashboard/index";
    }

    @GetMapping("/dashboard/users")
    public String dashboardUsers(Model model) {
        model.addAttribute("activePage", "users");
        model.addAttribute("pageHeading", "Usuarios");
        return "dashboard/users";
    }

    @GetMapping("/dashboard/clients")
    public String dashboardClients(Model model) {
        model.addAttribute("activePage", "clients");
        model.addAttribute("pageHeading", "Clientes");
        return "dashboard/clients";
    }

    @GetMapping("/dashboard/services")
    public String dashboardServices(Model model) {
        model.addAttribute("activePage", "services");
        model.addAttribute("pageHeading", "Catálogo de Servicios");
        return "dashboard/services";
    }

    @GetMapping("/dashboard/requests")
    public String dashboardRequests(Model model) {
        model.addAttribute("activePage", "requests");
        model.addAttribute("pageHeading", "Solicitudes");
        return "dashboard/requests";
    }

    @GetMapping("/client/dashboard")
    public String clientDashboard(Model model) {
        model.addAttribute("activePage", "home");
        model.addAttribute("pageHeading", "Mi Panel");
        return "client/dashboard";
    }

    @GetMapping("/client/services")
    public String clientServices(Model model) {
        model.addAttribute("activePage", "services");
        model.addAttribute("pageHeading", "Servicios disponibles");
        return "client/services";
    }

    @GetMapping("/client/requests")
    public String clientRequests(Model model) {
        model.addAttribute("activePage", "requests");
        model.addAttribute("pageHeading", "Mis solicitudes");
        return "client/requests";
    }

    @GetMapping("/client/requests/{id}")
    public String clientRequestDetail(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "requests");
        model.addAttribute("pageHeading", "Detalle de solicitud");
        model.addAttribute("requestId", id);
        return "client/request-detail";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }

    @GetMapping("/404")
    public String notFound() {
        return "404";
    }
}
