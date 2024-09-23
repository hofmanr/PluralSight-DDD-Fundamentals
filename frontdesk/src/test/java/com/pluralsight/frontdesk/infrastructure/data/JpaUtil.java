package com.pluralsight.frontdesk.infrastructure.data;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;

import java.sql.*;

public class JpaUtil {

    Connection conn;
    Session session;
//    Statement st;

    public JpaUtil() throws Exception{
        Class.forName("org.h2.Driver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:h2:mem:vetClinicTest";

        Connection conn = DriverManager.getConnection(url, "sa", "");
        System.out.println("Got Connection.");
    }

    public JpaUtil(EntityManager em) {
        session = (Session)em.getDelegate();
    }

    public void executeSQLCommand(String sql) throws Exception {
        if (session != null) {
            MutationQuery query = session.createNativeMutationQuery(sql);
            query.executeUpdate();
        } else {
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
        }
    }

    public void checkData(String sql) throws Exception {
        if (session != null) {
            session.doWork(connection -> {
                Statement st = connection.createStatement();
                showOutcome(st.executeQuery(sql));
            });
        } else {
            Statement st = conn.createStatement();
            showOutcome(st.executeQuery(sql));
        }
    }

    private void showOutcome(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();

        for (int i = 0; i < metadata.getColumnCount(); i++) {
            System.out.print("\t" + metadata.getColumnLabel(i + 1));
        }
        System.out.println("\n-----------------------------------------------------");

        while (rs.next()) {
            for (int i = 0; i < metadata.getColumnCount(); i++) {
                Object value = rs.getObject(i + 1);
                if (value == null) {
                    System.out.print("\t       ");
                } else {
                    System.out.print("\t" + value.toString().trim());
                }
            }
            System.out.println("");
        }
    }
}
