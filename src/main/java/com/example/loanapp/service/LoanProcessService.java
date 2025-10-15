package com.example.loanapp.service;

import com.example.loanapp.entity.LoanApplication;
import com.example.loanapp.repository.LoanApplicationRepository;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanProcessService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final LoanApplicationRepository loanApplicationRepository;

    @Autowired
    public LoanProcessService(RuntimeService runtimeService,
                             TaskService taskService,
                             LoanApplicationRepository loanApplicationRepository) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public void startProcess(String applicantName, String loanAmount, String creditScore) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicantName(applicantName);
        loanApplication.setLoanAmount(loanAmount);
        loanApplication.setCreditScore(creditScore);
        loanApplicationRepository.save(loanApplication);

        Map<String, Object> variables = new HashMap<>();
        variables.put("loanApplication", loanApplication);
        runtimeService.startProcessInstanceByKey("loanApp_v2", variables);
    }

    public List<Task> getRunningTasks() {
        return taskService.createTaskQuery().active().list();
    }

    public List<Task> getCompletedTasks() {
        return taskService.createTaskQuery().finished().list();
    }

    public Task getTaskDetails(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    public void completeTask(String taskId, String approvalStatus, String verificationResult) {
        Map<String, Object> variables = new HashMap<>();
        if (approvalStatus != null) {
            variables.put("approvalStatus", approvalStatus);
        }
        if (verificationResult != null) {
            variables.put("verificationResult", verificationResult);
        }
        taskService.complete(taskId, variables);
    }
}
