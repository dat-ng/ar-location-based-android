package ng.dat.ar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.opengl.Matrix;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.helper.LocationHelper;

/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<Location> locations;


    public AROverlayView(Context context) {
        super(context);

        this.context = context;

        //Demo points
        final Location sunWheel = new Location("sunWheel");
        sunWheel.setLatitude(16.0404856);
        sunWheel.setLongitude(108.2262447);

        final Location linhUngPagoda = new Location("linhUngPagoda");
        linhUngPagoda.setLatitude(16.1072989);
        linhUngPagoda.setLongitude(108.2343984);
        locations = new ArrayList<Location>() {{
            add(sunWheel);
            add(linhUngPagoda);
        }};
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int radius = 5;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        for (int i = 0; i < locations.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(locations.get(i));
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();

                canvas.drawCircle(x, y, radius, paint);
            }
        }
    }
}
