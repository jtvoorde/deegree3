//$HeadURL: svn+ssh://lbuesching@svn.wald.intevation.de/deegree/base/trunk/resources/eclipse/files_template.xml $
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
package org.deegree.services.wps.provider.jrxml;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.deegree.commons.config.DeegreeWorkspace;
import org.deegree.commons.config.ResourceInitException;
import org.deegree.commons.config.ResourceManager;
import org.deegree.commons.xml.XMLAdapter;
import org.deegree.services.wps.provider.ProcessProvider;
import org.deegree.services.wps.provider.ProcessProviderProvider;
import org.deegree.services.wps.provider.jrxml.jaxb.process.JrxmlProcess;
import org.deegree.services.wps.provider.jrxml.jaxb.process.JrxmlProcess.Subreport;
import org.deegree.services.wps.provider.jrxml.jaxb.process.JrxmlProcesses;
import org.deegree.services.wps.provider.jrxml.jaxb.process.Metadata;
import org.deegree.services.wps.provider.jrxml.jaxb.process.Metadata.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link ProcessProviderProvider} for processes encapuslating jrxml files.
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 * @author last edited by: $Author: lyn $
 * 
 * @version $Revision: $, $Date: $
 */
public class JrxmlProcessProviderProvider implements ProcessProviderProvider {

    private static final Logger LOG = LoggerFactory.getLogger( JrxmlProcessProviderProvider.class );

    private static final String CONFIG_NS = "http://www.deegree.org/processes/jrxml";

    private DeegreeWorkspace workspace;

    @Override
    public void init( DeegreeWorkspace workspace ) {
        this.workspace = workspace;
    }

    @Override
    public ProcessProvider create( URL configUrl )
                            throws ResourceInitException {

        LOG.info( "Configuring jrxml process provider using file '" + configUrl + "'." );

        List<JrxmlProcessDescription> processes = new ArrayList<JrxmlProcessDescription>();
        String jrxml = null;
        try {
            JAXBContext jc = JAXBContext.newInstance( "org.deegree.services.wps.provider.jrxml.jaxb.process",
                                                      workspace.getModuleClassLoader() );
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JrxmlProcesses config = (JrxmlProcesses) unmarshaller.unmarshal( configUrl );

            XMLAdapter a = new XMLAdapter( configUrl );

            List<JrxmlProcess> processList = config.getJrxmlProcess();
            for ( JrxmlProcess jrxmlProcess : processList ) {
                jrxml = jrxmlProcess.getJrxml();
                org.deegree.services.wps.provider.jrxml.jaxb.process.ResourceBundle resourceBundle = jrxmlProcess.getResourceBundle();
                URL template = null;
                String description = null;
                Map<String, ParameterDescription> paramDescription = new HashMap<String, ParameterDescription>();
                if ( jrxmlProcess.getMetadata() != null ) {
                    Metadata metadata = jrxmlProcess.getMetadata();
                    if ( metadata.getTemplate() != null ) {
                        template = a.resolve( metadata.getTemplate() );
                    }
                    description = metadata.getDescription();
                    for ( Parameter p : metadata.getParameter() ) {
                        paramDescription.put( p.getId(),
                                              new ParameterDescription( p.getId(), p.getTitle(), p.getDescription() ) );
                    }
                }
                Map<String, URL> subreports = new HashMap<String, URL>();
                for ( Subreport subreport : jrxmlProcess.getSubreport() ) {
                    subreports.put( subreport.getId(), a.resolve( subreport.getValue() ) );
                }
                processes.add( new JrxmlProcessDescription( jrxmlProcess.getId(), a.resolve( jrxml ), description,
                                                            paramDescription, template, subreports, resourceBundle ) );
            }

        } catch ( JAXBException e ) {
            String msg = "Could not parse configuration " + configUrl + ": " + e.getMessage();
            LOG.debug( msg, e );
            throw new ResourceInitException( msg );
        } catch ( MalformedURLException e ) {
            String msg = "Could not resolve path to the jrxml file " + jrxml + ": " + e.getMessage();
            LOG.debug( msg, e );
            throw new ResourceInitException( msg );
        }
        return new JrxmlProcessProvider( processes );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends ResourceManager>[] getDependencies() {
        return new Class[] {};
    }

    @Override
    public String getConfigNamespace() {
        return CONFIG_NS;
    }

    @Override
    public URL getConfigSchema() {
        return JrxmlProcessProviderProvider.class.getResource( "META-INF/schemas/processes/jrxml/0.1.0/jrxmlProcess.xsd" );
    }

}
