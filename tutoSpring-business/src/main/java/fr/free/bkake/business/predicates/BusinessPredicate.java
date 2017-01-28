package fr.free.bkake.business.predicates;


import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.core.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class BusinessPredicate {

    public static final Predicate<UserInfo> requestIsNull = getGenericPredicate(request -> request == null);
    public static final Predicate<String> isRequestFieldNotValid = getGenericPredicate(StringUtils::isBlank);
    public static final Predicate<User> notExistingUser = getGenericPredicate(user -> user == null);


    private static <T> Predicate<T> getGenericPredicate(Predicate<T> p) {
        return p;
    }


}
