package com.api.service;

import com.api.model.Role;
import com.api.model.dto.RoleResponseDto;

import java.util.List;

public interface RoleService {
    public Role addNewRole(Role role);
    public void deleteRole(long id);
    List<RoleResponseDto> fetchAllRoles();
    public Role fetchRole(long id);
    public  void deleteAUserRole(long userId, long roleId);
}
