package gerrymandering.service;

import com.vividsolutions.jts.geom.Polygon;
import gerrymandering.model.*;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

/**
 * Created by yisuo on 11/12/17.
 */
public interface GeoRenderingService {
    public GeoJson buildGeoJson(List<State> states);

    public GeoJson buildGeoJson(State state);

    public GeoJson buildGeoJson(District district);

    public GeoJson buildGeoJson(SuperDistrict superDistrict);

    public Double getArea(GeoRegion region);

    public Double getArea(List<Boundary> boundaries);

    public Double getArea(Polygon shape);

    public Polygon latLngToCartesian(Polygon polygon);

    public SuperDistrict buildSuperdistrict(Feature fc);

    public List<SuperDistrict> buildSuperdistricts(FeatureCollection fc);
}
