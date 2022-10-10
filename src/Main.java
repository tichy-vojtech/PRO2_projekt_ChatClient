import models.chatClients.ChatClient;
import models.Message;
import models.chatClients.inMemoryChatClient;
import models.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        //test();
        MainFrame window = new MainFrame(800,600);
    }

    private static void test(){

        System.out.println("Hello world!");

        ChatClient client = new inMemoryChatClient();

        client.login("TichyV");

        System.out.println(client.isAuthenticated());

        client.sendMessage("Hi!");
        client.sendMessage("Už nevim.");

        for (Message msg :
                client.getMessages()) {
            System.out.println(msg.getText());
        }
        client.logout();
        System.out.println(client.isAuthenticated());
    }
}