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
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;

public class Day {
    private static Connection connection;
    private static PreparedStatement getAllDates;
    private static ResultSet resultSet;
    private static ArrayList<String> dates;
    
    public static ArrayList<String> getAllDates()
    {
        try
        {
            connection = DBConnection.getConnection();
            dates = new ArrayList();
            getAllDates = connection.prepareStatement("select DATE from DAY");
            resultSet = getAllDates.executeQuery();
            while (resultSet.next())
            {
                dates.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return dates;

    }
    
    public String convertDateToString(Date date, String format) {
        String dateString = null;
        DateFormat dayformat = new SimpleDateFormat(format);
        try {
            dateString = dayformat.format(date);
        } 
        catch (Exception exception) {
            System.out.println(exception);
        }
        return dateString;
    }
    
}
