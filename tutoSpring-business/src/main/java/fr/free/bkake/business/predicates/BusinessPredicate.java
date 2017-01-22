package fr.free.bkake.business.predicates;


import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.core.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class BusinessPredicate {

	public static Predicate<UserInfo> requestIsNull = getGenericPredicate(request -> request == null);
	public static Predicate<String> emptyString = getGenericPredicate(s -> StringUtils.isBlank(s));
	public static Predicate<User> notExistingUser = getGenericPredicate(user -> user == null);


	private static <T> Predicate<T> getGenericPredicate(Predicate<T> p) {
	    return p;
	}
}
