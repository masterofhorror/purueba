/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.runt.sagir.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dsalamanca
 */
public class SQLTools {
    /**
     * Metodo que construye los Package para ser llamados
     * @param packageName
     * @param procedureName
     * @param paramCount
     * @return 
     */
    public static String buildPPackageCall(String packageName, String procedureName, int paramCount) {
        StringBuilder sb = new StringBuilder("{call "+packageName+"."+procedureName+"(");
        for (int n = 1; n <= paramCount; n++) {
            sb.append("?");
            if (n < paramCount) sb.append(",");
        }
        return sb.append(")}").toString();
    }
    
    public static String buildFunctionCall(String functionName, int paramCount){
        StringBuilder sb = new StringBuilder("{? = call" + functionName + "(");
        for (int n = 1; n <= paramCount; n++){
            sb.append("?");
            if (n < paramCount) sb.append(",");
        }
        return sb.append(")}").toString();
    }
  
    /**
     * Metodo que construye los Procedimientos Almacenados para ser llamados
     * @param procedureName
     * @param paramCount
     * @return 
     */
    public static String buildProcedureCall(String procedureName, int paramCount) {
        StringBuilder sb = new StringBuilder("{call "+procedureName+"(");
        for (int n = 1; n <= paramCount; n++) {
            sb.append("?");
            if (n < paramCount) sb.append(",");
        }
        return sb.append(")}").toString();
    }
    
    public static void close(ResultSet rs, Statement s, Connection c) {
        try { if (rs != null) rs.close(); } catch (SQLException e) {}
        try { if (s != null) s.close(); } catch (SQLException e) {}
        try { if (c != null) c.close(); } catch (SQLException e) {}
    }
}

