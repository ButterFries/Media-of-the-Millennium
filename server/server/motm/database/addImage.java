package server.motm.database;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.*;


/* Run from commandline,  takes one argument : the path to the image flie
 */

public class addImage
{

    public static void main(String[] args) throws Exception {
        AppDatabase db = new AppDatabase();
        Connection conn = db.connect();
        String img_path = args[0];
        System.out.println("Adding Image to last row insert;  path to img ::  \""+img_path+"\"");

        System.out.println("--adding img to database");
        try{
            db.add_image(conn, img_path);
            System.out.println("----successfully added media to database");        
            conn.close();
        }catch (Exception e){
            System.out.println("----error occurred :  "+e);        
            System.exit(1);
        }
    }
}



