package ru.ezhov.duplicate.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController {
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}