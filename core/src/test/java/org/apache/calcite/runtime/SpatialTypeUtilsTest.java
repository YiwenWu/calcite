/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.runtime;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests {@link org.apache.calcite.runtime.SpatialTypeUtilsTest}.
 */
class SpatialTypeUtilsTest {

  @Test void testFromEwkt() {
    Geometry g1 = SpatialTypeUtils.fromEwkt("POINT(1 2)");
    assertThat(g1.getCoordinate().getX(), is(1D));
    assertThat(g1.getCoordinate().getY(), is(2D));

    Geometry g2 = SpatialTypeUtils.fromEwkt("srid:1234;POINT(1 2)");
    assertThat(g2.getSRID(), is(1234));
    assertThat(g2.getCoordinate().getX(), is(1D));
    assertThat(g2.getCoordinate().getY(), is(2D));

    Geometry g3 = SpatialTypeUtils.fromEwkt("GEOMETRYCOLLECTION(\n"
        + "  POLYGON((0 0, 3 -1, 1.5 2, 0 0)),\n"
        + "  POLYGON((2 0, 3 3, 4 2, 2 0)),\n"
        + "  POINT(5 6),\n"
        + "  LINESTRING(1 1, 1 6))");
    assertThat(g3.getSRID(), is(0));
  }

  @Test void testAsEwkt() {
    GeometryFactory gf = new GeometryFactory();
    Geometry g1 = gf.createPoint(new Coordinate(1, 2));
    g1.setSRID(1234);
    assertThat(SpatialTypeUtils.asEwkt(g1), is("srid:1234;POINT (1 2)"));
  }
}
