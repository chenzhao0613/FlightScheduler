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
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class BookingQueries {
    private static Connection connection;
    private PreparedStatement addBooking;
    private PreparedStatement getBookingSeats;
    private PreparedStatement getFlightSeats;
    private PreparedStatement getBookingCustomers;
    private PreparedStatement cancelBookedCustomer;
    private PreparedStatement cancelWaitlistCustomer;
    private PreparedStatement getAllBookedFlights;
    private PreparedStatement removeBooking;
    private PreparedStatement getCustomerByFlight;
    private ArrayList<String> flightList;
    private ArrayList <BookingEntry> bookingNameList = new ArrayList<>();
    private ArrayList <BookingEntry> getBookingCustomerByFlight = new ArrayList<>();
    private int seatsBooked;
    private int flightseat;
    ResultSet resultset;
    
    public BookingQueries(){
        
        try{
            connection = DBConnection.getConnection();
            addBooking = connection.prepareStatement("insert into BOOKINGS (customer,flight, day)" + "values(?,?,?)");
            getBookingSeats = connection.prepareStatement("select count(flight) from BOOKINGS WHERE flight = ? and day = ?"); 
            getBookingCustomers = connection.prepareStatement("select * from BOOKINGS where flight=? and day=?"); 
            getFlightSeats = connection.prepareStatement("select seats from FLIGHT WHERE name = ?");
            getAllBookedFlights = connection.prepareStatement("select FLIGHT from BOOKINGS where customer = ? and day = ? ");
            cancelBookedCustomer = connection.prepareStatement("delete from BOOKINGS where customer = ? and day = ?");
            cancelWaitlistCustomer = connection.prepareStatement("delete from WAITLIST where customer = ? and day = ?");
            removeBooking = connection.prepareStatement("DELETE FROM BOOKINGS WHERE FLIGHT = ?");
            getCustomerByFlight = connection.prepareStatement("SELECT * FROM BOOKINGS where FLIGHT = ?");
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public void addCustomer(String customerName, String flightName, Date date){
        try{
            addBooking.setString(1, customerName);
            addBooking.setString(2, flightName);
            addBooking.setDate(3, date);
            addBooking.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    public int getBookingSeats(String flightName, Date dates){
            try{
                getBookingSeats.setString(1, flightName); 
                getBookingSeats.setDate(2, dates); 
                resultset = getBookingSeats.executeQuery(); 
                resultset.next(); 
                seatsBooked = resultset.getInt(1);
                 }
     
            catch(SQLException sqlException)
             {
                sqlException.printStackTrace();
             }
             return seatsBooked;
     }
    
    public int getFlightseat(String flightName){   
            try{
                getFlightSeats.setString(1, flightName); 
                resultset = getFlightSeats.executeQuery();  
                resultset.next();
                flightseat = resultset.getInt(1);
                }
     
            catch(SQLException sqlException){
                sqlException.printStackTrace();
                }
                return flightseat;
     }
    
   public ArrayList<BookingEntry>getBookedCustomers(String flightName,Date Date)
   { 
        try
        {
        getBookingCustomers.setString(1, flightName); 
        getBookingCustomers.setDate(2, Date);
        resultset= getBookingCustomers.executeQuery();
        while (resultset.next()){
            bookingNameList.add(new BookingEntry(resultset.getString("CUSTOMER"),resultset.getString("FLIGHT"),resultset.getDate("DAY")));}
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
       return bookingNameList;
   }
    
    public ArrayList<String> getCancelFlights(String customer, Date day){
        try{
            connection = DBConnection.getConnection();
            flightList = new ArrayList();
            getAllBookedFlights.setString(1, customer);
            getAllBookedFlights.setDate(2, day);
            resultset = getAllBookedFlights.executeQuery();
            
            while(resultset.next()){
                flightList.add(resultset.getString(1));
            } 
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return flightList;
    }
    
    public void cancelBookedCustomer(String customer, Date day){
        try{
            cancelBookedCustomer.setString(1, customer);
            cancelBookedCustomer.setDate(2, day);
            cancelBookedCustomer.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public void cancelWaitlistCustomer(String customer, Date day){
        try{
            cancelWaitlistCustomer.setString(1, customer);
            cancelWaitlistCustomer.setDate(2, day);
            cancelWaitlistCustomer.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public void removeBooking(String customer){
        try{
            removeBooking.setString(1, customer);
            removeBooking.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public ArrayList<BookingEntry> getBookingCustomerByFlight(String flightName){
        try{
            getCustomerByFlight.setString(1, flightName);
            resultset = getCustomerByFlight.executeQuery();
            while(resultset.next()){
                getBookingCustomerByFlight.add(new BookingEntry(resultset.getString("CUSTOMER"), resultset.getString("FLIGHT"), resultset.getDate("DAY")));      
            }  
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return getBookingCustomerByFlight;
    }
    
    
    public void cancellation(String customer, Date day){
        cancelBookedCustomer(customer, day);
        cancelWaitlistCustomer(customer, day);
    }
   
    
    public boolean checkSeatAvailable(int Bookseat,int Flightseat){
        return Bookseat >= Flightseat;
    }

    
    
}
