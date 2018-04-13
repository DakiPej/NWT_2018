package com.meminator.demo.interfaces;

import com.meminator.demo.models.RegisteredUser;

public interface iNotify {
	
	public String getType(); 
	public String getPayload(); 
	public String getNotifier(); 
	public RegisteredUser getNotified(); 
}
