package me.kakaopay.homework.entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "balance_sprinkles")
public class BalanceSprinkle extends AbstractAuditingEntity {

    private static final long serialVersionUID = 326282368896040865L;

    private static final Duration SPRINKLE_DURATION = Duration.ofMinutes(10);

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    /**
     * 뿌리기를 한 Room
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "room_id", nullable = false, updatable = false)
    private Room room;

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

    @OneToMany(mappedBy = "balanceSprinkle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SprinkleTransaction> transactions;
}
