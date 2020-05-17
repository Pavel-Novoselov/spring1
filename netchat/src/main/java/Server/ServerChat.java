package Server;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

@Service
public class ServerChat {
    private Vector<ClientsHandler> clients;
    private static final Logger admin = Logger.getLogger("admin");
    private ClientsHandler clientsHandler;

    public ServerChat() {
    }

    public void start() throws SQLException {
        clients=new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try{
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Server started");
            admin.info("Server started");
            while (true){
                socket = server.accept();
                System.out.println("Client connected");
                clientsHandler.setSocket(socket);
                clientsHandler.setServ(this);
                clientsHandler.start();
                admin.info("New client connected");
            }
        } catch (IOException e){
            e.printStackTrace();
            admin.error(e.getMessage());
        } finally {
            try {
                socket.close();
                admin.info("Соединение с клиентом закрыто");
            } catch (IOException e){
                e.printStackTrace();
                admin.error(e.getMessage());
            }
            try {
                server.close();
                admin.info("Сервер завершил работу");
            } catch (IOException e) {
                e.printStackTrace();
                admin.error(e.getMessage());
            }
            AuthService.disconnect();
        }
    }

    public void broadcastMsg(ClientsHandler from, String msg) throws SQLException {
        for (ClientsHandler o: clients) {
            if (!from.checkBlackList(o.getNick()))
            o.sendMsg(from.getNick() + ": " + msg);
        }
        AuthService.saveMsg(LocalDateTime.now(), from.getNick(), msg);
    }
//приватная переписка
    public void privatMsg(String nickname, String msg){
        for (ClientsHandler o: clients) {
            String nick = o.getNick();
            if(nick.equals(nickname)){
                o.sendMsg(msg);
            }
        }
    }
//проверка уникален ли НИК
    public boolean isNickUnic (String nickname){
        for (ClientsHandler o: clients) {
          //  String nick = o.getNick();
            if(o.getNick().equals(nickname)){
               return false;
            }
        }
        return true;
    }
//рассылка списка онлайн
    public void broadcastClientsList(){
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist ");
        for (ClientsHandler o: clients) {
            sb.append(o.getNick()+" ");
        }
        String out = sb.toString();
        for (ClientsHandler o: clients) {
            o.sendMsg(out);
        }
    }

    public void subscribe(ClientsHandler client){
        clients.add(client);
        broadcastClientsList();
    }

    public void unsubscribe(ClientsHandler client){
        clients.remove(client);
        broadcastClientsList();
    }
}
