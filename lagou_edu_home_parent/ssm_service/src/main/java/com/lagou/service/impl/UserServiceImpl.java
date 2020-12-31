package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVo userVO) {
        PageHelper.startPage(userVO.getCurrentPage(),userVO.getPageSize());
        List<User> users = userMapper.findAllUserByPage(userVO);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public User login(User user) throws Exception {
        User user2 = userMapper.login(user);

        if (user2 != null && Md5.verify(user.getPassword(),"lagou",user2.getPassword())) {
            return user2;
        }

        return null;
    }

    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        return userMapper.findUserRelationRoleById(id);
    }

    @Override
    public void userContextRole(UserVo userVo) {
        userMapper.deleteUserContextRole(userVo.getUserId());
        List<Integer> roleIdList = userVo.getRoleIdList();

        for (Integer integer: roleIdList) {
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(integer);

            Date date = new Date();
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }
    }

    @Override
    public ResponseResult getUserPermissions(Integer id) {
        List<Role> roleList = userMapper.findUserRelationRoleById(id);
        ArrayList<Integer> roleIds = new ArrayList<>();
        roleList.forEach((e)->roleIds.add(e.getId()));

        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);
        for (Menu menu: parentMenu) {
            List<Menu> menuList = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(menuList);
        }
        List<Resource> resource = userMapper.findResourceByRoleId(roleIds);
        HashMap<String,Object> map = new HashMap<>();
        map.put("menuList",parentMenu);
        map.put("resourceList", resource);
        return new ResponseResult(true,200,"响应成功",map);
    }
}
