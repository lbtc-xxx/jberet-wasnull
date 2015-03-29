package app;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DataEntryBatchlet extends AbstractBatchlet {

    @Inject
    @BatchProperty
    String url;

    @Override
    public String process() throws Exception {
        try (Connection cn = DriverManager.getConnection(url)) {

            try (Statement st = cn.createStatement()) {
                st.execute("drop table my_table");
            } catch (Exception e) {
                // nop
            }

            try (Statement st = cn.createStatement()) {
                st.execute("create table my_table (col1 integer, col2 varchar(32))");
            }

            try (PreparedStatement ps = cn.prepareStatement("insert into my_table (col1, col2) values (?, ?)")) {
                ps.setInt(1, 1);
                ps.setString(2, "COL1 should be 1");
                ps.executeUpdate();

                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setString(2, "COL1 should be NULL");
                ps.executeUpdate();
            }
        }
        return null;
    }
}
