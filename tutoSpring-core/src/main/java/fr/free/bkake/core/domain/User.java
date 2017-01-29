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
import java.io.Serializable;


@Entity
@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String number;

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private SexType sexType;

    @Enumerated(EnumType.STRING)
    @Column(length = 5)
    private CivilityType civilityType;

    @Embedded
    private Address address;
}
