package fr.free.bkake.business.utils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import fr.free.bkake.business.model.ImmutableUserInfo;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.core.domain.User;


public class BusinessUtils {

	public static void addOrClause(BooleanBuilder whereBuilder, Predicate likePredicate) {
		BooleanBuilder booleanBuilder =  whereBuilder.or(likePredicate);
		whereBuilder.or(booleanBuilder);
	}


	public static User userToUserInfo(UserInfo request){
		User user = new User();
		user.setNumber(request.number());
		user.setFirstName(request.firstName());
		user.setLastName(request.lastName());
		user.setSexType(request.sexType());
		user.setCivilityType(request.civilityType());
		user.setAddress(request.address());
		return  user;
	}

	public static UserInfo userInfoToUser(User user){
		return ImmutableUserInfo.builder()
				.number(user.getNumber())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.sexType(user.getSexType())
				.civilityType(user.getCivilityType())
				.address(user.getAddress())
				.build();
	}

}
