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

public class Customer
{

    private static Connection connection;
    private static PreparedStatement getAllCustomers;
    private static PreparedStatement insertNewCustomer;
    private static PreparedStatement getBookedCustomer;
    private static PreparedStatement getWaitlistCustomer;
    private static ResultSet resultSet;
    private ArrayList<BookingEntry> bookedCustomer;
    private ArrayList<BookingEntry> waitlistCustomer;
    private static ArrayList<String> customers;
    
    public Customer(){
        
    }
    
    public static ArrayList<String> getAllCustomers()
    {
        try
        {
            connection = DBConnection.getConnection();
            customers = new ArrayList();
            getAllCustomers = connection.prepareStatement("select name from customer");
            resultSet = getAllCustomers.executeQuery();
            while (resultSet.next())
            {
                customers.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return customers;

    }

        public static void addNewCustomer(String customer)
    {
        try{
            connection = DBConnection.getConnection();
            insertNewCustomer = connection.prepareStatement("insert into customer (name) values (?)");
            insertNewCustomer.setString(1, customer);
            insertNewCustomer.executeUpdate();
        } 
        
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

    }
        
        public ArrayList<BookingEntry> getBookedCustomer(String customerName){
            try{
                connection = DBConnection.getConnection();
                bookedCustomer = new ArrayList();
                getBookedCustomer = connection.prepareStatement("SELECT * FROM BOOKINGS WHERE CUSTOMER = ?");
                getBookedCustomer.setString(1, customerName);
                resultSet = getBookedCustomer.executeQuery();
                while(resultSet.next()){
                    bookedCustomer.add(new BookingEntry(resultSet.getString("CUSTOMER"), resultSet.getString("FLIGHT"), resultSet.getDate("DAY")));
                }
            }
            
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
            return bookedCustomer;
        }
        
        public ArrayList<BookingEntry> getWaitlistCustomer(String customerName){
            try{
                connection = DBConnection.getConnection();
                waitlistCustomer = new ArrayList();
                getWaitlistCustomer = connection.prepareStatement("select * from WAITLIST where customer = ?");
                getWaitlistCustomer.setString(1, customerName);
                resultSet = getWaitlistCustomer.executeQuery();
                while(resultSet.next()){
                    waitlistCustomer.add(new BookingEntry(resultSet.getString("CUSTOMER"), resultSet.getString("FLIGHT"), resultSet.getDate("DAY")));
                }
            }
            
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
            
            return waitlistCustomer;
            
        }
        
}