package fr.free.bkake.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.free.bkake.business.model.ImmutableUserInfo;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.usecase.user.AddUser;
import fr.free.bkake.business.usecase.user.FindUser;
import fr.free.bkake.business.usecase.user.ImmutableAddUser;
import fr.free.bkake.business.usecase.user.ImmutableFindUser;
import fr.free.bkake.rest.RestLuncher;
import fr.free.bkake.rest.mock.MockUsers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestLuncher.class)
@WebAppConfiguration
public class UserControllerTest {

    private static  final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMVC;


    @Before
    public void setup() {
        this.mockMVC = webAppContextSetup(context).build();

    }


    public MvcResult createUser(UserInfo userInfo) throws Exception {
        MvcResult result   = mockMVC.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(ImmutableUserInfo.copyOf(userInfo))))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result).isNotNull();
        return result;
    }


    @Test
    public void createUserSuccess() throws Exception {
        UserInfo userInfo = MockUsers.getUsers().get(0);
        MvcResult result = createUser(userInfo);
        ImmutableAddUser.Response response = fromJSON(result.getResponse().getContentAsString(),ImmutableAddUser.Response.class);

        assertThat(response).isNotNull();
        assertThat(response.userId()).isNotNull();
        assertThat(response.status()).isNotNull().isEqualTo(AddUser.Status.OK);

        logger.info(response.toString());
    }


    @Test
    public void searchAllUseTest() throws Exception {
        for (UserInfo user: MockUsers.getUsers()) {
            if(user.number() =="user1"){
                continue;
            }
            createUser(user);
        }

        MvcResult result  = mockMVC.perform(post("/api/users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(ImmutableUserInfo.builder().build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userInfos", hasSize(3)))
                .andExpect(jsonPath("status").value(FindUser.Status.OK.toString()))
                .andReturn();

        assertThat(result).isNotNull();

        logger.info("searchAllUseTest");
        logger.info(fromJSON(result.getResponse().getContentAsString(),ImmutableFindUser.Response.class).toString());

    }


    @Test
    public void searchUserByNumberTest() throws Exception {
       MvcResult result =  this.mockMVC.perform(post("/api/users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(ImmutableUserInfo.builder().number("user1").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userInfos", hasSize(1)))
                .andExpect(jsonPath("status").value(FindUser.Status.OK.toString()))
                .andReturn();

        assertThat(result).isNotNull();

        logger.info("searchUserByNumberTest");
        logger.info(fromJSON(result.getResponse().getContentAsString(), ImmutableFindUser.Response.class).toString());
    }


   @Test
    public void searUserEmptyResultTest() throws Exception {
        UserInfo userInfo = ImmutableUserInfo.builder().number("0099").build();
        MvcResult result =  this.mockMVC.perform(post("/api/users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(ImmutableUserInfo.copyOf(userInfo))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(FindUser.Status.EMPTY.toString()))
                .andReturn();

        assertThat(result).isNotNull();

        logger.info("searUserEmptyResultTest");
        logger.info(fromJSON(result.getResponse().getContentAsString(), ImmutableFindUser.Response.class).toString());
    }



    private String toJSON(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private <T> T fromJSON(String json,Class<T> type) throws IOException {
        return new ObjectMapper().readValue(json,type);
    }

}