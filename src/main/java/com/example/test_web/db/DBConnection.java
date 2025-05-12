package com.example.test_web.db;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBConnection {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/test_web";
    private final String USER = "root";
    private final String PASSWORD = "local_password";

    public Map<String,Object> get(String sql) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Map<String,Object> resultMap = new HashMap<>();

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("JDBC Driver load Succ");

            con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("DB Connections...");
            stmt = con.createStatement();

            String selectSQL = sql;//"SELECT * FROM test_table";
            rs = stmt.executeQuery(selectSQL);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                // 각 행에서 모든 컬럼 출력
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object columnValue = rs.getObject(i);
                    resultMap.put(columnName, columnValue);
                }
            }

            stmt.close();
            con.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return resultMap;
    }

    public List<Map<String,Object>> getList(String sql) {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String,Object>> resultMapList = new ArrayList<>();

        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("JDBC Driver load Succ");

            //con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("DB Connections...");
            stmt = con.createStatement();

            String selectSQL = sql;//"SELECT * FROM test_table";
            rs = stmt.executeQuery(selectSQL);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String,Object> resultMap = new HashMap<>();
                // 각 행에서 모든 컬럼 출력
                for (int i = 1; i <= columnCount; i++) { // 컬럼 인덱스는 1부터 시작
                    String columnLabel = metaData.getColumnLabel(i);
                    Object columnValue = rs.getObject(i); // 컬럼 값 가져오기
                    resultMap.put(columnLabel, columnValue);
                }
                resultMapList.add(resultMap);
            }

            stmt.close();
            con.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return resultMapList;
    }

    public int insert(String sql) {
        Connection con = null;
        Statement stmt = null;
        int result = 0;

        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("JDBC Driver load Succ");

            con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("DB Connections...");
            stmt = con.createStatement();

            String selectSQL = sql;//"SELECT * FROM test_table";
            result = stmt.executeUpdate(sql);

            stmt.close();
            con.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }
}
