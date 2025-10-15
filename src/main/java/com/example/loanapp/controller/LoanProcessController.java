package com.example.loanapp.controller;

import com.example.loanapp.service.LoanProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loan")
public class LoanProcessController {

    private final LoanProcessService loanProcessService;

    @Autowired
    public LoanProcessController(LoanProcessService loanProcessService) {
        this.loanProcessService = loanProcessService;
    }

    @GetMapping("/start")
    public String showStartProcessForm() {
        return "start-process";
    }

    @PostMapping("/start")
    public String startProcess(@RequestParam String applicantName,
                              @RequestParam String loanAmount,
                              @RequestParam String creditScore) {
        loanProcessService.startProcess(applicantName, loanAmount, creditScore);
        return "redirect:/loan/tasks";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("runningTasks", loanProcessService.getRunningTasks());
        model.addAttribute("completedTasks", loanProcessService.getCompletedTasks());
        return "task-list";
    }

    @GetMapping("/task/{taskId}")
    public String showTaskDetails(@PathVariable String taskId, Model model) {
        model.addAttribute("task", loanProcessService.getTaskDetails(taskId));
        return "task-detail";
    }

    @PostMapping("/task/complete/{taskId}")
    public String completeTask(@PathVariable String taskId,
                              @RequestParam(required = false) String approvalStatus,
                              @RequestParam(required = false) String verificationResult) {
        loanProcessService.completeTask(taskId, approvalStatus, verificationResult);
        return "redirect:/loan/tasks";
    }
}
