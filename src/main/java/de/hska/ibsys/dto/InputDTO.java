package de.hska.ibsys.dto;

import de.hska.ibsys.input.Input;

/**
 *
 * @author p000p
 */
public class InputDTO {
    
    private Input input;
    
    
    public InputDTO() {
    }
    
    public InputDTO(Input input) {
        this.input = input;
    }
    
    public Input getInput() {
        return input;
    }
    
    public void setInput(Input input) {
        this.input = input;
    }
}
