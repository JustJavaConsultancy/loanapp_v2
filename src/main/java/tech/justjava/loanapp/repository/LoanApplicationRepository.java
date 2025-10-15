package tech.justjava.loanapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.justjava.loanapp.entity.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    // Custom query methods can be added here if needed
}
