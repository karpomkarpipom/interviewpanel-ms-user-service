package com.interviewpanel.user.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interviewpanel.user.bean.Role;
import com.interviewpanel.user.bean.User;
import com.interviewpanel.user.kafka.Sender;
import com.interviewpanel.user.exception.CustomException;
import com.interviewpanel.user.service.ISignupService;

@RestController
public class UserController {
	
	
	
 //   @Value("${spring.kafka.topic.userCreated}")
    @Value("${spring.cloud.stream.bindings.output.destination}")
    private String USER_CREATED_TOPIC;
	
    @Autowired
    private Sender sender;

    
    @Autowired
    private ISignupService iSignupService;
    
    @CrossOrigin("*")
    @PostMapping("v1/signup")
    @ResponseBody
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
    	Map<String, String> responseSuccessMap=new HashMap<String,String>();
    	//revisit this code feature------
    	Set<Role> roleSet=new HashSet<Role>();
    	roleSet.add(new Role(1,"USER"));
    	user.setRole(roleSet);
    	//------------
    	
    	User createdUser=iSignupService.saveUser(user);
    	//After successful signup send notification to the user successfully signedup using Kafka
    	sender.send(USER_CREATED_TOPIC, createdUser);
    	
    	if(createdUser!=null) {
    		responseSuccessMap.put("status","User "+createdUser.getFirstName() +" signedup Successfully!" );
    	}else {
    	      throw new CustomException("Signup unsuccessful!",HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        return new ResponseEntity<Map<String, String>>(responseSuccessMap, HttpStatus.CREATED);
    }
    
	

}
