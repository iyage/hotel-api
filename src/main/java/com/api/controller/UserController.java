package com.api.controller;

import com.api.config.JwtTokenUtil;
import com.api.config.S3Util;
import com.api.model.JwtRequest;
import com.api.model.UserDao;
import com.api.model.UserDto;
import com.api.model.dto.ChangePasswordDto;
import com.api.model.dto.EmailUpdateDto;
import com.api.model.dto.ResponseDto;
import com.api.service.JwtUserDetailsService;

import com.api.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private UserServiceImpl userService;
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, WebRequest request) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		ResponseDto responseDto = new ResponseDto(
				"success","200",token,
				request.getDescription(false),new Date() );

		return new ResponseEntity<>(responseDto,HttpStatus.OK);
	}
	@RequestMapping( path = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> saveUser(@Valid @RequestBody UserDto user,WebRequest request) throws Exception {

		ResponseDto responseDto = new ResponseDto(
				"success","201",userService.save(user),
				request.getDescription(false),new Date() );

		return  new ResponseEntity<>(responseDto,HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
@GetMapping("/get_all_user")
	public ResponseEntity<ResponseDto> fetchAllUser(WebRequest request)
	{
		ResponseDto responseDto = new ResponseDto(
				"success","200",userService.fetchAllUsers(),
				request.getDescription(false),new Date() );

		return  new  ResponseEntity<>(responseDto,HttpStatus.OK);
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ClIENT')")
	@GetMapping("/get_user/{id}")
	public ResponseEntity<ResponseDto> fetchUser(@PathVariable (value = "id") long id,WebRequest request)
	{
		ResponseDto responseDto = new ResponseDto(
				"success","200",userService.fetchUserById(id),
				request.getDescription(false),new Date() );

		return new  ResponseEntity<>(responseDto,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/change_password")
	public ResponseEntity<ResponseDto> changePassword(@RequestBody ChangePasswordDto passwordDto,WebRequest request)
	{
		 userService.changePassword(passwordDto);

		ResponseDto responseDto = new ResponseDto(
				"success","200",null,
				request.getDescription(false),new Date() );

		return  new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ClIENT')")
	@PutMapping("/change_email")
	public ResponseEntity<ResponseDto> changeEmail(@RequestBody EmailUpdateDto emailUpdateDto,WebRequest request)
	{
		userService.updateUserEmail(emailUpdateDto);
		ResponseDto responseDto = new ResponseDto(
				"success","200",null,
				request.getDescription(false),new Date() );
		return  new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ClIENT')")
	@PutMapping("/update_user")
	public ResponseEntity<ResponseDto> updateUser(@RequestBody UserDao userDao,WebRequest request)
	{
		userService.updateUser(userDao);

		ResponseDto responseDto = new ResponseDto(
				"success","200",null,
				request.getDescription(false),new Date() );

		return  new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/delete_user/{id}")
	public ResponseEntity<ResponseDto> deleteUser(@PathVariable (value = "id") long id,WebRequest request)
	{
		userService.deleteUser(id);
		ResponseDto responseDto = new ResponseDto(
				"success","200",null,
				request.getDescription(false),new Date() );
		return  new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
	}


}
