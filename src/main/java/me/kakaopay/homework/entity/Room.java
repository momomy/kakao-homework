package me.kakaopay.homework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "rooms")
public class Room extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1193876359955498662L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name = "xid", updatable = false, nullable = false, unique = true)
    private String xid;
}
