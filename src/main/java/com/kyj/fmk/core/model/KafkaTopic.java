package com.kyj.fmk.core.model;
/**
 * 2025-08-29
 * @author 김용준
 * KAFKA 토픽 리스트
 */
public class KafkaTopic {

    // 배치 웹소켓 세션 종료
    public static final String BATCH_WS_DISCONNECT = "batch.ws-disconnect";
    //배치 날씨 업데이트
    public static final String BATCH_WHEATHER_UPDATE = "batch.wheather-update";
    // 날씨 정량평가 및 상태코드 산출
    public static final String WHEATHER_BGM_WHEATHER_UPDATE = "wheather-bgm.wheather-update";
    //웹소켓 연결 시
    public static final String REALTIME_MEMBER_LOCATION = "realtime.websocket-connect";
    //로그아웃 시
    public static final String MEMBER_LOGOUT = "member.logout";
    // 유리병 서비스 - > 유리병 흐름
    public static final String BOTTLE_BOTTLE_FLOW = "bottle.bottle-flow";
    // 멤버 서비스  - > 유리병 흐름
    public static final String MEMBER_BOTTLE_FLOW = "member.bottle-flow";
    // 유리병 서비스 - > 후속 동작
    public static final String BOTTLE_BOTTLE_FLOW_RE_1 = "bottle.bottle-flow-re-1";
    //유리병 서비스 - > 답글작성 시
    public static final String BOTTLE_WRITE_REPLY = "bottle.write-reply";
    //웹소켓 푸시 이벤트 목록 들
    public static final String REALTIME_PUSH_WS_DISCONNECT = "realtime.push-ws-disconnect";
    public static final String REALTIME_PUSH_WTHR_BGM = "realtime.push-wthrBgm";
    public static final String REALTIME_PUSH_BTL_LIST = "realtime.push-btlList";
    public static final String REALTIME_PUSH_BTL_REPLY = "realtime.push-btlReply";
    public static final String REALTIME_PUSH_LIVE_USER = "realtime.push-liveUser";

}
