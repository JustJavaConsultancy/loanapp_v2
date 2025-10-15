    package tech.justjava.loanapp_v2.controller;

    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class HomeController {
        @GetMapping("/")
        public String index(Model model) {
            model.addAttribute("appName", "loanApp_v2");
            return "home";
        }
    }
