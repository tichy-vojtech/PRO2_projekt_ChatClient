package models.chatClients;

import models.Message;
import models.chatClients.chatFileOperations.ChatFileOperations;
import models.chatClients.database.DatabaseOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseChatClient implements ChatClient{
    private String loggedUser;
    private List<String> loggedUsers;
    private List<Message> messages;

    private List<ActionListener> listenerLoggedUsersChanged = new ArrayList<>();
    private List<ActionListener> listenerMessagesChanged = new ArrayList<>();

    private DatabaseOperations databaseOperations;

    public DatabaseChatClient(DatabaseOperations databaseOperations){
        loggedUsers = new ArrayList<>();
        messages = new ArrayList<>();
         this.databaseOperations = databaseOperations;

        messages = databaseOperations.getMessages();

    }

    @Override
    public void sendMessage(String text) {
        databaseOperations.addMessage(new Message(loggedUser, text, LocalDateTime.now()));
        raiseEventMessagesChanged();
    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers.add(userName);
       // addSystemMessage(Message.USER_LOGGED_IN, userName);
        raiseEventLoggedUsersChanged();
    }

    @Override
    public void logout() {
        loggedUsers.remove(loggedUser);
       // addSystemMessage(Message.USER_LOGGED_OUT, loggedUser);
        loggedUser = null;
        raiseEventLoggedUsersChanged();
    }

    @Override
    public Boolean isAuthenticated() {
        return loggedUser!= null;
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addActionListenerLoggedUsersChanged(ActionListener toAdd) {
        listenerLoggedUsersChanged.add(toAdd);
    }

    @Override
    public void addActionListenerMessageChanged(ActionListener toAdd) {
        listenerMessagesChanged.add(toAdd);
    }

    private void raiseEventLoggedUsersChanged(){
        for(ActionListener al :
                listenerLoggedUsersChanged){
            al.actionPerformed(new ActionEvent(this, 1, "usersChanged"));
        }
    }

    private void raiseEventMessagesChanged(){
        for(ActionListener al :
                listenerMessagesChanged) {
            al.actionPerformed(new ActionEvent(this, 1, "messagesChanged"));
        }
    }

  /**  private void addSystemMessage(int type, String userName){ //TODO message nemůže ve formátu type/userName do databáze, co s tím?
        messages.add(new Message(type, userName));
        databaseOperations.addMessage(new Message(type, userName));
        raiseEventMessagesChanged();
    }*/
}
