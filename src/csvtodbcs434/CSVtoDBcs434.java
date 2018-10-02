/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvtodbcs434;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVtoDBcs434 {

    //static String FILENAME = "/Users/augustgate/Desktop/Project 4 Data 434/xboxone.csv";
    public static void main(String[] args) {
        File file = new File("/Users/augustgate/Desktop/Project 4 Data 434/all.csv");
        try {
            Scanner csvIn = new Scanner(file);
            String temp;
            String developer;
            String publisher;
            String ratings = " N/A";
            Connection conn = null;

            try {
                String userName = "root";
                String password = "root";
                String url = "jdbc:mysql://localhost:3306/CS434";

                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection(url, userName, password);

                System.out.println("Database connection established");
//
//             String query = " insert ignore into developer (name)"
//        + " values (?)";
//           String query = " insert ignore into publisher (name)"
//        + " values (?)";
               // String query = " insert ignore into platforms (systemName)"
               //         + " values (?)";
               
//               String query = " insert ignore into ratings (ratingName)"
//       + " values (?)";
//               
//               String query = " insert ignore into videogame (title, dateReleased, genre, secondaryGenre, developerName , publisherName)"
//        + " values (?, ?, ?, ?, ?,?)";
//
//String query = " insert ignore into isGivenRatings (gameTitle, dateReleased, ratingName)"
//     + " values (?, ?, ?)";

//String query = " insert ignore into developedBy (gameTitle, dateReleased, developerName)"
//     + " values (?, ?, ?)";

//String query = " insert ignore into publishedBy (gameTitle, dateReleased, publisherName)"
//     + " values (?, ?, ?)";



   String query = " insert ignore into playson (gameTitle, gameYearReleased, platformName)"
        + " values (?, ?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
           
                csvIn.useDelimiter(",");
                while (csvIn.hasNextLine()) {
                    temp = csvIn.nextLine();
                    String[] tokens = temp.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (tokens.length > 7) {
                        ratings = tokens[7];
                    };
                    developer = tokens[6];
                   developer = developer.trim();
                    developer =  developer.replaceAll("^\"|\"$", "");
                    publisher = tokens[5];
                    publisher = publisher.trim();
                    publisher = publisher.replaceAll("^\"|\"$", "");
                    String system = tokens[4].trim();
                    if(system.equalsIgnoreCase("nintedo entertainment system")){
                       system = "Nintendo Entertainment System";
                    }
                    String title = tokens[0].trim();
                    title =title.replaceAll("^\"|\"$", "");
                   
                 //  System.out.println(title + ", " + system);
                    // create the mysql insert preparedstatement
                    preparedStmt.setString(1, title);
                    preparedStmt.setString (2, tokens[1]);
                    preparedStmt.setString(3,system );
//                    preparedStmt.setString(4, tokens[3].trim());
//                    preparedStmt.setString(5, developer);
//                    preparedStmt.setString(6, publisher);
                    // execute the preparedstatement
                    preparedStmt.addBatch();
                }
                preparedStmt.executeBatch();
            } catch (Exception e) {
                System.err.println("Database Connection Failed");
                e.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVtoDBcs434.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
