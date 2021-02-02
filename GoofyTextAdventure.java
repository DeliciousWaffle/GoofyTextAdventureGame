import net.datastructures.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Scanner;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class GoofyTextAdventure {

    // kind of a mess because I need access to these variables across the program
    private static Scanner fileReader;
    private static PrintWriter printWriter;
    private static JButton leftChoiceButton, rightChoiceButton;
    private static JLabel storyLabel;
    private static Position<StoryNode> p;
    private static LinkedBinaryTree<StoryNode> storyTree;
    private static boolean gameOver, gameReset;
    private static boolean wentLeft, wentRight;
    
    public static void main(String[] args) {
        
        createWindow();
        
        LinkedBinaryTree<StoryNode> story = createStory();
        
        playStory(story);
    } 
    
    public static void createWindow() {
       
        // used later for choices and story text
        Font font = new Font("Arial", Font.PLAIN, 30);
        
        // story, leftChoice, rightChoice initially blank just to set their proper locations
        storyLabel = new JLabel("", SwingConstants.LEFT);
        // starting story in top left of the window, credit: http://www.java2s.com/Code/Java/Swing-JFC/AsimpledemonstrationoftextalignmentinJLabels.htm
        storyLabel.setVerticalAlignment(SwingConstants.TOP);
        storyLabel.setFont(font);
        storyLabel.setBounds(10, 0, 770, 672);
        
        leftChoiceButton = new JButton();
        leftChoiceButton.setFont(font);
        leftChoiceButton.setBounds(0, 672, 400, 100);
        
        rightChoiceButton = new JButton();
        rightChoiceButton.setFont(font);
        rightChoiceButton.setBounds(400, 672, 400, 100);
        
        // listen for a click action from the user
        leftChoiceButton.addActionListener(new LeftClicked());
        rightChoiceButton.addActionListener(new RightClicked());
        
        // adding previous components into the panel
        JPanel panel = new JPanel();
        panel.add(storyLabel);
        panel.add(leftChoiceButton);
        panel.add(rightChoiceButton);

        JFrame frame = new JFrame("Goofy Survival Game");
        // add panel to the frame
        frame.setContentPane(panel);                                 
        frame.setSize(800, 800);
        // JFrame by default makes things pretty with a Flow Layout, don't want that for this
        frame.setLayout(null);   
        // position window in center of monitor                                    
        frame.setLocationRelativeTo(null); 
        frame.setResizable(false);                          
        frame.setVisible(true);
        // when user closes window, terminates program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static class LeftClicked implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            
            // note: for whatever reason, can't just change the position here and within rightClicked, so using many booleans
            // eg: p = storyTree.left(p);
            wentLeft = true;
            
            if(gameOver) {
            
                gameReset = true;
                gameOver = false;
                wentLeft = false;
            }
        }  
    }
    
    public static class RightClicked implements ActionListener {
    
        public void actionPerformed(ActionEvent e) {
            
            wentRight = true;
            
            if(gameOver) 
                System.exit(0);
        }
    }
    
    public static LinkedBinaryTree<StoryNode> createStory() {
    
        LinkedBinaryTree<StoryNode> story = new LinkedBinaryTree<>();
        
        String currentStory = "The year is 2070. Due to an ever increasing population, Earth's resources have become limited. The United States and China are currently on the brink of nuclear war. Climate change has reshaped Earth's geography for the worst. The future does not sound fun. You are lowly programmer on a mission to make the world a better place. With fast typing skills and a mountain of student loans, you are determined to make your dream a reality. You are currently asleep, dreaming about bogosort. Suddenly, you hear the sound of sirens. These are scary, legit sirens too. Do you go back to sleep or get up and check things out?";
        p = story.addRoot(new StoryNode(currentStory, "sleep", "wake", "none"));
      
        currentStory = "For whatever reason, you decide to stay in bed. That bogosort dream must have been tantalizing. Sadly, the sirens are too deafening and you have trouble falling back asleep. Your phone gets blown up with messages saying to seek shelter immediately. This can't be good. You get out of bed, but have the sudden urge to make coffee. You know this is a bad idea and should seek shelter, but the urge is strong. Do you make coffee or seek shelter?";
        p = story.addLeft(p, new StoryNode(currentStory, "shelter", "coffee", "none"));
        
        currentStory = "You enter the kitchen and make your way towards the coffee maker. You own one of those slow drip coffee makers too. As you are preparing your coffee, every ounce of your being is telling you to seek shelter. However, your desire for coffee is unfazed. Suddenly, you see a bright flash coming from your window. You think to youself, maybe it's just the sun? It's not the sun. You hear a massive explosion that shatters your eardrums. The house begins to shake and your windows shatter. Everything falls apart and you get crushed under the debris. You have died. You also didn't get a chance to drink your coffee.";        
        story.addRight(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You decide to head towards your basement to seek shelter. The basement is your natural habitat. Housing your computer and mountains of doritoes, no other area of your house feels as special. It also contains a cryogenic chamber because the narrative demands of it. You begin to pace around in circles, thinking about what you should do next. Suddenly, you hear an explosion and feel shaking. You need to decide now. The bathroom seems logical because a lot of people say to seek shelter there whenever a tornado strikes. The cryogenic chamber would be risky because you still have some bugs to work out. Entering it could result in a slow, freezing, agonizing death. Do you decide to enter the chamber or bathroom?";
        p = story.addLeft(p, new StoryNode(currentStory, "bathroom", "chamber", "none"));
        
        currentStory = "You sprint towards the bathroom, but you're having a hard time moving because of all the shaking. You almost make it, but trip over some laundry that you had lying around. To think, you might die at the hands of your own socks. You persevere and make it to the bathroom. You lock yourself inside and await what happens. While waiting, you noticed that you didn't brush your teeth yet. You start brushing your teeth using the titanium toothbrush that your parents gave you for your birthday. The explosion finally reaches your house and causes lots of shaking, but the bathroom is holding up strong! As you were finishing up, you get hit with one massive shockwave causing you to swing your arms up and hit your face. You end up stabbing yourself in the head with the toothbrush you were still holding, killing you instantly.";
        story.addLeft(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You enter the cyrogenic chamber and it automatically activates. You feel yourself getting increasingly cold. Before you blackout, you feel one last shockwave from the explosion. You eventually drift off into a deep slumber. You begin to dream about all the past cringey mistakes you have made. Remember that one time your waitress told you to enjoy your meal and you replied with you too? I'm sure you do. Eventually, you come back to your senses and wake up. Through your cyrogenic chamber's window, you make out a human-like creature walking around your basement. It gives off a faint yellow glow and appears completly docile. You slowly crack open the doors to the cryogenic chamber and make your way out. You wonder what the heck happened and why there is a glowing dude walking around your basement. It currently has its back turned away from you and there is a straight shot to your stairs. You could shout out to get its attention and figure out what's going on. Do you exit to the stairs or shout at the creature?";
        p = story.addRight(p, new StoryNode(currentStory, "exit", "shout", "none"));
        
        currentStory = "You decided to shout at this glowing creature to get its attention. It jerkingly turns its body and you can hear a lot of cracking bones. You think to yourself that it's probably just arthritis. You have no idea of what's causing the glowing, however. The creature lets out an ear-piercing screech and begins to run towards you. As it approaches, you can make out its ghoulish appearence. You obviously don't want to get to know this thing, so you begin to sprint to the stairs. As you are running, you trip and fall over some of the socks you had lying around. The creature pounces on you and begins clawing at your face and biting your arms. You blackout from blood loss and freight. The creature eats you. To think that the cause of your death was dirty laundry.";
        story.addRight(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You quietly sneak passed the glowing creature who's fixated by your anime collection and make it to the stairs. As you make your way up the stairs, you can see the sun. How's that? Once you reach the top, you realize that your living room, kitchen, and bedroom are all gone. Additionally, your surroundings appear to be slightly obstructed by a yellow hue. The surrounding houses have turned to rubble and you see a group of those glowing creatures off in the distance. You also hear a vehicle approaching and distant shouting. You immediately look for some rubble to hide behind. Eventually the vehicle arrives and it turns out to be a truck equipped with a mounted machine gun with normal-looking people standing in the back. These people are also armed with scary looking assult weapons. They notice the glowing creatures and begin shooting at them. After they finished, the people begin to look around. You don't know their motives and need to choose whether to reveal youself or hide.";
        p = story.addLeft(p, new StoryNode(currentStory, "reveal", "hide", "none"));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
        currentStory = "Thinking these guys could be dangerous scavengers, you hide behind what remains of your kitchen. They continue to look around and eventually drive off. It is safe now. You begin looking around, trying to figure out what could have possibly caused all of this destruction. You think there must have been some kind of nuclear war. You also get the sudden urge to use the bathroom. After looking around to make sure you're safe, you go ahead and release about 200 years of built up liquid rage. While unleashing Poseidon's wrath, your glowing ghoulish-looking friend from earlier has crawled its way up the stairs and notices you. It lets out a scream and begins running. You can say it caught you with your pants down. You don't have any time to react and it ends up pinning you on the ground. It eats you and you die.";
        story.addRight(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You decide to reveal yourself and shout that you mean no harm. This obviously gets their attention and they ask where you came from. You nonchalantly exclaim a cryogenic chamber. They shrug their shoulders and ask if you are a programmer. You say that you are and they start to become giddy. They say that they need a programmer to implement an incredibally inefficient algorithm in order to power their equipment. You think to yourself this is a once in a lifetime oppurtunity given your current circumstances. Right as you are about to answer, a large bear-like creature comes out of nowhere. It's horribly disfigured and lets out a horrible yell. It begins to run towards the vehicle. The people begin firing their weapons at the creature, but it has no effect. The truck drives off and you can faintly hear the people say that they will always remember you. Now the creature is staring you down. You remember reading in a survival book that if you make lots of noise, bears would usually run off. Do you make noise or run away?";
        p = story.addLeft(p, new StoryNode(currentStory, "noise", "run", "none"));
        
        currentStory = "The disfigured bear begins to run towards you and you shout at the top of your lungs. The bear pauses for a brief second. It looks dumbfounded and looks around. The bear starts charging at you again. You try to move your legs, but you are paralyzed with fear. This is the end, you think. The bear leaps up into the air and gets considerable height. This creature is going to take you down in one swoop. It ends up jumping over you. You turn around and realize that it pounced on a radioactive rabbit. Must be your lucky break. You waddle away from the hungry beast, but trip over one of your shoelaces. The bear looks up and notices you. You scramble to get up, but it's too late. The bear sumo slams you to the ground and you die.";
        story.addLeft(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You sprint as fast as you can away from the bear. In doing so, you notice a radioactive rabbit leap out of a bush. You look behind you and see that the bear is chasing after the rabbit now. You feel better, but your respite doesn't last long. Suddenly, a giant reptilian-like creature emerges from a nearby sewer pipe. It must stand atleast 12 feet tall and has claws of death, deathclaws if you will. It lunges towards the bear and rips it to shreds. There are always bigger fish. It looks at you now and lets out another horrifying roar. You pee your pants."; 
        p = story.addRight(p, new StoryNode(currentStory, "null", "pee", "none"));
        
        currentStory = "It begins running towards you, but suddenly, gets hit by the truck you saw earlier. It dealt a good blow, but the creature begins to shake it off. The truck approaches you and one of the people generically says get in if you want to live. You hop on in and you all make it out safely. The passengers introduce themselves and tell you it's been 200 years since the US and China went to war, causing all of the destruction that you see around you. They want to begin to rebuild society and hope that programmer like yourself will make a meaningful addition to the team. You end up spending the rest of your days writing poorly optimized code, but everyone around you appreciates your contribution. You live mostly happily ever after!";
        story.addRight(p, new StoryNode(currentStory, "null", "null", "none"));
        
        p = story.root();
        currentStory = "Since you are a sane individual, you decide to get up out of bed and check things out. You look at your phone for any updates. Lo and behold, nuclear war is happening. Your first instinct should be to seek shelter. However, you havn't eaten anything yet and you are hungry. There is a bagel place about 2 minutes away and they have some of the best cream cheese known to man. You can't survive a nuclear war on an empty stomach. You also think that seeking immediate shelter would probably be in your best interest. Do you seek shelter or go for that sweet bagel?";
        p = story.addRight(p, new StoryNode(currentStory, "shelter", "bagel", "none"));
        
        currentStory = "Your urge for bagels has overpowered you. You exit your house and see people running around the streets in panic. Being the incredibly dense individual that you are, you casually make your way to the bagel place. Once you make it inside, you notice that there is no one there. Strange, you think to yourself. After looking around some more, you realize there are so many of those sweet discs of joy just lying around. You begin stuffing your face, free of charge. Suddenly, you hear a massive explosion and remember that you probably should have been looking for shelter. You look around and notice a massive fridge that you could easily fit into. You also see a steel cabinet that's just your size. Do you seek shelter in the fridge or cabinet?";
        p = story.addRight(p, new StoryNode(currentStory, "fridge", "cabinet", "none"));
        
        currentStory = "You get into the fridge. It turns out to be one of those outlawed fridges that only opens from the outside. In other words, you're trapped. You hear an explosion off in the distance and the fridge begins to shake violently. Milk, eggs, and other food items slap you around. The full force of the explosion hits you and sends the fridge flying. You can feel yourself going airborne. Eventaully the fridge hits the ground and opens in such a way that you emerge unharmed. You make your way out of the fridge and see an explosion off in the distance. The shockwave must have sent you flying outside of the city. As you gather your thoughts, a plunger, probably traveling at Mach 3, impales you through the chest. You die.";
        story.addLeft(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You decide to stuff yourself into the steel cabinet. Why it's made out of steel will forever remain a mystery to you. When you open it, you realize that it's storing kitchen knives. You go ahead and remove all the knives and maneuver your way inside. It's dark in there. You wait and begin to think about all the poor choices you've made in life and how you regret not spending more time with family and friends. You think that after this is all over with, you will turn over a new leaf. Sadly this won't happen because our protagonist is about to get with a big dose of reality. A large shockwave strikes the bagel place and violently shakes everything including the cabinet. The cabinet appears to be holding up fine though. However, you didn't do a good job of getting rid of the knives. A remaining bread knife strikes from out of the shadows due to the force of the shockwave and stabs you in the head. You die.";
        story.addRight(p, new StoryNode(currentStory, "null", "null", "none"));
        
        p = story.parent(p);
        currentStory = "You decide that breakfast can wait, seeking immediate shelter is more important. You are on a rationale roll. You begin thinking about the safest location in your house when suddenly, your door gets smashed down. It turns out to be a person who bullied you in highschool. He exclaims that he wants to kick your ass one last time. He is also holding a machete. You notice that you left a toy lightsaber lying on your counter. There's no way you can use this as a weapon to defend yourself, running away seems to be the most valid option here. Do you grab the lightsaber to defend yourself or run away and hope for the best?";
        p = story.addLeft(p, new StoryNode(currentStory, "lightsaber", "run", "none"));
        
        currentStory = "You grab the lightsaber and think about how much of an absurd situation this is. Your bully starts laughing hysterically and says that he is going to enjoy this. You're not going to take that. After years of abuse, you channel your inner jedi and Duel of the Fates begins playing in your head. He charges at you and in retaliation, you do a corkscrew through the air, holding your lightsaber above your head, all while making a horrifying noise. You strike with incredibal ferocity and look animalistic. Your strike was so strong that you impale your lightsaber through his chest. He dies instantly. You feel at peace but see an explosion off in the distance and need to find shelter fast. While heading towards the basement, you trip over some laundry and fall down the stairs. You suffer a lot of damage and die. Atleast you died knowing you overcame your bully.";
        story.addLeft(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You run away from your bully. He laughs and starts to chase after you. You exit to the backyard of your house. You have quite the green thumb, so most of your backyard is a garden. You hide behind one of your avocado trees that you typically use to make the guacamole of champions. Your bully makes it outside and spots you. You have nothing to defend yourself with and get the idea to throw avocados at him. You could also run away and hide in better location. Do you throw the avocados at him or hide in a better location?";
        p = story.addRight(p, new StoryNode(currentStory, "throw", "hide", "none"));
        
        currentStory = "You begin to quickly pick avocados from your tree. They are as hard as rocks. Your bully is dumbstruck by the fact that you are picking avocados at a time like this. You get a handful and start to throw them. All of them horribly miss except for one. This one avocado looks like it's about to him in the head, but he slices it mid-air with his machete. Avocado splatters all over his face. He starts to laugh and taunt you. Suddenly, he stops and his face turns red. He collapses to the ground becuase I guess he's apparently allergic to avocados. You let out a heavy sigh. Everything is ok. In the distance, a nuclear bomb goes off and you turn to ash in a fraction of a second.";
        story.addLeft(p, new StoryNode(currentStory, "null", "null", "none"));
        
        currentStory = "You decide to run away and hide in a better location. He proceeds to chase after you, but you run faster. You spot a saguaro cactus that you've been growing for some reason, and hide behind that. It's big enough to cover your whole body and you don't think that your bully spotted you. Sadly, you are wrong. The bully approaches the cactus and cuts it down with his machete. When he does this, an explosion goes off and sends out a massive shockwave. The shockwave causes this massive saguaro to fall on him. The bully dies by cactus. You are thrown aback by the shockwave and hit your head on a rock, knocking you unconcious. Eventually, the explosion reaches and you die.";
        story.addRight(p, new StoryNode(currentStory, "null", "null", "none"));
        
        return story;
    }
    
    public static void playStory(LinkedBinaryTree<StoryNode> storyTree) {
        
        p = storyTree.root();
        Scanner sc = new Scanner(System.in);
        // essentially while not gameover, do stuff
        while(true) {
        
            if(gameReset) {
            
                p = storyTree.root();
                gameReset = false;
            }
            
            if(wentLeft) {
            
                p = storyTree.left(p);
                wentLeft = false;
            }
            
            if(wentRight) {
            
                p = storyTree.right(p);
                wentRight = false;
            }
        
            // need to retrieve the story node in order to see the text and choose an option
            StoryNode currentNode = p.getElement();
                
            // also need to extract the information from the node itself so that the user can see it
            String story = currentNode.getStory();
            String leftChoice = currentNode.getLeftChoice();
            String rightChoice = currentNode.getRightChoice();

            // this is super goofy, without the <html> at the beginning and end, won't properly show the String
            // don't know any html, can only assume this how it starts and ends with a body in the middle
            // credit: https://stackoverflow.com/questions/1842223/java-linebreaks-in-jlabels
            storyLabel.setText("<html>" + story + "<html>");
            
            if(leftChoice.equals("null")) {
            
                rightChoiceButton.setText(rightChoice);
                // don't want the left choice as an option
                leftChoiceButton.setBounds(0, 0, 0, 0);
            }
            
            else if(rightChoice.equals("null")) {
            
                leftChoiceButton.setText(leftChoice);
                rightChoiceButton.setBounds(0, 0, 0, 0);
            }
            
            // just display the options normally
            else {
            
                // choices might have been previously null, need to resize
                leftChoiceButton.setBounds(0, 672, 400, 100);
                rightChoiceButton.setBounds(400, 672, 400, 100);
                
                leftChoiceButton.setText(leftChoice);
                rightChoiceButton.setText(rightChoice);
            }
                           
            // the game is over! (since both options are null, then there must be no children, thus game over)
            if(leftChoice.equals("null") && rightChoice.equals("null")) {

                gameOver = true;
                
                while(gameOver){
             
		    storyLabel.setText("<html>" + story + " Would you like to play again?" + "<html>");
                
                    // again, choices might have been previously null, need to resize
                    leftChoiceButton.setBounds(0, 672, 400, 100);
                    rightChoiceButton.setBounds(400, 672, 400, 100);
                
                    leftChoiceButton.setText("Yes!");
                    rightChoiceButton.setText("No!");
		}    
            } 
        }        
    }
}
