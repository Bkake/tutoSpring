package fr.free.bkake.business;

import com.google.common.collect.ImmutableList;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.ImmutableUserInfo;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.usecase.user.FindUser;
import fr.free.bkake.business.utils.BusinessUtils;
import fr.free.bkake.core.domain.User;
import fr.free.bkake.core.domain.enums.CivilityType;
import fr.free.bkake.core.domain.enums.SexType;
import fr.free.bkake.core.repository.UserRespository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static fr.free.bkake.core.domain.QUser.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FindUserTest {

    private static final Logger logger = LoggerFactory.getLogger(FindUserTest.class);
    @Mock
    private UserRespository userRespository;

    @InjectMocks
    private FindUser findUser;

    @Test(expected = BusinessException.class)
    public void findUseRequestNullValueTest() throws BusinessException {
        findUser.execute(null);
    }

    @Test
    public void findUserEmptyContentRequestTest() throws BusinessException {
        ImmutableList<User> users = givenUsers();
        given(userRespository.findAll(any(Predicate.class))).willReturn(users);
        UserInfo request = ImmutableUserInfo.builder().build();

        FindUser.Response response = findUser.execute(request);

        verify(userRespository, times(1)).findAll(any(Predicate.class));

        assertThat(response.status()).isEqualTo(FindUser.Status.OK);
        assertThat(response.userInfos().size()).isEqualTo(users.size());

        logger.info("findUserEmptyContentRequestTest : " + response.status());
        response.userInfos().stream().forEach(resp -> logger.info(resp.toString()));
    }

    @Test
    public void findUserEmptyResultTest() throws BusinessException {
        ImmutableList<User> users = ImmutableList.copyOf(new ArrayList<User>());
        given(userRespository.findAll(any(Predicate.class))).willReturn(users);
        UserInfo request = ImmutableUserInfo.builder().build();

        FindUser.Response response = findUser.execute(request);

        verify(userRespository, times(1)).findAll(any(Predicate.class));

        assertThat(response.status()).isEqualTo(FindUser.Status.EMPTY);

        logger.info("findUserEmptyResultTest : " + response.status());
    }

    @Test
    public void findUserByNumberTest() throws BusinessException {
        ImmutableList<User> users = givenUsers();
        ImmutableList<User> userExcepted = ImmutableList.of(users.get(1));

        UserInfo request = ImmutableUserInfo.builder().number("300PL").build();

        BooleanBuilder whereBuilder = new BooleanBuilder();
        Predicate eqNumberPredicate = user.number.eq(request.number());
        BusinessUtils.addOrClause(whereBuilder, eqNumberPredicate);

        given(userRespository.findAll(any(Predicate.class))).willReturn(users);
        given(userRespository.findAll(whereBuilder.getValue())).willReturn(userExcepted);

        FindUser.Response response = findUser.execute(request);

        verify(userRespository, times(1)).findAll(any(Predicate.class));
        verify(userRespository, times(1)).findAll(whereBuilder.getValue());

        assertThat(response.status()).isEqualTo(FindUser.Status.OK);
        assertThat(response.userInfos().size()).isEqualTo(userExcepted.size());

        logger.info("findUserByNumberTest : " + response.status());
        response.userInfos().stream().forEach(resp -> logger.info(resp.toString()));
    }

    @Test
    public void findUserLikeFirstNameTest() throws BusinessException {
        ImmutableList<User> users = givenUsers();
        ImmutableList<User> userExcepted = ImmutableList.of(users.get(1), users.get(3));

        UserInfo request = ImmutableUserInfo.builder().firstName("KA%").build();

        BooleanBuilder whereBuilder = new BooleanBuilder();
        Predicate likeFirstNamePredicate = user.firstName.like(request.firstName());
        BusinessUtils.addOrClause(whereBuilder, likeFirstNamePredicate);

        given(userRespository.findAll(any(Predicate.class))).willReturn(users);
        given(userRespository.findAll(whereBuilder.getValue())).willReturn(userExcepted);

        FindUser.Response response = findUser.execute(request);

        verify(userRespository, times(1)).findAll(any(Predicate.class));
        verify(userRespository, times(1)).findAll(whereBuilder.getValue());

        assertThat(response.status()).isEqualTo(FindUser.Status.OK);
        assertThat(response.userInfos().size()).isEqualTo(userExcepted.size());

        logger.info("findUserLikeFirstNameTest : " + response.status());
        response.userInfos().stream().forEach(resp -> logger.info(resp.toString()));
    }


    @Test
    public void findUserLastNameContentTest() throws BusinessException {
        ImmutableList<User> users = givenUsers();
        ImmutableList<User> userExcepted = ImmutableList.of(users.get(0), users.get(3));

        UserInfo request = ImmutableUserInfo.builder().lastName("%TOTO%").build();

        BooleanBuilder whereBuilder = new BooleanBuilder();
        Predicate likeLastNamePredicate = user.lastName.like(request.lastName());
        BusinessUtils.addOrClause(whereBuilder, likeLastNamePredicate);

        given(userRespository.findAll(any(Predicate.class))).willReturn(users);
        given(userRespository.findAll(whereBuilder.getValue())).willReturn(userExcepted);

        FindUser.Response response = findUser.execute(request);

        verify(userRespository, times(1)).findAll(any(Predicate.class));
        verify(userRespository, times(1)).findAll(whereBuilder.getValue());

        assertThat(response.status()).isEqualTo(FindUser.Status.OK);
        assertThat(response.userInfos().size()).isEqualTo(userExcepted.size());

        logger.info("findUserLastNameContentTest : " + response.status());
        response.userInfos().stream().forEach(resp -> logger.info(resp.toString()));
    }


    private ImmutableList<User> givenUsers() {
        List<User> users = new ArrayList<>();

        UserInfo userInfo = ImmutableUserInfo.builder()
                .number("200PL").firstName("TOTO").lastName("TOTO.TXT")
                .civilityType(CivilityType.MLLE).sexType(SexType.F).build();

        User user = BusinessUtils.userToUserInfo(userInfo);
        user.setId(1L);
        users.add(user);

        UserInfo userInfo2 = ImmutableUserInfo.builder()
                .number("300PL").firstName("KAKE").lastName("BANGALY")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();

        User user2 = BusinessUtils.userToUserInfo(userInfo2);
        user.setId(2L);
        users.add(user2);

        UserInfo userInfo3 = ImmutableUserInfo.builder()
                .number("400PL").firstName("Conde").lastName("Lamaya")
                .civilityType(CivilityType.MME).sexType(SexType.F).build();

        User user3 = BusinessUtils.userToUserInfo(userInfo3);
        user.setId(3L);
        users.add(user3);

        UserInfo userInfo4 = ImmutableUserInfo.builder()
                .number("500PL").firstName("KABA").lastName("TOTO")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();

        User user4 = BusinessUtils.userToUserInfo(userInfo4);
        user.setId(4L);
        users.add(user4);

        return ImmutableList.copyOf(users);
    }
}
