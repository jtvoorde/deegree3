//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2010 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 Occam Labs UG (haftungsbeschränkt)
 Godesberger Allee 139, 53175 Bonn
 Germany
 http://www.occamlabs.de/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/

package org.deegree.tile;

import org.deegree.geometry.Envelope;

/**
 * Utility methods.
 * 
 * @author <a href="mailto:schmitz@occamlabs.de">Andreas Schmitz</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 31882 $, $Date: 2011-09-15 02:05:04 +0200 (Thu, 15 Sep 2011) $
 */
public class Tiles {

    /**
     * Calculates minx, miny, maxx and maxy indices for the given envelope in the given tile matrix.
     * 
     * @param matrix
     * @param envelope
     * @return null, if envelopes do not intersect
     */
    public static int[] getTileIndexRange( TileDataLevel matrix, Envelope envelope ) {
        TileMatrix md = matrix.getMetadata();

        // calc tile indices
        Envelope menvelope = md.getSpatialMetadata().getEnvelope();
        if ( !menvelope.intersects( envelope ) ) {
            return null;
        }
        double mminx = menvelope.getMin().get0();
        double mminy = menvelope.getMin().get1();
        double minx = envelope.getMin().get0();
        double miny = envelope.getMin().get1();
        double maxx = envelope.getMax().get0();
        double maxy = envelope.getMax().get1();

        int tileminx = (int) Math.floor( ( minx - mminx ) / md.getTileWidth() );
        int tileminy = (int) Math.floor( ( miny - mminy ) / md.getTileHeight() );
        int tilemaxx = (int) Math.floor( ( maxx - mminx ) / md.getTileWidth() );
        int tilemaxy = (int) Math.ceil( ( maxy - mminy ) / md.getTileHeight() );

        // sanitize values
        tileminx = Math.max( 0, tileminx );
        tileminy = Math.max( 0, tileminy );
        tilemaxx = Math.max( 0, tilemaxx );
        tilemaxy = Math.max( 0, tilemaxy );
        tileminx = Math.min( md.getNumTilesX() - 1, tileminx );
        tileminy = Math.min( md.getNumTilesY() - 1, tileminy );
        tilemaxx = Math.min( md.getNumTilesX() - 1, tilemaxx );
        tilemaxy = Math.min( md.getNumTilesY() - 1, tilemaxy );

        int h = tileminy;
        tileminy = md.getNumTilesY() - tilemaxy - 1;
        tilemaxy = md.getNumTilesY() - h - 1;

        return new int[] { tileminx, tileminy, tilemaxx, tilemaxy };
    }
}
