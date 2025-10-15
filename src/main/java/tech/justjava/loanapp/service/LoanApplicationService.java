package tech.justjava.loanapp.service;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.justjava.loanapp.entity.LoanApplication;
import tech.justjava.loanapp.repository.LoanApplicationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanApplicationService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    public void deployProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/loanApp_v2.bpmn20.xml")
                .deploy();
    }

    public void startProcess(LoanApplication loanApplication) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicantName", loanApplication.getApplicantName());
        variables.put("loanAmount", loanApplication.getLoanAmount());
        variables.put("creditScore", loanApplication.getCreditScore());

        runtimeService.startProcessInstanceByKey("loanApp_v2", variables);
        loanApplicationRepository.save(loanApplication);
    }

    public List<LoanApplication> getActiveTasks() {
        List<Task> tasks = taskService.createTaskQuery().list();
        return tasks.stream()
                .map(task -> {
                    LoanApplication loanApplication = new LoanApplication();
                    loanApplication.setTaskId(task.getId());
                    loanApplication.setApplicantName((String) taskService.getVariable(task.getId(), "applicantName"));
                    loanApplication.setLoanAmount((Double) taskService.getVariable(task.getId(), "loanAmount"));
                    loanApplication.setCreditScore((Integer) taskService.getVariable(task.getId(), "creditScore"));
                    return loanApplication;
                })
                .collect(Collectors.toList());
    }

    public LoanApplication getTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setTaskId(task.getId());
        loanApplication.setApplicantName((String) taskService.getVariable(task.getId(), "applicantName"));
        loanApplication.setLoanAmount((Double) taskService.getVariable(task.getId(), "loanAmount"));
        loanApplication.setCreditScore((Integer) taskService.getVariable(task.getId(), "creditScore"));
        return loanApplication;
    }

    public void completeTask(String taskId, String decision) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("decision", decision);
        taskService.complete(taskId, variables);
    }

    public List<LoanApplication> getProcessHistory() {
        // Implement history retrieval logic
        return List.of();
    }
}
