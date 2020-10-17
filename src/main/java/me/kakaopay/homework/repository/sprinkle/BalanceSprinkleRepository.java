package me.kakaopay.homework.repository.sprinkle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.kakaopay.homework.entity.BalanceSprinkle;

@Repository
public interface BalanceSprinkleRepository extends JpaRepository<BalanceSprinkle, Long>,
                                                   BalanceSprinkleRepositoryCustom {
}
