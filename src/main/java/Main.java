import models.chatClients.ChatClient;
import models.Message;
import models.chatClients.DatabaseChatClient;
import models.chatClients.ToFileChatClient;
import models.chatClients.api.ApiChatClient;
import models.chatClients.chatFileOperations.ChatFileOperations;
import models.chatClients.chatFileOperations.JsonChatFileOperations;
import models.chatClients.database.DatabaseOperations;
import models.chatClients.database.DbInitializer;
import models.chatClients.database.JdbcDatabaseOperations;
import models.chatClients.inMemoryChatClient;
import models.gui.MainFrame;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //test();
        String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String databaseUrl = "jdbc:derby:ChatClientDb_skA";

        DbInitializer dbInitializer = new DbInitializer(databaseDriver, databaseUrl);
        //dbInitializer.init();

        ChatFileOperations chatFileOperations = new JsonChatFileOperations();
        DatabaseOperations databaseOperations = new JdbcDatabaseOperations(databaseDriver, databaseUrl);
        ChatClient client = new ToFileChatClient(chatFileOperations);

       /* Class<ApiChatClient> reflectionExample = ApiChatClient.class;
        List<Field> fields = getAllFields(reflectionExample);

        System.out.println("Class name: " + reflectionExample.getSimpleName() + "|" + reflectionExample.getName());

        for(Field f : fields){
            System.out.println(f.getName() + " : " + f.getType());
        }*/

        MainFrame window = new MainFrame(800, 600, client);
    }

    private static void test() {

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

    private static List<Field> getAllFields(Class<?> cls) {
        List<Field> fieldList = new ArrayList<>();
        for (Field f : cls.getDeclaredFields()) {
            fieldList.add(f);
        }
        return fieldList;
    }
}