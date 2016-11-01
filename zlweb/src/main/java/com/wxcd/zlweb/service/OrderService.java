package com.wxcd.zlweb.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wenjusun on 6/27/16.
 */
public class OrderService {
    private DataSource dataSource = null;

    public OrderService() {
        dataSource = initDataSource();
    }

    public void addStock(int productId, int inStock) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO stock (pid,instock) values(?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, productId);
                pstmt.setInt(2, inStock);

                pstmt.execute();

            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void updateStock_nolock(int productId, int num) {
        long startTime = System.currentTimeMillis();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            int inStock = -1;

            String sql = "UPDATE stock SET instock=instock-? WHERE pid=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, num);
                pstmt.setInt(2, productId);
                pstmt.executeUpdate();

                conn.commit();
                conn.setAutoCommit(true);

            } catch (SQLException sqle1) {
                conn.rollback();
                conn.setAutoCommit(true);
                sqle1.printStackTrace();
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     *
     *
     */
    @Deprecated
    public void updateStock_withlock_old(int productId, int num) {
        long startTime = System.currentTimeMillis();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            int inStock = -1;

            String sql = "SELECT instock FROM stock WHERE pid=? FOR UPDATE ";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, productId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    inStock = rs.getInt(1);
                    rs.close();
                }
            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }

            if (inStock - num >= 0) {
                sql = "UPDATE stock SET instock=? WHERE pid=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, inStock - num);
                    pstmt.setInt(2, productId);
                    pstmt.executeUpdate();

                    conn.commit();
                    conn.setAutoCommit(true);

                } catch (SQLException sqle1) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    sqle1.printStackTrace();
                }
            }
            else{
                throw new RuntimeException("Out of stock...");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }

    public void updateStock_withlock(int productId, int num) {
        long startTime = System.currentTimeMillis();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            int inStock = -1;

            String sql = "SELECT pid,instock FROM stock WHERE pid=? FOR UPDATE ";
            try (PreparedStatement pstmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE)) {
                pstmt.setInt(1, productId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    inStock = rs.getInt(1);

                    if (inStock - num >= 0) {
                        rs.updateInt(1,inStock-num);
                        rs.updateRow();
//                        conn.commit();
//                        conn.setAutoCommit(true);
                    }
                    else{
                        throw new RuntimeException("Out of stock...");
                    }

                }
                rs.close();
                conn.commit();

            } catch (SQLException sqle1) {
                sqle1.printStackTrace();
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }

    /**
     * @param userId    The user Id.
     * @param productId the id of the target production
     * @num how many will be ordered by userId
     */
    public void placeOrder(int userId, int productId, int num) {
        long startTime = System.currentTimeMillis();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO orders(userid,pid,num)values(?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                pstmt.setInt(3, num);

                pstmt.executeUpdate();

                conn.commit();

            } catch (SQLException sqle1) {
                conn.rollback();
                conn.setAutoCommit(true);
            }

            conn.setAutoCommit(true);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);


    }

    private DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Failed to initialize pool: CLIENT_PLUGIN_AUTH is required
     */
    private DataSource initDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        String jdbcUrl = "jdbc:mysql://localhost:3306/api_log?useSSL=false";
        String username = "root";
        String password = "";

        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(250);
//        hikariConfig.setAutoCommit(false);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;

    }

    public void closeDataSource() {
        ((HikariDataSource) dataSource).close();
    }

}
