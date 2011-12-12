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
package org.deegree.tile.persistence.geotiff;

import java.io.File;

import org.deegree.geometry.Envelope;
import org.deegree.geometry.GeometryFactory;
import org.deegree.tile.TileMatrix;
import org.deegree.tile.TileMatrixMetadata;

/**
 * <code>GeoTIFFTileMatrix</code>
 * 
 * @author <a href="mailto:schmitz@occamlabs.de">Andreas Schmitz</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 31882 $, $Date: 2011-09-15 02:05:04 +0200 (Thu, 15 Sep 2011) $
 */

public class GeoTIFFTileMatrix implements TileMatrix {

    private final TileMatrixMetadata metadata;

    private final int imageIndex;

    private final GeometryFactory fac = new GeometryFactory();

    private final File file;

    public GeoTIFFTileMatrix( TileMatrixMetadata metadata, File file, int imageIndex ) {
        this.metadata = metadata;
        this.file = file;
        this.imageIndex = imageIndex;
    }

    @Override
    public TileMatrixMetadata getMetadata() {
        return metadata;
    }

    @Override
    public GeoTIFFTile getTile( int x, int y ) {
        double width = metadata.getTileWidth();
        double height = metadata.getTileHeight();
        Envelope env = metadata.getSpatialMetadata().getEnvelope();
        double minx = width * x + env.getMin().get0() - metadata.getResolution() / 2;
        double miny = env.getMax().get1() - height * y + metadata.getResolution() / 2;
        Envelope envelope = fac.createEnvelope( minx, miny, minx + width + metadata.getResolution() / 2,
                                                miny - height - metadata.getResolution() / 2, env.getCoordinateSystem() );
        return new GeoTIFFTile( file, imageIndex, x, y, envelope, metadata.getTileSize() );
    }

}
