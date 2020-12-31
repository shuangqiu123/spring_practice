package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

public interface RoleMapper {

    public List<Role> findAllRole(Role role);

    List<String> findMenuByRoleId(Integer roleId);

    public void deleteRoleContextMenu(Integer roleId);

    public void roleContextMenu(Role_menu_relation role_menu_relation);

    public void deleteRole(Integer RoleId);
}
