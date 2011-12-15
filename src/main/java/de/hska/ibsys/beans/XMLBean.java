package de.hska.ibsys.beans;


import de.hska.ibsys.dto.ResultDTO;
import de.hska.ibsys.result.Results;
import de.hska.ibsys.util.Constant;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author p0004
 */
public class XMLBean {
    
    /**
     * Marshals an Object to XML
     * 
     * @param object
     * @return Filled (XML)StringWriter
     */
    public static StringWriter marshal(Object object) {
        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance( object.getClass() ); 
            Marshaller m = context.createMarshaller(); 
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
            m.marshal( object, stringWriter );
            return stringWriter;
        } catch (JAXBException ex) {
            Logger.getLogger(XMLBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Unmarshals the (XML)byteArray to an ResultDTO-Object
     * 
     * @param byteArray
     * @return Filled ResultDTO-Object with period information
     */
    public static ResultDTO unmarshal(byte[] byteArray) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            JAXBContext context = JAXBContext.newInstance(Constant.JAXB_RESULT);
            Unmarshaller um = context.createUnmarshaller();
            resultDTO.setResult((Results)um.unmarshal( new InputStreamReader(bais) ));
        } catch (JAXBException ex) {
            Logger.getLogger(XMLBean.class.getName()).log(Level.SEVERE, null, ex);
            resultDTO = null;
        }
        return resultDTO;
    }
}