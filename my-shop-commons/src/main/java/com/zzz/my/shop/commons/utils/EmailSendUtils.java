package com.zzz.my.shop.commons.utils;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 邮件发送工具类
 * <p>Title: EmailSendUtils</p>
 * <p>Description: </p>
 *
 * @author 111
 * @version 1.0.0
 * @date 2019/8/29 20:35
 */
public class EmailSendUtils {

    @Autowired
    private Email email;

    public void send(String subject,String msg,String... to) throws EmailException {
        email.setSubject(subject);
        email.setMsg(msg);
        email.addTo(to);
        email.send();
    }
}
