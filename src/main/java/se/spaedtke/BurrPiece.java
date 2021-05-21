package se.spaedtke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class BurrPiece {
    private final String[] layers;

    private BurrPiece(String[] layers) {
        this.layers = compact(layers);
    }

    private String[] compact(String[] layers) {
        var noEmptyLayers = new ArrayList<String>();
        for (int i = 0; i < layers.length; i++) {
            if (layers[i].contains("x")) {
                noEmptyLayers.add(layers[i]);
            }
        }

        int substringIndex = noEmptyLayers.get(0).length();
        while (columnEmptyInAllLayers(noEmptyLayers, substringIndex) && substringIndex > 0) {
            substringIndex--;
        }

        var res = new String[noEmptyLayers.size()];
        for (int i = 0; i < noEmptyLayers.size(); i++) {
            res[i] = layers[i].substring(0, substringIndex);
        }
        return res;
    }

    private boolean columnEmptyInAllLayers(ArrayList<String> noEmptyLayers, int i) {
        for (String s : noEmptyLayers) {
            if (s.charAt(i-1) == 'x') {
                return false;
            }
        }
        return true;
    }

    public static BurrPiece from(int[][] points) {
        return from(Arrays.stream(points).map(Point::from).collect(Collectors.toSet()));
    }

    public static BurrPiece from(Set<Point> points) {
        var normalisedPoints = normalise(points);
        var size = normalisedPoints.stream().flatMapToInt(Point::toIntStream).max().orElseThrow();
        var layers = new String[size + 1];
        for (int z = 0; z <= size; z++) {
            var layer = new String[size + 1];
            for (int y = 0; y <= size; y++) {
                var line = new StringBuilder();
                for (int x = 0; x <= size; x++) {
                    if (normalisedPoints.contains(Point.from(x, y, z))) {
                        line.append("x");
                    } else {
                        line.append(".");
                    }
                }
                layer[y] = line.toString();
            }
            layers[z] = String.join("|", layer);
        }
        return new BurrPiece(layers);
    }

    private static Set<Point> normalise(Set<Point> points) {
        var xMin = points.stream().mapToInt(Point::getX).min().orElseThrow();
        var yMin = points.stream().mapToInt(Point::getY).min().orElseThrow();
        var zMin = points.stream().mapToInt(Point::getZ).min().orElseThrow();
        if (xMin >= 0 && yMin >= 0 && zMin >= 0) {
            return points;
        } else {
            var delta = new int[]{xMin < 0 ? -xMin : xMin, yMin < 0 ? -yMin : yMin, zMin < 0 ? -zMin : zMin};
            return points.stream().map(p -> p.translate(delta)).collect(Collectors.toSet());
        }
    }

    public String toBurrSpec() {
        return "[\"" + String.join("\", \"", layers) + "\"]";
    }
}
