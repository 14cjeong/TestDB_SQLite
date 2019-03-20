package com.company;

import org.sqlite.core.CoreResultSet;

import java.sql.*;

//I created a new main2 class to focus on
//the .executeQuery() method
//Constants and a method for insertions
//We'd usually put the constants in a separate
//class, but for simplicity, it's all going to be
//in here

public class Main2 {

   //Notice the constants. It's going to make everything smoother
    //because we don't have to "hard code"
    //compared to the original Main class
    public static final String DB_NAME = "testjava.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\14cjeong\\testjava.db" + DB_NAME;

    public static final String TABLE_CONTACTS = "contacts";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";


    public static void main(String[] args) {

        try {

            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();
            statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                                        TABLE_CONTACTS +
                                " (" + COLUMN_NAME + " text, " +
                                        COLUMN_PHONE + " integer, " +
                                        COLUMN_EMAIL + " text" +
                                            ")");


           //Notice how messy all of these INSERTS are.
            //that's why there is a method below called insertContact
            //that simplifies the code
            //Demonstration:
            insertContact(statement, "Tim", 5324454, "tim@gmail.com");
            insertContact(statement, "Joe", 5674454, "joey@gmail.com");
            insertContact(statement, "Jane", 537894, "Jane@email.com");
            insertContact(statement, "Fido", 5389074, "Fido@email.com");


            /*statement.execute("INSERT INTO " + TABLE_CONTACTS +
                                " (" + COLUMN_NAME + ", " +
                                        COLUMN_PHONE + ", " +
                                        COLUMN_EMAIL +
                                            " ) " +
                    "VALUES('Tim', 5324454, 'tim@email.com')");

            statement.execute("INSERT INTO " + TABLE_CONTACTS +
                    " (" + COLUMN_NAME + ", " +
                    COLUMN_PHONE + ", " +
                    COLUMN_EMAIL +
                    " ) " +
                    "VALUES('Joe', 5674454, 'joey@email.com')");

            statement.execute("INSERT INTO " + TABLE_CONTACTS +
                    " (" + COLUMN_NAME + ", " +
                    COLUMN_PHONE + ", " +
                    COLUMN_EMAIL +
                    " ) " +
                    "VALUES('Jane', 537894, 'Jane@email.com')");

            statement.execute("INSERT INTO " + TABLE_CONTACTS +
                    " (" + COLUMN_NAME + ", " +
                    COLUMN_PHONE + ", " +
                    COLUMN_EMAIL +
                    " ) " +
                    "VALUES('Fido', 5389074, 'Fido@email.com')");

            statement.execute("UPDATE " + TABLE_CONTACTS + " SET " +
                    COLUMN_PHONE + "=537894" +
                    " WHERE " + COLUMN_NAME + "='Jane'");

            statement.execute("DELETE FROM " + TABLE_CONTACTS +
                            " WHERE " + COLUMN_NAME + "='Joe'");*/



//            statement.execute("SELECT * FROM contacts");
//            ResultSet results = statement.getResultSet();
            //We can replace lines 22 and 23 with an easier method
            //It's called the .executeQuery() method
            //Demonstration:
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS);
            while(results.next()) {
                System.out.println(results.getString(COLUMN_NAME) + " " +
                        results.getInt(COLUMN_PHONE) + " " +
                        results.getString(COLUMN_EMAIL));
            }

            results.close();

            statement.close();
            conn.close();

        } catch(SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }


    }
    //this needs to be static because we're calling it from the main
    //class
    private static void insertContact(Statement statement, String name, int phone, String email)
        throws SQLException
    {
        statement.execute("INSERT INTO " + TABLE_CONTACTS +
                " (" + COLUMN_NAME + ", " +
                COLUMN_PHONE + ", " +
                COLUMN_EMAIL +
                " ) " +
                "VALUES('" + name + "', " + phone + ", '" + email + "')");
    }
}

//Usually we wnat data from a column record
//asking for the column indicies
//It's fastest that way, rather than the way
//we did in line 27-32