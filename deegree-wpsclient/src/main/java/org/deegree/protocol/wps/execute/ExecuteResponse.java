//$HeadURL$
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
package org.deegree.protocol.wps.execute;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.protocol.wps.execute.input.ExecuteInput;
import org.deegree.protocol.wps.execute.output.DocumentOutputDefinition;
import org.deegree.protocol.wps.execute.output.ExecuteOutput;
import org.deegree.protocol.wps.execute.output.ExecuteStatus;
import org.deegree.protocol.wps.execute.output.OutputDefinition;

/**
 * The <code></code> class TODO add class documentation here.
 * 
 * @author <a href="mailto:ionita@lat-lon.de">Andrei Ionita</a>
 * 
 * @author last edited by: $Author: ionita $
 * 
 * @version $Revision: $, $Date: $
 * 
 */
public class ExecuteResponse {

    private CodeType processId;

    private ExecuteStatus status;

    private ExecuteInput[] inputs;

    private DocumentOutputDefinition[] outputDefs;

    private ExecuteOutput[] outputs;

    public ExecuteResponse( CodeType processId, ExecuteStatus status, ExecuteInput[] inputs,
                            DocumentOutputDefinition[] outputDefs, ExecuteOutput[] outputs ) {
        this.processId = processId;
        this.status = status;
        this.inputs = inputs;
        this.outputDefs = outputDefs;
        this.outputs = outputs;
    }

    public CodeType getProcessId() {
        return processId;
    }

    public ExecuteStatus getStatus() {
        return status;
    }

    public ExecuteInput[] getInputs() {
        return inputs;
    }

    public OutputDefinition[] getDocumentOutputDefinition() {
        return outputDefs;
    }

    public ExecuteOutput[] getOutputs() {
        return outputs;
    }

}
