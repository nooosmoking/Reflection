package edu.school21.repositories;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class EmbeddedDataSource {
    private DataSource dataSource;

    public EmbeddedDataSource() {
        this.dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
