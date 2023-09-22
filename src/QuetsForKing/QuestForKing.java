package QuetsForKing;

import javax.swing.*;
import java.util.HashMap;
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
    public QuestForKing(){
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

    public void game(String name)  {
        mainKey = "Start";

        Map<String, AdventureStep> myStoryMap = new HashMap();

        //Start Of Adventure
        Map options = new HashMap();
        options.put("A. Go left", "left");
        options.put("B. Go right ", "right");
        AdventureStep firstStep = new AdventureStep("You have been invited to a challenge the winner will become king of the kingdom \n" +name +
                " as you are walking on a path to the castle it splits into two. Which way do you go?" +
                "\nA. Go left \nB. Go Right", options);
        myStoryMap.put("Start", firstStep);

        //Story For going left at the crossroads
        options = new HashMap();
        options.put("A. Go in", "house");
        options.put("B. Continue on your way", "castle");
        AdventureStep leftStep = new AdventureStep("You went left and see a house what do you do? " +
                "\nA. Go in\n B. continue on your way", options);
        myStoryMap.put("left", leftStep);

        //Story for going right at the crossroads
        options = new HashMap();
        options.put("A. Jump off", "fall");
        options.put("B. Go Back", "left");
        AdventureStep cliffStep = new AdventureStep("You are walking when the path disappears and a cliff is in front of you. What do you do?" +
                "\nA. Jump off \n B. Go Back ", options);
        myStoryMap.put("right", cliffStep);

        //Story for entering the house
        options = new HashMap();
        options.put("A. Leave", "castle");
        AdventureStep houseStep = new AdventureStep("You went into the house and see nothing of interest. \n(You should probably leave before the owners come back)" +
                "\nA. Leave", options);
        myStoryMap.put("house", houseStep);

        //Story for Ariving at the castle safely
        options = new HashMap();
        options.put("A. Rule The kingdom", "dead");
        AdventureStep castleStep = new AdventureStep("You arrive at the castle and you are the only one there for the challenge. You are immediately pointed as the king." +
                "\nYou become known as " + name +" The Great and died many years later.", options);
        myStoryMap.put("castle", castleStep);


            do {

                AdventureStep step = myStoryMap.get(mainKey);

                //Main Story Menu
                if (!(mainKey=="dead") && !(mainKey=="fall")) {
                    Object[] choices = step.options.keySet().toArray();
                    Object selectedChoice = JOptionPane.showInputDialog(null, step.text,
                            "12", JOptionPane.INFORMATION_MESSAGE, castle, choices, choices[0]).toString();
                    mainKey = step.options.get(selectedChoice);

                //Dead Menu
                } else if (mainKey=="dead") {
                    JOptionPane.showMessageDialog(null, "You have died", "You Died",JOptionPane.INFORMATION_MESSAGE, skull);
                    alive = false;

                //Falling menu
                } else if (mainKey=="fall") {
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
        new QuestForKing();
    }
}
