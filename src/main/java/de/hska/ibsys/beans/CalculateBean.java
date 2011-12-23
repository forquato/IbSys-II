package de.hska.ibsys.beans;

import de.hska.ibsys.dto.InputDTO;
import de.hska.ibsys.dto.ResultDTO;
import de.hska.ibsys.input.*;
import de.hska.ibsys.util.Constant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author p0004
 */
// TODO: Keine Extraschichten oder Überstunden anordnen, wenn kein Material zur bearbeitung bereit stehen kann
// TODO: Losgrößensplitting einfuehren
public class CalculateBean {
    
    private ResultDTO resultDTO;
    private InputDTO inputDTO;
    private Input input = new Input();
    private boolean next = true;
    private ResourceBundle bundle = ResourceBundle.getBundle(Constant.CALCULATE_RESOURCE);
    
    /**
     * Constructor gets information from resultDTO parameter and calls all necessary methods
     * 
     * @param resultDTO
     */
    public CalculateBean(ResultDTO resultDTO) {
        this.resultDTO = resultDTO;
        
        setQualitycontrol();
        setSellwishAndSelldirect();
        setOrderlist();
        setProductionlist();
        setWorkingtimelist();
        
        inputDTO = new InputDTO();
        inputDTO.setInput(input);
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
     * @return
     */
    public InputDTO getInputDTO() {        
        return inputDTO;
    }
    
    // =========================================================================
    // INPUT METHODS
    // =========================================================================
        
    private void setQualitycontrol() {
        Qualitycontrol qualitycontrol = new Qualitycontrol();
        qualitycontrol.setType("no");
        qualitycontrol.setLosequantity(BigInteger.valueOf(0));
        qualitycontrol.setDelay(BigInteger.valueOf(0));
        
        input.setQualitycontrol(qualitycontrol);
    }
    
    private void setSellwishAndSelldirect() {
        // Set sellwishes for P1, P2, P3
        Item itemP1 = new Item();
        Item itemP2 = new Item();
        Item itemP3 = new Item();
        
        itemP1.setArticle(BigInteger.valueOf(1));
        itemP2.setArticle(BigInteger.valueOf(2));
        itemP3.setArticle(BigInteger.valueOf(3));
        
        itemP1.setQuantity(BigInteger.valueOf(resultDTO.getSalesOrdersP1()));
        itemP2.setQuantity(BigInteger.valueOf(resultDTO.getSalesOrdersP2()));
        itemP3.setQuantity(BigInteger.valueOf(resultDTO.getSalesOrdersP3()));
        
        Sellwish sellwish = new Sellwish();
        sellwish.getItem().add(itemP1);
        sellwish.getItem().add(itemP2);
        sellwish.getItem().add(itemP3);
        
        input.setSellwish(sellwish);
        
        // Set selldirects for P1, P2, P3
        Item itemSelldirectP1 = new Item();
        Item itemSelldirectP2 = new Item();
        Item itemSelldirectP3 = new Item();
        
        itemSelldirectP1.setArticle(BigInteger.valueOf(1));
        itemSelldirectP1.setQuantity(BigInteger.valueOf(resultDTO.getSelldirectQuantityP1()));
        itemSelldirectP1.setPrice(BigDecimal.valueOf(resultDTO.getSelldirectPriceP1()));
        itemSelldirectP1.setPenalty(BigDecimal.valueOf(resultDTO.getSelldirectPenaltyP1()));
        resultDTO.setSalesOrdersP1(resultDTO.getSalesOrdersP1() + resultDTO.getSelldirectQuantityP1());
        
        itemSelldirectP2.setArticle(BigInteger.valueOf(2));
        itemSelldirectP2.setQuantity(BigInteger.valueOf(resultDTO.getSelldirectQuantityP2()));
        itemSelldirectP2.setPrice(BigDecimal.valueOf(resultDTO.getSelldirectPriceP2()));
        itemSelldirectP2.setPenalty(BigDecimal.valueOf(resultDTO.getSelldirectPenaltyP2()));
        resultDTO.setSalesOrdersP2(resultDTO.getSalesOrdersP2() + resultDTO.getSelldirectQuantityP2());
        
        itemSelldirectP3.setArticle(BigInteger.valueOf(3));
        itemSelldirectP3.setQuantity(BigInteger.valueOf(resultDTO.getSelldirectQuantityP3()));
        itemSelldirectP3.setPrice(BigDecimal.valueOf(resultDTO.getSelldirectPriceP3()));
        itemSelldirectP3.setPenalty(BigDecimal.valueOf(resultDTO.getSelldirectPenaltyP3()));
        resultDTO.setSalesOrdersP3(resultDTO.getSalesOrdersP3() + resultDTO.getSelldirectQuantityP3());
        
        Selldirect selldirect = new Selldirect();
        selldirect.getItem().add(itemSelldirectP1);
        selldirect.getItem().add(itemSelldirectP2);
        selldirect.getItem().add(itemSelldirectP3);
        
        input.setSelldirect(selldirect);
    }
    
    private void setOrderlist() {
        Orderlist orderlist = new Orderlist();
        
        // Information about each item
        // itemNo, deliverytime, deviation, consumption P1, consumption P2, consumption P3, discount quantity
        List<double[]> orderInfoList = new ArrayList<double[]>();
        orderInfoList.add(new double[]{21,1.8,0.4,1,0,0,300});
        orderInfoList.add(new double[]{22,1.7,0.4,0,1,0,300});
        orderInfoList.add(new double[]{23,1.2,0.2,0,0,1,300});
        orderInfoList.add(new double[]{24,3.2,0.3,7,7,7,6100});
        orderInfoList.add(new double[]{25,0.9,0.2,4,4,4,3600});
        orderInfoList.add(new double[]{27,0.9,0.2,2,2,2,1800});
        orderInfoList.add(new double[]{28,1.7,0.4,4,5,6,4500});
        orderInfoList.add(new double[]{32,2.1,0.5,3,3,3,2700});
        orderInfoList.add(new double[]{33,1.9,0.5,0,0,2,900});
        orderInfoList.add(new double[]{34,1.6,0.3,0,0,72,22000});
        orderInfoList.add(new double[]{35,2.2,0.4,4,4,4,3600});
        orderInfoList.add(new double[]{36,1.2,0.1,1,1,1,900});
        orderInfoList.add(new double[]{37,1.5,0.3,1,1,1,900});
        orderInfoList.add(new double[]{38,1.7,0.4,1,1,1,300});
        orderInfoList.add(new double[]{39,1.5,0.3,2,2,2,1800});
        orderInfoList.add(new double[]{40,1.7,0.2,1,1,1,900});
        orderInfoList.add(new double[]{41,0.9,0.2,1,1,1,900});
        orderInfoList.add(new double[]{42,1.2,0.3,2,2,2,1800});
        orderInfoList.add(new double[]{43,2.0,0.5,1,1,1,2700});
        orderInfoList.add(new double[]{44,1.0,0.2,3,3,3,900});
        orderInfoList.add(new double[]{45,1.7,0.3,1,1,1,900});
        orderInfoList.add(new double[]{46,0.9,0.3,1,1,1,900});
        orderInfoList.add(new double[]{47,1.1,0.1,1,1,1,900});
        orderInfoList.add(new double[]{48,1.9,0.2,2,2,2,1800});
        orderInfoList.add(new double[]{52,1.6,0.4,2,0,0,600});
        orderInfoList.add(new double[]{53,1.6,0.2,72,0,0,22000});
        orderInfoList.add(new double[]{57,1.7,0.3,0,2,0,600});
        orderInfoList.add(new double[]{58,1.6,0.5,0,72,0,22000});
        orderInfoList.add(new double[]{59,0.7,0.2,2,2,2,1800});
        
        for (double[] orderInfo : orderInfoList) {
            int period0 = (int)orderInfo[3] * resultDTO.getSalesOrdersP1() + (int)orderInfo[4] * resultDTO.getSalesOrdersP2() + (int)orderInfo[5] * resultDTO.getSalesOrdersP3();
            int period1 = (int)orderInfo[3] * resultDTO.getForcastP1f1() + (int)orderInfo[4] * resultDTO.getForcastP2f1() + (int)orderInfo[5] * resultDTO.getForcastP3f1();
            int period2 = (int)orderInfo[3] * resultDTO.getForcastP1f2() + (int)orderInfo[4] * resultDTO.getForcastP2f2() + (int)orderInfo[5] * resultDTO.getForcastP3f2();
            int period3 = (int)orderInfo[3] * resultDTO.getForcastP1f3() + (int)orderInfo[4] * resultDTO.getForcastP2f3() + (int)orderInfo[5] * resultDTO.getForcastP3f3();
            
            int warehousestock = resultDTO.getResult().getWarehousestock().getArticle().get((int)orderInfo[0]-1).getAmount().intValue();
            int futureinwardstockmovement = 0;
            if (resultDTO.getResult().getFutureinwardstockmovement().getOrder() == null) {
                futureinwardstockmovement = resultDTO.getResult().getFutureinwardstockmovement().getOrder().get((int)orderInfo[0]).getAmount().intValue();
            }
            Order order = new Order();
            if (period0 >= (warehousestock + futureinwardstockmovement)) {
                order.setArticle(BigInteger.valueOf(((int)orderInfo[0])));
                if ((warehousestock + futureinwardstockmovement + orderInfo[6]) <= period0) {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6] + period0)));
                } else {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6])));
                }
                order.setModus(BigInteger.valueOf(4));
                orderlist.getOrder().add(order);
            } else if (((period0 + period1) >= (warehousestock + futureinwardstockmovement)) && ((orderInfo[1] + orderInfo[2]) >= 1.0)) {
                order.setArticle(BigInteger.valueOf(((int)orderInfo[0])));
                if ((warehousestock + futureinwardstockmovement + orderInfo[6]) <= (period0 + period1)) {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6] + period0 + period1)));
                } else {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6])));
                }
                if ((orderInfo[1] + orderInfo[2]) >= 2) {
                    order.setModus(BigInteger.valueOf(4));
                } else {
                    order.setModus(BigInteger.valueOf(5));
                }
                orderlist.getOrder().add(order);
            } else if (((period0 + period1 + period2) >= (warehousestock + futureinwardstockmovement)) && ((orderInfo[1] + orderInfo[2]) >= 2.0)) {
                order.setArticle(BigInteger.valueOf(((int)orderInfo[0])));
                if ((warehousestock + futureinwardstockmovement + orderInfo[6]) <= (period0 + period1 + period2)) {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6] + period0 + period1 + period2)));
                } else {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6])));
                }
                if ((orderInfo[1] + orderInfo[2]) >= 3) {
                    order.setModus(BigInteger.valueOf(4));
                } else {
                    order.setModus(BigInteger.valueOf(5));
                }
                orderlist.getOrder().add(order);
            } else if (((period0 + period1 + period2 + period3) >= (warehousestock + futureinwardstockmovement)) && ((orderInfo[1] + orderInfo[2]) >= 3.0)) {
                order.setArticle(BigInteger.valueOf(((int)orderInfo[0])));
                if ((warehousestock + futureinwardstockmovement + orderInfo[6]) <= (period0 + period1 + period2 + period3)) {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6] + period0 + period1 + period2 + period3)));
                } else {
                    order.setQuantity(BigInteger.valueOf(((int)orderInfo[6])));
                }
                if ((orderInfo[1] + orderInfo[2]) >= 4) {
                    order.setModus(BigInteger.valueOf(4));
                } else {
                    order.setModus(BigInteger.valueOf(5));
                }
                orderlist.getOrder().add(order);
            }
        }
        
        input.setOrderlist(orderlist);
    }
    
    private void setProductionlist() {
        Productionlist productionlist = new Productionlist();
        
        // Stock of P1, P2 and P3 at the end of period
        int[] plannedStock = planPStock();
        
        // Calculate P1, P2, P3 waitingorders and next production orders for placement and further operations
        int[][] P1_P2_P3 = new int[3][2];
        int[] waitingOrdersP1 = calculateWaitingOrders(1);
        int nextProdOrdersP1 = planNextProductionOrders(resultDTO.getSalesOrdersP1(),new int[]{0,0}, plannedStock[0], 1, waitingOrdersP1);
        P1_P2_P3[0][0] = 1;
        P1_P2_P3[0][1] = nextProdOrdersP1;
        int[] waitingOrdersP2 = calculateWaitingOrders(2);
        int nextProdOrdersP2 = planNextProductionOrders(resultDTO.getSalesOrdersP2(),new int[]{0,0}, plannedStock[1], 2, waitingOrdersP2);
        P1_P2_P3[1][0] = 2;
        P1_P2_P3[1][1] = nextProdOrdersP2;        
        int[] waitingOrdersP3 = calculateWaitingOrders(3);
        int nextProdOrdersP3 = planNextProductionOrders(resultDTO.getSalesOrdersP3(),new int[]{0,0}, plannedStock[2], 3, waitingOrdersP3);
        P1_P2_P3[2][0] = 3;
        P1_P2_P3[2][1] = nextProdOrdersP3;
        
        // Calculate E51, E56, E31 waitingorders and next production orders for placement and further operations
        int[][] E51_E56_E31 = new int[3][2];
        int waitingOrdersE51[] = calculateWaitingOrders(51);
        int nextProdOrdersE51 = planNextProductionOrders(nextProdOrdersP1, waitingOrdersP1, plannedStock[0], 51, waitingOrdersE51);
        E51_E56_E31[0][0] = 51;
        E51_E56_E31[0][1] = nextProdOrdersE51;
        int waitingOrdersE56[] = calculateWaitingOrders(56);
        int nextProdOrdersE56 = planNextProductionOrders(nextProdOrdersP2, waitingOrdersP2, plannedStock[1], 56, waitingOrdersE56);
        E51_E56_E31[1][0] = 56;
        E51_E56_E31[1][1] = nextProdOrdersE56;
        int waitingOrdersE31[] = calculateWaitingOrders(31);
        int nextProdOrdersE31 = planNextProductionOrders(nextProdOrdersP3, waitingOrdersP3, plannedStock[2], 31, waitingOrdersE31);
        E51_E56_E31[2][0] = 31;
        E51_E56_E31[2][1] = nextProdOrdersE31;
        
        // Calculate E26, E16, E17 (multiple used articles) waitingorders and next production orders for placement and further operations
        int[][] E26_E16_E17 = new int[3][2];
        int waitingOrdersE26[] = calculateWaitingOrders(26);
        int nextProdOrdersE26 = planNextProductionOrders(nextProdOrdersP1, waitingOrdersP1, plannedStock[0], 26, waitingOrdersE26);
        nextProdOrdersE26 += planNextProductionOrders(nextProdOrdersP2, waitingOrdersP2, plannedStock[1], 26, waitingOrdersE26);
        nextProdOrdersE26 += planNextProductionOrders(nextProdOrdersP3, waitingOrdersP3, plannedStock[2], 26, waitingOrdersE26);
        nextProdOrdersE26 += 2*resultDTO.getResult().getWarehousestock().getArticle().get(25).getAmount().intValue();
        E26_E16_E17[0][0] = 26;
        E26_E16_E17[0][1] = nextProdOrdersE26;
        int waitingOrdersE16[] = calculateWaitingOrders(16);
        int nextProdOrdersE16 = planNextProductionOrders(nextProdOrdersE51, waitingOrdersE51, plannedStock[0], 16, waitingOrdersE16);
        nextProdOrdersE16 += planNextProductionOrders(nextProdOrdersE56, waitingOrdersE56, plannedStock[1], 16, waitingOrdersE16);
        nextProdOrdersE16 += planNextProductionOrders(nextProdOrdersE31, waitingOrdersE31, plannedStock[2], 16, waitingOrdersE16);
        nextProdOrdersE16 += 2*resultDTO.getResult().getWarehousestock().getArticle().get(15).getAmount().intValue();
        E26_E16_E17[1][0] = 16;
        E26_E16_E17[1][1] = nextProdOrdersE16;
        int waitingOrdersE17[] = calculateWaitingOrders(17);
        int nextProdOrdersE17 = planNextProductionOrders(nextProdOrdersE51, waitingOrdersE51, plannedStock[0], 17, waitingOrdersE17);
        nextProdOrdersE17 += planNextProductionOrders(nextProdOrdersE56, waitingOrdersE56, plannedStock[1], 17, waitingOrdersE17);
        nextProdOrdersE17 += planNextProductionOrders(nextProdOrdersE31, waitingOrdersE31, plannedStock[2], 17, waitingOrdersE17);
        nextProdOrdersE17 += 2*resultDTO.getResult().getWarehousestock().getArticle().get(16).getAmount().intValue();
        E26_E16_E17[2][0] = 17;
        E26_E16_E17[2][1] = nextProdOrdersE17;
        
        // Calculate E50, E55, E30 (multiple used articles) waitingorders and next production orders for placement and further operations
        int[][] E50_E55_E30 = new int[3][2];
        int waitingOrdersE50[] = calculateWaitingOrders(50);
        int nextProdOrdersE50 = planNextProductionOrders(nextProdOrdersE51, waitingOrdersE51, plannedStock[0], 50, waitingOrdersE50);
        E50_E55_E30[0][0] = 50;
        E50_E55_E30[0][1] = nextProdOrdersE50;
        int waitingOrdersE55[] = calculateWaitingOrders(55);
        int nextProdOrdersE55 = planNextProductionOrders(nextProdOrdersE56, waitingOrdersE56, plannedStock[1], 55, waitingOrdersE55);
        E50_E55_E30[1][0]=55;
        E50_E55_E30[1][1] = nextProdOrdersE55;
        int waitingOrdersE30[] = calculateWaitingOrders(30);
        int nextProdOrdersE30 = planNextProductionOrders(nextProdOrdersE31, waitingOrdersE31, plannedStock[2], 30, waitingOrdersE30);
        E50_E55_E30[2][0] = 30;
        E50_E55_E30[2][1] = nextProdOrdersE30;        
        
        // Calculate E4, E10, E49 waitingorders and next production orders for placement and further operations
        int[][] E4_E10_E49 = new int[3][2];
        int waitingOrdersE4[] = calculateWaitingOrders(4);
        int nextProdOrdersE4 = planNextProductionOrders(nextProdOrdersE50, waitingOrdersE50, plannedStock[0], 4, waitingOrdersE4);
        E4_E10_E49[0][0] = 4;
        E4_E10_E49[0][1] = nextProdOrdersE4;
        int waitingOrdersE10[] = calculateWaitingOrders(10);
        int nextProdOrdersE10 = planNextProductionOrders(nextProdOrdersE50, waitingOrdersE50, plannedStock[1], 10, waitingOrdersE10);
        E4_E10_E49[1][0] = 10;
        E4_E10_E49[1][1] = nextProdOrdersE10;
        int waitingOrdersE49[] = calculateWaitingOrders(49);
        int nextProdOrdersE49 = planNextProductionOrders(nextProdOrdersE50, waitingOrdersE50, plannedStock[2], 49, waitingOrdersE49);
        E4_E10_E49[2][0] = 49;
        E4_E10_E49[2][1] = nextProdOrdersE49;
        
        // Calculate E5, E11, E54 waitingorders and next production orders for placement and further operations
        int[][] E5_E11_E54 = new int[3][2];
        int waitingOrdersE5[] = calculateWaitingOrders(5);
        int nextProdOrdersE5 = planNextProductionOrders(nextProdOrdersE55, waitingOrdersE55, plannedStock[0], 5, waitingOrdersE5);
        E5_E11_E54[0][0] = 5;
        E5_E11_E54[0][1] = nextProdOrdersE5;
        int waitingOrdersE11[] = calculateWaitingOrders(11);
        int nextProdOrdersE11 = planNextProductionOrders(nextProdOrdersE55, waitingOrdersE55, plannedStock[1], 11, waitingOrdersE11);
        E5_E11_E54[1][0] = 11;
        E5_E11_E54[1][1] = nextProdOrdersE11;
        int waitingOrdersE54[] = calculateWaitingOrders(54);
        int nextProdOrdersE54 = planNextProductionOrders(nextProdOrdersE55, waitingOrdersE55, plannedStock[2], 54, waitingOrdersE54);
        E5_E11_E54[2][0] = 54;
        E5_E11_E54[2][1] = nextProdOrdersE54;
        
        // Calculate E6, E12, E29 waitingorders and next production orders for placement and further operations
        int[][] E6_E12_E29 = new int[3][2];
        int waitingOrdersE6[] = calculateWaitingOrders(6);
        int nextProdOrdersE6 = planNextProductionOrders(nextProdOrdersE30, waitingOrdersE30, plannedStock[0], 6, waitingOrdersE6);
        E6_E12_E29[0][0] = 6;
        E6_E12_E29[0][1] = nextProdOrdersE6;
        int waitingOrdersE12[] = calculateWaitingOrders(12);
        int nextProdOrdersE12 = planNextProductionOrders(nextProdOrdersE30, waitingOrdersE30, plannedStock[1], 12, waitingOrdersE12);
        E6_E12_E29[1][0] = 12;
        E6_E12_E29[1][1] = nextProdOrdersE12;
        int waitingOrdersE29[] = calculateWaitingOrders(29);
        int nextProdOrdersE29 = planNextProductionOrders(nextProdOrdersE30, waitingOrdersE30, plannedStock[2], 29, waitingOrdersE29);
        E6_E12_E29[2][0] = 29;
        E6_E12_E29[2][1] = nextProdOrdersE29;
        
        // Calculate E7, E13, E18 waitingorders and next production orders for placement
        int[][] E7_E13_E18 = new int[3][2];
        int waitingOrdersE7[] = calculateWaitingOrders(7);
        int nextProdOrdersE7 = planNextProductionOrders(nextProdOrdersE49, waitingOrdersE49, plannedStock[0], 7, waitingOrdersE7);
        E7_E13_E18[0][0] = 7;
        E7_E13_E18[0][1] = nextProdOrdersE7;
        int waitingOrdersE13[] = calculateWaitingOrders(13);
        int nextProdOrdersE13 = planNextProductionOrders(nextProdOrdersE49, waitingOrdersE49, plannedStock[1], 4, waitingOrdersE13);
        E7_E13_E18[1][0] = 13;
        E7_E13_E18[1][1] = nextProdOrdersE13;
        int waitingOrdersE18[] = calculateWaitingOrders(18);
        int nextProdOrdersE18 = planNextProductionOrders(nextProdOrdersE49, waitingOrdersE49, plannedStock[2], 18, waitingOrdersE18);
        E7_E13_E18[2][0] = 18;
        E7_E13_E18[2][1] = nextProdOrdersE18;
        
        // Calculate E8, E14, E19 waitingorders and next production orders for placement
        int[][] E8_E14_E19 = new int[3][2];
        int waitingOrdersE8[] = calculateWaitingOrders(8);
        int nextProdOrdersE8 = planNextProductionOrders(nextProdOrdersE55, waitingOrdersE54, plannedStock[0], 8, waitingOrdersE8);
        E8_E14_E19[0][0] = 8;
        E8_E14_E19[0][1] = nextProdOrdersE8;
        int waitingOrdersE14[] = calculateWaitingOrders(14);
        int nextProdOrdersE14 = planNextProductionOrders(nextProdOrdersE55, waitingOrdersE54, plannedStock[1], 14, waitingOrdersE14);
        E8_E14_E19[1][0] = 14;
        E8_E14_E19[1][1] = nextProdOrdersE14;
        int waitingOrdersE19[] = calculateWaitingOrders(19);
        int nextProdOrdersE19 = planNextProductionOrders(nextProdOrdersE55, waitingOrdersE54, plannedStock[2], 19, waitingOrdersE19);
        E8_E14_E19[2][0] = 19;
        E8_E14_E19[2][1] = nextProdOrdersE19;
        
        // Calculate E9, E15, E20 waitingorders and next production orders for placement
        int[][] E9_E15_E20 = new int[3][2];
        int waitingOrdersE9[] = calculateWaitingOrders(9);
        int nextProdOrdersE9 = planNextProductionOrders(nextProdOrdersE30, waitingOrdersE29, plannedStock[0], 9, waitingOrdersE9);
        E9_E15_E20[0][0] = 9;
        E9_E15_E20[0][1] = nextProdOrdersE9;
        int waitingOrdersE15[] = calculateWaitingOrders(15);
        int nextProdOrdersE15 = planNextProductionOrders(nextProdOrdersE30, waitingOrdersE29, plannedStock[1], 15, waitingOrdersE15);
        E9_E15_E20[1][0] = 15;
        E9_E15_E20[1][1] = nextProdOrdersE15;
        int waitingOrdersE20[] = calculateWaitingOrders(20);
        int nextProdOrdersE20 = planNextProductionOrders(nextProdOrdersE30, waitingOrdersE29, plannedStock[2], 29, waitingOrdersE20);
        E9_E15_E20[2][0] = 20;
        E9_E15_E20[2][1] = nextProdOrdersE20;
                
        // Plan priority of P1, P2 and P3
        int[][] priority = new int[][]{{1,(int)nextProdOrdersP1},{2,(int)nextProdOrdersP2},{3,(int)nextProdOrdersP3}};
        sort(priority);
        
        // Split production orders
        while(next) {
            next = false;
            for (int i = 0; i < priority.length; i++) {
                if (priority[i][0] == 1) {
                    addBlockProductions(productionlist, E4_E10_E49, new int[][]{{1,1},{2,1},{3,1}});
                } else if (priority[i][0] == 2) {
                    addBlockProductions(productionlist, E5_E11_E54, new int[][]{{1,1},{2,1},{3,1}});
                } else if (priority[i][0] == 3) {
                    addBlockProductions(productionlist, E6_E12_E29, new int[][]{{1,1},{2,1},{3,1}});
                }
            }
            addBlockProductions(productionlist, P1_P2_P3, priority);
            addBlockProductions(productionlist, E51_E56_E31, priority);
            addBlockProductions(productionlist, E26_E16_E17, priority);
            addBlockProductions(productionlist, E50_E55_E30, priority);
            for (int i = 0; i < priority.length; i++) {
                if (priority[i][0] == 1) {
                    addBlockProductions(productionlist, E7_E13_E18, new int[][]{{1,1},{2,1},{3,1}});
                } else if (priority[i][0] == 2) {
                    addBlockProductions(productionlist, E8_E14_E19, new int[][]{{1,1},{2,1},{3,1}});
                } else if (priority[i][0] == 3) {
                    addBlockProductions(productionlist, E9_E15_E20, new int[][]{{1,1},{2,1},{3,1}});
                }
            }
        }
        
        // Set the splitted production list
        input.setProductionlist(productionlist);
    }
    
    private void setWorkingtimelist() {
        Workingtimelist workingtimelist = new Workingtimelist();
        
        List<int[]> workstationInfo = new ArrayList<int[]>();
        // {workstation, setup time, article, process-time, article, process-time, ...}
        workstationInfo.add(new int[]{1,20,49,6,54,6,29,6});
        workstationInfo.add(new int[]{2,30,50,5,55,5,30,5});
        workstationInfo.add(new int[]{3,20,51,5,56,6,31,6});
        workstationInfo.add(new int[]{4,30,1,6,2,7,3,7});
        workstationInfo.add(new int[]{6,15,16,2,18,3,19,3,20,3});
        workstationInfo.add(new int[]{7,20,10,2,11,2,12,2,13,2,14,2,15,2,18,2,19,2,20,2,26,2});
        workstationInfo.add(new int[]{8,15,10,1,11,2,12,2,13,1,14,2,15,2,18,3,19,3,20,3});
        workstationInfo.add(new int[]{9,15,10,3,11,3,12,3,13,3,14,3,15,3,18,2,19,2,20,2});
        workstationInfo.add(new int[]{10,20,4,4,5,4,6,4,7,4,8,4,9,4});
        workstationInfo.add(new int[]{11,20,4,3,5,3,6,3,7,3,8,3,9,3});
        workstationInfo.add(new int[]{12,0,10,3,11,3,12,3,13,3,14,3,15,3});
        workstationInfo.add(new int[]{13,0,10,2,11,2,12,2,13,2,14,2,15,2});
        workstationInfo.add(new int[]{14,0,16,3});
        workstationInfo.add(new int[]{15,15,17,3,26,3});
        
        for (int[] workstation : workstationInfo) {
            Workingtime workingtime = new Workingtime();
            
            // Machine time
            int machineTime = 0;
            for (int i = 3; i < workstation.length; i=i+2) {
                for (int j = 0; j < input.getProductionlist().getProduction().size(); j++) {
                    if (input.getProductionlist().getProduction().get(j).getArticle().doubleValue() == workstation[i-1]) {
                        machineTime += input.getProductionlist().getProduction().get(j).getQuantity().intValue() * workstation[i];
                    }
                }
            }
            
            // Setup time and setup events
            int setupTime =workstation[1];
            int setupEvents = Integer.valueOf(bundle.getString("setuptime." + workstation[0]));
            
            int total = machineTime + setupTime + setupEvents;
            
            if (total <= 2400) {
                workingtime.setShift(BigInteger.ONE);
                workingtime.setOvertime(BigInteger.ZERO);
            } else if (total > 2400 && total <= 3600) {
                workingtime.setShift(BigInteger.ONE);
                workingtime.setOvertime(BigInteger.valueOf((total - 2400) / 5));
            } else if (total > 3600 && total <= 4800) {
                workingtime.setShift(BigInteger.valueOf(2));
                workingtime.setOvertime(BigInteger.ZERO);
            } else if (total > 4800 && total <= 6000) {
                workingtime.setShift(BigInteger.valueOf(2));
                workingtime.setOvertime(BigInteger.valueOf((total - 4800) / 5));
            } else {
                workingtime.setShift(BigInteger.valueOf(3));
                workingtime.setOvertime(BigInteger.ZERO);
            }
            
            workingtime.setStation(BigInteger.valueOf((int)workstation[0]));
            workingtimelist.getWorkingtime().add(workingtime);
            input.setWorkingtimelist(workingtimelist);
        }
    }
      
    // =========================================================================
    // HELPER METHODS
    // =========================================================================
    
    /**
     * Plans the stock for P1, P2 and P3 at the and of the next period
     */
    private int[] planPStock() {
        double[] plandPStockTemp = new double[3];
        double factorPeriod1 = Double.valueOf(bundle.getString("factor.period1"));
        double factorPeriod2 = Double.valueOf(bundle.getString("factor.period2"));
        double factorPeriod3 = Double.valueOf(bundle.getString("factor.period3"));
        double factorStock = Double.valueOf(bundle.getString("factor.stock"));
        
        plandPStockTemp[0] = (resultDTO.getForcastP1f1() * factorPeriod1 + resultDTO.getForcastP1f2() * factorPeriod2 + resultDTO.getForcastP1f3() * factorPeriod3) 
                / (factorPeriod1 + factorPeriod2 + factorPeriod3) 
                * factorStock;
       
        plandPStockTemp[1] = (resultDTO.getForcastP2f1() * factorPeriod1 + resultDTO.getForcastP2f2() * factorPeriod2 + resultDTO.getForcastP2f3() * factorPeriod3) 
                / (factorPeriod1 + factorPeriod2 + factorPeriod3) 
                * factorStock;
        
        plandPStockTemp[2] = (resultDTO.getForcastP3f1() * factorPeriod1 + resultDTO.getForcastP3f2() * factorPeriod2 + resultDTO.getForcastP3f3() * factorPeriod3) 
                / (factorPeriod1 + factorPeriod2 + factorPeriod3) 
                * factorStock;
        
        int[] plandPStock = new int[3];
        for (int i = 0; i < plandPStockTemp.length; i++) {
            plandPStock[i] = (int)plandPStockTemp[i];
        }
        
        return plandPStock;
    }
    
    /**
     * Plans next production orders for article
     * 
     * @param salesOrders
     * @param prevWaiting
     * @param plannedStock
     * @param articleNo
     * @param waitingOrders
     * @return 
     */
    private int planNextProductionOrders(int salesOrders, int prevWaitingOrders[], int plannedStock, int articleNo, int waitingOrders[]) {
        double productionOrders = salesOrders
                                + prevWaitingOrders[0]
                                + plannedStock
                                - resultDTO.getResult().getWarehousestock().getArticle().get(articleNo-1).getAmount().intValue()
                                - waitingOrders[0]
                                - waitingOrders[1];
        
        // Don't reset quantity if article is multiple used
        if (productionOrders < 0 && articleNo != 16 && articleNo != 17 && articleNo != 26) {
            productionOrders = 0;
        }
        
        // Round to nearest ten
        int roundedProductionOrders = (int) Math.ceil(productionOrders / 10) * 10;
                
        return roundedProductionOrders;
    }
    
    /**
     * Calculates the waitingorders
     * 
     * @param articleNo
     * @return the waitingorders
     */
    private int[] calculateWaitingOrders(int articleNo) {
        int[] waitingOrders = new int[2];
        
        // Orders in work
        if (resultDTO.getResult().getOrdersinwork().getWorkplace() != null) {
            for(int i = 0; i < resultDTO.getResult().getOrdersinwork().getWorkplace().size(); i++) {
                if (resultDTO.getResult().getOrdersinwork().getWorkplace().get(i).getItem().doubleValue() == articleNo)
                    waitingOrders[0] += resultDTO.getResult().getOrdersinwork().getWorkplace().get(i).getAmount().intValue();
            }
        }
        
        // Orders in front of the workstation
        if (resultDTO.getResult().getWaitinglistworkstations().getWorkplace() != null) {
            for(int i = 0; i < resultDTO.getResult().getWaitinglistworkstations().getWorkplace().size(); i++) {
                if (resultDTO.getResult().getWaitinglistworkstations().getWorkplace().get(i).getItem() != null) {
                    if (resultDTO.getResult().getWaitinglistworkstations().getWorkplace().get(i).getItem().intValue() == articleNo) {
                        waitingOrders[1] += resultDTO.getResult().getWaitinglistworkstations().getWorkplace().get(i).getAmount().intValue();
                    }
                }
            }
        }
        
        return waitingOrders;
    }
    
    /**
     * Adds a production to the productionlist
     * 
     * @param article
     * @param quantity
     * @param productionlist 
     */
    private void addProduction(int article, int quantity, Productionlist productionlist) {
        if (quantity != 0) {
            Production production = new Production();
            production.setArticle(BigInteger.valueOf(article));
            production.setQuantity(BigInteger.valueOf(quantity));
            productionlist.getProduction().add(production);
        }
    }
    
    /**
     * Adds three productions to the productionlist based on priority
     * 
     * @param productionlist
     * @param nextProdOrders
     * @param priority 
     */
    private void addBlockProductions(Productionlist productionlist, int[][] nextProdOrders, int[][] priority) {
        int[][] splitOrders = split(nextProdOrders);
        
        if (priority[0][0] == 1) {
            addProduction(splitOrders[0][0], splitOrders[0][1], productionlist);
        } else if (priority[0][0] == 2) {
            addProduction(splitOrders[1][0], splitOrders[1][1], productionlist);
        } else if (priority[0][0] == 3) {
            addProduction(splitOrders[2][0], splitOrders[2][1], productionlist);
        }
        
        if (priority[1][0] == 1) {
            addProduction(splitOrders[0][0], splitOrders[0][1], productionlist);
        } else if (priority[1][0] == 2) {
            addProduction(splitOrders[1][0], splitOrders[1][1], productionlist);
        } else if (priority[1][0] == 3) {
            addProduction(splitOrders[2][0], splitOrders[2][1], productionlist);
        }
                
        if (priority[2][0] == 1) {
            addProduction(splitOrders[0][0], splitOrders[0][1], productionlist);
        } else if (priority[2][0] == 2) {
            addProduction(splitOrders[1][0], splitOrders[1][1], productionlist);
        } else if (priority[2][0] == 3) {
            addProduction(splitOrders[2][0], splitOrders[2][1], productionlist);
        }
    }
    
    /**
     * Splits the production orders into defined values
     * 
     * @param nextProdOrdersArray
     * @return splitted productionOrders
     */
    private int[][] split(int[][] nextProdOrders) {
        int splitValue = Integer.valueOf(bundle.getString("split"));
        int[][] splitOrders = new int[3][2];
        
        for (int i = 0; i < nextProdOrders.length; i++) {
            splitOrders[i][0] = nextProdOrders[i][0];
            if (nextProdOrders[i][1] > splitValue) {
                splitOrders[i][1] = splitValue;
                nextProdOrders[i][1] -= splitValue;
                this.next = true;
            } else {
                if (nextProdOrders[i][1] > 0) {
                    splitOrders[i][1] = nextProdOrders[i][1];
                    nextProdOrders[i][1] -= nextProdOrders[i][1];
                }
            }
        }
        
        return splitOrders;
    }
    
    /**
     * Creates the priority array
     * 
     * @param priority
     * @return the priority list of P1, P2 and P3
     */
    private int[][] sort(int[][] priority) {  
        boolean unsorted = true;
        int temp0;
        int temp1;
        
        while (unsorted){
            unsorted = false;
            for (int i = 0; i < priority.length-1; i++)
                if (priority[i][1] > priority[i+1][1]) {
                    temp0 = priority[i][0];
                    temp1 = priority[i][1];
                    priority[i][0] = priority[i+1][0];
                    priority[i][1] = priority[i+1][1];
                    priority[i+1][0] = temp0;
                    priority[i+1][1] = temp1;
                    unsorted = true;
                }
        }
        
        return priority;
    }
}