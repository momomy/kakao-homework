package me.kakaopay.homework.repository.sprinkle;

import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Sets;

import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.QBalanceSprinkle;
import me.kakaopay.homework.entity.QSprinkleTransaction;
import me.kakaopay.homework.entity.QUser;
import me.kakaopay.homework.entity.User;
import me.kakaopay.homework.repository.AbstractRepositorySupport;

public class BalanceSprinkleRepositoryImpl
        extends AbstractRepositorySupport
        implements BalanceSprinkleRepositoryCustom {

    private static final QBalanceSprinkle Q_BALANCE_SPRINKLE = QBalanceSprinkle.balanceSprinkle;

    private static final QUser Q_USER = QUser.user;

    private static final QSprinkleTransaction Q_SPRINKLE_TRANSACTION = QSprinkleTransaction.sprinkleTransaction;

    public BalanceSprinkleRepositoryImpl() {
        super(BalanceSprinkle.class);
    }

    @Override
    public Optional<BalanceSprinkle> find(User user, String token) {
        return Optional.ofNullable(
                from(Q_BALANCE_SPRINKLE)
                        .innerJoin(Q_BALANCE_SPRINKLE.user, Q_USER).fetchAll()
                        .innerJoin(Q_BALANCE_SPRINKLE.transactions, Q_SPRINKLE_TRANSACTION).fetchAll()
                        .where(Q_BALANCE_SPRINKLE.user.eq(user))
                        .where(Q_BALANCE_SPRINKLE.token.eq(token))
                        .select(Q_BALANCE_SPRINKLE)
                        .fetchOne());
    }

    @Override
    public Set<String> findTokens(User user) {
        return Sets.newHashSet(
                from(Q_BALANCE_SPRINKLE)
                        .where(Q_BALANCE_SPRINKLE.user.eq(user))
                        .select(Q_BALANCE_SPRINKLE.token)
                        .fetch());
    }
}
