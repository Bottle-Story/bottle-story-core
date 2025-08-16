package com.kyj.fmk.core.mail;
/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 메일전송 인터페이스
 */
public interface MailSender {


    public void send(String template,String subject,String email);


}
