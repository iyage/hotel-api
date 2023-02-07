package com.api.service.serviceImpl;

import com.api.exceptions.ResourceNotFoundExption;
import com.api.model.Role;
import com.api.model.UserDao;
import com.api.model.dto.RoleResponseDto;
import com.api.repository.RoleRepository;
import com.api.repository.UserRepository;
import com.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(long id) {
       Role role= roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExption("Role does not exist"));
        roleRepository.delete(role);
    }

    public List<RoleResponseDto> fetchAllRoles()
    {
        List<Role>roles = roleRepository.findAll();
        Set< RoleResponseDto> roleList = new HashSet<>();
        roles.forEach((role)->{
            roleList.add(RoleResponseDto.builder()
                    .roleName(role.getRoleName())
                    .id(role.getId())
                    .users(role.getUsers())
                    .build());
        });

        return new ArrayList<RoleResponseDto>(roleList);
    }
    public Role fetchRole(long id)
    {
        return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundExption("Role does not exist"));
    }

    @Transactional
    @Override
    public void deleteAUserRole(long userId, long roleId) {
     Role role =  roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundExption("Role does not exist"));
        UserDao user  = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundExption( String.format("User with %d id",userId)));
        user.getRoles().remove(role);
        role.getUsers().remove(user);


    }
}
