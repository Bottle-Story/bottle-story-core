package com.kyj.fmk.core.model.enm;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
/**
 * 2025-05-29
 * @author 김용준
 * 파일 타입별로 확장자를 정의한 enum.
 * 유효성 검증 및 파일 분류 용도로 사용.
 */
public enum FileType {

    IMAGE(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp", "svg", "heic")),
    VIDEO(Arrays.asList("mp4", "mov", "avi", "mkv", "wmv", "webm")),
    DOCUMENT(Arrays.asList("pdf", "doc", "docx", "hwp", "odt")),
    SPREADSHEET(Arrays.asList("xls", "xlsx", "csv", "ods")),
    ARCHIVE(Arrays.asList("zip", "rar", "7z", "tar", "gz")),
    TEXT(Arrays.asList("txt", "log", "md", "rtf")),
    AUDIO(Arrays.asList("mp3", "wav", "aac", "flac", "ogg")),
    CODE(Arrays.asList("java", "py", "js", "ts", "html", "css", "json", "xml", "yml", "sql", "c", "cpp")),
    ETC(Arrays.asList("exe", "bat", "apk", "jar", "bin", "dat", "iso", "db", "bak"));

    // 각 파일 타입에 허용되는 확장자 목록
    private final List<String> extensions;

    /**
     * 생성자 - 확장자 목록을 초기화
     *
     * @param extensions 해당 파일 타입의 허용 확장자 목록
     */
    FileType(List<String> extensions) {
        this.extensions = extensions;
    }

    /**
     * 해당 파일 타입의 확장자 목록을 반환
     *
     * @return 확장자 리스트
     */
    public List<String> getExtensions() {
        return extensions;
    }

    /**
     * 주어진 확장자가 이 파일 타입에 속하는지 검사
     *
     * @param ext 검사할 확장자 (예: "jpg", "pdf")
     * @return true면 해당 타입에 속함
     */
    public boolean supports(String ext) {
        return ext != null && extensions.contains(ext.toLowerCase());
    }

    /**
     * 확장자를 기준으로 파일 타입을 유추
     *
     * @param ext 확장자 (예: "mp4")
     * @return 매칭되는 FileType이 있으면 Optional에 담아 반환
     */
    public static Optional<FileType> fromExtension(String ext) {
        if (ext == null) return Optional.empty();
        String lower = ext.toLowerCase();

        for (FileType type : FileType.values()) {
            if (type.supports(lower)) {
                return Optional.of(type);
            }
        }

        return Optional.empty();
    }
}
