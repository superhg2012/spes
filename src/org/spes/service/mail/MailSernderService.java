package org.spes.service.mail;

import javax.mail.MessagingException;

/**
 * �ʼ����ͷ���
 * 
 * @author HeGang
 * 
 */
public interface MailSernderService {

	public void sendEmail(String targetEmail, String userName, String realName,
			String password) throws MessagingException;
}
