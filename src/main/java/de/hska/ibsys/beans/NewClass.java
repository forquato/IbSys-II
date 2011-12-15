/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hska.ibsys.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author dennis
 */
@Named
@SessionScoped
public class NewClass implements Serializable {
    private String name = "default";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}   
