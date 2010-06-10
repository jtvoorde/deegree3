package org.deegree.protocol.wps.execute;

public class ComplexData {

    private String mimeType;

    private String encoding;

    private String schema;

    private Object object;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType( String mimeType ) {
        this.mimeType = mimeType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding( String encoding ) {
        this.encoding = encoding;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema( String schema ) {
        this.schema = schema;
    }

    public Object getObject() {
        return object;
    }

    public void setObject( Object object ) {
        this.object = object;
    }

}
