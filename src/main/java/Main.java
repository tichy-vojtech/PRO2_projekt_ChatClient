import models.chatClients.ChatClient;
import models.Message;
import models.chatClients.ToFileChatClient;
import models.chatClients.chatFileOperations.ChatFileOperations;
import models.chatClients.chatFileOperations.JsonChatFileOperations;
import models.chatClients.inMemoryChatClient;
import models.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        //test();

        ChatFileOperations chatFileOperations = new JsonChatFileOperations();
        ChatClient client = new ToFileChatClient(chatFileOperations);

        MainFrame window = new MainFrame(800,600, client);
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