package se.spaedtke;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class BurrPieceTest
{

    @Test
    public void canHandlePoint()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0)));

        Assert.assertEquals("[\"x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleUncompactedPoint()
    {
        var piece = BurrPiece.from("...|...|...", "...|.x.|...", "...|...|...");

        Assert.assertEquals("[\"x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleXLine()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0), Point.from(1)));

        Assert.assertEquals("[\"xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleYLine()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0, 0), Point.from(0, 1)));

        Assert.assertEquals("[\"x|x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleZLine()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0, 0, 0), Point.from(0, 0, 1)));

        Assert.assertEquals("[\"x\", \"x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleXYPlane()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(1, 0, 0),
                Point.from(0, 1, 0), Point.from(1, 1, 0)));

        Assert.assertEquals("[\"xx|xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleXZPlane()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(1, 0, 0),
                Point.from(0, 0, 1), Point.from(1, 0, 1)));

        Assert.assertEquals("[\"xx\", \"xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleYZPlane()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(0, 1, 0),
                Point.from(0, 0, 1), Point.from(0, 1, 1)));

        Assert.assertEquals("[\"x|x\", \"x|x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleCube()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(0, 1, 0),
                Point.from(0, 0, 1), Point.from(1, 0, 0),
                Point.from(1, 1, 0), Point.from(1, 0, 1),
                Point.from(0, 1, 1), Point.from(1, 1, 1)));

        Assert.assertEquals("[\"xx|xx\", \"xx|xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleNegativeCube()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(0, -1, 0),
                Point.from(0, 0, -1), Point.from(-1, 0, 0),
                Point.from(-1, -1, 0), Point.from(-1, 0, -1),
                Point.from(0, -1, -1), Point.from(-1, -1, -1)));

        Assert.assertEquals("[\"xx|xx\", \"xx|xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleLinePassingThroughOrigin()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(-2, 0, 0), Point.from(-1, 0, 0),
                Point.from(0, 0, 0), Point.from(1, 0, 0),
                Point.from(2, 0, 0)));

        Assert.assertEquals("[\"xxxxx\"]", piece.toBurrSpec());
    }

    @Test
    public void canRoundTripPositivePoints()
    {
        var points = Set.of(Point.from(0), Point.from(1), Point.from(2),
                Point.from(0, 1), Point.from(0, 2), Point.from(0, 3),
                Point.from(0, 0, 1), Point.from(0, 0, 2), Point.from(0, 0, 3));
        var piece = BurrPiece.from(points);
        System.out.println(piece.toBurrSpec());
        Assert.assertEquals(points, piece.toPoints());
    }

    @Test
    public void dummy()
    {
        var cubeSize = 10;
        var points = new HashSet<Point>();
        for (int x = 0; x < cubeSize; x++)
        {
            for (int y = 0; y < cubeSize; y++)
            {
                for (int z = 0; z < cubeSize; z++)
                {
                    if (Math.random() < 0.5)
                    {
                        points.add(Point.from(x, y, z));
                    }
                }
            }
        }
        System.out.println(BurrPiece.from(points).toPuzzlecad());
    }

    @Test
    public void diagonal(){
        var cubeSize = 16;
        var points = new HashSet<Point>();
        for (int x = 0; x < cubeSize; x++)
        {
            for (int y = 0; y < cubeSize; y++)
            {
                for (int z = 0; z < cubeSize; z++)
                {
                    if (x==y&&y==z)
                    {
                        points.add(Point.from(x, y, z));
                    }
                }
            }
        }
        System.out.println(BurrPiece.from(points).toBurrSpec());
    }
}