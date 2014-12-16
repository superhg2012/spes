package org.spes.service.mail.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.velocity.app.VelocityEngine;
import org.spes.service.mail.MailSernderService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailSenderServiceImpl implements MailSernderService {

	// IOC
	private JavaMailSenderImpl mailSender;
	private VelocityEngine velocityEngine;

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public JavaMailSenderImpl getJavaMailSender() {
		return mailSender;
	}

	public void setJavaMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String targetEmail, String userName, String realName,
			String password) throws MessagingException {
		Map<String, String> model = new HashMap<String, String>();
		model.put("username", userName);
		model.put("realname", realName);
		model.put("password", password);
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/"));
		model.put("url", url);
		String emailText = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "mail.vm", "UTF-8",model);
		// 和上面一样的发送邮件的工作
		
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setFrom(mailSender.getUsername());
		helper.setTo(targetEmail);
		helper.setSubject("【政务服务中心绩效评价系统】您的账号已经通过审核");
		helper.setText(emailText, true);
		mailSender.send(msg);
	}
}
