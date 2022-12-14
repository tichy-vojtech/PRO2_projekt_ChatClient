package models.chatClients.database;

import models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class JdbcDatabaseOperations implements DatabaseOperations{
    private final Connection connection;

    public JdbcDatabaseOperations(String driver, String url) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url);
    }

    @Override
    public void addMessage(Message message) {
        try {
            Statement statement = connection.createStatement();
            String sql =
                    "INSERT INTO ChatMessages (author, text, created) "
                    + "VALUES ("
                    +"'" + message.getAuthor() + "', "
                    +"'" + message.getText() + "', "
                    +"'" + Timestamp.valueOf(message.getCreated()) + "' "
                    +")";
            statement.executeUpdate(sql);
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM ChatMessages";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Message message = new Message(resultSet.getString("author"), resultSet.getString("text") +
                        resultSet.getString("created"));
                messages.add(message);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}
