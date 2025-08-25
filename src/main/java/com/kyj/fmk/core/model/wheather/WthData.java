package com.kyj.fmk.core.model.wheather;

import lombok.Getter;
import lombok.Setter;
/**
 * 2025-08-25
 * @author 김용준
 * 기상청 날씨정보 상세 데이터
 */
@Getter
@Setter
public class WthData {

    private int sky;
    private int pty;
    private int lgt;
    private int wsd;
    private String t1h;
}
