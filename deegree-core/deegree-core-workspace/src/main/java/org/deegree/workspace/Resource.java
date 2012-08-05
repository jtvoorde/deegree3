//$HeadURL: svn+ssh://svn.wald.intevation.org/deegree/deegree3/trunk/deegree-core/deegree-core-commons/src/main/java/org/deegree/commons/config/Resource.java $
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

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.workspace;

/**
 * A resource of a {@link Workspace}.
 * 
 * @author <a href="mailto:schmitz@lat-lon.de">Andreas Schmitz</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 29972 $, $Date: 2011-03-09 23:50:45 +0100 (Wed, 09 Mar 2011) $
 */
public interface Resource {

    /**
     * Usually called by the {@link ResourceManager} upon workspace startup.
     * 
     * @param workspace
     *            the workspace the resource belongs to, may be null
     * @throws ResourceInitException
     */
    public void init( Workspace workspace )
                            throws ResourceInitException;

    /**
     * Usually called by the {@link ResourceManager} upon workspace shutdown.
     */
    public void destroy();

    /**
     * It is recommended to use the {@link AbstractResource} class to provide an automatic and correct implementation
     * for this method. Failing to provide proper metadata will cause problems when re-initializing resources.
     * 
     * @return the metadata corresponding to this resource, never null
     */
    public ResourceMetadata getMetadata();

}
