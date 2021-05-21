package se.spaedtke;


import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class BurrPieceTest {

    @Test
    public void canHandlePoint(){
        var piece = BurrPiece.from(Set.of(Point.from(0)));

        Assert.assertEquals("[\"x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleXLine(){
        var piece = BurrPiece.from(Set.of(Point.from(0), Point.from(1)));

        Assert.assertEquals("[\"xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleYLine(){
        var piece = BurrPiece.from(Set.of(Point.from(0,0),Point.from(0,1)));

        Assert.assertEquals("[\"x.|x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleZLine(){
        var piece = BurrPiece.from(Set.of(Point.from(0,0,0),Point.from(0,0,1)));

        Assert.assertEquals("[\"x\", \"x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleXYPlane(){
        var piece = BurrPiece.from(Set.of(
                Point.from(new int[]{0,0,0}),Point.from(new int[]{1,0,0}),
                Point.from(new int[]{0,1,0}),Point.from(new int[]{1,1,0})));

        Assert.assertEquals("[\"xx|xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleXZPlane(){
        var piece = BurrPiece.from(Set.of(
                Point.from(new int[]{0,0,0}),Point.from(new int[]{1,0,0}),
                Point.from(new int[]{0,0,1}),Point.from(new int[]{1,0,1})));

        Assert.assertEquals("[\"xx\", \"xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleYZPlane(){
        var piece = BurrPiece.from(Set.of(
                Point.from(new int[]{0,0,0}),Point.from(new int[]{0,1,0}),
                Point.from(new int[]{0,0,1}),Point.from(new int[]{0,1,1})));

        Assert.assertEquals("[\"x.|x\", \"x.|x\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleCube(){
        var piece = BurrPiece.from(Set.of(
                Point.from(new int[]{0,0,0}),Point.from(new int[]{0,1,0}),
                Point.from(new int[]{0,0,1}),Point.from(new int[]{1,0,0}),
                Point.from(new int[]{1,1,0}),Point.from(new int[]{1,0,1}),
                Point.from(new int[]{0,1,1}),Point.from(new int[]{1,1,1})));

        Assert.assertEquals("[\"xx|xx\", \"xx|xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleNegativeCube(){
        var piece = BurrPiece.from(Set.of(
                Point.from(new int[]{0,0,0}),Point.from(new int[]{0,-1,0}),
                Point.from(new int[]{0,0,-1}),Point.from(new int[]{-1,0,0}),
                Point.from(new int[]{-1,-1,0}),Point.from(new int[]{-1,0,-1}),
                Point.from(new int[]{0,-1,-1}),Point.from(new int[]{-1,-1,-1})));

        Assert.assertEquals("[\"xx|xx\", \"xx|xx\"]", piece.toBurrSpec());
    }

    @Test
    public void canHandleLinePassingThroughOrigin(){
        var piece = BurrPiece.from(Set.of(
                Point.from(new int[]{-2,0,0}),Point.from(new int[]{-1,0,0}),
                Point.from(new int[]{0,0,0}),Point.from(new int[]{1,0,0}),
                Point.from(new int[]{2,0,0})));

        Assert.assertEquals("[\"xxxxx\"]", piece.toBurrSpec());
    }

    @Test
    public void dummy(){
        System.out.println(BurrPiece.from(Constants.UNFOLDINGS[4]).toBurrSpec());
    }
}