package com.company;

import org.sqlite.core.CoreResultSet;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        try {
            //This is critical. If we don't connect. Our code
            //is going nowhere.
            //It doesn't matter what SQL database is used
            //it's generally done the same way
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\14cjeong\\testjava.db");
            Statement statement = conn.createStatement();
            //creating a table. Basically, the same exact sql statements we've used in the past
            //Also, notice the IF NOT EXISTS
            statement.execute("CREATE TABLE IF NOT EXISTS contacts" +
                    "(name TEXT, phone INTEGER, email TEXT)");
            //One thing to note for the above is that we don't have to
            //pass in the semi colon at the end of our statement
            //the JDBC driver puts it in there for us


/*
            statement.execute("INSERT INTO contacts (name, phone, email)" +
                    "VALUES('Chang', 123123, 'chang@gmail.com')");


            statement.execute("INSERT INTO contacts (name, phone, email)" +
                    "VALUES('Joe', 1345123, 'chan234g@gmail.com')");

            statement.execute("INSERT INTO contacts (name, phone, email)" +
                    "VALUES('Jane', 19023, 'cha78ng@gmail.com')");
*/
     /*       //HOW TO UPDATE
            statement.execute("UPDATE contacts SET phone=1256456 WHERE name='Jane'");
            //HOW TO DELETE
            statement.execute("DELETE FROM contacts WHERE name='Joe'");*/

     //HOW TO READ

     statement.execute("SELECT * FROM contacts");
            ResultSet results = statement.getResultSet();
            //This loops to find a next result
            //As long as the boolean is true
            //it will process the following statements
            while(results.next()) {
                System.out.println(results.getString("name") + " " +
                                                        results.getInt("phone") + " " +
                                                        results.getString("email"));
            }

            results.close();

            //Now, after the execute, we want to close the connection.
            //It's good practice to do so.
            statement.close();
            conn.close();
            //VERY IMPORTANT THAT WE CLOSE THE STATEMENT
            //BEFORE WE CLOSE THE CONNECTION.
            //That's obvious.
        } catch(SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }


        //Remember, that it's very important to close a connection
        //once we're done modifying it.
        //We can have it automatically close by
        //using the try with resources method.
        //when a try with resources is run, the method
        //automatically closes as soon as it exits
        //the try/catch block.
        //All you have to do is the following:
        //try(Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\14cjeong\\testjava.db");
        //Statement statement = conn.createStatement())
        // {statement.execute("CREATE TABLE contacts (name TEXT, phone INTEGER, email TEXT)");}
        // catch (SQLException e) {
        // System.out.print.;n("Something went wrong: " + e.getMessage());
        // }
    }
}

//Thing to note:
//Every result set has a cursor
//if you reuse a statement object to do a query
//than any result set associated with that
//statement object is swiped (or closed)
//then a new one is created for the new query
//So, if we want to work with several query results
//at the same time, it's important that we use
//a different statement instance for each query
//Now, reusing statement instances were okay
//when we're just doing insertions, updates, and deletes
//because we weren't using or checking the result
//So, if you want to reuse a statement object to query
//make sure you finish processing its results before
//you reuse it.


//Another thing to note:
//Sometimes, when an exception is thrown
//not all the resources are closed.
//For this reason, it is always good practice
//to make sure we explicitly code to CLOSE
//we don't want to wait for things to close
//automatically in case the database has
//a lot of open connections.

//Key word definitions
//Cursor - temporary work area created in the system
//memory when a SQL statement is executed
//Example: A cursor contains information on
// a select statement and the rows of data
//accessed by it.
//Temporary work area is used to store the data
//retrieved from the database and manipulate this
//data