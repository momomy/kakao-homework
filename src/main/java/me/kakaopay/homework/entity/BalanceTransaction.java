package me.kakaopay.homework.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "balance_transactions")
public class BalanceTransaction extends AbstractAuditingEntity {

    private static final long serialVersionUID = 3621427635028829785L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_transaction_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 64, nullable = false, updatable = false)
    private BalanceTransactionType transactionType;

    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(name = "reference_user_id", nullable = false, updatable = false)
    private long referenceUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", length = 64, nullable = false, updatable = false)
    private BalanceReferenceType referenceType;

    public BalanceTransaction(long userId,
                              BalanceTransactionType transactionType,
                              BigDecimal amount,
                              long referenceUserId,
                              BalanceReferenceType referenceType) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.referenceUserId = referenceUserId;
        this.referenceType = referenceType;
    }

    public static BalanceTransaction deposit(
            long userId, BigDecimal amount, long referenceUserId, BalanceReferenceType referenceType) {
        return new BalanceTransaction(
                userId, BalanceTransactionType.DEPOSIT, amount, referenceUserId, referenceType);
    }

    public static BalanceTransaction withdraw(
            long userId, BigDecimal amount, long referenceUserId, BalanceReferenceType referenceType) {
        return new BalanceTransaction(
                userId, BalanceTransactionType.WITHDRAW, amount, referenceUserId, referenceType);
    }

}
