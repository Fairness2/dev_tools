package lesson_125344.Server;

import lesson_125344.Server.Auth.AuthenticationService;
import lesson_125344.Server.Auth.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class Server {
    private ServerSocket socket;
    private final int portNumber = 8000;
    private final AuthenticationService authService;
    private final Set<ClientHandler> clients;
    private static Logger logger = LogManager.getLogger("serversideChatLogger");

    public Server(){
        authService = new AuthenticationService();
        clients = new HashSet<>();

        try {
            socket = new ServerSocket(portNumber);
            logger.info("Сервер запущен");
            run();
        }
        catch (IOException e){
            logger.fatal(String.format("Can't initialize server! Exception: %s%n", e.getMessage()), e);
        }
    }

    private void run() {
        while (true){
            try {
                Socket client = socket.accept();
                new ClientHandler(this, client);
            }
            catch (IOException e){
                logger.error(String.format("Can't accept client! Exception: %s%n", e.getMessage()), e);
            }
        }
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public synchronized boolean isLoginAvailable(String login) {
        return findClientByLogin(login) == null;
    }

    private synchronized ClientHandler findClientByLogin(String login) {
        Optional<ClientHandler> oClient = clients.stream().filter(client -> client.getUser().getLogin().equals(login)).findFirst();
        return oClient.orElse(null);
    }

    private synchronized ClientHandler findClientByName(String name) {
        Optional<ClientHandler> oClient = clients.stream().filter(client -> client.getUser().getName().equals(name)).findFirst();
        return oClient.orElse(null);
    }

    public synchronized void broadcast(String message){
        for (ClientHandler client: clients) {
            sendMessage(client, message);
        }
    }

    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void sendDirectMessage(String name, String message){
        ClientHandler client = findClientByName(name);
        if (client != null){
            sendMessage(client, message);
        }
    }

    private void sendMessage(ClientHandler client, String message) {
        try {
            client.sendMessage(message);
        }
        catch (IOException e){
            unsubscribe(client);
            logger.error(String.format("Не получилось отправить сообщение клиенту %s", client.getUser().getLogin()), e);
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
