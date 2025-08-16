package com.kyj.fmk.core.file;

import com.kyj.fmk.core.model.enm.FileType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
/**
 * 2025-05-29
 * @author 김용준
 * 파일 업로드,삭제,다운로드하기 위한 서비스
 */
public interface FileService {
    public String upload(MultipartFile file, FileType[] fileTypes);
    public void delete(String fileAddress);
    public ResponseEntity<byte[]> download(String fileAddress);
}
