package fr.free.bkake.rest.controller;

import fr.free.bkake.business.exception.BusinessException;
import fr.free.bkake.business.model.UserInfo;
import fr.free.bkake.business.usecase.user.AddUser;
import fr.free.bkake.business.usecase.user.FindUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private AddUser addUser;

    @Autowired
    private FindUser findUser;


    @RequestMapping(method = RequestMethod.POST, path="/create")
    public final @ResponseBody AddUser.Response insertUser(@RequestBody UserInfo request) throws BusinessException {
         return  addUser.execute(request);
    }

    @RequestMapping(method = RequestMethod.POST, path ="/search")
    public final @ResponseBody FindUser.Response searchUser(@RequestBody UserInfo request) throws BusinessException {
        return findUser.execute(request);
    }
}
