package fr.free.bkake.rest.controller;

import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.usecase.user.AddUser;
import fr.free.bkake.business.usecase.user.FindUser;
import fr.free.bkake.rest.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private AddUser addUser;

    @Autowired
    private FindUser findUser;

    @Value("${name}")
    private String name;

    @RequestMapping(method = RequestMethod.GET, path = "/name")
    public final String testValue() {
        return "Hello Mr Kanté";
    }


    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public final AddUser.Response insertUser(@RequestBody UserInfo request) throws BusinessException, CustomException {
        if (request == null) throw new CustomException("No such Request");
        return addUser.execute(request);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/search")
    public final FindUser.Response searchUser(@RequestBody UserInfo request) throws BusinessException {
        return findUser.execute(request);
    }
}
