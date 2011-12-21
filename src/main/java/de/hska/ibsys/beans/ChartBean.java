/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hska.ibsys.beans;

import de.hska.ibsys.dto.InputDTO;
import de.hska.ibsys.input.Order;
import de.hska.ibsys.input.Production;
import de.hska.ibsys.input.Workingtime;
import de.hska.ibsys.util.Constant;
import java.util.ResourceBundle;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author p0004
 */
public class ChartBean {
    
    private InputDTO inputDTO;
    
    ResourceBundle bundle = ResourceBundle.getBundle(Constant.LOCALE_RESOURCES);
    
    public ChartBean(InputDTO inputDTO) {
        this.inputDTO = inputDTO;
    }
    
    public CartesianChartModel getShiftModel() {
        return createShiftModel(inputDTO);
    }
    
    public CartesianChartModel getOvertimeModel() {
        return createOvertimeModel(inputDTO);
    }
    
    public CartesianChartModel getOrderModel() {
        return createOrderModel(inputDTO);
    }
    
    public CartesianChartModel getProductionModel() {
        return createProductionModel(inputDTO);
    }

    private CartesianChartModel createShiftModel(InputDTO inputDTO) {
        CartesianChartModel workingtimeShiftModel = new CartesianChartModel();
        ChartSeries shiftSeries = new ChartSeries();
        String localeShift = bundle.getString("chart.shifts");
        
        if (inputDTO != null && inputDTO.getInput().getWorkingtimelist().getWorkingtime() != null) {
            shiftSeries.setLabel(localeShift);
            for(Workingtime workingtime : inputDTO.getInput().getWorkingtimelist().getWorkingtime()) {
                shiftSeries.set(String.valueOf(workingtime.getStation()), workingtime.getShift());
            }
        } else {
            for(int i = 1; i < 16; i++) {
                if (i != 5) {
                    shiftSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        workingtimeShiftModel.addSeries(shiftSeries);
        
        return workingtimeShiftModel;
    }
    
    private CartesianChartModel createOvertimeModel(InputDTO inputDTO) {
        CartesianChartModel workingtimeOvertimeModel = new CartesianChartModel();
        ChartSeries overtimeSeries = new ChartSeries();
        String localeOvertime = bundle.getString("chart.overtime");
        
        if (inputDTO != null && inputDTO.getInput().getWorkingtimelist().getWorkingtime() != null) {
            overtimeSeries.setLabel(localeOvertime);
            for(Workingtime workingtime : inputDTO.getInput().getWorkingtimelist().getWorkingtime()) {
                overtimeSeries.set(String.valueOf(workingtime.getStation()), workingtime.getOvertime());
            }
        } else {
            for(int i = 1; i < 16; i++) {
                if (i != 5) {
                    overtimeSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        workingtimeOvertimeModel.addSeries(overtimeSeries);
        
        return workingtimeOvertimeModel;
    }
    
    private CartesianChartModel createOrderModel(InputDTO inputDTO) {
        CartesianChartModel orderModel = new CartesianChartModel();
        ChartSeries orderSeries = new ChartSeries();
        String localeOrder = bundle.getString("chart.ordered");
        
        if (inputDTO != null && inputDTO.getInput().getOrderlist() != null) {
            orderSeries.setLabel(localeOrder);
            for(Order order : inputDTO.getInput().getOrderlist().getOrder()) {
                orderSeries.set(String.valueOf(order.getArticle()), order.getQuantity());
            }
        } else {
            for(int i = 1; i < 30; i++) {
                if (i != 5) {
                    orderSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        orderModel.addSeries(orderSeries);
        
        return orderModel;
    }
    
    private CartesianChartModel createProductionModel(InputDTO inputDTO) {
        CartesianChartModel productionModel = new CartesianChartModel();
        ChartSeries productionSeries = new ChartSeries();
        String localeProduction = bundle.getString("chart.produced");
        
        if (inputDTO != null && inputDTO.getInput().getProductionlist() != null) {
            productionSeries.setLabel(localeProduction);
            for(Production production : inputDTO.getInput().getProductionlist().getProduction()) {
                productionSeries.set(String.valueOf(production.getArticle()), production.getQuantity());
            }
        } else {
            for(int i = 1; i < 37; i++) {
                if (i != 5) {
                    productionSeries.set(String.valueOf(i),0);
                }
            }
        }
        
        productionModel.addSeries(productionSeries);
        
        return productionModel;
    }
}
