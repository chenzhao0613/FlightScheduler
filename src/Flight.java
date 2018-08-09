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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Flight {
    private static Connection connection;
    private static PreparedStatement getAllFlights;
    private static PreparedStatement addFlight;
    private static PreparedStatement addFlightDate;
    private static PreparedStatement dropFlight;
    private PreparedStatement getAddedFlight;
    private static ArrayList<String> flights;
    private static ArrayList<String> getAddFlight;
    private static ResultSet resultset;
    
    public static ArrayList<String> getAllFlights()
    {
        try
        {
            connection = DBConnection.getConnection();
            flights = new ArrayList();
            getAllFlights = connection.prepareStatement("select NAME from FLIGHT");
            resultset = getAllFlights.executeQuery();
            while (resultset.next())
            {
                flights.add(resultset.getString(1));
            }
        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return flights;
    }
    
    public static void addFlight(String flightName, int seats){
        try{
            connection = DBConnection.getConnection();
            addFlight = connection.prepareStatement("INSERT INTO FLIGHT(NAME,SEATS) VALUES(?,?)");
            addFlight.setString(1, flightName);
            addFlight.setInt(2, seats);
            addFlight.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void addFlightDate(Date date){
        try{
            connection = DBConnection.getConnection();
            addFlightDate = connection.prepareStatement("INSERT INTO DAY(DATE) VALUES(?)");
            addFlightDate.setDate(1, date);
            addFlightDate.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void dropFlight(String customer){
        try{
            connection = DBConnection.getConnection();
            dropFlight = connection.prepareStatement("DELETE FROM FLIGHT WHERE NAME = ?");
            dropFlight.setString(1, customer);
            dropFlight.executeUpdate();
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public ArrayList<String> getAddFlight(){
        try{
            connection = DBConnection.getConnection();
            getAddedFlight = connection.prepareStatement("SELECT NAME FROM FLIGHT");
            resultset = getAddedFlight.executeQuery();
            
            getAddFlight = new ArrayList<String>();
            
            while(resultset.next()){
                getAddFlight.add(resultset.getString("NAME"));
            }
        }
        
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return getAddFlight;
    }
    
    
   

    
    
}
