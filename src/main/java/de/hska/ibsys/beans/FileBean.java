package de.hska.ibsys.beans;

import de.hska.ibsys.dto.InputDTO;
import de.hska.ibsys.dto.ResultDTO;
import de.hska.ibsys.util.Constant;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 * 
 * @author p0004
 */
@Named
@SessionScoped
public class FileBean implements Serializable {
    
    private InputDTO inputDTO;
    private ResultDTO resultDTO;
    private UploadedFile uploadedFile;

    /**
     * 
     * @return
     */
    public InputDTO getInputDTO() {
        return inputDTO;
    }

    /**
     * 
     * @param inputDTO
     */
    public void setInputDTO(InputDTO inputDTO) {
        this.inputDTO = inputDTO;
    }

    /**
     * 
     * @return
     */
    public ResultDTO getResultDTO() {
        return resultDTO;
    }

    /**
     * 
     * @param resultDTO
     */
    public void setResultDTO(ResultDTO resultDTO) {
        this.resultDTO = resultDTO;
    }
    
    /**
     * 
     * @return
     */
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     * 
     * @param uploadedFile
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    /**
     * Starts the page flow
     * 
     * @throws IOException
     * @throws Throwable
     */
    public void start() throws IOException, Throwable {
        // Clear old values
        init();
        
        // Redirect the user to the upload page
        FacesContext.getCurrentInstance().getExternalContext().redirect(Constant.PAGE_UPLOAD);
    }
    
    /**
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void submitUpload() throws IOException, InterruptedException {        
        if (uploadedFile != null) {
            // Unmarshal the XML-File
            byte[] bytes = uploadedFile.getBytes();
            resultDTO = (ResultDTO)XMLBean.unmarshal(bytes);
        }
        if (resultDTO != null) {
            // Redirect the user to the forecast page
            FacesContext.getCurrentInstance().getExternalContext().redirect(Constant.PAGE_FORECAST);
        } else {
            // Show a message in the browser if the upload faild
            String text = null;
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(Constant.LOCALE_RESOURCES, FacesContext.getCurrentInstance().getViewRoot().getLocale());
                text = bundle.getString(Constant.MESSAGE_UPLOAD_ERROR);
            } catch (Exception ex) {
                Logger.getLogger(XMLBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text));
        }
    }
    
    /**
     * 
     * @throws IOException
     */
    public void submitForcasts() throws IOException {
        //Perform the necessary calculations and write the results into the inputDTO-object
        CalculateBean calculateBean = new CalculateBean(resultDTO);
        // Get the filled input-Object 
        inputDTO = calculateBean.getInputDTO();
        
        // Redirect the user to the download page
        FacesContext.getCurrentInstance().getExternalContext().redirect(Constant.PAGE_DOWNLOAD);
    }
    
    /**
     * 
     * @throws IOException
     * @throws Throwable
     */
    public void submitDownload() throws IOException, Throwable {
        // Start the user download process
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        response.setContentType("application/xml");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + Constant.INPUT_XML_FILE_NAME + "\"");
        try {
            StringWriter stringWriter = XMLBean.marshal(inputDTO.getInput());
            response.getOutputStream().write(stringWriter.toString().getBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            fc.responseComplete();
        } catch(Exception ex) {
            Logger.getLogger(XMLBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Page flow end
     * 
     * @throws IOException
     */
    public void end() throws IOException {
        // Redirect the user to the index page
        FacesContext.getCurrentInstance().getExternalContext().redirect(Constant.PAGE_INDEX);
    }
    
    private void init() {
        uploadedFile = null;
        inputDTO = null;
        resultDTO = null;
    }
}