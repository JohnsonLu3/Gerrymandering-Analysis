package gerrymandering.measure;

import com.vividsolutions.jts.geom.Polygon;
import gerrymandering.common.CommonConstants;
import gerrymandering.model.*;
import gerrymandering.service.GeoRenderingService;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.wololo.jts2geojson.GeoJSONReader;

import java.util.Comparator;
import java.util.List;

/**
 * Created by yisuo on 11/7/17.
 */
public class GeoCompactMeasure implements Measure {
    @Autowired
    GeoRenderingService geoRenderingService;
    GeoJSONReader reader = new GeoJSONReader();
    UserService userService;

    public GeoCompactMeasure(UserService userSerivce){
        this.userService = userSerivce;
    }

    @Override
    public MeasureResults runMeasure(MultiDistrictRegion region) {
        Double threshold = -1.0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user == null)
            threshold = CommonConstants.POLSBY_THRESHOLD;
        else{
            if(user.getCompactnessThreshold() != null){
                threshold = user.getCompactnessThreshold();
            }
        }

        GeoCompactResults results = new GeoCompactResults();
        List<Boundary> boundaries = region.getBoundaries();
        Polygon largest = boundaries.stream().map(b -> b.getShape()).max(new Comparator<Polygon>() {
            @Override
            public int compare(Polygon o1, Polygon o2) {
                return geoRenderingService.getArea(o1) > geoRenderingService.getArea(o2) ? 1 : -1;
            }
        }).get();

        Polygon cartesian = geoRenderingService.latLngToCartesian(largest);
        Double g = polsbyPopper(cartesian);

        results.setThreshold(threshold);
        results.setCompactness(g);

        if(g < threshold)
            results.addTestResult(false);
        else
            results.addTestResult(true);
        return results;
    }

    private Double polsbyPopper(Polygon shape){
        Double area = shape.getArea();
        Double perimeter = shape.getLength();
        return 4 * Math.PI * area / Math.pow(perimeter, 2.0);
    }
}
