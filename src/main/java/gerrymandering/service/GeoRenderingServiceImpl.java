package gerrymandering.service;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import gerrymandering.common.CommonConstants;
import gerrymandering.model.*;
import org.springframework.stereotype.Service;
import org.wololo.geojson.*;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yisuo on 11/14/17.
 */
@Service("geoRenderingService")
public class GeoRenderingServiceImpl implements GeoRenderingService {
    private GeoJSONWriter writer = new GeoJSONWriter();
    private GeoJSONReader reader = new GeoJSONReader();
    GeometryFactory factory = new GeometryFactory();

    @Override
    public SuperDistrict buildSuperdistrict(Feature fc){
        SuperDistrict sd = new SuperDistrict();
        Geometry shape = reader.read(fc.getGeometry());
        List<Polygon> polygons = new ArrayList<>();
        if(shape.getGeometryType().equals("Polygon"))
            polygons.add((Polygon)shape);
        else if(shape.getGeometryType().equals("MultiPolygon")){
            Integer numPolygons = shape.getNumGeometries();
            for(int i = 0;i < numPolygons;i++){
                Polygon p = (Polygon)shape.getGeometryN(i);
                polygons.add(p);
            }
        }

        List<Boundary> boundaries = polygons.stream().map(p -> new Boundary(p)).collect(Collectors.toList());
        sd.setBoundaries(boundaries);
        return sd;
    }

    @Override
    public List<SuperDistrict> buildSuperdistricts(FeatureCollection fc) {
        List<Feature> features = Arrays.asList(fc.getFeatures());
        return null;
    }

    @Override
    public GeoJson buildGeoJson(List<State> states) {
        List<Feature> featureCollection =
            states.stream().map(this::buildState).collect(Collectors.toList());
        FeatureCollection result = writer.write(featureCollection);
        return new GeoJson(result);
    }

    @Override
    public GeoJson buildGeoJson(State state) {
        List<District> districts = state.getDistricts();
        List<Feature> featureCollection = new ArrayList<>();
        districts.forEach(district -> {
            featureCollection.add(buildDistrict(district));
        });
        FeatureCollection result = writer.write(featureCollection);
        return new GeoJson(result);
    }

    @Override
    public GeoJson buildGeoJson(District district) {
        List<Feature> featureCollection = new ArrayList<>();
        featureCollection.add(buildDistrict(district));
        FeatureCollection result = writer.write(featureCollection);
        return new GeoJson(result);
    }

    @Override
    public GeoJson buildGeoJson(SuperDistrict superDistrict) {
        return null;
    }

    private Feature buildState(State state){
        Map<String, Object> properties = new HashMap<>();
        properties.put("StateId", state.getStateId());
        properties.put("StateName", state.getStateName());
        properties.put("Area", getArea(state));
        addElectionData(state, properties);
        addCentroid(state, properties);
        return buildFeature(state.getBoundaries(), properties);
    }

    private Feature buildDistrict(District district){
        Map<String, Object> properties = new HashMap<>();
        properties.put("StateId", district.getState().getStateId());
        properties.put("DistrictNo", district.getDistrictNo());
        properties.put("Area", getArea(district));
        addElectionData(district, properties);
        addCentroid(district, properties);
        return buildFeature(district.getBoundaries(), properties);
    }

    private Feature buildFeature(List<Boundary> boundaries, Map<String, Object> properties){
        org.wololo.geojson.Geometry converted = null;
        if(boundaries.size() == CommonConstants.CONTIGUOUS){
            Polygon polygon = boundaries.get(CommonConstants.FIRST_ELEMENT).getShape();
            converted = writer.write(polygon);
        }
        else if(boundaries.size() > CommonConstants.CONTIGUOUS){
            Polygon[] polygons = new Polygon[boundaries.size()];
            boundaries.stream().map(Boundary::getShape).collect(Collectors.toList()).toArray(polygons);
            MultiPolygon multi = new MultiPolygon(polygons, new GeometryFactory());
            converted = writer.write(multi);
        }
        return new Feature(converted, properties);
    }

    private void addElectionData(BipartisanRegion electionRegion, Map<String, Object> properties){
        properties.put("ElectedParty", electionRegion.getElectedParty());
        properties.put("Votes", electionRegion.getVotes());
        properties.put("TotalVotes", electionRegion.getTotalVotes());
        properties.put("TotalPopulation", electionRegion.getTotalPopulation());
        properties.put("Population", electionRegion.getPopulationGroups());
        properties.put("PercentPopulation", electionRegion.getPopulationPercents());
        properties.put("PercentVotes", electionRegion.getPercentVotes());
    }

    private void addCentroid(GeoRegion region, Map<String, Object> properties){
        List<Boundary> boundaries = region.getBoundaries();
        Point mainArea = boundaries
                .stream()
                .max((a, b) -> a.getShape().getArea() > b.getShape().getArea() ? 1 : -1)
                .get()
                .getShape()
                .getCentroid();

        properties.put("CenterX", mainArea.getX());
        properties.put("CenterY", mainArea.getY());
    }

    @Override
    public Double getArea(GeoRegion region){
        return getArea(region.getBoundaries());
    }

    @Override
    public Double getArea(List<Boundary> boundaries){
        return boundaries
                .stream()
                .map(boundary -> boundary.getShape().getCoordinates())
                .map(coordinates -> convertLatLongToCartisian(coordinates))
                .map(coordinates -> factory.createPolygon(coordinates))
                .mapToDouble(polygon -> polygon.getArea())
                .sum();
    }

    @Override
    public Double getArea(Polygon shape){
        Coordinate[] coordinates = convertLatLongToCartisian(shape.getCoordinates());
        return factory.createPolygon(coordinates).getArea();
    }

    @Override
    public Polygon latLngToCartesian(Polygon polygon){
        Coordinate[] cartesian = convertLatLongToCartisian(polygon.getCoordinates());
        return factory.createPolygon(cartesian);
    }

    private Coordinate[] convertLatLongToCartisian(Coordinate[] latlng){
        return Arrays.stream(latlng)
                .map(coordinate ->
                        new Coordinate(coordinate.x * CommonConstants.EARTH_DEGREE_LENGTH
                                * Math.cos(Math.toRadians(coordinate.y)),
                                coordinate.y * CommonConstants.EARTH_DEGREE_LENGTH))
                .toArray(size -> new Coordinate[size]);
    }
}
