package fr.free.bkake.business.usecase.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import fr.free.bkake.business.constants.BusinessConstants;
import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.predicates.BusinessPredicate;
import fr.free.bkake.business.utils.BusinessUtils;
import fr.free.bkake.core.domain.QUser;
import fr.free.bkake.core.domain.User;
import fr.free.bkake.core.repository.UserRespository;
import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Value.Enclosing
public class FindUser {

    @Inject
    private UserRespository userRespository;


    public Response execute(UserInfo request) throws BusinessException{

        QUser user = QUser.user;

        if (BusinessPredicate.requestIsNull.test(request)){
               throw new BusinessException("FindUser request null value");
        }

        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (BusinessPredicate.isRequestFieldNotValid.negate().test(request.number())) {
            Predicate eqNumberPredicate = user.number.eq(request.number());
            BusinessUtils.addOrClause(whereBuilder, eqNumberPredicate);
        }

        if (BusinessPredicate.isRequestFieldNotValid.negate().test(request.firstName())) {
            Predicate likeFirstNamePredicate = StringUtils.contains(request.firstName(), BusinessConstants._LIKEOPERATOR) ?
                    user.firstName.like(request.firstName()) : user.firstName.eq(request.firstName());
            BusinessUtils.addOrClause(whereBuilder, likeFirstNamePredicate);
        }

        if (BusinessPredicate.isRequestFieldNotValid.negate().test(request.lastName())) {
            Predicate likeLastNameUserPredicate = StringUtils.contains(request.lastName(), BusinessConstants._LIKEOPERATOR) ?
                    user.lastName.like(request.lastName()) : user.lastName.eq(request.lastName());
            BusinessUtils.addOrClause(whereBuilder, likeLastNameUserPredicate);
        }

        List<User> userResponses = (List<User>) userRespository.findAll(whereBuilder.getValue());

         List<UserInfo> userInfos = userResponses.stream()
                 .map(BusinessUtils::userInfoToUser)
                 .collect(Collectors.toList());

        if(userInfos.isEmpty()) {
            return ImmutableFindUser.Response.builder()
                    .status(Status.EMPTY).build();
        }

        return ImmutableFindUser.Response.builder()
                .status(Status.OK).userInfos(userInfos).build();
    }

    @Value.Immutable
    @JsonSerialize(as = ImmutableFindUser.Response.class)
    @JsonDeserialize(as = ImmutableFindUser.Response.class)
    public interface Response {
        @JsonProperty
        @Nullable List<UserInfo> userInfos();

        @Value.Parameter
        @JsonProperty
        Status status();
    }

    public enum Status {
        EMPTY,
        OK,
    }
}
