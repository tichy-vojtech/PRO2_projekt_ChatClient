package models.gui;

import models.Message;
import models.chatClients.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    ChatClient chatClient;
    private JTextField txtInputName;
    private JTextField txtInputMessage;
    private JTextArea txtChat;
    public MainFrame(int width, int height, ChatClient chatClient){
        super("ChatClient");
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chatClient = chatClient;
        initGui();
        setVisible(true); //set visible až po přípravě GUI!!!
    }
    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(),BorderLayout.CENTER);
        panelMain.add(initLoggedUsersPanel(), BorderLayout.EAST);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);

        add(panelMain);
    }
    private JPanel initLoginPanel(){
        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLogin.add(new JLabel("Username"));
        txtInputName = new JTextField("", 30);
        panelLogin.add(txtInputName);
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("login clicked - " + txtInputName.getText());

                if(chatClient.isAuthenticated()) {
                    chatClient.logout();
                    btnLogin.setText("Login");
                    txtInputName.setEditable(true);
                    txtInputName.setText("");
                    txtChat.setEnabled(false);
                    txtInputMessage.setEnabled(false);
                }
                else {
                    String userName = txtInputName.getText();
                    if(userName.length()<1){
                        JOptionPane.showMessageDialog(null, "Enter your username!","Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    chatClient.login(txtInputName.getText());
                    btnLogin.setText("Logout");
                    txtInputName.setEditable(false);
                    txtChat.setEditable(true);
                    txtInputMessage.setEnabled(true);

                }
            }
        });
        panelLogin.add(btnLogin);

        return panelLogin;
    }
    private JPanel initChatPanel(){
        JPanel panelChat = new JPanel();
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.X_AXIS));

        txtChat = new JTextArea();
        txtChat.setEditable(false);
        txtChat.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(txtChat);

        panelChat.add(scrollPane);

        chatClient.addActionListenerMessageChanged(e -> {refreshMessages();});

        return panelChat;
    }
    private JPanel initMessagePanel(){
        JPanel panelMessage = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtInputMessage = new JTextField("", 50);
        txtInputMessage.setEnabled(false);
        panelMessage.add(txtInputMessage);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            String messageText = txtInputMessage.getText();
            System.out.println("btnSend clicked" + messageText);
            if(messageText.length()<1){
                return;
            }
            if(!chatClient.isAuthenticated()){return;}
            chatClient.sendMessage(messageText);
            txtInputMessage.setText("");
           // refreshMessages();
        });
        panelMessage.add(btnSend);

        return panelMessage;
    }
    private void refreshMessages(){
        if(!chatClient.isAuthenticated()){return;}

        txtChat.setText("");
        for(Message msg :
        chatClient.getMessages()){
            txtChat.append(msg.toString());
        }
    }
    private JPanel initLoggedUsersPanel(){
        JPanel panel = new JPanel();

        /*Object[][] data = new Object[][]{
                {"1,1", "1,2"},
                {"2,1", "2,2"},
                {"SDSD", "aa"}
        };
        String[] columnNames = new String[] {"Col A", "Col B"};

        JTable tblLoggedUsers = new JTable(data, columnNames);*/

        JTable tblLoggedUsers = new JTable();

        LoggedUsersTableModel loggedUsersTableModel = new LoggedUsersTableModel(chatClient);
        tblLoggedUsers.setModel(loggedUsersTableModel);

        chatClient.addActionListenerLoggedUsersChanged(e -> {
            loggedUsersTableModel.fireTableDataChanged();});

        JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
        scrollPane.setPreferredSize(new Dimension(250,500));
        panel.add(scrollPane);
        return panel;
    }
}
