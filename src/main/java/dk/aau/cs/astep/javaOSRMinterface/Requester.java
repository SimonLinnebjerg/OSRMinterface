package dk.aau.cs.astep.javaOSRMinterface;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Requester {

    private JSONObject jsonNode;
    private Route route;

    public Requester() throws UnirestException {}

    /**
     * Calling methods getJSONObjectRoute and jsonToRoute
     * @param startLocation Startlocation of request
     * @param dropOffLocation Endlocation of request
     * @param geometries Type of geometries. either "geojson" or "polyline"
     * @return returns JSONObject with information about route
     * */
    public Route GetRoute(String geometries, Point2D.Double startLocation, Point2D.Double dropOffLocation) throws UnirestException {

        jsonNode = getJSONObjectRoute(geometries,startLocation,dropOffLocation);
        route = jsonToRoute(jsonNode);
        return route;
    }

    /**
     * Method to retrieve route from OSRM.
     * @param geometries specify if geojson or polyline is requested
     * @param startLocation start location of the request
     * @param dropOffLocation final location of the request
     * @return returns JSONObject with information about route
     * */
    private JSONObject getJSONObjectRoute(String geometries, Point2D.Double startLocation, Point2D.Double dropOffLocation ) throws UnirestException{

        StringBuilder finalRequest = new StringBuilder();
        finalRequest.append("http://db1-astep.cs.aau.dk:5000/route/v1/driving/")
                .append(startLocation.getX() + "," + startLocation.getY())
                .append(";")
                .append(dropOffLocation.getX() + "," + dropOffLocation.getY())
                .append("?annotations=true&geometries=")
                .append(geometries)
                .append("&overview=full");

        HttpResponse<JsonNode> request = Unirest.get(finalRequest.toString())
                .asJson();
        jsonNode = request.getBody().getObject();
        return jsonNode;
    }

    /**
     * Method to extract coordinates, nodes, distance, duration and intermediate durations from JSONObject.
     * @param jsonNode JSONObject containing data about the route
     * @return Route object containing the data extracted
     * */
    private Route jsonToRoute(JSONObject jsonNode) {

        double totalDuration;
        double totalDistance;

        JSONObject route;
        JSONArray legs;
        JSONObject geometry;
        ArrayList nodes = new ArrayList<Integer>();
        ArrayList duration = new ArrayList<Double>();
        ArrayList coordinates = new ArrayList<Point2D[]>();
        JSONArray array = jsonNode.getJSONArray("routes");
        route = array.getJSONObject(0);
        legs = route.getJSONArray("legs");
        geometry = route.getJSONObject("geometry");

        totalDuration = route.getDouble("duration");
        totalDistance = route.getDouble("distance");

        int i;
        JSONArray annotation = legs.getJSONObject(0).getJSONObject("annotation").getJSONArray("duration");
        for (i = 0; i < annotation.length(); i++) {
            duration.add(annotation.get(i).toString());
        }

        JSONArray routeNodes = legs.getJSONObject(0).getJSONObject("annotation").getJSONArray("nodes");
        for (i = 0;i < routeNodes.length(); i++){
            nodes.add(routeNodes.get(i));
        }
        JSONArray geometry1 = geometry.getJSONArray("coordinates");
        for (i = 0; i < geometry1.length(); i++) {
            coordinates.add(geometry1.get(i).toString());
        }

        return new Route(totalDistance,totalDuration, nodes, duration, coordinates );
    }

}
