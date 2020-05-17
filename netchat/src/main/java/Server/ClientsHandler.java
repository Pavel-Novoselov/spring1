package Server;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Component
public class ClientsHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerChat serv;
    private String nick;
    private List<String> blackList;
    private TreeSet<String> history = new TreeSet<>();
    private static final Logger admin = Logger.getLogger("admin");

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    public void setServ(ServerChat serv) {
        this.serv = serv;
    }

    public ClientsHandler() {
        }

    public void start() {
        this.blackList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String str = in.readUTF();
                            if (str.startsWith("/reg")) {
                                String[] regTokens = str.split(" ");
                                AuthService.addNewUser(regTokens[1], regTokens[2], regTokens[3]);
                                sendMsg("/regok");
                                admin.info("Новый клиент успешно зарегистрировался");
                            }
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String currentNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (currentNick == null) {
                                    sendMsg("неверный логин/пароль");
                                    admin.warn("неудачная попытка залогиниться - неверный логин/пароль");
                                }
                                else if (!serv.isNickUnic(currentNick)) {
                                    sendMsg("пользователь с таким логином уже существует");
                                    admin.warn("неудачная попытка залогиниться - пользователь с таким логином уже существует");
                                }
                                else {
                                    sendMsg("/authok");
                                    nick = currentNick;
                                    serv.subscribe(ClientsHandler.this);
                                    System.out.println("Auth "+nick+" is OK");
                                    admin.info("Клиент "+ nick+" успешно залогинился");
                                    //загрузка черного списка
                                    ClientsHandler.this.blackList = AuthService.blackListFromDB (ClientsHandler.this);
                                    //загрузка истории
                                    history = AuthService.getHistory();
//                                        cleanHistory();
                                    sendMsg(history);
                                    break;
                                }
                            }
                    }
                    while (true){
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.startsWith("/toBlackList")){
                                String[] tokens = str.split(" ");
                                blackList.add(tokens[1]);
                                AuthService.addToBlackList(getNick(), tokens[1]);
                                sendMsg("You've added "+tokens[1]+" into Black List");
                                admin.info("Клиент "+ nick+" добавил в черный лист клиента "+tokens[1]);
                            }
                            if (str.equalsIgnoreCase("/end")) {
                                sendMsg("/clientClose");
                                break;
                            }
                            if (str.startsWith("/w")){
                                 String[] tokens = str.split(" ", 3);
                                 serv.privatMsg(tokens[1], "Privat message from "+getNick()+": "+tokens[2]);
                                 serv.privatMsg(getNick(), "Private message to "+tokens[1]+": "+tokens[2]);
                                 admin.info("Клиент "+ nick+" отправил приватное сообщение клиенту "+tokens[1]);

                            }
                        } else
                            serv.broadcastMsg(ClientsHandler.this, str);
                            admin.info("Клиент "+ nick+" отправил сообщение в чат");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    admin.error(e.getMessage()+ "Ошибка программы");
                } catch (IOException e){
                    e.printStackTrace();
                    admin.error(e.getMessage()+ "Ошибка программы");
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        admin.error(e.getMessage()+ "Ошибка программы");
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        admin.error(e.getMessage()+ "Ошибка программы");
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        admin.error(e.getMessage()+ "Ошибка программы");
                    }
                    serv.unsubscribe(ClientsHandler.this);
                    admin.info("Клиент "+nick+" вышел из чата");
                }
            }
        }).start();
    }

    public void sendMsg(String msg){
        try{
            out.writeUTF(msg);
        } catch (IOException e){
            e.printStackTrace();
            admin.error(e.getMessage()+ "Ошибка программы");
        }
    }
//перегружаем метод на отправку списка
    public void sendMsg(TreeSet<String> msg){
        try{
            out.writeUTF("History of chats:");
            for (String s:
                 msg) {
                out.writeUTF(s);
            }
            out.writeUTF("-----------------------___--");
        } catch (IOException e){
            e.printStackTrace();
            admin.error(e.getMessage()+ "Ошибка программы");
        }
    }

    public String getNick() {
        return nick;
    }

    public boolean checkBlackList(String nick){
        return blackList.contains(nick);
    }
}