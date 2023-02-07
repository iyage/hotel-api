package com.api.service.serviceImpl;

import com.api.exceptions.ResourceNotFoundExption;
import com.api.model.Role;
import com.api.model.UserDao;
import com.api.repository.RoleRepository;
import com.api.repository.UserRepository;
import com.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<Role> fetchAllRoles()
    {
        return  roleRepository.findAll();
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
