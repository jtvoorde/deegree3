//$HeadURL: svn+ssh://mschneider@svn.wald.intevation.org/deegree/deegree3/branches/aionita/deegree-wpsclient/src/main/java/org/deegree/protocol/wps/execute/output/ExecuteOutput.java $
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
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

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.protocol.wps.execute.output;

import org.deegree.commons.tom.ows.CodeType;

/**
 * Base class for output parameters returned by a process execution.
 * 
 * @author <a href="mailto:ionita@lat-lon.de">Andrei Ionita</a>
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 25694 $, $Date: 2010-08-04 21:45:33 +0200 (Mi, 04. Aug 2010) $
 */
public class BBoxOutput extends ExecutionOutput {

    private double[] lower;

    private double[] upper;

    private String crs;

    private int dim;

    public BBoxOutput( CodeType id, double[] lower, double[] upper, String crs ) {
        super( id );
        this.lower = lower;
        this.upper = upper;
        this.dim = lower.length;
        this.crs = crs;
    }

    public double[] getLower() {
        return lower;
    }

    public double[] getUpper() {
        return upper;
    }

    public int getDimension() {
        return dim;
    }

    /**
     * Get coordinate system of the bounding box
     * 
     * @return crs as String
     */
    public String getCrs() {
        return crs;
    }
}