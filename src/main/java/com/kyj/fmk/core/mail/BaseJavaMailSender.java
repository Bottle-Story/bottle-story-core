package com.kyj.fmk.core.mail;

import com.kyj.fmk.core.async.annotation.AsyncVoidInvoke;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.util.RandomGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
/**
 * 2025-05-30
 * @author 김용준
 * Restful Api에서 사용하는 메일전송클래스이다.
 */
@Service
@RequiredArgsConstructor
public class BaseJavaMailSender implements MailSender {

    private final JavaMailSender javaMailSender;

    /**
     *
     * @param templateNm
     * @param subject
     * @param email
     */
    @Override
    @AsyncVoidInvoke
    public void send(String templateNm,String subject, String email) {

        MimeMessage message= javaMailSender.createMimeMessage(); //메일링 객체 생성
        try {
            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(message,true);

            String htmlContent= MailUtil.getTemplate(templateNm,RandomGenerator.generateRandom(10));

            mimeMessageHelper.setTo(email);//보낼 상대
            mimeMessageHelper.setSubject(MailUtil.getSubject(subject)); //제목
            mimeMessageHelper.setText(htmlContent,true);

            javaMailSender.send(message);//전송
        } catch (MessagingException e) {
            throw new KyjSysException(CmErrCode.CM005);
        }

    }
}
