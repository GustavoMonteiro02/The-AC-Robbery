import org.academiadecodigo.bootcamp.Prompt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Game {

    private final int MAX_PLAYERS = 2;
    private final String INITIAL_NAME = "Thief ";
    private ServerSocket serverSocket;
    private final int port;
    public ArrayList<ClientHandler> players = new ArrayList();
    int playersConnected = 0;

    public Game(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("Waiting for the players to connect! (Min. 2)");

            this.serverSocket = new ServerSocket(this.port);
            ExecutorService playerThreads = Executors.newFixedThreadPool(2);

            while (playersConnected < MAX_PLAYERS) {
                Socket clientSocket = this.serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                playersConnected++;
                clientHandler.setName(INITIAL_NAME + playersConnected);
                this.players.add(clientHandler);

                System.out.println(clientHandler.getName() + ": joined.");

                for (int i = 0; i < this.players.size(); i++) {
                    playerThreads.submit(this.players.get(i));
                    Thread.currentThread().setName("Thread " + (i + 1));

                }


                }

            }


    public synchronized void sendMessage(String message) {
        for (ClientHandler player : players) {
            try {
                PrintStream printStream = new PrintStream(player.getClientSocket().getOutputStream());
                printStream.println(message);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void sendMessage(ClientHandler client, String message){
        for (ClientHandler player : players) {
            if(player == client){
                try {
                     PrintStream outMenu = new PrintStream(player.getClientSocket().getOutputStream(), true);
                     outMenu.println(message);
                } catch (IOException e) {
                throw new RuntimeException(e);
                }
            }
        }
    }

    public int getPlayersConnected() {return playersConnected;}

}








