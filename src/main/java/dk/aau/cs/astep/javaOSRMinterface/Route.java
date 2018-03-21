package dk.aau.cs.astep.javaOSRMinterface;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Route {

    private double totalDistance;
    private double totalDuration;
    private ArrayList nodes = new ArrayList<Integer>();
    private ArrayList routeCoordinates = new ArrayList<Point2D.Double>();
    private ArrayList intermediateDurations = new ArrayList<Double>();

    public Route(double totalDistance, double totalDuration, ArrayList<Integer> nodes, ArrayList<Double> intermediateDurations){
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.nodes = nodes;
        this.intermediateDurations = intermediateDurations;
    }

    public Route(double totalDistance, double totalDuration, ArrayList<Integer> nodes, ArrayList<Double> intermediateDurations, ArrayList<Point2D.Double> routeCoordinates){
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.nodes = nodes;
        this.routeCoordinates = routeCoordinates;
        this.intermediateDurations = intermediateDurations;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    public ArrayList<Double> getIntermediateDurations() {
        return intermediateDurations;
    }

}
