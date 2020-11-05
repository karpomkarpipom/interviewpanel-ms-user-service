package com.interviewpanel.user.kafka;

 
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.interviewpanel.user.bean.User;


public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);
  
  @Autowired
  private MessageChannel output;
	
  
  /*
	 * @Autowired private KafkaTemplate<String, User> kafkaTemplate;
	 * 
	 * public void send(String topic, User payload) {
	 * LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
	 * kafkaTemplate.send(topic, payload); }
	 */
  
  public void send(String topic, User payload) {
      LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
      //kafkaTemplate.send(topic, payload);
      output.send(MessageBuilder.withPayload(payload).build());
      
  }
}