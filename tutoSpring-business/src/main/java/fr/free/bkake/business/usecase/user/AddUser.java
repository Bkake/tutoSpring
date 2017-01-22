package fr.free.bkake.business.usecase.user;

import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.predicates.BusinessPredicate;
import fr.free.bkake.business.utils.BusinessUtils;
import fr.free.bkake.core.domain.User;
import fr.free.bkake.core.repository.UserRespository;
import org.immutables.value.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.inject.Inject;

@Component
@Transactional
@Value.Enclosing
public class AddUser {

    @Inject
    private UserRespository userRespository;

    public Response execute(UserInfo request) throws BusinessException{

        if (BusinessPredicate.requestIsNull.test(request)) {
            throw new BusinessException("AddUser request null value");
        }

        if (BusinessPredicate.emptyString.test(request.number())) {
            throw new BusinessException("AddUser request user number is null value");
         }

        User isExistingUser = userRespository.findByNumber(request.number());
        if (BusinessPredicate.notExistingUser.negate().test(isExistingUser)) {
             throw new BusinessException("AddUser this login is exist, choice another login");
         }

        User userSaved = userRespository.save(BusinessUtils.userToUserInfo(request));
        ImmutableAddUser.Response.Builder response ;

        if (BusinessPredicate.notExistingUser.test(userSaved) ){
            response = ImmutableAddUser.Response.builder().status(Status.ERROR)
                    .msg("AddUser error save user ");
        }else{
            response = ImmutableAddUser.Response.builder().status(Status.OK)
                    .userId(userSaved.getId().longValue()).msg("AddUser saving success");
        }

        return response.build();
    }

    @Value.Immutable
    public interface Response {
        @Nullable Long userId();

        @Value.Parameter String msg();
        @Value.Parameter Status status();
    }

    public enum Status {
        ERROR,
        OK,
    }
}
