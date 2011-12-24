package de.hska.ibsys.dto;

import de.hska.ibsys.result.Results;
import de.hska.ibsys.util.Constant;
import java.util.ResourceBundle;

/**
 *
 * @author p0004
 */
public class ResultDTO {
    /**
     * Note: This Class must be generated by the Java Architecture for XML Binding (JAXB).
     *       Please run maven if it does not exist.
     */
    private Results result;
    
    private ResourceBundle bundle = ResourceBundle.getBundle(Constant.CALCULATE_RESOURCE);
    private Integer[] salesOrders = new Integer[3];
    private Integer[][] forcasts = new Integer[3][3];
    private Integer[] selldirectQuantities = new Integer[3];
    private Double[] selldirectPrices = new Double[3];
    private Double[] selldirectPenalties = new Double[3];
    private double stockFactor = Double.valueOf(bundle.getString("factor.stock"));
    private double[] periodFactors = new double[3];
    private int[] setuptimes = new int[14];
    
    public ResultDTO() {
        this.init();
    }

    public ResultDTO(Results result) {
        this.result = result;
        this.init();
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Integer[][] getForcasts() {
        return forcasts;
    }

    public void setForcasts(Integer[][] forcasts) {
        this.forcasts = forcasts;
    }

    public double[] getPeriodFactors() {
        return periodFactors;
    }

    public void setPeriodFactors(double[] periodFactors) {
        this.periodFactors = periodFactors;
    }

    public Results getResult() {
        return result;
    }

    public void setResult(Results result) {
        this.result = result;
    }

    public Integer[] getSalesOrders() {
        return salesOrders;
    }

    public void setSalesOrders(Integer[] salesOrders) {
        this.salesOrders = salesOrders;
    }

    public Double[] getSelldirectPenalties() {
        return selldirectPenalties;
    }

    public void setSelldirectPenalties(Double[] selldirectPenalties) {
        this.selldirectPenalties = selldirectPenalties;
    }

    public Double[] getSelldirectPrices() {
        return selldirectPrices;
    }

    public void setSelldirectPrices(Double[] selldirectPrices) {
        this.selldirectPrices = selldirectPrices;
    }

    public Integer[] getSelldirectQuantities() {
        return selldirectQuantities;
    }

    public void setSelldirectQuantities(Integer[] selldirectQuantities) {
        this.selldirectQuantities = selldirectQuantities;
    }

    public int[] getSetuptimes() {
        return setuptimes;
    }

    public void setSetuptimes(int[] setuptimes) {
        this.setuptimes = setuptimes;
    }

    public double getStockFactor() {
        return stockFactor;
    }

    public void setStockFactor(double stockFactor) {
        this.stockFactor = stockFactor;
    }
    
    private void init() {
        for (int i = 0; i < salesOrders.length; i++) {
            salesOrders[i] = Integer.valueOf(0);
        }
        for (int i = 0; i < forcasts.length; i++) {
            forcasts[i][0] = Integer.valueOf(0);
            forcasts[i][1] = Integer.valueOf(0);
            forcasts[i][2] = Integer.valueOf(0);
        }
        for (int i = 0; i < selldirectPrices.length; i++) {
            selldirectPrices[i] = Double.valueOf(0.0);
        }
        for (int i = 0; i < selldirectQuantities.length; i++) {
            selldirectQuantities[i] = Integer.valueOf(0);
        }
        for (int i = 0; i < selldirectPrices.length; i++) {
            selldirectPrices[i] = Double.valueOf(0.0);
        }
        for (int i = 0; i < selldirectPenalties.length; i++) {
            selldirectPenalties[i] = Double.valueOf(0.0);
        }
        for (int i = 0; i < periodFactors.length; i++) {
            periodFactors[i] = Double.valueOf(bundle.getString("factor.period." + (i+1)));
        }
        for (int i = 0; i < setuptimes.length; i++) {
            if (i != 4) {
                setuptimes[i] = Integer.valueOf(bundle.getString("setuptime." + (i+1)));
            }
        }
    }
}