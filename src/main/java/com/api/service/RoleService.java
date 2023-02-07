package com.api.service;

import com.api.model.Role;

import java.util.List;

public interface RoleService {
    public Role addNewRole(Role role);
    public void deleteRole(long id);
    List<Role> fetchAllRoles();
    public Role fetchRole(long id);
    public  void deleteAUserRole(long userId, long roleId);
}
