package com.lagou.service;

import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;

import java.util.List;

public interface RoleService {
    public List<Role> findAllRole(Role role);
    List<String> findMenuByRoleId(Integer roleId);

    public void RoleContextMenu(RoleMenuVo roleMenuVo);

    public void deleteRole(Integer RoleId);
}
