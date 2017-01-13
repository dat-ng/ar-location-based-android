package ng.dat.ar.helper;

import android.location.Location;

/**
 * Created by ntdat on 1/13/17.
 */

public class LocationHelper {
    private final static double WGS84_A = 6378137.0;                  // WGS 84 semi-major axis constant in meters
    private final static double WGS84_E2 = 0.00669437999014;          // square of WGS 84 eccentricity

    public static float[] WSG84toECEF(Location location) {
        double radLat = Math.toRadians(location.getLatitude());
        double radLon = Math.toRadians(location.getLongitude());

        float clat = (float) Math.cos(radLat);
        float slat = (float) Math.sin(radLat);
        float clon = (float) Math.cos(radLon);
        float slon = (float) Math.sin(radLon);

        float N = (float) (WGS84_A / Math.sqrt(1.0 - WGS84_E2 * slat * slat));

        float x = (float) ((N + location.getAltitude()) * clat * clon);
        float y = (float) ((N + location.getAltitude()) * clat * slon);
        float z = (float) ((N * (1.0 - WGS84_E2) + location.getAltitude()) * slat);

        return new float[] {x , y, z};
    }

    public static float[] ECEFtoENU(Location currentLocation, float[] ecefCurrentLocation, float[] ecefPOI) {
        double radLat = Math.toRadians(currentLocation.getLatitude());
        double radLon = Math.toRadians(currentLocation.getLongitude());

        float clat = (float)Math.cos(radLat);
        float slat = (float)Math.sin(radLat);
        float clon = (float)Math.cos(radLon);
        float slon = (float)Math.sin(radLon);

        float dx = ecefCurrentLocation[0] - ecefPOI[0];
        float dy = ecefCurrentLocation[1] - ecefPOI[1];
        float dz = ecefCurrentLocation[2] - ecefPOI[2];

        float east = -slon*dx + clon*dy;

        float north = -slat*clon*dx - slat*slon*dy + clat*dz;

        float up = clat*clon*dx + clat*slon*dy + slat*dz;

        return new float[] {east , north, up, 1};
    }
}
