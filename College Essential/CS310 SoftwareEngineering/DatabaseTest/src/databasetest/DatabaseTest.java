package databasetest;

import java.sql.*;
import java.util.ArrayList;
import org.json.simple.*;

public class DatabaseTest {
    
    public static void main(String[] args) {
        DatabaseTest d = new DatabaseTest();
        d.getJSONData();
    }

    public JSONArray getJSONData() {
        
        JSONArray data = new JSONArray(); //wo jia de
        
        Connection conn = null;
        PreparedStatement pstSelect = null, pstUpdate = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        
        String query, key, value;

        
        boolean hasresults;
        int resultCount, columnCount, updateCount = 0;
        
        try {
            
            /* Identify the Server */
            
            String server = ("jdbc:mysql://localhost/p2_test");
            String username = "root";
            String password = "a26248579@A!";
            System.out.println("Connecting to " + server + "...");
            
            /* Load the MySQL JDBC Driver */
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            /* Open Connection */

            conn = DriverManager.getConnection(server, username, password);

            /* Test Connection */
            
            if (conn.isValid(0)) {
                
                /* Connection Open! */
                
                System.out.println("Connected Successfully!");
                
                /* Prepare Update Query
                
                query = "INSERT INTO people (firstname, lastname) VALUES (?, ?)";
                pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                //pstUpdate.setString(1, newFirstName);
                //pstUpdate.setString(2, newLastName);
                
                // Execute Update Query
                
                updateCount = pstUpdate.executeUpdate();
                
                // Get New Key; Print To Console
                
                if (updateCount > 0) {
            
                    resultset = pstUpdate.getGeneratedKeys();

                    if (resultset.next()) {

                        //System.out.print("Update Successful!  New Key: ");
                        //System.out.println(resultset.getInt(1));

                    }

                }*/
                
                
                /* Prepare Select Query */
                
                query = "SELECT * FROM people";
                pstSelect = conn.prepareStatement(query);
                
                /* Execute Select Query */
                
                System.out.println("Submitting Query ...");
                
                hasresults = pstSelect.execute();                
                
                /* Get Results */
                
                System.out.println("Getting Results ...");
                
                while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                    if ( hasresults ) {
                        
                        /* Get ResultSet Metadata */
                        
                        resultset = pstSelect.getResultSet();
                        metadata = resultset.getMetaData();
                        columnCount = metadata.getColumnCount();
                        
                        /* Get Column Names; Print as Table Header */
                        
                        ArrayList <String> columnLabel = new ArrayList<>(); //wo jia de
                        
                        for (int i = 1; i <= columnCount; i++) {

                            key = metadata.getColumnLabel(i);
                            
                            
                            columnLabel.add(key);//wo jia de
                            
                            //System.out.format("%30s", key);

                        }
                        
                        
                        
                        
                        /* Get Data; Print as Table Rows */
                        
                        
                        
                        while(resultset.next()) {
                            
                            /* Begin Next ResultSet Row */

                            System.out.println();
                            
                            /* Loop Through ResultSet Columns; Print Values */
                            
                            JSONObject row = new JSONObject();//wo jia de
                            
                            for (int i = 2; i <= columnCount; i++) {

                                value = resultset.getString(i);
                                row.put(columnLabel.get(i-1), value);//wo jia de
                                
                            }
                            
                            data.add(row);// wo jia de

                        }
                        
                        System.out.println(data.toString());
                        
                    }

                    else {

                        resultCount = pstSelect.getUpdateCount();  

                        if ( resultCount == -1 ) {
                            break;
                        }

                    }
                    
                    /* Check for More Data */

                    hasresults = pstSelect.getMoreResults();

                }
                
    
            }
            
            System.out.println();
            
            /* Close Database Connection */
            
            conn.close();
            
        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        
        /* Close Other Database Objects */
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }
            
            if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null; } catch (Exception e) {} }
            
        }
        
        return data;
        
    }

    /*public JSONArray getJSONData() {
        
        return 
        
    }*/
    
    
    
}