package fr.free.bkake.business.model;

import fr.free.bkake.core.domain.embaddable.Address;
import fr.free.bkake.core.domain.enums.CivilityType;
import fr.free.bkake.core.domain.enums.SexType;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
public interface UserInfo {
   String number();
   String firstName();
   String lastName();
   CivilityType civilityType();
   SexType sexType();
   @Nullable Address address();
}