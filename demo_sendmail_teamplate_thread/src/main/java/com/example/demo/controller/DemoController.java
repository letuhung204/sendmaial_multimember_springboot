package com.example.demo.controller;

import javax.swing.SwingWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MailService;

@RestController
public class DemoController{
    @Autowired
    MailService mailService;

    @PostMapping
    public ResponseEntity<String> sendMail() {
    	
    	
    	SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
    		  @Override
    		  protected Void doInBackground() throws Exception {
    		    Thread.sleep(10000);
    		    for (int i = 0; i < 10; i++) {
    	    		mailService.sendMail();
    			}
    		    return null;
    		  }
    		};
    		sw.execute();
        
    	return new ResponseEntity<>("hello world",HttpStatus.OK); 
    }
}