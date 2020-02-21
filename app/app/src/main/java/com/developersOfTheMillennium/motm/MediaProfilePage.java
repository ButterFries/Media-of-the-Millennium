package com.developersOfTheMillennium.motm;

import java.sql.*;
import java.util.ArrayList;


public class MediaProfilePage {
    private int mediaID;         //unique integer id, PRIMARY KEY
    private String title;        //the title/name of the movie/music/game/etc
    private String mediaType;    //type of media;  {'cinema', 'music', 'tv-series', 'video game', 'novel'}
    private String summary;      //string of words
    //private ArrayList<String> genres = new ArrayList<String>(); //list of tags (strings) associated to title, contents are different based on mediaType DEV-60
    private String[] genres;
    //private double rating = 0.0; //current rating, seems well fleshed out in appdb.java already, exists
    //private int numRaters;       //total number of all people who've rated this title, exists

    public MediaProfilePage(int ID, String t, String mt, String s, String[] g){
        this.mediaID = ID;
        this.title = t;
        this.mediaType = mt;
        this.summary = s;
        this.genres = g;
        //might merge ratings in this class object
    }
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
    public String[] get_genres() {
        return this.genres;
    }
    //public ArrayList<String> get_genres() {
    //    return this.genres;
    //}
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
//NOTE TO SELF: IF result.next is used for ONE ROW, while result.next is more multiple rows
//Design (Genres): Tags is stored as a COMMA SEPARATED VALUE inside the database
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
    public MediaProfilePage get_mediaProfilePageInfo(Connection conn, int mediaID) throws SQLException {
        String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = \"" + mediaID + "\"";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) { //found something
                String mg = rs.getString("genres");
                String[] media_genres = mg.split(","); //place each genre into a list
                return new MediaProfilePage(rs.getInt("mediaID"), rs.getString("title"),
                        rs.getString("mediaType"), rs.getString("summary"), media_genres);

            } else { //didnt find
                throw new SQLException("No ID found");
            }
        } catch (SQLException ex) {
            //ex.printStackTrace();
            //System.out.println("#  ERROR :  " + ex);
            throw new SQLException("Error while fetching MPP data");
        }
    }

    /**
     *
     * Returns a list of MediaProfilePage objects that have the associated mediaType
     *
     */
    public ArrayList<MediaProfilePage> get_mediaIDs(Connection conn, String mediaType) throws SQLException{
        String sqlReq = "SELECT * FROM mediaTitles WHERE mediaType = \"" + mediaType + "\"";
        ArrayList<MediaProfilePage> pages = new ArrayList<MediaProfilePage>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                String mg = rs.getString("genres");
                String[] media_genres = mg.split(","); //place each genre into a list
                MediaProfilePage m = new MediaProfilePage(rs.getInt("mediaID"), rs.getString("title"),
                        rs.getString("mediaType"), rs.getString("summary"), media_genres);

                pages.add(m);

            }
            if (pages.isEmpty())
                throw new SQLException("No objects created");
            else
                return pages;

        } catch (SQLException ex) {
            //ex.printStackTrace();
            //System.out.println("#  ERROR :  " + ex);
            throw new SQLException("Error while fetching MPP data using specified Type");
        }

    }
    /**
     * Returns a list of MediaProfilePage objects that have the associated genre within its DB entry
     * YOU CAN MODIFY QUERY TO PULL ROWS WITH SPECIFIC TYPE AND GENRE SINCE SOME TYPES HAVE OVERLAPPING GENRES
     */
    public ArrayList<MediaProfilePage> get_genrePages(Connection conn, String genre) throws SQLException{
        String sqlReq = "SELECT *, INSTR(genres, \"" + genre + "\") gen FROM mediaTitles WHERE gen > 0";
        //Option 2
        //could be '' instead of ""
        //String sqlReq = "SELECT * FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\"";


        ArrayList<MediaProfilePage> pages = new ArrayList<MediaProfilePage>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                String mg = rs.getString("genres");
                String[] media_genres = mg.split(","); //place each genre into a list
                MediaProfilePage m = new MediaProfilePage(rs.getInt("mediaID"), rs.getString("title"),
                        rs.getString("mediaType"), rs.getString("summary"), media_genres);

                pages.add(m);

            }
            if (pages.isEmpty())
                throw new SQLException("No objects created");
            else
                return pages;

        } catch (SQLException ex) {
            //ex.printStackTrace();
            //System.out.println("#  ERROR :  " + ex);
            throw new SQLException("Error while fetching MPP data using specified Genre");
        }
    }


    /**
     * Adds specified media title (ID) to User's favorite/watchlist
     */
    public void add_titleToFavorites(Connection var1, int mediaID, int accountID) throws Exception {
        String ID = Integer.toString(mediaID);
        //could be '' instead of ""
        String var3 = "UPDATE accounts SET favorites = favorites || \"" + ID + "\" WHERE userID = \"" + accountID + "\"";

        try {
            PreparedStatement var4 = var1.prepareStatement(var3);
            var4.executeUpdate();
        } catch (SQLException var5) {
            System.out.println("#  ERROR :  " + var5);
            throw new Exception("Could not update account favorites");
        }
    }







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
