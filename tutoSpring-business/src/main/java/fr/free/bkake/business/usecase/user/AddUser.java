package fr.free.bkake.business.usecase.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.predicates.BusinessPredicate;
import fr.free.bkake.business.utils.BusinessUtils;
import fr.free.bkake.core.domain.User;
import fr.free.bkake.core.repository.UserRespository;
import org.immutables.value.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.inject.Inject;

@Component
@Value.Enclosing
public class AddUser {

    @Inject
    private  UserRespository userRespository;


    public Response execute(UserInfo request) throws BusinessException {

        if (BusinessPredicate.requestIsNull.test(request)) {
            throw new BusinessException("AddUser request null value");
        }

        if (BusinessPredicate.isRequestFieldNotValid.test(request.number())) {
            throw new BusinessException("AddUser request user number is blank");
        }

        if (BusinessPredicate.isRequestFieldNotValid.test(request.firstName())) {
            throw new BusinessException("AddUser request user first name is blank");
        }

        if (BusinessPredicate.isRequestFieldNotValid.test(request.lastName())) {
            throw new BusinessException("AddUser request user last name is blank");
        }

        User isExistingUser = userRespository.findByNumber(request.number());
        if (BusinessPredicate.notExistingUser.negate().test(isExistingUser)) {
            throw new BusinessException("AddUser this user number is exist, choice another number");
        }

        User userSaved = userRespository.save(BusinessUtils.userToUserInfo(request));
        ImmutableAddUser.Response.Builder response;

        if (BusinessPredicate.notExistingUser.test(userSaved)) {
            response = ImmutableAddUser.Response.builder().status(Status.ERROR);
        } else {
            response = ImmutableAddUser.Response.builder().status(Status.OK)
                    .userId(userSaved.getId());
        }

        return response.build();
    }

    @Value.Immutable
    @JsonSerialize(as = ImmutableAddUser.Response.class)
    @JsonDeserialize(as = ImmutableAddUser.Response.class)
    public interface Response {
        @Nullable Long userId();

        Status status();
    }

    public enum Status {
        ERROR,
        OK,
    }
}
