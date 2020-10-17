package me.kakaopay.homework.repository.sprinkle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.kakaopay.homework.entity.SprinkleTransaction;

@Repository
public interface SprinkleTransactionRepository extends JpaRepository<SprinkleTransaction, Long> {
}
