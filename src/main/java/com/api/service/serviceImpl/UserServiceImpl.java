package com.api.service.serviceImpl;

import com.api.exceptions.ResourceExistException;
import com.api.exceptions.ResourceNotFoundExption;
import com.api.model.UserDao;
import com.api.model.UserDto;
import com.api.model.dto.ChangePasswordDto;
import com.api.model.dto.EmailUpdateDto;
import com.api.repository.UserRepository;
import com.api.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Override
    public List<UserDao> fetchAllUsers() {
        List<UserDao> userDaoArrayList = null;
      return userRepository.findAll();

    }

    @Override
    public UserDao fetchUserById(Long id) {
        return  userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExption(String.format("User with id %d does not exist",id)));
    }


    @Override
    public Object save(UserDto user) {
        UserDao newUser = new UserDao();
        UserDao optionalUserDao = userRepository.findByEmail(user.getEmail());
        if(optionalUserDao !=null)
        {
            throw  new ResourceExistException("User with email exist already");
        }
        else
        {
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setPassword(user.getPassword());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            return  userRepository.save(newUser);
        }
    }

    @Transactional
    @Override
    public void updateUserEmail(EmailUpdateDto emailUpdateDto) {
        UserDao user = userRepository.findByEmail(emailUpdateDto.getOldEmail());
        if(user == null)
        {
           throw  new ResourceNotFoundExption(String.format("User with  id %s does not exist",emailUpdateDto.getOldEmail()));
        }
        else
        {
            UserDao optionalUserDao = userRepository.findByEmail(emailUpdateDto.getNewEmail());
            if(optionalUserDao !=null)
            {
                throw  new ResourceExistException(String.format("User with email %s exist already",emailUpdateDto.getNewEmail()));
            }
            else
            {
                user.setEmail(emailUpdateDto.getNewEmail());
            }
        }

    }
@Transactional
    @Override
    public void changePassword(ChangePasswordDto passwordDto) {
        UserDao user  = userRepository.findById(passwordDto.getId()).orElseThrow(()-> new ResourceNotFoundExption( String.format("User with %d id",passwordDto.getId())));

        if(bcryptEncoder.matches(passwordDto.getOldPassword(),user.getPassword()))
        {
            user.setPassword(bcryptEncoder.encode(passwordDto.getNewPassword()));
        }
        else{
            throw new ResourceNotFoundExption("Old-Password is wrong");
        }
    }
    @Transactional
    @Override
    public void updateUser(UserDao userDao) {
        UserDao user  = userRepository.findById(userDao.getId()).orElseThrow(()-> new ResourceNotFoundExption( String.format("User with %d id",userDao.getId())));

         user.setFirstName(userDao.getFirstName());
         user.setFirstName(userDao.getLastName());
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        UserDao user  = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExption( String.format("User with %d id",id)));
         userRepository.delete(user);
    }
}
