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

        Assert.assertEquals("[\"x\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleUncompactedPoint()
    {
        var piece = BurrPiece.from("...|...|...", "...|.x.|...", "...|...|...");

        Assert.assertEquals("[\"x\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleXLine()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0), Point.from(1)));

        Assert.assertEquals("[\"xx\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleYLine()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0, 0), Point.from(0, 1)));

        Assert.assertEquals("[\"x|x\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleZLine()
    {
        var piece = BurrPiece.from(Set.of(Point.from(0, 0, 0), Point.from(0, 0, 1)));

        Assert.assertEquals("[\"x\", \"x\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleXYPlane()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(1, 0, 0),
                Point.from(0, 1, 0), Point.from(1, 1, 0)));

        Assert.assertEquals("[\"xx|xx\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleXZPlane()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(1, 0, 0),
                Point.from(0, 0, 1), Point.from(1, 0, 1)));

        Assert.assertEquals("[\"xx\", \"xx\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleYZPlane()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(0, 1, 0),
                Point.from(0, 0, 1), Point.from(0, 1, 1)));

        Assert.assertEquals("[\"x|x\", \"x|x\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleCube()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(0, 1, 0),
                Point.from(0, 0, 1), Point.from(1, 0, 0),
                Point.from(1, 1, 0), Point.from(1, 0, 1),
                Point.from(0, 1, 1), Point.from(1, 1, 1)));

        Assert.assertEquals("[\"xx|xx\", \"xx|xx\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleNegativeCube()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(0, 0, 0), Point.from(0, -1, 0),
                Point.from(0, 0, -1), Point.from(-1, 0, 0),
                Point.from(-1, -1, 0), Point.from(-1, 0, -1),
                Point.from(0, -1, -1), Point.from(-1, -1, -1)));

        Assert.assertEquals("[\"xx|xx\", \"xx|xx\"]", piece.toPuzzlecad());
    }

    @Test
    public void canHandleLinePassingThroughOrigin()
    {
        var piece = BurrPiece.from(Set.of(
                Point.from(-2, 0, 0), Point.from(-1, 0, 0),
                Point.from(0, 0, 0), Point.from(1, 0, 0),
                Point.from(2, 0, 0)));

        Assert.assertEquals("[\"xxxxx\"]", piece.toPuzzlecad());
    }

    @Test
    public void canRoundTripPositivePoints()
    {
        var points = Set.of(Point.from(0), Point.from(1), Point.from(2),
                Point.from(0, 1), Point.from(0, 2), Point.from(0, 3),
                Point.from(0, 0, 1), Point.from(0, 0, 2), Point.from(0, 0, 3));
        var piece = BurrPiece.from(points);
        System.out.println(piece.toPuzzlecad());
        Assert.assertEquals(points, piece.toPoints());
    }
}