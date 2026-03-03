package com.udb.desafio2.dse.infrastructure.web.controller;

import com.udb.desafio2.dse.infrastructure.web.util.RoleTranslator;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AuthModelAdvice {

    @ModelAttribute
    public void addAuthAttributes(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            model.addAttribute("userEmail", authentication.getName());
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .orElse("USER");
            model.addAttribute("userRawRole", role);
            model.addAttribute("userRole", RoleTranslator.translateRole(role));
        }
    }
}
