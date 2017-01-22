package fr.free.bkake.core.domain;

import fr.free.bkake.core.domain.embaddable.Address;
import fr.free.bkake.core.domain.enums.CivilityType;
import fr.free.bkake.core.domain.enums.SexType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Entity
@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class User extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 10, nullable = false, unique = true)
    private String number;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private SexType sexType;

    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private CivilityType civilityType;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
}
