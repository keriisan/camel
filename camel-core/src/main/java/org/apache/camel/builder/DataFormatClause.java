/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.builder;

import java.util.zip.Deflater;

import org.w3c.dom.Node;

import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.dataformat.ArtixDSContentType;
import org.apache.camel.model.dataformat.ArtixDSDataFormat;
import org.apache.camel.model.dataformat.BindyDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.model.dataformat.GzipDataFormat;
import org.apache.camel.model.dataformat.HL7DataFormat;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.RssDataFormat;
import org.apache.camel.model.dataformat.SerializationDataFormat;
import org.apache.camel.model.dataformat.StringDataFormat;
import org.apache.camel.model.dataformat.TidyMarkupDataFormat;
import org.apache.camel.model.dataformat.XMLBeansDataFormat;
import org.apache.camel.model.dataformat.XMLSecurityDataFormat;
import org.apache.camel.model.dataformat.XStreamDataFormat;
import org.apache.camel.model.dataformat.ZipDataFormat;

/**
 * An expression for constructing the different possible {@link org.apache.camel.spi.DataFormat}
 * options.
 *
 * @version $Revision$
 */
public class DataFormatClause<T extends ProcessorDefinition> {
    private final T processorType;
    private final Operation operation;

    /**
     * {@link org.apache.camel.spi.DataFormat} operations.
     */
    public enum Operation {
        Marshal, Unmarshal
    }

    public DataFormatClause(T processorType, Operation operation) {
        this.processorType = processorType;
        this.operation = operation;
    }

    /**
     * Uses the
     * <a href="http://camel.apache.org/artix-data-services.html">Artix Data Services</a>
     * data format for dealing with lots of different message formats such as SWIFT etc.
     */
    public T artixDS() {
        return dataFormat(new ArtixDSDataFormat());
    }

    /**
     * Uses the
     * <a href="http://camel.apache.org/artix-data-services.html">Artix Data Services</a>
     * data format with the specified type of ComplexDataObject
     * for marshalling and unmarshalling messages using the dataObject's default Source and Sink.
     */
    public T artixDS(Class<?> dataObjectType) {
        return dataFormat(new ArtixDSDataFormat(dataObjectType));
    }


    /**
     * Uses the
     * <a href="http://camel.apache.org/artix-data-services.html">Artix Data Services</a>
     * data format with the specified type of ComplexDataObject
     * for marshalling and unmarshalling messages using the dataObject's default Source and Sink.
     */
    public T artixDS(Class<?> elementType, ArtixDSContentType contentType) {
        return dataFormat(new ArtixDSDataFormat(elementType, contentType));
    }

    /**
     * Uses the
     * <a href="http://camel.apache.org/artix-data-services.html">Artix Data Services</a>
     * data format with the specified content type
     * for marshalling and unmarshalling messages
     */
    public T artixDS(ArtixDSContentType contentType) {
        return dataFormat(new ArtixDSDataFormat(contentType));
    }

    /**
     * Uses the Bindy data format
     *
     * @param type the type of bindy data format to use
     * @param packages packages to scan for Bindy annotated POJO classes
     */
    public T bindy(BindyType type,  String... packages) {
        BindyDataFormat bindy = new BindyDataFormat();
        bindy.setType(type);
        bindy.setPackages(packages);
        return dataFormat(bindy);
    }

    /**
     * Uses the CSV data format
     */
    public T csv() {
        return dataFormat(new CsvDataFormat());
    }

    /**
     * Uses the HL7 data format
     */
    public T hl7() {
        return dataFormat(new HL7DataFormat());
    }

    /**
     * Uses the JAXB data format
     */
    public T jaxb() {
        return dataFormat(new JaxbDataFormat());
    }

    /**
     * Uses the JAXB data format turning pretty printing on or off
     */
    public T jaxb(boolean prettyPrint) {
        return dataFormat(new JaxbDataFormat(prettyPrint));
    }

    /**
     * Uses the RSS data format
     */
    public T rss() {
        return dataFormat(new RssDataFormat());
    }    
    
    /**
     * Uses the Java Serialization data format
     */
    public T serialization() {
        return dataFormat(new SerializationDataFormat());
    }

    /**
     * Uses the String data format
     */
    public T string() {
        return string(null);
    }

    /**
     * Uses the String data format supporting encoding using given charset
     */
    public T string(String charset) {
        StringDataFormat sdf = new StringDataFormat();
        sdf.setCharset(charset);
        return dataFormat(sdf);
    }

    /**
     * Uses the JAXB data format
     */
    public T xmlBeans() {
        return dataFormat(new XMLBeansDataFormat());
    }

    /**
     * Return WellFormed HTML (an XML Document) either 
     * {@link java.lang.String} or {@link org.w3c.dom.Node}
     */
    public T tidyMarkup(Class<?> dataObjectType) {
        return dataFormat(new TidyMarkupDataFormat(dataObjectType));
    }

    /**
     * Return TidyMarkup in the default format 
     *  as {@link org.w3c.dom.Node}
     */
    public T tidyMarkup() {
        return dataFormat(new TidyMarkupDataFormat(Node.class));
    }

    
    /**
     * Uses the XStream data format
     */
    public T xstream() {
        return dataFormat(new XStreamDataFormat());
    }
    
    /**
     * Uses the JSON data format using the XStream json library
     */
    public T json() {
        return dataFormat(new JsonDataFormat());
    }

    /**
     * Uses the JSON data format
     *
     * @param library the json library to use
     */
    public T json(JsonLibrary library) {
        return dataFormat(new JsonDataFormat(library));
    }

    /**
     * Uses the JSON data format
     *
     * @param type the json type to use
     * @param unmarshalType unmarshal type for json jackson type
     */
    public T json(JsonLibrary type, Class<?> unmarshalType) {
        JsonDataFormat json = new JsonDataFormat(type);
        json.setUnmarshalType(unmarshalType);
        return dataFormat(json);
    }

    /**
     * Uses the XML Security data format
     */
    public T secureXML() {
        XMLSecurityDataFormat xsdf = new XMLSecurityDataFormat();
        return dataFormat(xsdf);
    }

    /**
     * Uses the XML Security data format
     */
    public T secureXML(String secureTag, boolean secureTagContents) {
        XMLSecurityDataFormat xsdf = new XMLSecurityDataFormat(secureTag, secureTagContents);
        return dataFormat(xsdf);
    }

    /**
     * Uses the XML Security data format
     */
    public T secureXML(String secureTag, boolean secureTagContents, String passPhrase) {
        XMLSecurityDataFormat xsdf = new XMLSecurityDataFormat(secureTag, secureTagContents, passPhrase);
        return dataFormat(xsdf);
    }

    /**
     * Uses the XML Security data format
     */
    public T secureXML(String secureTag, boolean secureTagContents, String passPhrase, String xmlCipherAlgorithm) {
        XMLSecurityDataFormat xsdf = new XMLSecurityDataFormat(secureTag, secureTagContents, passPhrase, xmlCipherAlgorithm);
        return dataFormat(xsdf);
    }

    /**
     * Uses the ZIP deflater data format
     */
    public T zip() {
        ZipDataFormat zdf = new ZipDataFormat(Deflater.DEFAULT_COMPRESSION);
        return dataFormat(zdf);
    }

    /**
     * Uses the ZIP deflater data format
     */
    public T zip(int compressionLevel) {
        ZipDataFormat zdf = new ZipDataFormat(compressionLevel);
        return dataFormat(zdf);
    }
    
    /**
     * Uses the GZIP deflater data format
     */
    public T gzip() {
        GzipDataFormat gzdf = new GzipDataFormat();
        return dataFormat(gzdf);
    }

    @SuppressWarnings("unchecked")
    private T dataFormat(DataFormatDefinition dataFormatType) {
        switch (operation) {
        case Unmarshal:
            return (T)processorType.unmarshal(dataFormatType);
        case Marshal:
            return (T)processorType.marshal(dataFormatType);
        default:
            throw new IllegalArgumentException("Unknown DataFormat operation: " + operation);
        }
    }
}
