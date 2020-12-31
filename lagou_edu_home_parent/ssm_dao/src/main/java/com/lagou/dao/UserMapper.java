package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface UserMapper {

    public List<User> findAllUserByPage(UserVo userVO);

    public User login(User user);


    public List<Role> findUserRelationRoleById(Integer id);

    public void deleteUserContextRole(Integer userId);
    public void userContextRole(User_Role_relation user_role_relation);


//    public List<Role> findUserRelationRoleById(int id);

    /**
     * 根据角色id,查询角色拥有的顶级菜单信息
     * */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);
    /**
     * 根据PID 查询子菜单信息
     * */
    public List<Menu> findSubMenuByPid(int pid);
    /**
     * 获取用户拥有的资源权限信息
     * */
    public List<Resource> findResourceByRoleId(List<Integer> ids);


}
