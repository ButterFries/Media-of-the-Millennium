package server.motm.database;

import java.sql.*;
import server.motm.utils.*;



public class AppDatabase{
    public static String dbPath = "./server/motm/database/appdatabase.db";
    private static String saltshaker = "||password_security++"; //using PBKDF2, this is extra and isn't necessary

    public static void main(String[] args){ //does this execute when making new class object? i dun remember
        System.out.println("AppDatabase running main");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e){ e.printStackTrace(); }
    }

    public AppDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e){ 
            e.printStackTrace();
            System.exit(1);
        }
    }


    //only use if this class makes the handling decisions, otherwise just make new class object and call methods
    private void handler(String args){ 	//(if using client-server-db model)
        //handle client requests 
        //when req is received then "communicate" with db
    }


    // the general procedure should be like
    /* AppDatabase db = new AppDatabase();
     *  Connection conn = db.connect(args)
     * ~~~do some work, like write_to_DB(conn, args)
     * db.disconnect(conn);
     */
    //try/catch the functions and print the exception messages as needed


    /*
     * This method connects to the database while enforcing foreign key 
     * constraints.
     *  It returns a Connection object.
     */
    public Connection connect(/*args*/){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON; ");
            conn.setAutoCommit(true);
        } catch (Exception e) { e.printStackTrace(); System.exit(1); }
        return conn;
    }

    /*
     * This method safely disconnects to the database.
     */
    public void disconnect(Connection conn){
        try{
            conn.close();
        } catch (SQLException close_exception){
            System.out.println("#  ERROR:  error when closing db connection");
            /* handle it */
        }
    }

    /*
     * this is an example, not to be used at runtime but rather use it as a 
     * coding template
     */ 
    private /*public*/ void write_to_DB(Connection conn, String args) throws Exception{
        String sqlReq = "INSERT OR REPLACE INTO table (attr_1, attr_2) VALUES(?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, "attribute 1 value");
            pstmt.setString(2, "attribute 2 value");
            pstmt.executeUpdate();
        } 
        catch (SQLException ex) {
            //ex.printStackTrace();
            System.out.println("#  ERROR :  "+ex);
            throw new Exception("some error");
        }
    }

    /*
     * this is an example, not to be used at runtime but rather use it as a 
     * coding template
     */ 
    private /*public*/ String read_from_DB(Connection conn, String args) throws Exception{
        String sqlReq = "SELECT * FROM table WHERE example = 'something'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){ //found something
                String attribute = "some_attr";
                String value = rs.getString(attribute);
                return value;
            } else { //didnt find
                throw new Exception("some error");
            }
        } 
        catch (SQLException ex) {
            //ex.printStackTrace();
            System.out.println("#  ERROR :  "+ex);
            throw new Exception("some error");
        }
    }




//==============================================================================
//###   accounts   ###
//==============================================================================
//see passwordHashing.txt


    public static class accountInfo {
        private int userID;
        private String username;
        private String email;
        private String passwordHash;
        
        public accountInfo(int id, String name, String email, String hash){
            this.userID = id;
            this.username = name;
            this.email = email;
            this.passwordHash = hash;
        }
        public int get_ID(){ return this.userID; }
        public String get_username(){ return this.username; }
        public String get_email(){ return this.email; }
        public String get_password(){ return this.passwordHash; } 
    }


    /*
     * Obtain the account info where the username matches
     */
    public accountInfo get_user_from_name(Connection conn, String username) throws SQLException{
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT * FROM accounts WHERE username = \""+username+"\"";   
        try {
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return new accountInfo( rs.getInt("userID"),
                                        rs.getString("username"),
                                        rs.getString("email"),
                                        rs.getString("passwordhash"));
            } else {
                throw new SQLException("Failed to fetch user with 'username' from 'accounts' table");
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when executing query to fetch user with 'username' from 'accounts' table");
        }
    }

    /*
     * Obtain the account info where the email matches
     */
    public accountInfo get_user_from_email(Connection conn, String email) throws SQLException{
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT * FROM accounts WHERE email = \""+email+"\"";   
        try {
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return new accountInfo( rs.getInt("userID"),
                                        rs.getString("username"),
                                        rs.getString("email"),
                                        rs.getString("passwordhash"));
            } else {
                throw new SQLException("Failed to fetch user with 'email' from 'accounts' table");
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when executing query to fetch user with 'email' from 'accounts' table");
        }
    }




    /*
     * This adds the account to the database.
     * 
     * Warning!  the input validity is not checked, you must do so before calling.
     */
    public void add_account(Connection conn, String u_name, String e_addr, /*String pass_hash*/String pw) throws SQLException{
        try {
            String sqlReq = "INSERT INTO accounts (username, email, passwordhash) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
        
            String pass_hash = hashingFunction( pw );    
        
            pstmt.setString(1, u_name);
            pstmt.setString(2, e_addr);
            pstmt.setString(3, pass_hash);
            pstmt.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("Failed to add account to database");
        } 
        catch (Exception e){
            System.out.println("#  ERROR :  "+e);
            throw new SQLException("Failed to add account to database");
        }
    }

    /*
     * Hashes the password given a hashing function of choice and returns the hash 
     * to be stored in the database.
     */
    public String hashingFunction(String pw) throws Exception{
        try {
            return generatePasswordHash.generateStrongPasswordHash( pw + saltshaker );
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("An error occured when hashing the password using the hashing function");
        }

    }

    /*
     * Validates the password by comparing it to the entry in the database using
     * the same hashing function or validation method of that function.
     * 
     * --modified due to change in server exchange process
     * --resumed due to change from http to https, 
     *     enabling safe transfer of plain text password
     */
    /*public boolean validatePassword_0(Connection conn, String s_pw, accountInfo acc){
        String storedPassword = acc.get_password();
        return s_pw.equals(storedPassword);
    }*/
    public boolean validatePassword(Connection conn, String pw, accountInfo acc) throws Exception, SQLException{
        String storedPassword = acc.get_password();
        try {
            return validatePasswordHash.validatePassword( pw + saltshaker , storedPassword);
        } 
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("An error occured when validating password with database entry");
        }
    }
    

    /*
     * Checks if username exists in 'accounts' table, returns true if it does exist.
     */
    public boolean usernameExists(Connection conn, String username) throws SQLException{
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT (count(*) > 0) FROM accounts WHERE username = \""+username+"\"";
        try {
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return rs.getBoolean(1);
            } else {
                throw new SQLException("Failed to fetch query on existence of 'username' from 'accounts' table");
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when executing query on existence of 'username' from 'accounts' table");
        }
    }

    /*
     * Checks if email exists in 'accounts' table, returns true if it does exist.
     */
    public boolean emailExists(Connection conn, String email) throws SQLException{
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT (count(*) > 0) FROM accounts WHERE email = \""+email+"\"";
        try {
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return rs.getBoolean(1);
            } else {
                throw new SQLException("Failed to fetch query on existence of 'email' from 'accounts' table");
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when executing query on existence of 'email' from 'accounts' table");
        }
    }

    /*
     * TODO add update methods for username and email (low priority)
     */





//==============================================================================
//###   mediaTitles   ###
//==============================================================================

//TODO this needs to be more developed when we establish a structure behind media titles
// as of now we have a very simplistic notion of a media title

    /*
     * Adds the media title to the database (careful duplicates are possible!)
     */
    public void add_media_title(Connection conn, String title, String mediaType, String summary) throws SQLException{
        String sqlReq = "INSERT INTO mediaTitles (title, mediaType, summary) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, title);
            pstmt.setString(2, mediaType);
            pstmt.setString(3, summary);
            pstmt.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("Failed to add media title");
        }
    }






//==============================================================================
//###   ratings   ###
//==============================================================================


    public static class mediaRatingInfo {
        private float rating;
        private int numRaters;

        public mediaRatingInfo(float rating, int raters){
            this.rating = rating;
            this.numRaters = raters;
        }
        public float get_rating(){ return this.rating; }
        public int get_raters(){ return this.numRaters; }
    }



    /*
     * Obtains the rating for a media title given the ID number
     */
    public int get_mediaRating(Connection conn, int mediaId) throws SQLException{
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = "+mediaId;
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return rs.getInt("numRaters");    //this should be in the table, no need to 'try/catch'
            } else {  
                throw new SQLException("Failed to fetch media rating, mediaID: "+mediaId);
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when fetching media rating");
        }
    }

    /*
     * Obtains the rating info for a media title given the ID number
     */
    public mediaRatingInfo get_mediaRatingInfo(Connection conn, int mediaId) throws SQLException{
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = "+mediaId;
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return new mediaRatingInfo(rs.getFloat("rating"), rs.getInt("numRaters"));
            } else { 
                throw new SQLException("Failed to fetch media info, mediaID: "+mediaId);
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when fetching media info");
        }
    }

    /*
     * Checks if the user has rated on a media title.
     */
    public boolean hasRated(Connection conn, int userId, int mediaId) throws SQLException{
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT (count(*) > 0) FROM ratings WHERE mediaID = "+mediaId+" AND userID = "+userId;
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return rs.getBoolean(1);
            } else {    //error occurred, this shouldnt happen
                throw new SQLException("Failed to fetch media info, usedID: "+userId+"    mediaID: "+mediaId);
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when checking if user has rated");
        }
    }



    /*
     * Updates the rating of a media title by adding/update the current users
     * rating.
     * This will also update or insert the user rated on media title relation.
     */
    public void update_mediaRating(Connection conn, int userId, int mediaId, float newRating) throws SQLException{
        if ( hasRated(conn, userId, mediaId) ){
            /*user has rated on this media, handle it by updating or rejecting*/
            
            //if updating
            float previousRating = get_userMediaRating(conn, userId, mediaId);
            mediaRatingInfo r_i = get_mediaRatingInfo(conn, mediaId);
            float currentRating = r_i.get_rating();
            int numRaters = r_i.get_raters();
            float revertedRating = currentRating - previousRating*(1/numRaters);
            float updatedRating = revertedRating + newRating*(1/numRaters);
            try {
                String sqlReq = "UPDATE mediaTitles SET rating = (?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlReq);
                pstmt.setFloat(1, updatedRating);
                pstmt.executeUpdate();
                update_usersMediaRating(conn, userId, mediaId, newRating);
            } 
            catch (SQLException ex) {
                System.out.println("#  ERROR :  "+ex);
                throw new SQLException("An error occurred when updating user rating on media");
            }
        }

        else {
            add_usersMediaRating(conn, userId, mediaId, newRating);
            mediaRatingInfo r_i = get_mediaRatingInfo(conn, mediaId);
            float rating = r_i.get_rating();
            int numRaters = r_i.get_raters();
            float updatedRating = (numRaters/(numRaters+1))*rating + newRating*(1/(numRaters+1));
            try {
                String sqlReq = "UPDATE mediaTitles SET rating = (?), numRaters = (?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlReq);
                pstmt.setFloat(1, updatedRating);
                pstmt.setInt(2, numRaters+1);
                pstmt.executeUpdate();
            } 
            catch (SQLException ex) {
                System.out.println("#  ERROR :  "+ex);
                throw new SQLException("An error occurred when adding user rating on media");
            }
        }
    }


    /*
     * Get the rating value a user had for the media title
     */
    public float get_userMediaRating(Connection conn, int userId, int mediaId) throws SQLException{
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM ratings WHERE mediaID = "+mediaId+" AND userID = "+userId;
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return rs.getFloat("rating");
            } else {    //rating relation doesnt exist
                throw new SQLException("Failed to fetch relation rating, usedID: "+userId+"    mediaID: "+mediaId);
            }
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when getting the rating value a user had on a media title");
        }

    }

    /*
     * Adds the user rated on media relation with the rating value.
     */
    private void add_usersMediaRating(Connection conn, int userId, int mediaId, float newRating) throws SQLException{
        try {
            String sqlReq = "INSERT INTO ratings (rating, userID, mediaID) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setFloat(1, newRating);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, mediaId);
            pstmt.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when adding the user rated on media relation");
        }
    }

    /*
     * Updates the user rated on media relation with the new rating value.
     */
    private void update_usersMediaRating(Connection conn, int userId, int mediaId, float newRating) throws SQLException{
        try {
            String sqlReq = "UPDATE ratings SET rating = (?) WHERE userID = ? AND mediaID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setFloat(1, newRating);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, mediaId);
            pstmt.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when adding the user rated on media relation");
        }
    }
 







}

