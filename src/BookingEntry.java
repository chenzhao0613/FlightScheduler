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
import java.sql.Timestamp;

public class BookingEntry {
    private String customerName;
    private String flightName;
    private Date date;
    //private Timestamp timestamp;

    public BookingEntry(String customerName, String flightName, Date date){
        this.customerName = customerName; 
        this.flightName = flightName;
        this.date = date;
        //this.timestamp = timestamp;
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
    
    /*public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    } */
    
    public String getCustomerName(){ 
        return customerName;
    
    }
    public String getFlightName(){ 
        return flightName;
    
    }
    public Date getDate(){ 
        return date;
    }
    
    /*public Timestamp getTimestamp(){
        return timestamp;
    } */
    
}
