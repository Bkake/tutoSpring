package fr.free.bkake.business.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.free.bkake.core.domain.embaddable.Address;
import fr.free.bkake.core.domain.enums.CivilityType;
import fr.free.bkake.core.domain.enums.SexType;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(as = ImmutableUserInfo.class)
@JsonDeserialize(as = ImmutableUserInfo.class)
public interface UserInfo {
   @JsonProperty @Nullable String number();
   @JsonProperty @Nullable String firstName();
   @JsonProperty @Nullable String lastName();
   @JsonProperty @Nullable CivilityType civilityType();
   @JsonProperty @Nullable SexType sexType();
   @JsonProperty @Nullable Address address();
}