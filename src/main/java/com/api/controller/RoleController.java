package com.api.controller;

import com.api.exceptions.ResourceNotFoundExption;
import com.api.model.Role;
import com.api.model.UserDao;
import com.api.model.dto.AddUserRoleDto;
import com.api.model.dto.ResponseDto;
import com.api.repository.RoleRepository;
import com.api.repository.UserRepository;
import com.api.service.serviceImpl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/new_role")
    public ResponseEntity<ResponseDto>addNewRole(@RequestBody String  role,WebRequest request)
    {
        Role newRole = new Role();
          newRole.setRoleName(role);
        ResponseDto responseDto = new ResponseDto(
                "success","201",roleService.addNewRole(newRole),
                request.getDescription(false),new Date() );
        return new  ResponseEntity<ResponseDto>(responseDto,HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Transactional
    @PostMapping("/add_role_user")
    public ResponseEntity<ResponseDto>addRoleToUser(@RequestBody AddUserRoleDto addUserRoleDto, WebRequest request)
    {
        UserDao userDao = userRepository.findById(addUserRoleDto.getId()).orElseThrow(()-> new ResourceNotFoundExption("User Does not exit"));
        Role newRole = roleRepository.findByRoleName(addUserRoleDto.getRoleName()).orElseThrow(()-> new ResourceNotFoundExption("Role does not exist"));
        userDao.getRoles().add(newRole);
        newRole.getUsers().add(userDao);
        ResponseDto responseDto = new ResponseDto(
                "success","201",null,
                request.getDescription(false),new Date() );
        return new  ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/delete_role/{id}")
    public ResponseEntity<ResponseDto> deleteRole(@PathVariable( value = "id")   long id,WebRequest request) {
          roleService.deleteRole(id);
        ResponseDto responseDto = new ResponseDto(
                "success","200",null,
                request.getDescription(false),new Date() );
          return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/get_all_roles")
    public ResponseEntity<ResponseDto> fetchAllRoles(WebRequest request)
    {
         List<Role> roleList = roleService.fetchAllRoles();

        ResponseDto responseDto = new ResponseDto(
                "success","200",roleList,
                request.getDescription(false),new Date() );

         return  new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/get_role/{id}")
    public ResponseEntity<ResponseDto> fetchRole(@PathVariable (value = "id") long id,WebRequest request)
    {
        Role role = roleService.fetchRole(id);
        ResponseDto responseDto = new ResponseDto(
                "success","200",role,
                request.getDescription(false),new Date() );
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/remove_role_from_user/{userId}/{roleId}")
    public ResponseEntity<ResponseDto> deleteAUserRole(@PathVariable (value = "userId") long userId, @PathVariable (value = "roleID") long roleId,WebRequest request)
    {
        roleService.deleteAUserRole(userId,roleId);
        ResponseDto responseDto = new ResponseDto(
                "success","200",null,
                request.getDescription(false),new Date() );
        return  new ResponseEntity<>(responseDto,HttpStatus.OK);
    }


}
