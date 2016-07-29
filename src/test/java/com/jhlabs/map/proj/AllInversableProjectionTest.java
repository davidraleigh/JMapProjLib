package com.jhlabs.map.proj;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AllInversableProjectionTest {

    private static final double PRECESISSION = 1e-3;

    private final List<Projection> inversableProjections = new ArrayList<>();

    @Before
    public void setUp() {
        for (final String projectionName : ProjectionFactory.getOrderedProjectionNames()) {
            final Projection projection = ProjectionFactory.getNamedProjection(projectionName);
            if (!projection.hasInverse()) {
                System.out.println("INFO: Ignore not inverable projection: " + projection);
                continue;
            }
            inversableProjections.add(projection);
        }
        System.out.println();
        System.out.flush();
    }

    @Test
    public void testProjectionAndInverseAreTheSame() throws Exception {
        for (final Projection projection : inversableProjections) {
            try {
                final Point2D.Double source = new Point2D.Double(0, 1);
                final Point2D.Double latLong = projection.projectInverse(source.getX(), source.getX(), new Point2D.Double());
                final Point2D.Double source2 = projection.project(latLong.getX(), latLong.getY(), new Point2D.Double());

                System.out.println("INFO: Test inverable projection: " + projection);
                checkAndPrintErrorForProjection(projection, source.getX(), source2.getX());
                checkAndPrintErrorForProjection(projection, source.getY(), source2.getY());
            } catch (final Exception e) {
                System.err.println("ERROR: Exception for  " + projection);
                e.printStackTrace(System.err);
            }
        }
    }

    private void checkAndPrintErrorForProjection(final Projection projection, final double expected, final double actual) {
        if (Math.abs(expected - actual) > PRECESISSION) {
            System.err.println("ERROR: Expected " + expected + ", but was " + actual + " for " + projection);
        }
    }
}
