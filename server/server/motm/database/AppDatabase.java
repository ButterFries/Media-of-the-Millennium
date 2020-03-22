package server.motm.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import org.json.*;

import server.motm.utils.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;

public class AppDatabase {
    public static String dbPath = "./server/motm/database/appdatabase.db";
    private static String saltshaker = "||password_security++"; //using PBKDF2, this is extra and isn't necessary


    public AppDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    //only use if this class makes the handling decisions, otherwise just make new class object and call methods
    private void handler(String args) {    //(if using client-server-db model)
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
    public Connection connect(/*args*/) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON; ");
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return conn;
    }

    /*
     * This method safely disconnects to the database.
     */
    public void disconnect(Connection conn) {
        try {
            conn.close();
        } catch (SQLException close_exception) {
            System.out.println("#  ERROR:  error when closing db connection :  " + close_exception);
            /* handle it */
        }
    }

    /*
     * this is an example, not to be used at runtime but rather use it as a
     * coding template
     */
    private /*public*/ void write_to_DB(Connection conn, String args) throws Exception {
        String sqlReq = "INSERT OR REPLACE INTO table (attr_1, attr_2) VALUES(?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, "attribute 1 value");
            pstmt.setString(2, "attribute 2 value");
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            //ex.printStackTrace(); 
            throw new Exception("some error :  " + ex);
        }
    }

    /*
     * this is an example, not to be used at runtime but rather use it as a
     * coding template
     */
    private /*public*/ String read_from_DB(Connection conn, String args) throws Exception {
        String sqlReq = "SELECT * FROM table WHERE example = 'something'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) { //found something
                String attribute = "some_attr";
                String value = rs.getString(attribute);
                return value;
            } else { //didnt find
                throw new Exception("some error");
            }
        } catch (SQLException ex) {
            //ex.printStackTrace(); 
            throw new Exception("some error :  " + ex);
        }
    }


//==============================================================================
//###   main testing   ###
//==============================================================================
    /* for testing database functions alone
     * the pwd should be the same as if launching the server
     */

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("AppDatabase running main");

        //put code to test here
        //
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

        private String favorites;
        private String bookmarks;


        public accountInfo(int id, String name, String email, String hash) {
            this.userID = id;
            this.username = name;
            this.email = email;
            this.passwordHash = hash;

            this.favorites = "";
            this.bookmarks = "";
        }

        //overloading
        public accountInfo(int id, String name, String email, String hash, String fav, String bm) {
            this.userID = id;
            this.username = name;
            this.email = email;
            this.passwordHash = hash;

            this.favorites = fav;
            this.bookmarks = bm;
        }

        public int get_ID() {
            return this.userID;
        }

        public String get_username() {
            return this.username;
        }

        public String get_email() {
            return this.email;
        }

        public String get_password() {
            return this.passwordHash;
        }

        public String get_favorites() {
            return this.favorites;
        }

        public String get_bookmarks() {
            return this.bookmarks;
        }
    }


    /*
     * Obtain the account info where the username matches
     */
    public accountInfo get_user_from_name(Connection conn, String username) throws SQLException {
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT * FROM accounts WHERE username = \"" + username + "\"";
        try {
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return new accountInfo(rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("passwordhash"));
            } else {
                throw new SQLException("Failed to fetch user with 'username' from 'accounts' table");
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when executing query to fetch user with 'username' from 'accounts' table :  " + ex);
        }
    }

    /*
     * Obtain the account info where the email matches
     */
    public accountInfo get_user_from_email(Connection conn, String email) throws SQLException {
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT * FROM accounts WHERE email = \"" + email + "\"";
        try {
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return new accountInfo(rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("passwordhash"));
            } else {
                throw new SQLException("Failed to fetch user with 'email' from 'accounts' table");
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when executing query to fetch user with 'email' from 'accounts' table :  " + ex);
        }
    }


    /*
     * This adds the account to the database.
     *
     * Warning!  the input validity is not checked, you must do so before calling.
     */
    public void add_account(Connection conn, String u_name, String e_addr, /*String pass_hash*/String pw) throws SQLException {
        try {
            String sqlReq = "INSERT INTO accounts (username, email, passwordhash) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);

            String pass_hash = hashingFunction(pw);

            pstmt.setString(1, u_name);
            pstmt.setString(2, e_addr);
            pstmt.setString(3, pass_hash);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to add account to database :  " + ex);
        } catch (Exception e) {
            throw new SQLException("Failed to add account to database :  " + e);
        }
    }

    /*
     * Hashes the password given a hashing function of choice and returns the hash
     * to be stored in the database.
     */
    public String hashingFunction(String pw) throws Exception {
        try {
            return generatePasswordHash.generateStrongPasswordHash(pw + saltshaker);
        } catch (Exception e) {
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
    public boolean validatePassword(Connection conn, String pw, accountInfo acc) throws Exception, SQLException {
        String storedPassword = acc.get_password();
        try {
            return validatePasswordHash.validatePassword(pw + saltshaker, storedPassword);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("An error occured when validating password with database entry");
        }
    }


    /*
     * Checks if username exists in 'accounts' table, returns true if it does exist.
     */
    public boolean usernameExists(Connection conn, String username) throws SQLException {
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT (count(*) > 0) FROM accounts WHERE username = \"" + username + "\"";
        try {
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return rs.getBoolean(1);
            } else {
                throw new SQLException("Failed to fetch query on existence of 'username' from 'accounts' table");
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when executing query on existence of 'username' from 'accounts' table :  " + ex);
        }
    }

    /*
     * Checks if email exists in 'accounts' table, returns true if it does exist.
     */
    public boolean emailExists(Connection conn, String email) throws SQLException {
        Statement stmt = conn.createStatement();
        String sqlReq = "SELECT (count(*) > 0) FROM accounts WHERE email = \"" + email + "\"";
        try {
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return rs.getBoolean(1);
            } else {
                throw new SQLException("Failed to fetch query on existence of 'email' from 'accounts' table");
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when executing query on existence of 'email' from 'accounts' table :  " + ex);
        }
    }

    /**
     * Adds specified media title (ID) to User's favorite/
     */
    public void add_titleToFavorites(Connection conn, int mediaID, String accountInfo, String accountType) throws SQLException {
        String var3 = "";
        String ID = Integer.toString(mediaID);
        if (accountType.equals("username"))
            var3 = "UPDATE accounts SET favorites = favorites || \"" + ID + ",\" WHERE username = \"" + accountInfo + "\"";
        else
            var3 = "UPDATE accounts SET favorites = favorites || \"" + ID + ",\" WHERE email = \"" + accountInfo + "\"";

        try {
            PreparedStatement var4 = conn.prepareStatement(var3);
            var4.executeUpdate();
        } catch (SQLException var5) {
            System.out.println("#  ERROR :  " + var5);
            throw new SQLException("Could not update account favorites");
        }

    }
    /**
     * Adds specified media title (ID) to User's bookmarks
     */
    public void add_titleToBookmarks(Connection conn, int mediaID, String accountInfo, String accountType) throws SQLException {
        String var3 = "";
        String ID = Integer.toString(mediaID);
        if (accountType.equals("username"))
            var3 = "UPDATE accounts SET bookmarks = bookmarks || \"" + ID + ",\" WHERE username = \"" + accountInfo + "\"";
        else
            var3 = "UPDATE accounts SET bookmarks = bookmarks || \"" + ID + ",\" WHERE email = \"" + accountInfo + "\"";

        try {
            PreparedStatement var4 = conn.prepareStatement(var3);
            var4.executeUpdate();
        } catch (SQLException var5) {
            //System.out.println("#  ERROR :  " + var5);
            throw new SQLException("Could not update account bookmarks");
        }

    }

    /**
     * Remove favorite if media title exists in user favorites
     * Note: strings are indexed from 1 in SQLite
     */
    public void remove_favorite(Connection conn, int mediaID, String accountInfo, String accountType) throws SQLException {
        String SQLreq = "";
        String mID = Integer.toString(mediaID);
        if (accountType.equals("username"))
            SQLreq = "UPDATE accounts SET favorites = replace(favorites, \"" + mID + ",\", '') WHERE username = \"" + accountInfo + "\"";
        else
            SQLreq = "UPDATE accounts SET favorites = replace(favorites, \"" + mID + ",\", '') WHERE email = \"" + accountInfo + "\"";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQLreq);
            pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            //System.out.println("#  ERROR :  "+ex);
            throw new SQLException("Failed to remove media title from user favorites");
        }

    }
    /**
     * Remove bookmark if media title exists in user bookmarks
     * Note: strings are indexed from 1 in SQLite
     */
    public void remove_bookmark(Connection conn, int mediaID, String accountInfo, String accountType) throws SQLException {
        String SQLreq = "";
        String mID = Integer.toString(mediaID);
        if (accountType.equals("username"))
            SQLreq = "UPDATE accounts SET bookmarks = replace(bookmarks, \"" + mID + ",\", '') WHERE username = \"" + accountInfo + "\"";
        else
            SQLreq = "UPDATE accounts SET bookmarks = replace(bookmarks, \"" + mID + ",\", '') WHERE email = \"" + accountInfo + "\"";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQLreq);
            pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            //System.out.println("#  ERROR :  "+ex);
            throw new SQLException("Failed to remove media title from user bookmarks");
        }

    }


    /**
    * Returns True if user already has mediaId in their favorites list (string)
     */
    public boolean hasFavorite(Connection conn, int mediaID, String accountInfo, String accountType) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            String sqlReq = "";
            String mID = Integer.toString(mediaID);
            if (accountType.equals("username"))
                sqlReq = "SELECT *, INSTR(favorites, \"" + mID + "\") fav FROM accounts WHERE fav > 0 AND username = \"" + accountInfo + "\"";
            else
                sqlReq = "SELECT *, INSTR(favorites, \"" + mID + "\") fav FROM accounts WHERE fav > 0 AND email = \"" + accountInfo + "\"";

            ResultSet rs = stmt.executeQuery(sqlReq);
            return rs.next();

        }
        catch (SQLException ex) {
            //System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when checking if user has favorited");
        }
    }

    /**
     * Returns True if user already has mediaId in their favorites list (string)
     */
    public boolean hasBookmark(Connection conn, int mediaID, String accountInfo, String accountType) throws SQLException {
        try {
            Statement stmt = conn.createStatement();

            String sqlReq = "";
            String mID = Integer.toString(mediaID);
            if (accountType.equals("username"))
                sqlReq = "SELECT *, INSTR(bookmarks, \"" + mID + "\") bk FROM accounts WHERE bk > 0 AND username = \"" + accountInfo + "\"";
            else
                sqlReq = "SELECT *, INSTR(bookmarks, \"" + mID + "\") bk FROM accounts WHERE bk > 0 AND email = \"" + accountInfo + "\"";

            ResultSet rs = stmt.executeQuery( sqlReq );
            return rs.next();
        }
        catch (SQLException ex) {
            //System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when checking if user has bookmarked");
        }
    }

    /**
     * Retrieves favorite string for given user account
     */
    public String retrieve_favorites(Connection conn, String accountInfo, String accountType) throws Exception {
        try {
            Statement var3 = conn.createStatement();

            String var4 = "";
            if (accountType.equals("username"))
                var4 = "SELECT favorites FROM accounts WHERE username = \"" + accountInfo + "\"";
            else
                var4 = "SELECT favorites FROM accounts WHERE email = \"" + accountInfo + "\"";

            ResultSet var5 = var3.executeQuery(var4);
            if (var5.next()) {
                return var5.getString("favorites");
            } else {
                throw new SQLException("Failed to retrieve user favorites, UserID: " + accountInfo);
            }
        } catch (SQLException var6) {
            throw new SQLException("An error occurred when fetching user favorites :  " + var6);
        }
    }

    /**
     * Retrieves bookmarks string for given user account
     */
    public String retrieve_bookmarks(Connection conn, String accountInfo, String accountType) throws Exception {
        try {
            Statement var3 = conn.createStatement();

            String var4 = "";
            if (accountType.equals("username"))
                var4 = "SELECT bookmarks FROM accounts WHERE username = \"" + accountInfo + "\"";
            else
                var4 = "SELECT bookmarks FROM accounts WHERE email = \"" + accountInfo + "\"";

            ResultSet var5 = var3.executeQuery(var4);
            if (var5.next()) {
                return var5.getString("bookmarks");
            } else {
                throw new SQLException("Failed to retrieve user bookmarks, UserID: " + accountInfo);
            }
        } catch (SQLException var6) {
            throw new SQLException("An error occurred when fetching user bookmarks :  " + var6);
        }
    }

    /**
     * retrieves title (only)  for given media ID, maybe retireve images later
     */
    public String retrieve_title(Connection conn, String mediaID) throws Exception {
        try {
            Statement var3 = conn.createStatement();
            String var4 = "SELECT (title) FROM mediaTitles WHERE mediaID = \"" + mediaID + "\"";
            ResultSet var5 = var3.executeQuery(var4);
            if (var5.next()) {
                return var5.getString("title");
            } else {
                throw new SQLException("Failed to retrieve user title, mediaID: " + mediaID);
            }
        } catch (SQLException var6) {
            throw new SQLException("An error occurred when fetching title give mediaID (favorites) :  " + var6);
        }
    }


    /*
     * TODO add update methods for username and email (low priority)
     */


//==============================================================================
//###   mediaTitles   ###
//==============================================================================

    /* not used at the moment */
    public static enum mediaType {
        cinema,
        music,
        tvseries,
        videogame,
        novel
    }
    /*
    if mediaType.cinema then "cinema"
    else if mediaType.music then "music"
    else if mediaType.tvseries then "tvseries"
    else if mediaType.videogame then "videogame"
    else if mediaType.novel then "novel"
     */



    public void add_image(Connection conn, String img) throws IOException, SQLException{
        try {
            String sqlReq = "UPDATE mediaTitles SET image = (?) WHERE mediaID = last_insert_rowid()";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setBytes(1, getByteArrayImage(img));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to add image :  " + ex);
        }
    }
 

    /*
     * Adds the media title to the database (careful duplicates are possible!)

     * genres and tags should be delimited by comma
     */

    public void add_media_title(Connection conn, String title, String mediaType, String summary, String genres, String tags, String links, String img) throws IOException, SQLException{
        String sqlReq = "INSERT INTO mediaTitles (title, mediaType, summary, genres, tags, links, image) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, title);
            pstmt.setString(2, mediaType);
            pstmt.setString(3, summary);
            pstmt.setString(4, genres); //NOT SURE IF U SET STRING MIGHT HAVE TO LOOP?
            pstmt.setString(5, tags);
            pstmt.setString(6, links);
            pstmt.setBytes(7, getByteArrayImage(img));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to add media title :  " + ex);
        }
    }

    /*
     * Adds the media title to the database (careful duplicates are possible!)
     */
    public void add_media_title(Connection conn, String title, String mediaType, String summary, String[] genres) throws SQLException {
        String sqlReq = "INSERT INTO mediaTitles (title, mediaType, summary, genres) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, title);
            pstmt.setString(2, mediaType);
            pstmt.setString(3, summary);
            pstmt.setString(4, String.join(",", genres));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Failed to add media title :  " + ex);
        }
    }

    /*
     * Adds the media title to the database (careful duplicates are possible!)
     */
    public void add_media_title(Connection conn, String title, String mediaType, String summary, String genres, String[] tags) throws SQLException {
        String sqlReq = "INSERT INTO mediaTitles (title, mediaType, summary, genres, tags) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, title);
            pstmt.setString(2, mediaType);
            pstmt.setString(3, summary);
            pstmt.setString(4, String.join(",", genres));
            pstmt.setString(5, String.join(",", tags));
            pstmt.executeUpdate();
        } 
        catch (SQLException ex) { 
            throw new SQLException("Failed to add media title :  "+ex);
        }
        finally {
            conn.close();
        }
    }

    public byte[] getByteArrayImage(String img) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		//URL imageURL = null;
		try {
			//imageURL = new URL(url);
            //URLConnection ucon = imageURL.openConnection();
			is = new FileInputStream(img);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] data = new byte[50];

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int current = 0;
			while ((current = bis.read(data,0,data.length)) != -1) {
				buffer.write(data,0, current);
			}

			return buffer.toByteArray();
		} catch (IOException e) {
			//System.err.printf("Failed while reading bytes from %s: %s", imageURL.toExternalForm(), e.getMessage());
			e.printStackTrace();
			// Perform any other exception handling that's appropriate.
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

    public byte[] get_picture(Connection conn, int mediaID) throws SQLException, Exception, SQLDataException {
        
        String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = "+mediaID;
        JSONObject json = new JSONObject();
        JSONObject common = new JSONObject();
        JSONObject distinct = new JSONObject();
        String mediaType = null;
        ByteArrayOutputStream buffer = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            // write binary stream into file
			while (rs.next()) {

			    //fos = new FileOutputStream();
                InputStream input = rs.getBinaryStream("image");
                byte[] data = new byte[50];
                buffer = new ByteArrayOutputStream();
			    int current = 0;
			    while ((current = input.read(data,0,data.length)) != -1) {
				    buffer.write(data,0, current);
			    }
            }
            return buffer.toByteArray();
        } catch (SQLException ex) {
            throw new Exception("Error while fetching common info using mediaID ["+mediaID+"]:  "+ex);
        }
			
    }


    /*
     * Get the media info for that title, including the common info and the type specific info.
     * Returns the info as a JSON object.
     */
    public JSONObject get_all_media_Info(Connection conn, int mediaID) throws SQLException, Exception, SQLDataException {
        String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = "+mediaID;
        JSONObject json = new JSONObject();
        JSONObject common = new JSONObject();
        JSONObject distinct = new JSONObject();
        String mediaType = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            if (rs.next()) { //found something
                mediaType = rs.getString("mediaType");
                for(int i = 1; i <= cols; i++){
                    json.put(md.getColumnName(i), rs.getObject(i)+"");
                    //common.put(md.getColumnName(i), rs.getObject(i)+"");
                }
            } else
                throw new SQLException("Failed to fetch with mediaID [" + mediaID + "] from 'mediaTitles' table");
        } catch (SQLException ex) {
            throw new Exception("Error while fetching common info using mediaID [" + mediaID + "]:  " + ex);
        }
        // String sqlReq2 = null;
        // if (mediaType.equals("cinema"))
        //     sqlReq2 = "SELECT * FROM cinemaInfo WHERE mediaID = "+mediaID;
        // else if (mediaType.equals("music"))
        //     sqlReq2 = "SELECT * FROM musicInfo WHERE mediaID = "+mediaID;
        // else if (mediaType.equals("tvseriesInfo"))
        //     sqlReq2 = "SELECT * FROM tvseriesInfo WHERE mediaID = "+mediaID;
        // else if (mediaType.equals(""))
        //     sqlReq2 = "SELECT * FROM videogameInfo WHERE mediaID = "+mediaID;
        // else if (mediaType.equals("novel"))
        //     sqlReq2 = "SELECT * FROM novelInfo WHERE mediaID = "+mediaID;
        // else 
        //     throw new SQLDataException("!! CRITICAL ::  mediaID ["+mediaID+"] with invalid mediaType: "+mediaType+"");
        // try {
        //     Statement stmt = conn.createStatement();
        //     ResultSet rs = stmt.executeQuery(sqlReq2);
        //     ResultSetMetaData md = rs.getMetaData();
        //     int cols = md.getColumnCount();
        //     if (rs.next()) { //found something
        //         for(int i = 1; i < cols; i++){
        //             distinct.put(md.getColumnName(i), rs.getObject(i)+"");
        //         }
        //     }
        //     else
        //         throw new SQLDataException("!! CRITICAL ::  mediaID ["+mediaID+"] not found in table for mediaType: "+mediaType+"");
        // } catch (SQLException ex) {
        //     throw new Exception("Error while fetching type info using mediaID ["+mediaID+"]:  "+ex);
        // }
        //json.put("common", common);
        //json.put("distinct", distinct);
        return json;
    }

 



    /**
     * Returns a list of n (default 50) randomly chosen mediaIDs that contain the search string in its title.
     */
    public ArrayList<Integer> get_mediaIDs_by_search(Connection conn, String search_query, int n) throws SQLException {

        if (n < 1) n = 1;
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE title LIKE \"%" + search_query + "%\" COLLATE NOCASE ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while searching matching media titles using query string [" + search_query + "] :  " + ex);
        }
    }


    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated mediaType
     * from all available objects with the mediaType
     */
    public ArrayList<Integer> get_mediaIDs_by_type(Connection conn, String mediaType, int n) throws SQLException {
        if (n < 1) n = 1;
        //String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID in (SELECT mediaID FROM mediaTitles WHERE mediaType = \"" + mediaType + "\" ORDER BY RANDOM() LIMIT "+n+")";
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE mediaType = \"" + mediaType + "\" ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for mediaType [" + mediaType + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using mediaType [" + mediaType + "] :  " + ex);
        }
    }

    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated mediaType
     * from all available objects with the mediaType, which are not in the given list of Ids
     */
    public ArrayList<Integer> get_mediaIDs_by_type(Connection conn, String mediaType, int n, String[] already_used_IDs) throws SQLException {
        if (n < 1) n = 1;
        String avoid = Arrays.toString(already_used_IDs);
        avoid = avoid.substring(1, avoid.length() - 1); //prune []
        //String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID in (SELECT mediaID FROM mediaTitles WHERE mediaType = \"" + mediaType + "\" AND mediaID NOT IN ("+avoid+") ORDER BY RANDOM() LIMIT "+n+")";
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE mediaType = \"" + mediaType + "\" AND mediaID NOT IN (" + avoid + ") ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for mediaType [" + mediaType + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using mediaType [" + mediaType + "] :  " + ex);
        }
    }
 


    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated GENRE within its DB entry
     */
    public ArrayList<Integer> get_mediaIDs_by_genre(Connection conn, String genre, int n) throws SQLException {
        if (n < 1) n = 1;
        //String sqlReq = "SELECT *, INSTR(genres, \"" + genre + "\") gen FROM mediaTitles WHERE gen > 0";
        //String sqlReq = "SELECT * FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\"";
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\" ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for genre [" + genre + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using genre [" + genre + "] :  " + ex);
        }
    }

    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated GENRE within its DB entry
     */
    public ArrayList<Integer> get_mediaIDs_by_genre_and_type(Connection conn, String genre, String mediaType, int n) throws SQLException {
        if (n < 1) n = 1;
        //String sqlReq = "SELECT *, INSTR(genres, \"" + genre + "\") gen FROM mediaTitles WHERE gen > 0";
        //String sqlReq = "SELECT * FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\"";
        //String sqlReq = "SELECT mediaID FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\" AND mediaType = \"%" + mediaType + "%\" ORDER BY RANDOM() LIMIT " + n + "";
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\" AND mediaType = \"" + mediaType + "\" ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for genre [" + genre + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using genre [" + genre + "] :  " + ex);
        }
    }



    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated GENRE within its DB entry
     * and is not in the list of given IDs
     */
    public ArrayList<Integer> get_mediaIDs_by_genre(Connection conn, String genre, String[] already_used_IDs, int n) throws SQLException {
        if (n < 1) n = 1;
        String avoid = Arrays.toString(already_used_IDs);
        avoid = avoid.substring(1, avoid.length() - 1); //prune []
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE genres LIKE \"%" + genre + "%\" AND mediaID NOT IN (" + avoid + ") ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for genre [" + genre + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using genre [" + genre + "] :  " + ex);
        }
    } 


    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated TAG within its DB entry
     */
    public ArrayList<Integer> get_mediaIDs_by_tag(Connection conn, String tag, int n) throws SQLException {
        if (n < 1) n = 1;
        //String sqlReq = "SELECT *, INSTR(tags, \"" + tag + "\") tg FROM mediaTitles WHERE tg > 0";
        //String sqlReq = "SELECT * FROM mediaTitles WHERE tags LIKE \"%" + tag + "%\"";
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE tags LIKE \"%" + tag + "%\" ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for tag [" + tag + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using tag [" + tag + "]:  " + ex);
        }
    }

    /**
     * Returns a list of n randomly chosen mediaIDs that have the associated TAG within its DB entry
     * and is not in the list of given IDs
     */
    public ArrayList<Integer> get_mediaIDs_by_tag(Connection conn, String tag, String[] already_used_IDs, int n) throws SQLException {
        if (n < 1) n = 1;
        String avoid = Arrays.toString(already_used_IDs);
        avoid = avoid.substring(1, avoid.length() - 1); //prune []
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE tags LIKE \"%" + tag + "%\" AND mediaID NOT IN (" + avoid + ") ORDER BY RANDOM() LIMIT " + n + "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.add(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches for tag [" + tag + "]");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using tag [" + tag + "]:  " + ex);
        }
    }


    /**
     * Returns a list of 10 mediaIDs sorted by mediaID's and ordered in descending order
     */
    public JSONArray get_mediaIDs_by_NewMedia(Connection conn, String mediaType) throws SQLException {
        //if (n < 1) n = 1;
        //String sqlReq = "SELECT *, INSTR(tags, \"" + tag + "\") tg FROM mediaTitles WHERE tg > 0";
        //String sqlReq = "SELECT * FROM mediaTitles WHERE tags LIKE \"%" + tag + "%\"";
        //String sqlReq = "SELECT mediaID FROM mediaTitles ORDER BY rating DESC"; //change query to search for top rated
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE mediaType = \"" + mediaType + "\" ORDER BY mediaID DESC LIMIT 10";
        JSONArray ids = new JSONArray();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.put(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches using rating");
            else {
                System.out.println(ids);
                return ids;
            }
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using rating:  " + ex);
        }
    }

    /**
     * Returns a list of 10 mediaIDs sorted by rating and ordered in descending order
     */
    public JSONArray get_mediaIDs_by_TopRating(Connection conn, String mediaType) throws SQLException {
        //if (n < 1) n = 1;
        //String sqlReq = "SELECT *, INSTR(tags, \"" + tag + "\") tg FROM mediaTitles WHERE tg > 0";
        //String sqlReq = "SELECT * FROM mediaTitles WHERE tags LIKE \"%" + tag + "%\"";
        //String sqlReq = "SELECT mediaID FROM mediaTitles ORDER BY rating DESC"; //change query to search for top rated
        String sqlReq = "SELECT mediaID FROM mediaTitles WHERE mediaType = \"" + mediaType + "\" ORDER BY rating DESC LIMIT 10";
        JSONArray ids = new JSONArray();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                ids.put(rs.getInt("mediaID"));
            }
            if (ids.isEmpty())
                throw new SQLException("No matches using rating");
            else
                return ids;
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching mediaIDs using rating:  " + ex);
        }
    }


    /**
     * Update the media title with the specified ID with the new tags
     * <p>
     * NOTE: tags are delimited by comma, but may have a space following the comma (trim when needed)
     */
    public void update_tags(Connection conn, int mediaID, String[] new_Tags) throws SQLException {
        //get old tags
        String sqlReq = "SELECT tags FROM mediaTitles WHERE mediaID = " + mediaID;
        String old_tags = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlReq);
            while (rs.next()) { //found something
                old_tags = rs.getString("tags");
            }
        } catch (SQLException ex) {
            throw new SQLException("Error while fetching tags using mediaID [" + mediaID + "]:  " + ex);
        }
        List<String> tags = new ArrayList<String>(Arrays.asList(old_tags.split(",")));
        tags.addAll(Arrays.asList(new_Tags));
        String updated_tags = String.join(",", tags);
        //updated_tags = updated_tags.substring(1, updated_tags.length()-1); //prune []

        //update tags
        try {
            sqlReq = "UPDATE mediaTitles SET tags = (?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setString(1, updated_tags);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when updating tags : " + ex);

        }
    }
    /*
    * Adds the picture for the mediaId
    */
    public void add_picture(Connection conn, int mediaId, byte[] byteArrayImage) throws SQLException{
        try {
            //String byteString= Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            String sqlReq = "INSERT INTO pictures (mediaId, byteString) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setInt(1, mediaId);
            //pstmt.setString(2, byteString);
            pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            throw new SQLException("An error occurred when adding the picture :  "+ex);
        }
    }

    /*
    * Obtains the byte array for the picture from mediaId
    */
    public String get_picture_bytes(Connection conn, int mediaId) throws SQLException{
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM pictures WHERE mediaID = "+mediaId;
            ResultSet rs = stmt.executeQuery( sqlReq );
            if (rs.next()){
                return rs.getString("byteString");    //this should be in the table, no need to 'try/catch'
            } else {
                throw new SQLException("Failed to fetch byte array, mediaID: "+mediaId);
            }
        }
        catch (SQLException ex) {
            throw new SQLException("An error occurred when fetching media rating :  "+ex);
        }
    }



//==============================================================================
//###   ratings   ###
//==============================================================================


    public static class mediaRatingInfo {
        private float rating;
        private int numRaters;

        public mediaRatingInfo(float rating, int raters) {
            this.rating = rating;
            this.numRaters = raters;
        }

        public float get_rating() {
            return this.rating;
        }

        public int get_raters() {
            return this.numRaters;
        }
    }


    /*
     * Obtains the rating for a media title given the ID number
     */
    public int get_mediaRating(Connection conn, int mediaId) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = " + mediaId;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return rs.getInt("numRaters");    //this should be in the table, no need to 'try/catch'
            } else {
                throw new SQLException("Failed to fetch media rating, mediaID: " + mediaId);
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when fetching media rating :  " + ex);
        }
    }

    /*
     * Obtains the rating info for a media title given the ID number
     */
    public mediaRatingInfo get_mediaRatingInfo(Connection conn, int mediaId) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM mediaTitles WHERE mediaID = " + mediaId;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return new mediaRatingInfo(rs.getFloat("rating"), rs.getInt("numRaters"));
            } else {
                throw new SQLException("Failed to fetch media info, mediaID: " + mediaId);
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when fetching media info :  " + ex);
        }
    }

    /*
     * Checks if the user has rated on a media title.
     */
    public boolean hasRated(Connection conn, int userId, int mediaId) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT (count(*) > 0) FROM ratings WHERE mediaID = " + mediaId + " AND userID = " + userId;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return rs.getBoolean(1);
            } else {    //error occurred, this shouldnt happen
                throw new SQLException("Failed to fetch media info, usedID: " + userId + "    mediaID: " + mediaId);
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when checking if user has rated :  " + ex);
        }
    }


    /*
     * Updates the rating of a media title by adding/update the current users
     * rating.
     * This will also update or insert the user rated on media title relation.
     */
    public void update_mediaRating(Connection conn, int userId, int mediaId, float newRating) throws SQLException {
        if (hasRated(conn, userId, mediaId)) {
            /*user has rated on this media, handle it by updating or rejecting*/

            //if updating
            float previousRating = get_userMediaRating(conn, userId, mediaId);
            mediaRatingInfo r_i = get_mediaRatingInfo(conn, mediaId);
            float currentRating = r_i.get_rating();
            int numRaters = r_i.get_raters();
            float revertedRating = currentRating - previousRating * (1 / numRaters);
            float updatedRating = revertedRating + newRating * (1 / numRaters);
            try {
                String sqlReq = "UPDATE mediaTitles SET rating = (?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlReq);
                pstmt.setFloat(1, updatedRating);
                pstmt.executeUpdate();
                update_usersMediaRating(conn, userId, mediaId, newRating);
            } catch (SQLException ex) {
                throw new SQLException("An error occurred when updating user rating on media :  " + ex);
            }
        } else {
            add_usersMediaRating(conn, userId, mediaId, newRating);
            mediaRatingInfo r_i = get_mediaRatingInfo(conn, mediaId);
            float rating = r_i.get_rating();
            int numRaters = r_i.get_raters();
            float updatedRating = (numRaters / (numRaters + 1)) * rating + newRating * (1 / (numRaters + 1));
            try {
                String sqlReq = "UPDATE mediaTitles SET rating = (?), numRaters = (?)";
                PreparedStatement pstmt = conn.prepareStatement(sqlReq);
                pstmt.setFloat(1, updatedRating);
                pstmt.setInt(2, numRaters + 1);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new SQLException("An error occurred when adding user rating on media :  " + ex);
            }
        }
    }


    /*
     * Get the rating value a user had for the media title
     */
    public float get_userMediaRating(Connection conn, int userId, int mediaId) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM ratings WHERE mediaID = " + mediaId + " AND userID = " + userId;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                return rs.getFloat("rating");
            } else {    //rating relation doesnt exist
                throw new SQLException("Failed to fetch relation rating, usedID: " + userId + "    mediaID: " + mediaId);
            }
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when getting the rating value a user had on a media title :  " + ex);
        }

    }

    /*
     * Adds the user rated on media relation with the rating value.
     */
    private void add_usersMediaRating(Connection conn, int userId, int mediaId, float newRating) throws SQLException {
        try {
            String sqlReq = "INSERT INTO ratings (rating, userID, mediaID) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setFloat(1, newRating);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, mediaId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when adding the user rated on media relation :  " + ex);
        }
    }

    /*
     * Updates the user rated on media relation with the new rating value.
     */
    private void update_usersMediaRating(Connection conn, int userId, int mediaId, float newRating) throws SQLException {
        try {
            String sqlReq = "UPDATE ratings SET rating = (?) WHERE userID = ? AND mediaID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setFloat(1, newRating);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, mediaId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when adding the user rated on media relation :  " + ex);
        }
    }


//==============================================================================
//###   Reviews   ###
//==============================================================================

    public static class mediaReviewInfo {
        private int user_ID;
        private String username;
        private String media_ID;
        private float rating_FID;
        private float rating;
        private int review_ID;
        private String review_text;

        public mediaReviewInfo(int user_ID, String username, String media_ID, float rating_FID, int rating, int review_ID, String review_text) {
            this.user_ID = user_ID;
            this.username = username;
            this.media_ID = media_ID;
            this.rating_FID = rating_FID;
            this.rating = rating;
            this.review_ID = review_ID;
            this.review_text = review_text;
        }
//        public float get_rating(){ return this.rating; }
//        public int get_raters(){ return this.numRaters; }
    }

    public void insert_review(Connection conn, int user_ID, String username, String media_ID, int rating_FID, float rating, int review_ID, String review_text) throws SQLException {
        try {
            String sqlReq = "INSERT INTO reviews (user_ID,username media_ID,rating_FID,rating,review_ID,review_text) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setInt(1, user_ID);
            pstmt.setString(2, username);
            pstmt.setString(3, media_ID);
            pstmt.setInt(4, rating_FID);
            pstmt.setFloat(5, rating);
            pstmt.setInt(6, review_ID);
            pstmt.setString(7, review_text);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when adding the user rated on media relation");
        }
    }


    public void load_reviews(Connection conn) throws SQLException {
        try {
            JSONArray query = new JSONArray();
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT username,rating,review_text FROM reviews ORDER BY RANDOM() LIMIT 10";
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                while (rs.next()) {
                    JSONObject line = new JSONObject();
                    line.put("username", rs.getString("username"));
                    line.put("rating", rs.getInt("rating"));
                    line.put("review_text", rs.getString("review_text"));
                    query.put(line);
                }
            } else {
                throw new SQLException("An error occurred when getting the reviews on review  relation");
            }
        } catch (SQLException ex) {

            throw new SQLException("An error occurred when loading review relation");
        }
    }

    public void get_specific_review(int user_ID, Connection conn) throws SQLException {
        try {
            JSONArray query = new JSONArray();
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM reviews WHERE user_ID = ?";
            ResultSet rs = stmt.executeQuery(sqlReq);
            if (rs.next()) {
                JSONObject line = new JSONObject();
                line.put("username", rs.getString("username"));
                line.put("rating", rs.getInt("rating"));
                line.put("review_text", rs.getString("review_text"));
                query.put(line);
            } else {

                throw new SQLException("An error occurred when getting the reviews on review  relation");

            }
        } catch (SQLException ex) {

            throw new SQLException("An error occurred when getting specific review");
        }
    }

//==============================================================================
//###   Reports   ###
//==============================================================================
    public static class Report {
        private int report_ID;
        private int review_ID;
        private String report_text;

        public Report(int reportID, int reviewID, String text) {
            this.report_ID = reportID;
            this.review_ID = reviewID;
            this.report_text = text;
        }

        public int getReport_ID() {
            return report_ID;
        }

        public int getReview_ID() {
            return review_ID;
        }

        public String getReport_text() {
            return report_text;
        }
    }

    public void add_report(Connection conn, int reviewID, String accountInfo, String sessionID) throws SQLException{
        try {
            String sqlReq = "INSERT INTO reports (reviewID, accountInfo, sessionID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setInt(1, reviewID);
            pstmt.setString(2, accountInfo);
            pstmt.setString(3, sessionID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when adding the user rated on media relation");
        }
    }

    public void delete_report(Connection conn, int reportID) throws SQLException{
        try {
            String sqlReq = "DELETE FROM reports WHERE reportID = \"" + reportID + "\"";
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("An error occurred when deleting a report");
        }
    }
    /**
     * Returns True if user already made a report for specific review
     */
    public boolean hasReport(Connection conn, int reviewID, String accountInfo) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            String rID = Integer.toString(reviewID);
            String sqlReq = "SELECT * FROM reports WHERE reviewID = \"" + rID + "\" AND accountInfo = \"" + accountInfo + "\"";
            ResultSet rs = stmt.executeQuery(sqlReq);
            return rs.next();

        }
        catch (SQLException ex) {
            //System.out.println("#  ERROR :  "+ex);
            throw new SQLException("An error occurred when checking if user has favorited");
        }
    }

//==============================================================================
//###   Reviews   ###
//==============================================================================
    public static class userMediaList {

        private int listID;
        private int userID;
        private String list_name;
        private String items;
        public userMediaList(int listID,int userID,String list_name,String items){
            this.listID = listID;
            this.userID = userID;
            this.list_name =list_name;
            this.items = items;
        }
        //        public float get_rating(){ return this.rating; }
        //        public int get_raters(){ return this.numRaters; }
    }
    
    public void insert_media_item(Connection conn, int listID,int userID,String list_name,String items) throws SQLException{
        try {    
            JSONArray query = new JSONArray();
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM user_list WHERE listID = "+listID ;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if(rs.next()){
                JSONObject line = new JSONObject();
                line.put("listID",rs.getString("listID"));
                line.put("List Title",rs.getString("list_name"));
                line.put("list_items",rs.getInt("items"));
                query.put(line);
            }else{
                throw new SQLException("An error occurred when getting the reviews on review  relation");
            }
        }catch (SQLException ex) {
            throw new SQLException("An error occurred when getting specific review");
        }
    }

    public void get_user_lists(int userID,Connection conn)throws SQLException{
        try {
            JSONArray query = new JSONArray();
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT * FROM user_list WHERE userID = "+userID ;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if(rs.next()){
                JSONObject line = new JSONObject();
                line.put("listID",rs.getString("listID"));
                line.put("List Title",rs.getString("list_name"));
                line.put("list_items",rs.getInt("items"));
                query.put(line);
            }else{
                throw new SQLException("An error occurred when getting the reviews on review  relation");
            }
        }catch (SQLException ex) {
            throw new SQLException("An error occurred when getting specific review");
        }
    } 

    public void delete_item(int listID, String item,Connection conn)throws SQLException{
        try {
            String items= "";
            Statement stmt = conn.createStatement();
            String sqlReq = "SELECT items FROM user_list WHERE listID = "+listID ;
            ResultSet rs = stmt.executeQuery(sqlReq);
            if(rs.next()){
                items = rs.getString("items");
            }else{
                throw new SQLException("An error occurred when getting the reviews on review  relation");
            }
            if(items.contains(item)){
                String updated_items = items.replace(item,"");
                try{
                    String sqlReq2 = "UPDATE user_list SET items = ?" ;
                    PreparedStatement pstmt = conn.prepareStatement(sqlReq2);
                    pstmt.setString(1,updated_items);
                    pstmt.executeUpdate();
                }catch (SQLException ex){
                    throw new SQLException("An error occurred when deleting specific list item");
                }
            }else{
                System.out.println("Error");//will send back a message to the server for the code to interpet what to do with this info.
            }
        }catch (SQLException ex) {
            throw new SQLException("An error occurred when getting specific review");
        }
    }

    public void delete_list(int listID,Connection conn)throws SQLException{
        try {
            String sqlReq = "DELETE * FROM user_list WHERE listID = ?" ;
            PreparedStatement pstmt = conn.prepareStatement(sqlReq);
            pstmt.setInt(1,listID);
            pstmt.executeUpdate();
        }catch (SQLException ex) {
            throw new SQLException("An error occurred when getting specific review");
        }
    }

}
