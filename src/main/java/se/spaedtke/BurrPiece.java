package se.spaedtke;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class BurrPiece
{
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

    public String toPuzzlecad()
    {
        return "[\"" + String.join("\", \"", toBurrSpec()) + "\"]";
    }

    private List<String> toBurrSpec()
    {
        if (voxels == null)
        {
            return List.of();
        }
        var xyPlanes = new ArrayList<String>();
        for (int z = 0; z < voxels[0][0].length; z++)
        {
            var xyPlane = new String[voxels[0].length];
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
                xyPlane[y] = line.toString();
            }
            xyPlanes.add(String.join("|", xyPlane));
        }
        return xyPlanes;
    }
}
