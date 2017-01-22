package fr.free.bkake.core.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Account extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true, length = 30)
    private String accountId;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
