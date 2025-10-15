package tech.justjava.loanapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import tech.justjava.loanapp.entity.LoanApplication;
import tech.justjava.loanapp.service.LoanApplicationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoanApplicationControllerTest {

    @Mock
    private LoanApplicationService loanApplicationService;

    @Mock
    private Model model;

    @InjectMocks
    private LoanApplicationController loanApplicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showStartForm() {
        String viewName = loanApplicationController.showStartForm(model);
        assertEquals("loan-application/start-form", viewName);
        verify(model).addAttribute(eq("loanApplication"), any(LoanApplication.class));
    }

    @Test
    void startProcess() {
        LoanApplication loanApplication = new LoanApplication();
        String viewName = loanApplicationController.startProcess(loanApplication);
        assertEquals("redirect:/loan-application/tasks", viewName);
        verify(loanApplicationService).startProcess(loanApplication);
    }

    @Test
    void showTasks() {
        when(loanApplicationService.getActiveTasks()).thenReturn(List.of(new LoanApplication()));
        String viewName = loanApplicationController.showTasks(model);
        assertEquals("loan-application/tasks", viewName);
        verify(model).addAttribute(eq("tasks"), anyList());
    }

    @Test
    void showCompleteForm() {
        String taskId = "1";
        when(loanApplicationService.getTaskById(taskId)).thenReturn(new LoanApplication());
        String viewName = loanApplicationController.showCompleteForm(taskId, model);
        assertEquals("loan-application/complete-form", viewName);
        verify(model).addAttribute(eq("task"), any(LoanApplication.class));
    }

    @Test
    void completeTask() {
        String taskId = "1";
        String decision = "approve";
        String viewName = loanApplicationController.completeTask(taskId, decision);
        assertEquals("redirect:/loan-application/tasks", viewName);
        verify(loanApplicationService).completeTask(taskId, decision);
    }

    @Test
    void showHistory() {
        when(loanApplicationService.getProcessHistory()).thenReturn(List.of(new LoanApplication()));
        String viewName = loanApplicationController.showHistory(model);
        assertEquals("loan-application/history", viewName);
        verify(model).addAttribute(eq("history"), anyList());
    }
}
