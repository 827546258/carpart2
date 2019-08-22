package com.xiupeilian.carpart.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailTask implements Runnable {
    private JavaMailSenderImpl mailSender;
    private SimpleMailMessage mailMessage;

    public MailTask(JavaMailSenderImpl mailSender, SimpleMailMessage mailMessage) {
        this.mailSender = mailSender;
        this.mailMessage = mailMessage;
    }

    /*
     * 发邮件的任务类  任务类 必须实现 runnable接口
     * */
    @Override
    public void run() {
     mailSender.send(mailMessage);
    }

}
