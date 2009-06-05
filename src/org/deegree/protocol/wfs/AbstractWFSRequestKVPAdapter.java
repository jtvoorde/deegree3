//$HeadURL: svn+ssh://mschneider@svn.wald.intevation.org/deegree/base/trunk/resources/eclipse/files_template.xml $
/*----------------    FILE HEADER  ------------------------------------------
 This file is part of deegree.
 Copyright (C) 2001-2009 by:
 Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 Contact:

 Andreas Poth
 lat/lon GmbH
 Aennchenstr. 19
 53177 Bonn
 Germany
 E-Mail: poth@lat-lon.de

 Prof. Dr. Klaus Greve
 Department of Geography
 University of Bonn
 Meckenheimer Allee 166
 53115 Bonn
 Germany
 E-Mail: greve@giub.uni-bonn.de
 ---------------------------------------------------------------------------*/

package org.deegree.protocol.wfs;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.deegree.commons.utils.kvp.InvalidParameterValueException;
import org.deegree.protocol.i18n.Messages;

/**
 * Provides utility methods for parsing common constructs found in WFS KVP requests.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: schneider $
 * 
 * @version $Revision: $, $Date: $
 */
public abstract class AbstractWFSRequestKVPAdapter {

    /**
     * Extracts the qualified type names from the <code>TYPENAME</code> parameter.
     * 
     * @param kvpUC
     *            the parameters of the request, normalized
     * @param nsBindings 
     *              namespace bindings, may be null
     * @return qualified type names or null if no  <code>TYPENAME</code> parameter is present
     * @throws InvalidParameterValueException
     *             if the value of the <code>TYPENAME</code> attribute contains a syntactical error or uses unbound
     *             prefices
     */
    protected static QName[] extractTypeNames( Map<String, String> kvpUC, Map<String, String> nsBindings )
                            throws InvalidParameterValueException {

        QName[] typeNames = null;
        String typeNameString = kvpUC.get( "TYPENAME" );
        if ( typeNameString != null ) {
            String[] typeNameStrings = typeNameString.split( "," );
            typeNames = new QName[typeNameStrings.length];
            for ( int i = 0; i < typeNameStrings.length; i++ ) {
                typeNames[i] = qualifyName( typeNameStrings[i], nsBindings );
            }
        }
        return typeNames;
    }

    /**
     * Extracts the namespace bindings from the <code>NAMESPACE</code> parameter.
     * <p>
     * Example:
     * <ul>
     * <li><code>NAMESPACE=xmlns(myns=http://www.someserver.com),xmlns(yourns=http://www.someotherserver.com)</code></li>
     * </ul>
     * <p>
     * The default namespace may also be bound (two variants are supported):
     * <ul>
     * <li><code>NAMESPACE=xmlns(=http://www.someserver.com)</code></li>
     * <li><code>NAMESPACE=xmlns(http://www.someserver.com)</code></li>
     * </ul>
     * 
     * @param kvpUC
     *            the parameters of the request, normalized
     * @return mapping between prefices and namespaces (key: prefix, value: namespace), empty string as a key ('') is
     *         the binding of the default namespace, null is returned if no <code>NAMESPACE</code> parameter is present
     * @throws InvalidParameterValueException
     *             if the value of the NAMESPACE attribute contains a syntactical error
     */
    protected static Map<String, String> extractNamespaceBindings( Map<String, String> kvpUC )
                            throws InvalidParameterValueException {

        Map<String, String> nsContext = null;
        String nsString = kvpUC.get( "NAMESPACE" );
        if ( nsString != null ) {
            nsContext = new HashMap<String, String>();
            String nsDecls[] = nsString.split( "," );
            for ( int i = 0; i < nsDecls.length; i++ ) {
                String nsDecl = nsDecls[i];
                if ( nsDecl.startsWith( "xmlns(" ) && nsDecl.endsWith( ")" ) ) {
                    // 6 is the length of "xmlns("
                    nsDecl = nsDecl.substring( 6, nsDecl.length() - 1 );
                    int assignIdx = nsDecl.indexOf( '=' );
                    String prefix = "";
                    String nsURIString = null;
                    if ( assignIdx != -1 ) {
                        prefix = nsDecl.substring( 0, assignIdx );
                        nsURIString = nsDecl.substring( assignIdx + 1 );
                    } else {
                        nsURIString = nsDecl;
                    }
                    nsContext.put( prefix, nsURIString );
                } else {
                    String msg = Messages.getMessage( "WFS_NAMESPACE_PARAM_INVALID", nsString );
                    throw new InvalidParameterValueException( msg );
                }
            }
        }
        return nsContext;
    }

    /**
     * Transforms a (possibly prefixed) type name into a qualified name using the given namespace bindings.
     * 
     * @param name
     *            possibly prefixed type name
     * @param nsBindings
     *            namespace bindings, may be null
     * @return qualified name
     */
    private static QName qualifyName( String name, Map<String, String> nsBindings ) {
        QName typeName;
        String prefix = "";
        int idx = name.indexOf( ':' );
        if ( idx != -1 ) {
            prefix = name.substring( 0, idx );
            String localName = name.substring( idx + 1 );
            if (nsBindings == null) {
                typeName = new QName( XMLConstants.NULL_NS_URI, localName, prefix );
            } else {
                String nsURI = nsBindings.get( prefix );
                typeName = new QName( nsURI, localName, prefix );
            }
        } else {
            // default namespace prefix ("")
            if ( nsBindings == null ) {
                typeName = new QName( name );
            } else {
                typeName = new QName( nsBindings.get( "" ), name );
            }
        }
        return typeName;
    }
}
