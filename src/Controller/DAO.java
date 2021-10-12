/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
/**
 *
 * @author Admin
 */
public class DAO {
    Connection con;

    public DAO() {
        try {
            var port = 1433;
            var db = "CaroGame1";
            var user = "sa";
            var password = "giangtam8";
            var server = "ADMIN";
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(user);
            ds.setPassword(password);
            ds.setDatabaseName(db);
            ds.setServerName(server);
            ds.setPortNumber(port);
            con = (Connection) ds.getConnection();
        } catch (SQLServerException ex) {
            ex.printStackTrace();
        }
    }
    
}
