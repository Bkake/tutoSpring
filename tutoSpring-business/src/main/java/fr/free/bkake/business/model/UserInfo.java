package fr.free.bkake.business.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.free.bkake.core.domain.embaddable.Address;
import fr.free.bkake.core.domain.enums.CivilityType;
import fr.free.bkake.core.domain.enums.SexType;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonSerialize(as = ImmutableUserInfo.class)
@JsonDeserialize(as = ImmutableUserInfo.class)
public interface UserInfo {
   @Nullable
   String number();

   @Nullable
   String firstName();

   @Nullable
   String lastName();

   @Nullable
   CivilityType civilityType();

   @Nullable
   SexType sexType();

   @Nullable
   Address address();
}