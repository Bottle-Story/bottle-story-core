package com.kyj.fmk.core.util;

import com.kyj.fmk.core.model.wheather.KmaEntity;

import static java.lang.Math.*;

public class KmaGrid {

    private static int[] latLonToGrid(double lat, double lon) {
        double DEGRAD = Math.PI / 180.0;

        double re = 6371.00877; // 지구 반경(km)
        double grid = 5.0;      // 격자 간격(km)
        double slat1 = 30.0;
        double slat2 = 60.0;
        double olon = 126.0;
        double olat = 38.0;
        double xo = 210 / grid; // TM 좌표 기준점 X
        double yo = 675 / grid; // TM 좌표 기준점 Y

        double sn = tan(PI * 0.25 + slat2 * DEGRAD * 0.5) / tan(PI * 0.25 + slat1 * DEGRAD * 0.5);
        sn = log(cos(slat1 * DEGRAD) / cos(slat2 * DEGRAD)) / log(sn);

        double sf = tan(PI * 0.25 + slat1 * DEGRAD * 0.5);
        sf = pow(sf, sn) * cos(slat1 * DEGRAD) / sn;

        double ro = tan(PI * 0.25 + olat * DEGRAD * 0.5);
        ro = re * sf / pow(ro, sn);

        double ra = tan(PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / pow(ra, sn);

        double theta = lon * DEGRAD - olon * DEGRAD;
        if (theta > PI) theta -= 2.0 * PI;
        if (theta < -PI) theta += 2.0 * PI;
        theta *= sn;

        int x = (int) (ra * sin(theta) + xo + 0.5);
        int y = (int) (ro - ra * cos(theta) + yo + 0.5);

        return new int[]{x, y};
    }

    public static KmaEntity getKmaEntity(double lat, double lon) {
        int[] arr = latLonToGrid(lat, lon);

        KmaEntity kmaEntity = new KmaEntity();
        kmaEntity.setNx(String.valueOf(arr[0]));
        kmaEntity.setNy(String.valueOf(arr[1]));
        return kmaEntity;
    }
}
