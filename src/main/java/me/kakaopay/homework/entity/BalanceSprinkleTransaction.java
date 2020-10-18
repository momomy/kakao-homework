package me.kakaopay.homework.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "balance_sprinkle_transactions")
public class BalanceSprinkleTransaction extends AbstractAuditingEntity {

    private static final long serialVersionUID = 3567117919825649383L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_sprinkle_transaction_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "balance_sprinkle_id", nullable = false, updatable = false)
    private BalanceSprinkle balanceSprinkle;

    @Column(name = "user_id", nullable = false, updatable = false)
    private long userId;

    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "balance_transaction_id", nullable = false, updatable = false)
    private BalanceTransaction balanceTransaction;

    public BalanceSprinkleTransaction(BalanceSprinkle balanceSprinkle,
                                      long userId,
                                      BigDecimal amount,
                                      BalanceTransaction balanceTransaction) {
        this.balanceSprinkle = balanceSprinkle;
        this.userId = userId;
        this.amount = amount;
        this.balanceTransaction = balanceTransaction;
    }

    public static BalanceSprinkleTransaction create(BalanceSprinkle balanceSprinkle,
                                                    long userId,
                                                    BigDecimal amount,
                                                    BalanceTransaction balanceTransaction) {
        return new BalanceSprinkleTransaction(balanceSprinkle, userId, amount, balanceTransaction);
    }
}
