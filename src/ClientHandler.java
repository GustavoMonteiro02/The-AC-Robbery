import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerRangeInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private ArrayList<ClientHandler> players;
    private String name;
    private Game game;

    private PrintStream outMenu;
    private Prompt prompt;
    private MenuInputScanner menuScanner;

    public ClientHandler(Socket socket, Game game) throws IOException {
        this.clientSocket = socket;
        this.game = game;
        this.players = game.players;

        outMenu = new PrintStream(clientSocket.getOutputStream(), true);
        prompt = new Prompt(clientSocket.getInputStream(), outMenu);
    }

    public Socket getClientSocket() {
        return this.clientSocket;
    }


    public void setName(String name) {
        this.name = name;
        Thread.currentThread().setName(this.name);
    }

    public String getName() {
        return this.name;
    }


    @Override
    public void run() {



        String[] menu1Options = {"Friday", "Saturday", "Sunday", "Wednesday"};

        menuScanner = new MenuInputScanner(menu1Options);
        menuScanner.setMessage("You wake up to the sound of the alarm, and you know it's the big day. " + "\n" + "Those sweet Macs will be finally yours." + "\n" + "\n" + "You look at the calendar, what day of the week is today?" + "\n");

        int menu1AnswerIndex = prompt.getUserInput(menuScanner);
        try {
            clientSocket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (prompt.getUserInput(menuScanner) != menu1AnswerIndex) {
            menuScanner.setError("Pick a number from 1 to 4");

        }


        String[] menu2Options = {"Between 2am and 5am", "Between 5am and 9:30am", "Between 9:30am and 22pm", "Between 22pm and 2am"};
        menuScanner = new MenuInputScanner(menu2Options);
        menuScanner.setMessage("What time you're going to break in?");
        int menu2AnswerIndex = prompt.getUserInput(menuScanner);
        if (prompt.getUserInput(menuScanner) != menu2AnswerIndex) {
            menuScanner.setError("Pick a number from 1 to 4");

        if(game.getPlayersConnected() == 2) {
            StringInputScanner question1 = new StringInputScanner();
            game.sendMessage("You wake up to the sound of the alarm, and you know it's the big day. " + "\n" + "Those sweet Macs will be finally yours." + "\n" + "\n" + "You look at the calendar, what day of the week is today?" + "\n");
            String dayOfTheWeek = prompt.getUserInput(question1);

            game.sendMessage("------------------------------------------------------------------------------------------------");
            IntegerInputScanner question2 = new IntegerRangeInputScanner(0, 24);
            game.sendMessage("\n" + "\n" + "It's " + dayOfTheWeek + ", as you planned all along. You know you need to be smart, and choose wisely the right time for the approach." + "\n" + "What time you're going to break in? (between 0 and 24)");
            int hours = prompt.getUserInput(question2);

            game.sendMessage("------------------------------------------------------------------------------------------------");
            game.sendMessage("\n" + "\n" + hours + " o'clock is the perfect time. Nothing can go wrong. Nothing will go wrong. " + "\n" +  "You head to Jesus Street, number 27." + "\n"  + "You arrive and look at the building. I haven't been there since your bootcamp. But you remember exactly how It was.");
            game.sendMessage("\n" + "You verify a couple of alternatives to go inside. What do you choose? (by numeral)");


            String[] menuOptions1 = {"1. The Front Door.", "2. The Windows.", "3. The Sewer.", "The Chimney (Pai-Natal Style)."};
            menuScanner = new MenuInputScanner(menuOptions1);


            int wayIn = prompt.getUserInput(menuScanner);


            if (wayIn == 1) {
                game.sendMessage("------------------------------------------------------------------------------------------------");
                game.sendMessage("\n" + "You open the Front Door. somehow it's unlocked. You suddenly remember how The Code Cadets were stupid sometimes, and keep forgetting about safety measures. ");
                game.sendMessage("------------------------------------------------------------------------------------------------");
                game.sendMessage( "\n" + "You enter the Apple Sponsored Room and look deeply at those sweet pieces of technology. A cold chill goes down your spine as you rejoice!!! You grab the first MAC you see and run to the door." + "\n" + "You need to vanish. But, where?");
                game.sendMessage("------------------------------------------------------------------------------------------------");
                String[] menuOptions2 = {"1. Lajes Airport.", "2. Praia da Vitoria Docks.", "3. Your Mom's House.", "4. My Mom's House."};

                menuScanner = new MenuInputScanner(menuOptions2);
                int wayOut = prompt.getUserInput(menuScanner);


                if (wayOut == 1) {
                    game.sendMessage("------------------------------------------------------------------------------------------------");
                    game.sendMessage("\n" + "You run to the airport, loosing your shoes on the sprint!" + "\n" + "YOU FAILED.");

                } else if (wayOut == 2) {
                    game.sendMessage("------------------------------------------------------------------------------------------------");
                    game.sendMessage("\n" + "You go down to the docks, as fast as your short legs allow. By the shore, you meet your smuggler friend: CANOAS. You hop on his Duck Shaped Paddle Boat, and you both paddle safely to CUBA." + "\n" + "You live happily ever after, alongside your brand new (used) Mac." + "\n" + "\n" + "YOU WIN.");

                } else if (wayOut == 3) {
                    game.sendMessage("------------------------------------------------------------------------------------------------");
                    game.sendMessage("\n" + "You arrive at you mom's house and explains the situation. She immediately starts to beat the crap out of you. Running from your mom, you reach the nearest police station, where you confess and ask for shelter." + "\n" + "YOU FAILED");

                } else if (wayOut == 4) {
                    game.sendMessage("------------------------------------------------------------------------------------------------");
                    game.sendMessage("\n" + "You reach my mom's house and say you know me, and we are very close. My mom immediately realizes you're lying. She grabs her flip-flops and, before you know it, you got a good hit in the head and falls unconscious. Never f*** with my mom, dude." + "\n" + "YOU FAILED.");

                } else {
                    game.sendMessage("------------------------------------------------------------------------------------------------");
                    game.sendMessage("\n" + "Please, choose a number!");
                }


            } else if (wayIn == 2) {
                game.sendMessage("------------------------------------------------------------------------------------------------");
                game.sendMessage("\n" + "You try to open the Windows, and realise it's all fake. I guess they take this Mac-Only-Policy pretty serious." + "\n" + "\n" + "YOU FAILED.");

            } else if (wayIn == 3) {
                game.sendMessage("------------------------------------------------------------------------------------------------");
                game.sendMessage(this, "\n" + "You reach your hand on the toilet boarder, squizzing yourself out of the pipes. When suddenly you recognize JOANA'S stare. She swings the toilet cleaner against your skull, and the Casa de Banho goes dark." + "\n" + "\n" + "YOU FAILED.");



            } else if (wayIn == 3) {
                game.sendMessage("------------------------------------------------------------------------------------------------");
                game.sendMessage("\n" + "You climb the outside walls, like some sort of creepy, unemployed, spider-man, just to discover the AC doesn't have a Chimney. How dumb are you? Did you really study there?" + "\n" + "\n" + "YOU FAILED.");

            } else {

                game.sendMessage("\n" + "Please, choose a number!");
            }
        }
        }
    }

        /*if (game.getPlayersConnected() == 2) {
            try {
                menu1();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                menu2();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }*/





/*public void message1() throws IOException {
        Set<String> days = new HashSet();
        days.add("Segunda");
        days.add("segunda");
        days.add("Terça");
        days.add("terça");
        days.add("quarta");
        days.add("Quarta");
        days.add("quinta");
        days.add("Quinta");
        days.add("Sexta");
        days.add("sexta");
        days.add("Sábado");
        days.add("sábado");
        days.add("domingo");
        days.add("Domingo");


        StringInputScanner question1 = new StringInputScanner();
        question1.setMessage("You wake up to the sound of the alarm, and you know it's the big day. " + "\n" + "Those sweet Macs will be finally yours." + "\n" + "\n" + "You look at the calendar, what day of the week is today?" + "\n");

       String firstQuestion = prompt.getUserInput(question1);
        StringSetInputScanner answers = new StringSetInputScanner(days);
        if (clientSocket.getInputStream() != days)
            question1.setError("Pick a decent day moron");
        }*/


// (this, "You wake up to the sound of the alarm, and you know it's the big day. " + "\n" + "Those sweet Macs will be finally yours." + "\n" + "\n" + "You look at the calendar, what day of the week is today?");

//  StringInputScanner question1 = new StringInputScanner();

//   String dayOfTheWeek = prompt.getUserInput(question1);


//  game.sendMessage("\n" + "\n" + "It's " + dayOfTheWeek + ", as you planned all along. You know you need to be smart, and choose wisely the right time for the approach." + "\n" + "\n" + "What time you're going to break in? (between 0 and 24)");

      /*  IntegerInputScanner question2 = new IntegerRangeInputScanner(0, 24);

        int hours = prompt.getUserInput(question2);


        game.sendMessage(hours + " is the perfect time. Nothing can go wrong. Nothing will go wrong. " + "\n" + "\n" + "You head to Jesus Street, number 27." + "\n" + "\n" + "You arrive and look at the building. I haven't been there since your bootcamp. But you remember exactly how It was.");
        game.sendMessage("\n" + "You verify a couple of alternatives to go inside. What do you choose? (by numeral)");

        String[] menuOptions1 = {"1. The Front Door.", "2. The Windows.", "3. The Sewer.", "The Chimney (Pai-Natal Style)."};
        menuScanner = new MenuInputScanner(menuOptions1);

        int wayIn = prompt.getUserInput(menuScanner);


        if (wayIn == 1) {

            game.sendMessage("\n" + "You open the Front Door. somehow it's unlocked. You suddenly remember how The Code Cadets were stupid sometimes, and keep forgetting about safety measures. ");

            game.sendMessage(this, "\n" + "You enter the Apple Sponsored Room and look deeply at those sweet pieces of technology. A cold chill goes down your spine as you rejoice!!! You grab the first MAC you see and run to the door." + "\n" + "You need to vanish. But, where?");

            String[] menuOptions2 = {"1. Lajes Airport.", "2. Praia da Vitoria Docks.", "3. Your Mom's House.", "4. My Mom's House."};

            menuScanner = new MenuInputScanner(menuOptions2);
            int wayOut = prompt.getUserInput(menuScanner);


            if(wayOut == 1){

            game.sendMessage("\n" + "You run to the airport, loosing your shoes on the sprint!" + "\n" + "YOU FAILED.");

            }else if(wayOut == 2){

            game.sendMessage("\n" + "You go down to the docks, as fast as your short legs allow. By the shore, you meet your smuggler friend: CANOAS. You hop on his Duck Shaped Paddle Boat, and you both paddle safely to CUBA." +"\n" + "You live happily ever after, alongside your brand new (used) Mac." + "\n" + "\n" + "YOU WIN.");

            }else if(wayOut == 3){

            game.sendMessage("\n" + "You arrive at you mom's house and explains the situation. She immediately starts to beat the crap out of you. Running from your mom, you reach the nearest police station, where you confess and ask for shelter." + "\n" + "YOU FAILED");

            }else if(wayOut == 4){

            game.sendMessage("\n" + "You reach my mom's house and say you know me, and we are very close. My mom immediately realizes you're lying. She grabs her flip-flops and, before you know it, you got a good hit in the head and falls unconscious. Never f*** with my mom, dude." + "\n" + "YOU FAILED.");

            }else {

            game.sendMessage("\n" + "Please, choose a number!");

            }


        } else if (wayIn == 2) {

            game.sendMessage("\n" + "You try to open the Windows, and realise it's all fake. I guess they take this Mac-Only-Policy pretty serious." + "\n" + "\n" + "YOU FAILED.");

        } else if (wayIn == 3) {

            game.sendMessage(this, "\n" + "You reach your hand on the toilet boarder, squizzing yourself out of the pipes. When suddenly you recognize JOANA'S stare. She swings the toilet cleaner against your skull, and the Casa de Banho goes dark." + "\n" + "\n" + "YOU FAILED.");

            game.sendMessage("\n" + "");

        } else if (wayIn == 3) {

            game.sendMessage("\n" + "You climb the outside walls, like some sort of creepy, unemployed, spider-man, just to discover the AC doesn't have a Chimney. How dumb are you? Did you really study there?" + "\n" + "\n" + "YOU FAILED.");

        } else {
            game.sendMessage("\n" + "Please, choose a number!");
        }
    }*/
