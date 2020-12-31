package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){
        PageInfo pageInfo = userService.findAllUserByPage(userVo);
        return new ResponseResult(true,200,"响应成功",pageInfo);
    }


    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        User user2 = userService.login(user);
        if (user2 != null) {
            HttpSession session = request.getSession();
            String s = UUID.randomUUID().toString();
            session.setAttribute("access_token", s);
            session.setAttribute("user_id",user2.getId());
            Map<String,Object> map = new HashMap<>();
            map.put("access_token",s);
            map.put("user_id",user2.getId());
            map.put("user",user2);
            return new ResponseResult(true,1,"success",map);
        }

        return new ResponseResult(true,400,"用户名密码错误",null);
    }


    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(Integer id) {
        List<Role> roleList = userService.findUserRelationRoleById(id);
        return new ResponseResult(true,200,"分配角色回显成功",roleList);
    }

    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo) {
        userService.userContextRole(userVo);
        return new ResponseResult(true,200,"分配角色成功",null);
    }


    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request) {
        String header_token = request.getHeader("Authorization");
        String session_token = (String) request.getSession().getAttribute("access_token");
        if (header_token.equals(session_token)) {
            return userService.getUserPermissions((Integer) request.getSession().getAttribute("user_id"));
        } else {
            return new ResponseResult(false,400,"获取菜单信息失败",null);
        }
    }
}
