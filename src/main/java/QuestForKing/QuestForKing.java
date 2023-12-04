package QuestForKing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.swing.*;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestForKing {

    public Map<String, AdventureStep> myStoryMap = new HashMap();
    public String mainKey;

    int fallingOptions=100;
    boolean playing = true;
    boolean alive = true;
    char playAgain;

    String name = "";
    //Images
    public ImageIcon castle = new ImageIcon("Castle.png");
    public ImageIcon cliff = new ImageIcon("cliff.png");
    public ImageIcon crown = new ImageIcon("CrownMoji.png");
    public ImageIcon skull = new ImageIcon("Skull.png");

    //Starts The Game
    public QuestForKing() throws Exception{
        while (playing) {
            name = JOptionPane.showInputDialog(null, "What should we call you adventurer? (please enter a name)");
            game(name);
            gameOver();
        }

    }

    public void gameOver(){
        if (deadCheck()) {
            playAgain =  JOptionPane.showInputDialog(null, restartGameText(deadCheck(), name)).toUpperCase().charAt(0);
        }

        if (playAgain=='Y') {
            alive = true;
        } else if (playAgain=='N') {
            JOptionPane.showMessageDialog(null, "Thanks for playing", "End Game", JOptionPane.INFORMATION_MESSAGE, crown);
            playing=false;
        }
    }

    private Map<String, AdventureStep> loadAdventure() throws Exception {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();
            // convert a JSON string to a Book object
            InputStream fileInputStream = this.getClass().getResourceAsStream("/Adventure.json");
            Map<String, AdventureStep> data = mapper.readValue(fileInputStream, new TypeReference<Map<String, AdventureStep>>() {});
            // print book
            //System.out.println(book);
            return data;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }
    public void game(String name) throws Exception {
        mainKey = "Start";

        Map<String, AdventureStep> myStoryMap = new HashMap();

        //Start Of Adventure
       myStoryMap = loadAdventure();


            do {

                AdventureStep step = myStoryMap.get(mainKey);
                System.out.println(mainKey);

                //Main Story Menu code
                if ( !mainKey.equals("dead") && !mainKey.equals("fall")) {
                    Object[] choices = step.options().keySet().toArray();
                    Object selectedChoice = JOptionPane.showInputDialog(null, step.text(),
                            "12", JOptionPane.INFORMATION_MESSAGE, castle, choices, choices[0]).toString();
                    mainKey = step.options().get(selectedChoice);

                //Dead Menu
                } else if (mainKey.equals("dead")) {
                    JOptionPane.showMessageDialog(null, "You have died", "You Died",JOptionPane.INFORMATION_MESSAGE, skull);
                    alive = false;

                //Falling menu
                } else if (mainKey.equals("fall")) {
                    for (int i=0; i<10; i++) {
                        switch (fallingOptions) {
                            case 0:
                                JOptionPane.showMessageDialog(null, "You were falling and you grabbed a tree branch\nYou climbed up the cliff and continued on the path to the left.", "Landed in a tree",JOptionPane.INFORMATION_MESSAGE, cliff);
                                i=10;
                                mainKey="left";
                                break;
                            case 1:
                                JOptionPane.showMessageDialog(null, "You fell into water and survived\n you climbed up the cliff and continued on the path to the left.", "Landed in water",JOptionPane.INFORMATION_MESSAGE, cliff);
                                i=10;
                                mainKey="left";
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "You are falling. Maybe you can land in the water, or grab onto a tree.", "You are falling", JOptionPane.INFORMATION_MESSAGE, cliff);
                                mainKey="dead";
                                fallingOptions = (int) (Math.random() * 10);
                                break;
                        }

                    }

                }
            } while (alive);





        //initialise map of keys and values where values contain an "adventure" object
        // set the start key of the adventure
        // String key = "start"
        // get adventure from the map using the key. e.g. Adventure add = map.get(key);
        // display adventure text (add.getTest());
        // display options from adventure object
        // could be a list of options in the adventure object that have string and key
        // user chooses
        // get key based on choice
        // loop back to top
    }//void with 1 parameter

    public boolean deadCheck(){
        return true;
    } //typed with no parameter

    public String restartGameText(boolean dead, String name){

        if (dead) {
            return "Game Over " +
                    "\nTo Try become king again " + name + " Please press \"Y\" else press \"N\"";
        } else return "How did this happen!?";
    } //typed with no two parameters

    public static void main(String[] args) {
        try {
            new QuestForKing();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
