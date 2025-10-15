package tech.justjava.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.justjava.loanapp.entity.LoanApplication;
import tech.justjava.loanapp.service.LoanApplicationService;

import java.util.List;

@Controller
@RequestMapping("/loan-application")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping("/start")
    public String showStartForm(Model model) {
        model.addAttribute("loanApplication", new LoanApplication());
        return "loan-application/start-form";
    }

    @PostMapping("/start")
    public String startProcess(@ModelAttribute LoanApplication loanApplication) {
        loanApplicationService.startProcess(loanApplication);
        return "redirect:/loan-application/tasks";
    }

    @GetMapping("/tasks")
    public String showTasks(Model model) {
        List<LoanApplication> tasks = loanApplicationService.getActiveTasks();
        model.addAttribute("tasks", tasks);
        return "loan-application/tasks";
    }

    @GetMapping("/complete/{taskId}")
    public String showCompleteForm(@PathVariable String taskId, Model model) {
        LoanApplication task = loanApplicationService.getTaskById(taskId);
        model.addAttribute("task", task);
        return "loan-application/complete-form";
    }

    @PostMapping("/complete/{taskId}")
    public String completeTask(@PathVariable String taskId, @RequestParam String decision) {
        loanApplicationService.completeTask(taskId, decision);
        return "redirect:/loan-application/tasks";
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        List<LoanApplication> history = loanApplicationService.getProcessHistory();
        model.addAttribute("history", history);
        return "loan-application/history";
    }
}
