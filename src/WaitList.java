/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cz
 */

import java.sql.Date;

public class WaitList {
    private String customerName;
    private String flightName;
    private Date date;

    public WaitList(String customerName,String flightName,Date date){
        this.customerName = customerName;
        this.flightName = flightName;
        this.date = date;
    }
    
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setFlightName(String flightName){
        this.flightName = flightName;
    }
    public void setDate(Date date){
        this.date = date;
    }
    
    public String getCustomerName(){ 
        return customerName;
    }
    public String getFlightName(){ 
        return flightName;
    }
    public Date getDate(){ 
        return date;
    }
    
}
