package com.developersOfTheMillennium.motm;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MediaProfilePage {
    private int mediaID;         //unique integer id, PRIMARY KEY
    private String title;        //the title/name of the movie/music/game/etc
    private String mediaType;    //type of media;  {'cinema', 'music', 'tv-series', 'video game', 'novel'}
    private String summary;      //string of words
    private String[] tags;       //list of tags (strings) associated to title, contents are different based on mediaType DEV-60
    //private double rating = 0.0; //current rating, seems well fleshed out in appdb.java already, exists
    //private int numRaters;       //total number of all people who've rated this title, exists

    public int get_mediaID() {
        return this.mediaID;
    }

    public String get_title() {
        return this.title;
    }

    public String get_mediaType() {
        return this.mediaType;
    }

    public String get_summary() {
        return this.summary;
    }

    public String[] get_tags() {
        return this.tags;
    }
    //get_rating(){ return this.rating; } //exists
    //get_raters(){ return this.numRaters; } //existS

//-------------------------------CLASS ESSENTIALLY ENDS HERE---------------------------------------
//}

    /**
     * This method connects to the database while enforcing foreign key
     * constraints.
     * It returns a Connection object.
     * ONLY USE IF CLASS ISNT IMPLEMENTED IN APPDATABASE.JAVA ALREADY, I.E THIS CLASS IS HANDLES DB CONNECTION
     */
    public Connection connect(/*args*/) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:db/appdatabase.db");
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON; ");
            conn.setAutoCommit(true);
        } catch (Exception e) {/*etc*/}
        return conn;
    }

    /**
     * This method safely disconnects to the database.
     * ONLY USE IF CLASS ISNT IMPLEMENTED IN APPDATABASE.JAVA ALREADY, I.E THIS CLASS IS HANDLES DB CONNECTION
     */
    public void disconnect(Connection conn) {
        try {
            conn.close();
        } catch (SQLException close_exception) {
            System.out.println("#  ERROR:  error when closing db connection");
            /* handle it */
        }
    }

//--------------------THE FUNCTIONS BELOW WILL NOT BE DEFINED WITHIN THE CLASS----------------------
    /**
     * Adds the media title to the database (CAREFUL! DUPLICATES ARE POSSIBLE)
     */
    public void add_media_title(Connection conn, String title, String mediaType, String summary) throws SQLException {
        String sqlReq = "INSERT INTO mediaTitles (title, mediaType, summary) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, title);
            pstmt.setString(2, mediaType);
            pstmt.setString(3, summary);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("#  ERROR :  " + ex);
            throw new SQLException("Failed to add media title");
        }
    }

    /**
     * Retrieves media title information of specified mediaID
     * RETURN: MediaProfilePage object with title information
     */
    public MediaProfilePage get_mediaTitleInfo(Connection conn, int mediaID) {
        stmt = conn.createStatement();
        String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = " + mediaId; //Should this retrieve a single row or all rows
        try {
            ResultSet rs = stmt.executeQuery(sqlReq);
        } catch (SQLException ex) {/*handle it*/}

        if (rs.next()) {    //found something
            //make and return mediaRatingInfo class
            return new mediaRatingInfo(rs.getFloat("rating"), rs.getFloat("numRaters"));
        } else {    //didnt find
            //handle failure
        }
    }

    /**
     * Retrieves a list of mediaID's, useful if we sort each categories ID's OR we make separate tables
     * Call get_mediaTitleInfo() on each ID to get its information stored in a class object
     * RETURN: List of media IDs
     */
    public int[] get_mediaIDs(Connection conn, String mediaType){}

    /**
     * Adds specified media title to User's favorite/watchlist
     */
    public void add_userWatchlist(Connection conn, int mediaID) {}

    /**
     *
     */







    /**
     * example usage in code
     public static void main(String[] args) {
     SelectApp app = new SelectApp();
     app.selectAll();
     }
     ----------------------------------------------
     // the general procedure should be like
     /* appDatabase db = new appDatabase();
     *  Connect conn = db.connect(args)
     * ~~~do some work, like write_to_DB(conn, args)
     * db.disconnect(conn);

     //try/catch the functions and print the exception messages as needed
     */
//---------REMOVE THE BELOW } WHEN FINISHED-------------------------------------------------
}
