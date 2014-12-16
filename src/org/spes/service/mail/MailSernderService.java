package org.spes.service.mail;

import javax.mail.MessagingException;

/**
 * 邮件发送服务
 * 
 * @author HeGang
 * 
 */
public interface MailSernderService {

	public void sendEmail(String targetEmail, String userName, String realName,
			String password) throws MessagingException;
}
