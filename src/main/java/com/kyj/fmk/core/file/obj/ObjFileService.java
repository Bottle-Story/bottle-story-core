package com.kyj.fmk.core.file.obj;


import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.file.FileService;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.core.model.enm.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 2025-05-30
 * @author 김용준
 * S3파일 업로드,삭제,다운로드하기 위한 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ObjFileService implements FileService {

    @Value("${s3.credentials.bucket}")
    private String bucket;

    private static final long MAX_FILE_SIZE = 5* 1024 * 1024 ;


    private final S3Client s3Client;

    /**
     * 파일업로드
     * @param file
     * @param fileTypes
     * @return
     */
    @Override
    public String upload(MultipartFile file, FileType[] fileTypes) {
        if(file == null){
            throw new KyjBizException(CmErrCode.CM009);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

        boolean validResult;

        for(FileType fileType : fileTypes){
            validResult = fileType.supports(extension);
            if(!validResult){
                throw new KyjBizException(CmErrCode.CM007);
            }
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new KyjBizException(CmErrCode.CM008);
        }

        try {
            byte[] bytes = file.getBytes();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3FileName)
//                .contentType("image/" + extension)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
    } catch (S3Exception e) {
            throw new KyjSysException(CmErrCode.CM009);
    } catch (SdkException e) {
            throw new KyjSysException(CmErrCode.CM009);
    } catch (IOException e) {
            throw new KyjSysException(CmErrCode.CM009);
        }

        return s3Client
                .utilities()
                .getUrl(builder ->
                        builder.bucket(bucket).key(s3FileName)
                )
                .toExternalForm();
    }

    /**
     * 파일삭제
     * @param fileAddress
     */
    @Override
    public void delete(String fileAddress) {
        String key = getKeyFromImageAddress(fileAddress);
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);

        } catch (S3Exception e) {
            throw new KyjSysException(CmErrCode.CM010);
        } catch (Exception e) {
            throw new KyjSysException(CmErrCode.CM010);
        }

    }


    /**
     * 파일다운로드
     * @param filename
     * @return
     */
    @Override
    public ResponseEntity<byte[]> download(String filename) {
        try {
            // S3 객체 요청
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(filename)
                    .build();
            // S3 객체 가져오기
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);

            // Content-Type 및 파일 이름 지정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

            return new ResponseEntity<>(objectBytes.asByteArray(), headers, HttpStatus.OK);

        } catch (NoSuchKeyException e) {
           throw new KyjSysException(CmErrCode.CM011);
        } catch (Exception e) {
            throw new KyjSysException(CmErrCode.CM011);
        }

    }


    //파일 삭제시 키값 가져오기
    private String getKeyFromImageAddress(String fileAddress) {
        try {
            URL url = new URL(fileAddress);
            String decodedPath = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8.name());
            return decodedPath.startsWith("/") ? decodedPath.substring(1) : decodedPath;
        } catch (MalformedURLException e) {
            throw new KyjSysException(CmErrCode.CM010);
        } catch (UnsupportedEncodingException e) {
            throw new KyjSysException(CmErrCode.CM010);
        }
        catch (Exception e) {
            throw new KyjSysException(CmErrCode.CM010);
        }
    }
}
