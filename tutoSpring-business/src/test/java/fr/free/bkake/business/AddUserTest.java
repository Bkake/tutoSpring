package fr.free.bkake.business;

import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.ImmutableUserInfo;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.usecase.user.AddUser;
import fr.free.bkake.business.usecase.user.ImmutableAddUser;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddUserTest {

    private static final Logger logger = LoggerFactory.getLogger(AddUserTest.class);

    @Mock
    private UserRespository userRespository;

    @InjectMocks
    private AddUser addUser;


    @Test(expected = BusinessException.class)
    public void addUseRequestNullValueTest() throws BusinessException {
        addUser.execute(null);
    }

    @Test(expected = BusinessException.class)
    public void addUseRequestNumberNullValueTest() throws BusinessException {
        UserInfo request = ImmutableUserInfo.builder()
                .firstName("TOTO").lastName("TOTO.TXT").number(null)
                .civilityType(CivilityType.MR).sexType(SexType.M).build();

        addUser.execute(request);
    }

    @Test(expected = BusinessException.class)
    public void addUseRequestNumberNulValueTest() throws BusinessException {
        UserInfo request = ImmutableUserInfo.builder()
                .firstName("TOTO").lastName("TOTO.TXT")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();
        addUser.execute(request);
    }


    @Test(expected = BusinessException.class)
    public void addUseRequestFirstNameNulValueTest() throws BusinessException {
        UserInfo request = ImmutableUserInfo.builder().number("200L3")
                .lastName("TOTO.TXT")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();
        addUser.execute(request);
    }


    @Test(expected = BusinessException.class)
    public void addUseRequestLastNameNulValueTest() throws BusinessException {
        UserInfo request = ImmutableUserInfo.builder().number("200L3").firstName("TOTO")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();
        addUser.execute(request);
    }

    @Test
    public void addUserSuccessTest() throws BusinessException {

        AddUser.Response responseActual = ImmutableAddUser.Response.builder()
                .status(AddUser.Status.OK).userId(1L).build();

        User user = givenUser();
        given(userRespository.findByNumber(anyString())).willReturn(null);
        given(userRespository.save(any(User.class))).willReturn(user);

        UserInfo request = ImmutableUserInfo.builder().number("200PL")
                .firstName("TOTO").lastName("TOTO.TXT")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();

        AddUser.Response responseExcepted = addUser.execute(request);

        verify(userRespository).findByNumber(anyString());
        verify(userRespository).save(any(User.class));

        assertThat(responseActual.status()).isEqualTo(responseExcepted.status());
        assertThat(responseActual.userId()).isEqualTo(responseExcepted.userId());

        logger.info("addUserSuccessTest");
        logger.info(responseExcepted.status() + " " + responseExcepted.userId());
    }


    @Test
    public void addUserErrorTest() throws BusinessException {
        given(userRespository.findByNumber(anyString())).willReturn(null);
        given(userRespository.save(any(User.class))).willReturn(null);

        UserInfo request = ImmutableUserInfo.builder().number("200PL")
                .firstName("TOTO").lastName("TOTO.TXT")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();


        AddUser.Response response = addUser.execute(request);

        verify(userRespository).findByNumber(anyString());
        verify(userRespository).save(any(User.class));

        assertThat(response.status()).isEqualTo(AddUser.Status.ERROR);

        logger.info("addUserErrorTest");
        logger.info(response.status() + " " + response.userId());
    }

    @Test(expected = BusinessException.class)
    public void existingUserErrorTest() throws BusinessException {
        given(userRespository.findByNumber(anyString())).willReturn(givenUser());
        given(userRespository.save(any(User.class))).willReturn(null);

        UserInfo request = ImmutableUserInfo.builder().number("200PL")
                .firstName("TOTO").lastName("TOTO.TXT")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();


        AddUser.Response response= addUser.execute(request);

        verify(userRespository).findByNumber(anyString());
        verify(userRespository).save(any(User.class));

        assertThat(response.status()).isEqualTo(AddUser.Status.ERROR);

        logger.info("existingUserErrorTest");
        logger.info(response.status() + " " + response.userId());

    }

    private User givenUser() {
        UserInfo userInfo = ImmutableUserInfo.builder()
                .number("200PL").firstName("TOTO").lastName("TOTO.TXT")
                .civilityType(CivilityType.MR).sexType(SexType.M).build();

        User user = BusinessUtils.userToUserInfo(userInfo);
        user.setId(1L);

        return user;
    }
}
