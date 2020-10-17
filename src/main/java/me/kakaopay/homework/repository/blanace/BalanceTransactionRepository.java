package me.kakaopay.homework.repository.blanace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.kakaopay.homework.entity.BalanceTransaction;

@Repository
public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Long> {
}
