package me.kakaopay.homework.repository.sprinkle;

import java.util.Optional;

import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.QBalanceSprinkle;
import me.kakaopay.homework.entity.QBalanceSprinkleTransaction;
import me.kakaopay.homework.repository.AbstractRepositorySupport;

public class BalanceSprinkleRepositoryImpl
        extends AbstractRepositorySupport
        implements BalanceSprinkleRepositoryCustom {

    private static final QBalanceSprinkle Q_BALANCE_SPRINKLE = QBalanceSprinkle.balanceSprinkle;

    private static final QBalanceSprinkleTransaction Q_BALANCE_SPRINKLE_TRANSACTION =
            QBalanceSprinkleTransaction.balanceSprinkleTransaction;

    public BalanceSprinkleRepositoryImpl() {
        super(BalanceSprinkle.class);
    }

    @Override
    public BalanceSprinkle get(long id) {
        return Optional.of(
                from(Q_BALANCE_SPRINKLE)
                        .leftJoin(Q_BALANCE_SPRINKLE.transactions, Q_BALANCE_SPRINKLE_TRANSACTION).fetchAll()
                        .where(Q_BALANCE_SPRINKLE.id.eq(id))
                        .select(Q_BALANCE_SPRINKLE)
                        .fetchOne()).get();
    }
}
