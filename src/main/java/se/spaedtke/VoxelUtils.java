package se.spaedtke;

public final class VoxelUtils
{
    public static int[][][] findMinimumContainingCubeoid(int[][][] voxels)
    {
        return pruneX(pruneY(pruneZ(voxels)));
    }

    private static int[][][] pruneX(int[][][] voxels)
    {
        if (voxels == null)
        {
            return null;
        }
        var start = 0;
        var end = voxels.length - 1;
        while (planeIsEmpty(getYZPlane(voxels, start)))
        {
            start++;
        }
        while (end > start && planeIsEmpty(getYZPlane(voxels, end)))
        {
            end--;
        }
        if (start == voxels.length)
        {
            return null;
        }
        var res = new int[(end - start) + 1][voxels[0].length][voxels[0][0].length];
        for (int x = start; x <= end; x++)
        {
            var yzPlane = getYZPlane(voxels, x);
            for (int y = 0; y < voxels[0].length; y++)
            {
                for (int z = 0; z < voxels[0][0].length; z++)
                {
                    res[x - start][y][z] = yzPlane[y][z];
                }
            }
        }
        return res;
    }

    private static int[][][] pruneY(int[][][] voxels)
    {
        if (voxels == null)
        {
            return null;
        }
        var start = 0;
        var end = voxels[0].length - 1;
        while (planeIsEmpty(getXZPlane(voxels, start)))
        {
            start++;
        }
        while (end > start && planeIsEmpty(getXZPlane(voxels, end)))
        {
            end--;
        }
        if (start == voxels[0].length)
        {
            return null;
        }
        var res = new int[voxels.length][(end - start) + 1][voxels[0][0].length];
        for (int y = start; y <= end; y++)
        {
            var xzPlane = getXZPlane(voxels, y);
            for (int x = 0; x < voxels.length; x++)
            {
                for (int z = 0; z < voxels[0][0].length; z++)
                {
                    res[x][y - start][z] = xzPlane[x][z];
                }
            }
        }
        return res;
    }

    private static int[][][] pruneZ(int[][][] voxels)
    {
        if (voxels == null)
        {
            return null;
        }
        var start = 0;
        var end = voxels[0][0].length - 1;
        while (planeIsEmpty(getXYPlane(voxels, start)))
        {
            start++;
        }
        while (end > start && planeIsEmpty(getXYPlane(voxels, end)))
        {
            end--;
        }
        if (start == voxels[0][0].length)
        {
            return null;
        }
        var res = new int[voxels.length][voxels[0].length][(end - start) + 1];
        for (int z = start; z <= end; z++)
        {
            var xyPlane = getXYPlane(voxels, z);
            for (int y = 0; y < voxels[0].length; y++)
            {
                for (int x = 0; x < voxels.length; x++)
                {
                    res[x][y][z - start] = xyPlane[x][y];
                }
            }
        }
        return res;
    }

    private static int[][] getXYPlane(int[][][] voxels, int z)
    {
        var xyPlane = new int[voxels.length][voxels[0].length];
        for (int x = 0; x < voxels.length; x++)
        {
            for (int y = 0; y < voxels[0].length; y++)
            {
                xyPlane[x][y] = voxels[x][y][z];
            }
        }
        return xyPlane;
    }

    private static int[][] getXZPlane(int[][][] voxels, int y)
    {
        var xzPlane = new int[voxels.length][voxels[0][0].length];
        for (int x = 0; x < voxels.length; x++)
        {
            for (int z = 0; z < voxels[0][0].length; z++)
            {
                xzPlane[x][z] = voxels[x][y][z];
            }
        }
        return xzPlane;
    }

    private static int[][] getYZPlane(int[][][] voxels, int x)
    {
        var yzPlane = new int[voxels[0].length][voxels[0][0].length];
        for (int y = 0; y < voxels[0].length; y++)
        {
            for (int z = 0; z < voxels[0][0].length; z++)
            {
                yzPlane[y][z] = voxels[x][y][z];
            }
        }
        return yzPlane;
    }

    private static boolean planeIsEmpty(int[][] plane)
    {
        for (int x = 0; x < plane.length; x++)
        {
            for (int y = 0; y < plane[0].length; y++)
            {
                if (plane[x][y] != 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
