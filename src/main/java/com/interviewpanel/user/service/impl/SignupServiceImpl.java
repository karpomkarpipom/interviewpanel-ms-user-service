package com.interviewpanel.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interviewpanel.user.repo.UserRepository;
import com.interviewpanel.user.bean.User;
import com.interviewpanel.user.exception.CustomException;
import com.interviewpanel.user.service.ISignupService;

@Service
public class SignupServiceImpl implements ISignupService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
  
	@Autowired
    private UserRepository userRepository;
    
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()) );
        User dbCreatedUser = null;
        try {
        	dbCreatedUser=userRepository.save(user);
        }catch (DuplicateKeyException ex) {
        	throw new CustomException("User already exists!",HttpStatus.FOUND);
        }catch (Exception ex) {
        	throw new CustomException("User creation exception in DB!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return dbCreatedUser;
    }

}
