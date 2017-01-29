package fr.free.bkake.rest.mock;


import fr.free.bkake.business.model.ImmutableUserInfo;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.core.domain.enums.CivilityType;
import fr.free.bkake.core.domain.enums.SexType;

import java.util.ArrayList;
import java.util.List;

public class MockUsers {

    private static List<UserInfo> users = new ArrayList<>();


    public static void mock(){
        UserInfo user1  = ImmutableUserInfo.builder()
                .number("user1")
                .firstName("Kake")
                .lastName("Bangaly")
                .sexType(SexType.M)
                .civilityType(CivilityType.MR)
                .build();
        users.add(user1);


        UserInfo user2  = ImmutableUserInfo.builder()
                .number("user2")
                .firstName("Camara")
                .lastName("Aboubacar")
                .sexType(SexType.M)
                .civilityType(CivilityType.MR)
                .build();
        users.add(user2);

        UserInfo user3  = ImmutableUserInfo.builder()
                .number("user3")
                .firstName("Dupont")
                .lastName("Melanie")
                .sexType(SexType.F)
                .civilityType(CivilityType.MLLE)
                .build();
        users.add(user3);

    }

    public static List<UserInfo> getUsers(){
        if(users.isEmpty()){
            mock();
        }
        return users;
    }
}
