package de.tvneheim.scoreboardfx.infrastructure.persistence.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Database {
  private static final String URL = "jdbc:h2:./data/appdb";
  private static final String USER = "sa";
  private static final String PASSWORD = "";

  public static Connection connect() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
