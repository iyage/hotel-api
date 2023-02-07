package com.api.service;

import com.api.model.UserDao;
import com.api.model.UserDto;
import com.api.model.dto.ChangePasswordDto;
import com.api.model.dto.EmailUpdateDto;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface UserServices {
    public List<UserDao> fetchAllUsers();
    public UserDao fetchUserById(Long id);
    public Object save(UserDto user);
    public void updateUserEmail(EmailUpdateDto emailUpdateDto);
    public void changePassword(ChangePasswordDto passwordDto);
    public void updateUser(UserDao userDao);
    public void deleteUser(long id);
}

