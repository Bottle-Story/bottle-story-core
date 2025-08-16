package com.kyj.fmk.core.exception.handler;

import com.kyj.fmk.core.model.dto.ResApiErrDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.exception.custom.KyjBaseException;
import com.kyj.fmk.core.exception.custom.KyjBatException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 글로벌 익셉션 핸들러
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * base 익셉션 핸들러
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(KyjBaseException.class)
    public ResponseEntity<ResApiErrDTO<?>> handleBaseException(KyjBaseException ex){

       ResApiErrDTO<Void> resApiErrDTO = ErrHelper.determineErrRes(ex);
        return ResponseEntity
                .status(HttpStatus.valueOf(resApiErrDTO.getStatus()))
                .body(resApiErrDTO);
    }
    /**
     * 배치 익셉션 핸들러
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(KyjBatException.class)
    public void handleBatException(KyjBatException ex){
      String msg = ErrHelper.determineErrMsg(ex);

    }

    /**
     * 유효성 익셉션 핸들러
     * @Valid @RequestBody 바인딩 후 유효성/바인딩 오류
     * @param ex
     * @return ResponseEntity
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResApiErrDTO<?>> handleValidationException(MethodArgumentNotValidException ex) {

        // 타입 오류???
        List<String> list = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {

            if (error.isBindingFailure()) {
                list.add("타입변환에 실패하였습니다.");
            } else {
                list.add(error.getDefaultMessage());
            }
        });
        String msg = list.isEmpty() ? "잘못된 요청입니다." : list.get(0);
        msg = list.get(0);

        ResApiErrDTO<Void> resApiErrDTO = new ResApiErrDTO<>(msg, HttpStatus.BAD_REQUEST.value(), CmErrCode.CM013.getCode());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(resApiErrDTO);
    }
    /**
     * 유효성 익셉션 핸들러
     * 전체 예외 (최상위)
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResApiErrDTO<?>> handleValidationException(Exception ex) {

        ex = new KyjSysException(CmErrCode.CM002);
        ResApiErrDTO<Void> resApiErrDTO = ErrHelper.determineErrRes(ex);
        return ResponseEntity
                .status(HttpStatus.valueOf(resApiErrDTO.getStatus()))
                .body(resApiErrDTO);
    }


    /**
     * sql예외
     *
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public ResponseEntity<ResApiErrDTO<?>> handleValidationException(MyBatisSystemException ex) {

        ResApiErrDTO<Void> resApiErrDTO = ErrHelper.determineErrRes(new KyjSysException(CmErrCode.CM002));
        return ResponseEntity
                .status(HttpStatus.valueOf(resApiErrDTO.getStatus()))
                .body(resApiErrDTO);
    }


}
