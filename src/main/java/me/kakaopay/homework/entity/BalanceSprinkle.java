package me.kakaopay.homework.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "balance_sprinkles")
public class BalanceSprinkle extends AbstractAuditingEntity {

    private static final long serialVersionUID = 326282368896040865L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_sprinkle_id", updatable = false, nullable = false, unique = true)
    private Long id;

    /**
     * Token
     */
    @Column(name = "token", length = 3, nullable = false, updatable = false)
    private String token;

    /**
     * 뿌리기를 한 User
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    /**
     * 뿌리기를 한 Room
     */
    @Column(name = "room_id", nullable = false, updatable = false, length = 128)
    private String roomId;

    /**
     * 뿌리기 개수
     */
    @Column(name = "count", nullable = false, updatable = false)
    private int count;

    /**
     * 뿌리기 금액
     */
    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    /**
     * 뿌리기 만료일시
     */
    @Column(name = "expired_at", nullable = false, updatable = false)
    private LocalDateTime expiredAt;

    /**
     * 뿌리기 상태
     */
    @Setter(AccessLevel.PUBLIC)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SprinkleStatusType status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sprinkle_transaction_id", nullable = false, updatable = false)
    private BalanceTransaction sprinkleTransaction;

    @Setter(AccessLevel.PUBLIC)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_transaction_id")
    private BalanceTransaction refundTransaction;

    @OneToMany(mappedBy = "balanceSprinkle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BalanceSprinkleTransaction> transactions = Lists.newArrayList();

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiredAt);
    }

    public static BalanceSprinkle create(String token,
                                         long userId,
                                         String roomId,
                                         int count,
                                         BigDecimal amount,
                                         LocalDateTime expiredAt,
                                         BalanceTransaction sprinkleTransaction) {
        BalanceSprinkle sprinkle = new BalanceSprinkle();
        sprinkle.setToken(token);
        sprinkle.setUserId(userId);
        sprinkle.setRoomId(roomId);
        sprinkle.setCount(count);
        sprinkle.setAmount(amount);
        sprinkle.setExpiredAt(expiredAt);
        sprinkle.setSprinkleTransaction(sprinkleTransaction);
        sprinkle.setStatus(SprinkleStatusType.CREATED);
        return sprinkle;
    }

}
