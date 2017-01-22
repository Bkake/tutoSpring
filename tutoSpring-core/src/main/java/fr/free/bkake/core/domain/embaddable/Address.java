package fr.free.bkake.core.domain.embaddable;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 30)
	private String street;
	
	@Column(length = 30)
	private String city;
	
	@Column(length = 12)
	private String zipCode;

	@Column(length = 10)
	private  String telephone;

	@Column
	private String email;
}