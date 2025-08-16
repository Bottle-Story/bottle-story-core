package com.kyj.fmk.core.file;

import com.kyj.fmk.core.model.enm.FileType;
import org.springframework.web.multipart.MultipartFile;

/**
 * 2025-05-29
 * @author 김용준
 * Restful Api에서 사용하는 파일관련 validation
 */
public final class FileVailidator {




    public static boolean valid(MultipartFile file,FileType fileType, long maxSize){

        return false;
    }

    /**
     * 파일의 최대크기를 검증한다.
     * @param file
     * @param maxSize
     * @return
     */
    private static boolean validMaxSize(MultipartFile file , long maxSize){
       long fileSize= file.getSize();

        return fileSize <= maxSize;
    }

    /**
     * 파일타입을 검증한다.(doc,pdf...)
     *
     * @param fileType
     * @param extension
     * @return
     */
    private static boolean validExtension(FileType fileType , String extension){

        return fileType.supports(extension);
    }

}

