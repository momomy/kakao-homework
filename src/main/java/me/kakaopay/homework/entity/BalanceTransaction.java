package me.kakaopay.homework.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 64, nullable = false, updatable = false)
    private BalanceTransactionType transactionType;

    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "reference_user_id", nullable = false, updatable = false)
    private User referenceUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", length = 64, nullable = false, updatable = false)
    private BalanceReferenceType referenceType;

}
