package cn.h123456.sample.controller;


import cn.h123456.sample.entity.User;
import cn.h123456.sample.mapper.UserMapper;
import cn.h123456.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Controller
public class UserController {
    @Autowired
    private UserService userservice;

    @Autowired
    private UserMapper userMapper;

    //登录成功后，查询所有数据
   // @ResponseBody
    @RequestMapping("/userList")
    public String userList(Model model){
        List<User> userList = userservice.ListUser();
        model.addAttribute("userList", userList);
            return "listUser";

    }

    //注册账号
    @RequestMapping(value = "/insert")
    public String insert(User user, HttpServletRequest request)
    {
        if (userMapper.findUserByName(request.getParameter("username")).size() == 0) {  //查询数据库是否有该数据
            user.setName(request.getParameter("username"));     //获取注册的用户账号，存放到数据库
            user.setPassword(request.getParameter("password2"));  //获取注册的用户密码，存放到数据库
            userservice.insertUser(user);
            return "enter";
        } else {
            return "usernameisexist";
        }
    }

    //修改密码
    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request) {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        if (userMapper.findUserByPassword(name,password).size() != 0) {  //查询数据库该条数据是否存在
            String password1 = request.getParameter("password2");
            userMapper.Update(password1, name);
            return "请重新访问登录页面！！！";
        } else {
            return "update";
        }
    }

    //删除用户
    @RequestMapping(value = "/delete")
    public String delete(int id) {
        int result = userservice.delete(id);
        if (result >= 1) {
            return "Refresh";
        } else {
            return "Refresh_the_list";
        }
    }
}
