/*
   CS351, Fall 2018
   Covert
   Provided StoryNode for Graph based game
   Optiional Hwk 5
*/
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class GraphStoryNode implements Iterable<String> {
   private String storyText;
   private ArrayList<String> items;
   
   public GraphStoryNode(String text, String stuff){
      storyText = text; // story data
      items = new ArrayList<>();
      // 2nd item is comma separated list of items initially at
      // this location
      String[] things = stuff.split(",");
      for( String s : things )
         items.add(s);
   }
   
   public String getStory(){
      return storyText;
   }
   
   // attempts to get item s from this location
   // returns true if such an item is at this
   // location, false otherwise.
   // you must manage the player's inventory; i.e.
   // the fact that they now hold this item
   public boolean getItem( String s ){
      if( !items.contains(s) )
         return false;
      items.remove( s );
      return true;
   }
   
   // adds an item to this location
   // you handle removing it from player inventory
   public void putItem( String s ){
      items.add(s);
   }
   
   // utility method; does this location have an item named s
   public boolean contains( String s ){
      return items.contains(s);
   }   
   
   // allows user to iterate over the items at this
   // location; e.g. to print them
   public Iterator<String> iterator(){
      return items.iterator();
   }
   
   @Override
   public String toString(){
      return storyText.substring(0,10);
   }
     
}