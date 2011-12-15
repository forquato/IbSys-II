package de.hska.ibsys;

import de.hska.ibsys.beans.CalculateBean;
import de.hska.ibsys.beans.XMLBean;
import de.hska.ibsys.dto.InputDTO;
import de.hska.ibsys.dto.ResultDTO;
import java.io.*;
import org.custommonkey.xmlunit.Validator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author p0004
 */
public class TestCalculateBean {
    
    private static CalculateBean calculateBean;
    private static InputDTO inputDTO;
    private static ResultDTO resultDTO;
    
    @Before
    public void setUp() throws FileNotFoundException, IOException {
        inputDTO = new InputDTO();
        resultDTO = new ResultDTO();
        
        // Get Test-XML-file from Supply Chain Simulator
        String path = new java.io.File("").getAbsolutePath() 
                + File.separatorChar +  "src" + File.separatorChar + "test"  + File.separatorChar + "resources" 
                + File.separatorChar + "xml" + File.separatorChar + "result.xml";
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte fileContent[] = new byte[(int)file.length()];
        fileInputStream.read(fileContent);
        resultDTO = (ResultDTO)XMLBean.unmarshal(fileContent);
    }
    
    @After
    public void tearDown() {
        inputDTO = null;
        resultDTO = null;
        calculateBean = null;
    }

    @Test
    public void testAll() throws InterruptedException, SAXException {
        // Set sales orders, forecasts and selldirects
        resultDTO.setSelldirectQuantityP1(100);
        resultDTO.setSelldirectPenaltyP1(Double.valueOf(20.0));
        resultDTO.setSelldirectPriceP1(Double.valueOf(190.0));
        resultDTO.setSelldirectQuantityP2(0);
        resultDTO.setSelldirectPenaltyP2(Double.valueOf(0.0));
        resultDTO.setSelldirectPriceP2(Double.valueOf(0.0));
        resultDTO.setSelldirectQuantityP3(0);
        resultDTO.setSelldirectPenaltyP3(Double.valueOf(0.0));
        resultDTO.setSelldirectPriceP3(Double.valueOf(0.0));
        resultDTO.setSalesOrdersP1(200);
        resultDTO.setForcastP1f1(150);
        resultDTO.setForcastP1f2(100);
        resultDTO.setForcastP1f3(150);
        resultDTO.setSalesOrdersP2(50);
        resultDTO.setForcastP2f1(50);
        resultDTO.setForcastP2f2(100);
        resultDTO.setForcastP2f3(100);
        resultDTO.setSalesOrdersP3(150);
        resultDTO.setForcastP3f1(150);
        resultDTO.setForcastP3f2(100);
        resultDTO.setForcastP3f3(150);
        
        // Create a calculation bean for testing input methods
        calculateBean = new CalculateBean(resultDTO);
        
        // Get the calculated information to generate XML
        inputDTO = calculateBean.getInputDTO();
        StringWriter stringWriter = XMLBean.marshal(inputDTO.getInput());
        
        // Validate the XML
        Validator validator = new Validator(stringWriter.getBuffer().toString());
        validator.useXMLSchema(true);
        validator.setJAXP12SchemaSource(new File("").getAbsolutePath() 
                + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "resources" 
                + File.separatorChar + "xsd" + File.separatorChar +  "input.xsd");
        Assert.assertTrue(validator.toString(), validator.isValid());
        
        System.out.println(stringWriter);
    }
}
