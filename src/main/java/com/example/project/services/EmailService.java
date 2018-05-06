package com.example.project.services;

import com.example.project.models.EmailObject;

public interface EmailService {

	void sendTemplateMessage(EmailObject object) throws Exception;

}
