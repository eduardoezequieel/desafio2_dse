package com.udb.desafio2.dse.infrastructure.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirect", required = false) String redirect, Model model) {
        // Check if user is already authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }

        if (redirect != null && !redirect.isBlank()) {
            model.addAttribute("redirect", redirect);
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        // Check if user is already authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }

        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
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

