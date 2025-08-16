//package com.kyj.fmk.core.tx.dataSource;
//
//import org.springframework.context.annotation.Profile;
//
/**
 * 2025-06-18
 * @author 김용준
 * Restful Api에서 사용하는 DataSourceContextHolder
 * 동적 데이터소스에 대한
 */
//@Profile("prd")
//public class DataSourceContextHolder {
//    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
//
//    public static void setDataSourceType(String dataSourceType) {
//        CONTEXT.set(dataSourceType);
//    }
//
//    public static String getDataSourceType() {
//        return CONTEXT.get();
//    }
//
//    public static void clearDataSourceType() {
//        CONTEXT.remove();
//    }
//}
