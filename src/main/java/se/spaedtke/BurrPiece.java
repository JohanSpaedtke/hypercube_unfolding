package se.spaedtke;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class BurrPiece
{
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";

    private final int[][][] voxels;

    private BurrPiece(int[][][] voxels)
    {
        this.voxels = VoxelUtils.findMinimumContainingCubeoid(voxels);
    }

    public static BurrPiece from(String... burrSpec)
    {
        return from(List.of(burrSpec));
    }

    public static BurrPiece from(List<String> burrSpec)
    {
        var zSize = burrSpec.size();
        var ySize = burrSpec.get(0).split("\\|").length;
        var xSize = burrSpec.get(0).split("\\|")[0].length();
        var voxels = new int[xSize][ySize][zSize];
        for (int z = 0; z < burrSpec.size(); z++)
        {
            var xyPlane = burrSpec.get(z).split("\\|");
            for (int y = 0; y < xyPlane.length; y++)
            {
                var xLine = xyPlane[y];
                for (int x = 0; x < xLine.length(); x++)
                {
                    if (xLine.charAt(x) == '.')
                    {
                        voxels[x][y][z] = 0;
                    }
                    else
                    {
                        voxels[x][y][z] = 1;
                    }
                }
            }
        }
        return new BurrPiece(voxels);
    }

    public static BurrPiece from(Set<Point> points)
    {
        var normalisedPoints = normalise(points);
        var size = normalisedPoints.stream().flatMapToInt(Point::toIntStream).max().orElseThrow();
        var voxels = new int[size + 1][size + 1][size + 1];
        for (int z = 0; z <= size; z++)
        {
            for (int y = 0; y <= size; y++)
            {
                for (int x = 0; x <= size; x++)
                {
                    if (normalisedPoints.contains(Point.from(x, y, z)))
                    {
                        voxels[x][y][z] = 1;
                    }
                    else
                    {
                        voxels[x][y][z] = 0;
                    }
                }
            }
        }
        return new BurrPiece(voxels);
    }

    private static Set<Point> normalise(Set<Point> points)
    {
        var xMin = points.stream().mapToInt(Point::getX).min().orElseThrow();
        var yMin = points.stream().mapToInt(Point::getY).min().orElseThrow();
        var zMin = points.stream().mapToInt(Point::getZ).min().orElseThrow();
        if (xMin >= 0 && yMin >= 0 && zMin >= 0)
        {
            return points;
        }
        else
        {
            var delta = new int[] {xMin < 0 ? -xMin : xMin, yMin < 0 ? -yMin : yMin, zMin < 0 ? -zMin : zMin};
            return points.stream().map(p -> p.translate(delta)).collect(Collectors.toSet());
        }
    }

    public Set<Point> toPoints()
    {
        var points = new HashSet<Point>();
        for (int x = 0; x < voxels.length; x++)
        {
            for (int y = 0; y < voxels[0].length; y++)
            {
                for (int z = 0; z < voxels[0][0].length; z++)
                {
                    if (voxels[x][y][z] == 1)
                    {
                        points.add(Point.from(x, y, z));
                    }
                }
            }
        }
        return points;
    }

    /**
     * Generates the complete burr spec for the piece.
     * Puzzlecad isn't really designed for really large pieces though so at some point you probably won't be able to use this method directly
     * and should instead use {@link BurrPiece#toPuzzlecad()} instead
     *
     * @return a String that is a valid argument to the burr_piece method of puzzlecad
     */
    public String toBurrSpec()
    {
        return "[\"" + _burr_spec().stream()
                .map(xyPlane -> String.join("|", xyPlane))
                .reduce("", (s1, s2) -> s1.equals("") ? s2 : s1 + "\", \"" + s2) + "\"]";
    }

    /**
     * Breaks up your burr piece in to multiple lines of voxels since those are much less taxing on puzzlecad.
     * This comes with some restrictions though. The $auto_layout functionality will obviously no longer work since
     * as far puzzlecad is concerned the piecese are not connected. Also there will be internal beveling on the pieces
     * between the lines for the same reason. A workaround for the latter issue is to either use no bevel or use $unit_beveled=true
     *
     * @return a String describing a number of calls to the burr_piece method of puzzlecad and builtin OpenSCAD methods
     */
    public String toPuzzlecad()
    {
        double burr_scale = 11.15;
        boolean unit_beveled = true;
        var puzzlecad = new StringBuilder();
        puzzlecad.append("$burr_scale = ").append(burr_scale).append(";").append(NEW_LINE);
        puzzlecad.append("$unit_beveled = ").append(unit_beveled).append(";").append(NEW_LINE);
        var spec = _burr_spec();
        for (int z = 0; z < spec.size(); z++)
        {
            var xyPlane = spec.get(z);
            for (int y = 0; y < xyPlane.size(); y++)
            {
                var xLine = xyPlane.get(y);
                var numStartingDots = xLine.indexOf("x");
                puzzlecad.append("translate([").append(numStartingDots).append(" * $burr_scale")
                        .append(",").append(y).append(" * $burr_scale")
                        .append(",").append(z).append(" * $burr_scale")
                        .append("])").append(SPACE)
                        .append("burr_piece(\"").append(xLine).append("\");")
                        .append(NEW_LINE);
            }
        }
        return puzzlecad.toString();
    }

    private List<List<String>> _burr_spec()
    {
        if (voxels == null)
        {
            return List.of();
        }
        var xyPlanes = new ArrayList<List<String>>(voxels[0][0].length);
        for (int z = 0; z < voxels[0][0].length; z++)
        {
            var xyPlane = new ArrayList<String>(voxels[0].length);
            for (int y = 0; y < voxels[0].length; y++)
            {
                var line = new StringBuilder();
                for (int x = 0; x < voxels.length; x++)
                {
                    if (voxels[x][y][z] == 1)
                    {
                        line.append("x");
                    }
                    else
                    {
                        line.append(".");
                    }
                }
                xyPlane.add(line.toString());
            }
            xyPlanes.add(xyPlane);
        }
        return xyPlanes;
    }
}
