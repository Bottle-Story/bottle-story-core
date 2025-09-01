package com.kyj.fmk.core.util;

import org.springframework.data.geo.Point;
import java.util.concurrent.ThreadLocalRandom;

public class GeoUtil {

    private static final double MIN_LAT = 33.0;
    private static final double MAX_LAT = 38.5;
    private static final double MIN_LON = 124.5;
    private static final double MAX_LON = 131.0;

    /**
     * 대한민국 내에서 랜덤 이동
     */
    public static Point randomizeLocationWithinKorea(double lat, double lon, double maxDistanceKm) {
        double newLat, newLon;
        int attempts = 0;

        do {
            double angle = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);
            double distanceKm = ThreadLocalRandom.current().nextDouble(0, maxDistanceKm);

            double deltaLat = (distanceKm / 111.0) * Math.cos(angle);
            double deltaLon = (distanceKm / (111.0 * Math.cos(Math.toRadians(lat)))) * Math.sin(angle);

            newLat = lat + deltaLat;
            newLon = lon + deltaLon;

            attempts++;
            // 최대 10회 시도 후 강제 제한
            if (attempts > 10) {
                newLat = Math.max(MIN_LAT, Math.min(MAX_LAT, newLat));
                newLon = Math.max(MIN_LON, Math.min(MAX_LON, newLon));
                break;
            }
        } while (newLat < MIN_LAT || newLat > MAX_LAT || newLon < MIN_LON || newLon > MAX_LON);

        return new Point(newLon, newLat);
    }
}
