/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cz
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class WaitListQueries {
    private static Connection connection;
    private PreparedStatement addWaitingList;
    private PreparedStatement getWaitingListCustomers;
    private PreparedStatement cancelWaitlistCustomer;
    private PreparedStatement deleteWaitlistCustomer;
    private PreparedStatement getWaitlistedCustomer;
    private PreparedStatement removeWaitlist;
    private PreparedStatement getCustomerByFlight;
    private WaitList returnCustomerBooking;
    private ArrayList <WaitList> waitlistNameList = new ArrayList<>();
    private ArrayList <BookingEntry> getWaitlistCustomerByFlight = new ArrayList<>();
    ResultSet resultset = null;
    
    public WaitListQueries(){
        
        try{
            connection = DBConnection.getConnection();
            getWaitingListCustomers = connection.prepareStatement("SELECT * FROM WAITLIST where DAY=?");
            addWaitingList = connection.prepareStatement("INSERT INTO WAITLIST" + "(CUSTOMER,FLIGHT,DAY,TIMESTAMP)" + "VALUES(?,?,?,?)");
            cancelWaitlistCustomer = connection.prepareStatement("DELETE FROM WAITLIST where CUSTOMER = ? and DAY = ?");
            deleteWaitlistCustomer = connection.prepareStatement("DELETE FROM WAITLIST where CUSTOMER = ? and FLIGHT = ? and DAY = ?");
            getWaitlistedCustomer = connection.prepareStatement("SELECT * FROM WAITLIST where FLIGHT = ? and DAY = ? ");
            removeWaitlist = connection.prepareStatement("DELETE FROM WAITLIST WHERE FLIGHT = ?");
            getCustomerByFlight = connection.prepareStatement("SELECT * FROM WAITLIST where FLIGHT = ?");
        }
        
        catch(SQLException sqlException){
                sqlException.printStackTrace();
             }
        
    }
    
    public void addCustomerToWaitList(String customerName,String flightName,Date date) 
   { 
       Calendar calendar = Calendar.getInstance();
       Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
       try{
            addWaitingList.setString(1,customerName);
            addWaitingList.setString(2,flightName);
            addWaitingList.setDate(3,date);
            addWaitingList.setTimestamp(4,currentTimestamp);
            addWaitingList.executeUpdate();
           }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            }
   }
    
    public ArrayList<WaitList>getWaitingListCustomers(Date Dates){ 
       try
        {
            getWaitingListCustomers.setDate(1, Dates);
            resultset= getWaitingListCustomers.executeQuery();
            while (resultset.next()) {
                waitlistNameList.add(new WaitList(resultset.getString("CUSTOMER"),resultset.getString("FLIGHT"),resultset.getDate("DAY")));
            }
        }
        catch(SQLException sqlException)
        {sqlException.printStackTrace();}

       return waitlistNameList;
    }
    
    public WaitList getWaitlistedCustomer(String flightName, Date Date){
        try{
            getWaitlistedCustomer.setString(1, flightName);
            getWaitlistedCustomer.setDate(2, Date);
            resultset = getWaitlistedCustomer.executeQuery();
            //Timestamp time = resultset.getTimestamp("TIMESTAMP");
            //returnCustomerBooking = new WaitList(resultset.getString("CUSTOMER"), resultset.getString("FLIGHT"), resultset.getDate("DAY"));
            while(resultset.next()){
                Timestamp currentTime;
                Timestamp time = resultset.getTimestamp("TIMESTAMP");
                returnCustomerBooking = new WaitList(resultset.getString("CUSTOMER"), resultset.getString("FLIGHT"), resultset.getDate("DAY"));
                currentTime = resultset.getTimestamp("TIMESTAMP");
                if(time.after(currentTime)){
                    time = currentTime;
                    returnCustomerBooking = new WaitList(resultset.getString("CUSTOMER"), resultset.getString("FLIGHT"), resultset.getDate("DAY"));
                }
            }
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return returnCustomerBooking;
    }
    
    public boolean isEmpty(String flightName, Date Date){
        boolean empty = true;
        try{
            getWaitlistedCustomer.setString(1, flightName);
            getWaitlistedCustomer.setDate(2, Date);
            resultset = getWaitlistedCustomer.executeQuery();
            while(resultset.next()){
                empty = false;
            }
        } 
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return empty;
    } 
    
    public void deleteWaitlistCustomer(String customerName, String flightName, Date date){
        try{
            deleteWaitlistCustomer.setString(1, customerName);
            deleteWaitlistCustomer.setString(2, flightName);
            deleteWaitlistCustomer.setDate(3, date);
            deleteWaitlistCustomer.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public void removeWaitlist(String customerName){
        try{
            removeWaitlist.setString(1, customerName);
            removeWaitlist.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    public ArrayList<BookingEntry>getWaitlistCustomerByFlight(String flightName){
        
        
        try{
            getCustomerByFlight.setString(1, flightName);
            resultset = getCustomerByFlight.executeQuery();
            while(resultset.next()){
                getWaitlistCustomerByFlight.add(new BookingEntry(resultset.getString("CUSTOMER"), resultset.getString("FLIGHT"), resultset.getDate("DAY")));
            }
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return getWaitlistCustomerByFlight;
    }
    
}
