package com.jhlabs.map.proj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.Test;

public class MercatorProjectionTest {

    private static final double PRECESISSION = 1e-3;

    private final MercatorProjection projection = new MercatorProjection();

    @Test
    public void testProjectionAndInverseAreTheSame() throws Exception {
        assertTrue(projection.hasInverse());

        final Point2D.Double source = new Point2D.Double(0, 0);
        final Point2D.Double latLong = projection.projectInverse(source.getX(), source.getX(), new Point2D.Double());
        final Point2D.Double source2 = projection.project(latLong.getX(), latLong.getY(), new Point2D.Double());

        assertEquals(source.getX(), source2.getX(), PRECESISSION);
        assertEquals(source.getY(), source2.getY(), PRECESISSION);
    }
}
