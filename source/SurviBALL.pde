                                              /*          Robert Scully          */
                                              /*          C12405568              */
                                              /*          DT228/2                */
                                             
//Music player setup
import ddf.minim.*;

AudioPlayer player;//Initializes the Audioplayer
AudioPlayer song, song1, song2, song3, song6;//Initializes 6 song variables
Minim minim, minim1, minim2, minim3, minim6;//Initializes 6 minim variables

//Initializes images
PImage gamebackground;
PImage menubackground;


//Initializes States
final int stateMenu = 0;
final int stateClassic = 1;
final int stateBurst = 2;
final int stateRapidFire = 3;
final int stateYouWillDie = 6;
int state = stateMenu;

//Classes Initialization
Player p;
Timer t;

//Initializes Array Lists
ArrayList<Ball> balls_array;
ArrayList<Hunter> hunter_array;

//Initialize the Various Score Variables
int score = 0;

int highscore1 = 0;
int highscore2 = 0;
int highscore3 = 0;
int highscore6 = 0;

/*
int topscore1 = 0;
int topscore2 = 0;
int topscore3 = 0;
int topscore6 = 0;

String ts1 = str(topscore1);

String[] topscores;  This is apart of my failed attempt to implement a savestring function to save the top score in each mode
*/


void setup() {
  size(500, 600);//Sets size to 500 pixels x 600 pixels  (Odd sizing to prevent repetitive bouncing of balls caused by squares)
  background(0);//Sets background to black as default
  smooth();
  frameCount=60;//Initialize fps to 60

  menubackground = loadImage("Exploding_Sky_by_MyPlaceAtDeviantART.jpeg"); //Background image for the menu
  gamebackground = loadImage("colours.jpg");//Background image for the games
  
  //topscores = loadStrings("topscores.txt");//Load the best scores to a string array 
  
  //Music player setup
  minim = new Minim(this);
  minim1 = new Minim(this);
  minim2 = new Minim(this);
  minim3 = new Minim(this);
  minim6 = new Minim(this);
  
  //Song file setup
  song = minim.loadFile("Dark Space Intro 8-bit NES-Style Chiptune.mp3", 2048);//loads the menu screen song
  song1 = minim.loadFile("Carter_Outbreak - Dead Feelings.mp3", 2048);
  song2 = minim.loadFile("Most awesome 8-bit song ever.mp3",2048);
  song3 = minim.loadFile("LHS _ DFS - Fast Lane.mp3",2048);
  song6 = minim.loadFile("Dragonforce-Through the Fire and Flames.mp3",2048);
  
  //Initializes new timer
  t = new Timer();

  //Initializes New Player and Player spawn
  p = new Player(250,500);

  //Initializes balls_array and hunter array
  balls_array=new ArrayList<Ball>();
  hunter_array=new ArrayList<Hunter>();

}//End setup


void draw() {//Main draw which contains a switch statement that calls different cases which have individual draws depending on what state the user is in
  
  switch(state) {
    case stateMenu://Default Menu State
      drawForStateMenu();
      break;
      
    case stateClassic://First game mode the Classic state
      drawForStateClassic();
      break;
      
    case stateBurst://Second game mode the Burst state
      drawForStateBurst();
      break;
      
    case stateRapidFire: //Last game mode the Rapid Fire state
      drawForStateRapidFire();
      break;
      
    case stateYouWillDie://Easter Egg game mode with ridiculous spawn rates
      drawForStateYouWillDie();
      break;
      
    default:
    break;
  }//switch ends
}//draw ends


void reset(){
  score = 0;//Resets the players score which is based off of array_list.size
  t.time ="000";//resets the time string so the central timer resets
  
  balls_array.clear();//Resets the balls array
  hunter_array.clear();//Resets the hunter Array

  
  if(p.dead==true){
  p = new Player (250,500);//Resets the Player
  }
}

