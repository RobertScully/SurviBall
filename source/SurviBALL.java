import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SurviBALL extends PApplet {

                                              /*          Robert Scully          */
                                              /*          C12405568              */
                                              /*          DT228/2                */
                                             
//Music player setup


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


public void setup() {
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


public void draw() {//Main draw which contains a switch statement that calls different cases which have individual draws depending on what state the user is in
  
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


public void reset(){
  score = 0;//Resets the players score which is based off of array_list.size
  t.time ="000";//resets the time string so the central timer resets
  
  balls_array.clear();//Resets the balls array
  hunter_array.clear();//Resets the hunter Array

  
  if(p.dead==true){
  p = new Player (250,500);//Resets the Player
  }
}

class Ball {
  float diamB; //Ball size
  float xB,yB;//Balls location variable 
  float xspeed,yspeed; // Speed variable
  int ball_colour = color(random(50,250),random(50,250),random(50,250));//Randomizes the colour of each ball that is spawned
  
  //Constructor
  Ball(float temp) {
    diamB = temp;
    xB = width/2;//Ball location variable initiated at the centre of the sketch
    yB = height/2;//Ball location variable initiated at the centre of the sketch
    xspeed = random(-4,4) ;//Set speed on the x and y plane as random between -4,4
    yspeed = random(-4,4) ;//This causes the ball to spawn travelling in a random direction
  }
  
  //Updates balls position by incrementing x/y position by speed and detects walls and reverses direction causing bouncing
  public void move() {
    xB += xspeed; //Increment x by speed giving balls motion
    yB += yspeed; //Increment y by speed giving balls motion
    
   //Balls check for edges. Upon reaching edge sets speed to opposite which switches direction
    if (xB > width || xB < 0) {
      xspeed *= -1;
    }
    if (yB > height || yB < 0) {
      yspeed *= -1;
    }
  }
  
  public void collide() {//Detects if a collision has occurred and sets player to dead on collision ending the game
    if(sqrt (sq(p.x1Pos-xB) + sq(p.y1Pos-yB)) < (p.diamP + diamB)/2){// If the squareroot of players x and y position - balls x and y position squared is less than the diameter of player + ball set dead to true.
     p.dead=true;
    }
  }
  
  //Balls drawn  
  public void display() {
    smooth();
    stroke(0);//Sets edge of ball colour to black
    fill(ball_colour);//Sets colour of ball randomly
    ellipse(xB,yB,diamB,diamB);
  }
}
//Hunter subclass of ball. This class creates a ball that hunts the player down preventing them from hiding in a corner

class Hunter {
  PVector location;
  PVector velocity;
  PVector acceleration;
  float topspeed;
  float diamH;
  
  Hunter(float temp) {
    location = new PVector(250,200);// Sets spawn of The hunter
    velocity = new PVector(-1,1);// Sets speed of hunter to 1
    topspeed = 1;// Restricts hunter from passing top speed of 1
    diamH = temp;
  }
  
  public void hunting() {
    PVector player = new PVector(p.x1Pos,p.y1Pos);
    PVector dir = PVector.sub(player,location); // Find vector pointing towards the player
    dir.normalize();//Normalize
    dir.mult(0.5f);//Scale
    acceleration = dir;//Set to acceleration
    
    velocity.add(acceleration);//Velocity changes acceleration
    velocity.limit(topspeed);//Limits the top speed to 1, Except in Rapid fire wheres its custom set to 2
    location.add(velocity);//Velocity changes location
  }
  
  public void display() {
    stroke(0);
    fill(255,0,0);//Sets colour of hunter to red
    ellipse(location.x,location.y,diamH,diamH);//Spawns Hunter circle
  }
  
  public void collide() {//Detects if a collision has occurred and sets player to dead on collision ending the game
    if(sqrt (sq(p.x1Pos-location.x) + sq(p.y1Pos-location.y)) < (p.diamP + diamH)/2){ // If the squareroot, of players x and y position - hunters x and y position squared, 
     p.dead=true;                                                                     //is less than the diameter of the sum of both divided by 2, set dead to true.
    }
  }
}
class Player {
  float x1Pos, y1Pos;
  float diamP = 16;//Sets players diameter to 8
  float speed = 4;//Sets player speed to 4
  boolean moveLeft, moveRight, moveUp, moveDown = false;//Defaults move to false
  public boolean dead;//Public bool for whether the player is alive or dead

  Player(float xP, float yP)
  {
    x1Pos = xP;
    y1Pos = yP;
  }

  public void display() 
  {
    fill(255);//Sets colour of Player to White
    ellipse(x1Pos, y1Pos, diamP, diamP);//Sets position and size of Player
    x1Pos = constrain(x1Pos, 0, width);//Stops player from travelling through the sides of the screen
    y1Pos = constrain(y1Pos, 0, height);//Stops player from travelling through the top/bottom of the screen
  }

  public void move() 
  {
    if (moveLeft) x1Pos -= speed;//Treats left as negative normal speed
    if (moveRight) x1Pos += speed;//Treats right as normal speed
    if (moveUp) y1Pos -= speed;//Treats up as negative normal speed
    if (moveDown) y1Pos += speed; //Treats down as normal speed
  }
}

   class Timer {
  String time = "000";
  int interval = 1000;//Sets interval to 1000 = 1 seconds
  int font_size;
  int start_time;
  
  //Constructor
  Timer() {
  }  
  
  public void time(){
    if(p.dead==false){
      if (millis() - start_time >= interval) {//Uses millis function - time since program started and compares to the interval which is set to 1000 to determine seconds
        time = nf(PApplet.parseInt(time) + 1, 3); //Utility function to format time number into a string for an easily printable timer
        start_time = millis();//Sets start time = to millis
      }
    }
  }
  
  //Displays time passed since game began in the middle of the screen
  public void display() {
    fill(22,22,222);//Sets Timer colour to Blue
    if(p.dead==false){
    textAlign(CENTER);
    text(time, width/2 , height/2);//Displays timer at the center of the sketch
    }
    if(p.dead==true){
      textAlign(CENTER);
      text("Your Time = " + time, width/2 , 200);//Displays the time you lasted below score
    }
  }
}
    
//Codes keys for control of the player on the sketch
public void keyReleased() {
  if (key == CODED) 
  {
    if (keyCode == LEFT) 
    {
      p.moveLeft = false;
    } 
    else if (keyCode == RIGHT) 
    {
      p.moveRight = false;
    } 
    else if (keyCode == UP) 
    {
      p.moveUp = false;
    } 
    else if (keyCode == DOWN) 
    {
      p.moveDown = false;
    }
  }
}

public void keyPressed() {
  if (key == CODED) 
  {
    if (keyCode == LEFT) 
    {
      p.moveLeft = true;//If left key is pressed activates player move to left
    } 
    else if (keyCode == RIGHT) 
    {
      p.moveRight = true;
    } 
    else if (keyCode == UP) 
    {
      p.moveUp = true;
    } 
    else if (keyCode == DOWN) 
    {
      p.moveDown = true;
    }
  }


  switch(state) {
    case stateMenu:
      if (key == '1') 
      {
        song.rewind();
        state = stateClassic;
      }
      else if (key == '2') 
      {
        song.rewind();
        state = stateBurst;
      }
      else if (key == '3') 
      {
        song.rewind();
        state = stateRapidFire;
      }
      else if (key == '6') 
      {
        song.rewind();
        state = stateYouWillDie;
      }
      break;   
    }
      
  if(key == 'r'|| key == 'R') 
  {
    song.rewind();
    song1.rewind();
    song2.rewind();
    song3.rewind();
    song6.rewind();
    reset();
  }
  else if(key == 'm'||key == 'M')
  {
    reset();
    song.rewind();
    song1.rewind();
    song2.rewind();
    song3.rewind();
    song6.rewind();
    state = stateMenu;
  }
  else if (key == 'q'||key == 'Q') 
  {
    exit();
  }
}



  



public void drawForStateMenu() {
  //On opening the Menu sets all songs to pause to prevent songs overlapping when returning from a gamemode to the menu
  song1.pause();
  song2.pause();
  song3.pause();
  song6.pause();
  song.play();//Plays the Menu Music
  background(menubackground);//Sets the background image to the menu image
  textSize(22);//Sets the size of the text to 22
  fill(35,255,20);//Sets the colour of the text on the menu to a green that stands out from the background image
  textAlign(CENTER);//Aligns the text to the center of the screen
  text("Press 1 to START Classic Mode", width/2, 100);
  textAlign(CENTER);
  text("Press 2 to START Burst Mode", width/2, 200);
  textAlign(CENTER);
  text("Press 3 to START Rapid Fire Mode", width/2, 300);
  textAlign(CENTER);
  text("Press Q to QUIT", width/2, 400);
}
public void drawForStateClassic() {
  song.pause();//Pauses the menu music as it is the only thing that could possibly be playing due to the pause at the start of menu
  song1.play();//Plays the Music for the Classic Gamemode
  
  background(gamebackground); //Continuously sets background to the image
  
  if(p.dead==false){
    if(balls_array.size() < 5){//Uses the size of the array to display instructions for the first 5 seconds
      textAlign(CENTER);
      text("Use the arrow keys to dodge the balls!",width/2,200);//Prints instructions slightly above the timer for the first 5 seconds
    }
  }

  //Movement and display of player. On death player is removed from the screen by the if statement
  if (p.dead==false) {
    p.move();
    p.display();
  }

  //Movement and display of hunter. On death the hunter is removed from the screen by if statement
  if (p.dead==false) {
    for (int j = hunter_array.size()-1; j >=0; j--) {
      Hunter hunter = hunter_array.get(j);
      hunter.hunting();
      hunter.display();
      hunter.collide();
    }
  }
  
  if(hunter_array.size() != 1){//Spawns 1 hunter
  hunter_array.add(new Hunter(16));
  }

  //Controls movement/display of every ball in the balls_array and detects collision. Balls continue to bounce after death
  for (int i = balls_array.size()-1; i >=0; i--) {
    Ball ball = balls_array.get(i);
    ball.move();
    ball.display();
    ball.collide();
  }

  if (p.dead==false) {//If player is alive spawns 1 ball every second
    if (millis() - t.start_time >= t.interval) {//Checks time so spawn can occur every second
      balls_array.add(new Ball(16));//Adds a new ball to the arraylist
        }
      }
  
  //Displays timer
  t.time();
  t.display();

  
  //Displays death screen
    if (p.dead==true) {//Checks if player is dead if so displays game over text and seconds survived
    score = balls_array.size();
    
    textSize(22);//Sets size of text to 22
    fill(22,22,222);//Sets colour of highscore to blue
    if(score > highscore1) highscore1 = score;//If score is greater then highscore then it sets score = to highscore.
    
    /* 
    if(highscore1 > topscore1)
    { 
      topscore1 = highscore1;
      topscores[0] = ts1;
      saveStrings("topscores.txt",topscores);
    }

    textAlign(CENTER);
    text("Top Score = " + topscore1,width/2, 50 );//Apart of my attempt to implement saveString to save the users top scores.
    */
    
    textAlign(CENTER);
    text("High Score = " + highscore1,width/2, 100 );//Displays the users High Score for this game mode and this session
    textAlign(CENTER);
    text("Your Score = "+ score, width/2, 150);//Displays the number of balls the player survived. Using .size to get the number of balls in the arraylist
    
    fill(255,0,0);//Sets game over text to red
    textAlign(CENTER);
    text("Game Over!", width/2, 300);// Displays the Game Over text.
    
    fill(35,255,20);//Sets colour of Options to the same green as the menu
    textAlign(CENTER);
    text("To Restart the Game Press R",width/2, 400);
    textAlign(CENTER);
    text("To Return to the Menu press M", width/2, 450);
    textAlign(CENTER);
    text("To Quit the Game Press Q", width/2, 500);
  }
}
public void drawForStateBurst() {
  song.pause();//Pauses the menu music
  song2.play();//Plays the Burst mode music
  
  background(gamebackground); //Continuously sets background to gamebackground image
 
  if(p.dead==false){
    if(balls_array.size() <= 12){//Uses the size of the array to display instructions for the first 4 seconds
      textAlign(CENTER);
      text("Use the arrow keys to dodge the balls!",width/2,200);//Prints instructions slightly above the timer for the first 4 seconds
    }
  }

  //Movement and display of player. On death player is removed from the screen by the if statement
  if (p.dead==false) {
    p.move();
    p.display();
  }
  
  //Movement and display of hunter. On death the hunter is removed from the screen by if statement
  if (p.dead==false) {
    for (int j = hunter_array.size()-1; j >=0; j--) {
      Hunter hunter = hunter_array.get(j);
      hunter.hunting();
      hunter.display();
      hunter.collide();
    }
  }
  
  if(hunter_array.size() != 1){//Spawns 1 hunter
  hunter_array.add(new Hunter(16));
  }

  //Controls movement/display of every ball in the balls_array and detects collision. Balls continue to bounce after death
  for (int i = balls_array.size()-1; i >=0; i--) {
    Ball ball = balls_array.get(i);
    ball.move();
    ball.display();
    ball.collide();
  }

  if (p.dead==false) {//If the player is alive spawns 3 balls every second
    if (millis() - t.start_time >= t.interval) {//Checks time so spawn can occur every second
      for (int b = 0; b < 3; b++){//For loop to spawn 3 balls
        balls_array.add(new Ball(16));//Adds a new ball to the array list
          }
        }
      }
 
  //Displays timer
  t.time();
  t.display();

  
  //Displays death screen
    if (p.dead==true) {//Checks if player is dead if so displays game over text and seconds survived
    score = balls_array.size();
    
    textSize(22);//Sets size of text to 22
    fill(22,22,222);//Sets colour of highscore to blue 
    if(score > highscore2) highscore2 = score;//If score is greater then highscore then it sets score = to highscore.
    textAlign(CENTER);
    text("High Score = " + highscore2,width/2, 100 );//Displays the users High Score for this game mode and this session
    textAlign(CENTER);
    text("Your Score = "+ score, width/2, 150);//Displays the number of balls the player survived. Using .size to get the number of balls in the arraylist
    
    fill(255,0,0);//Sets game over text to red
    textAlign(CENTER);
    text("Game Over!", width/2, 300);// Displays the Game Over text.
    
    fill(35,255,20);//Sets colour of Options to the same green as the menu
    textAlign(CENTER);
    text("To Restart the Game Press R",width/2, 400);
    textAlign(CENTER);
    text("To Return to the Menu press M", width/2, 450);
    textAlign(CENTER);
    text("To Quit the Game Press Q", width/2, 500);
  }
}
public void drawForStateRapidFire() {
  song.pause();//Pauses the menu music
  song3.play();//Plays the rapid fire mode music
  
  background(gamebackground); //Continuously sets background to background image
  
  if(p.dead==false){
    if(balls_array.size() < 10){//Uses the size of the array to display instructions for the first 5 seconds
      textAlign(CENTER);
      text("Use the arrow keys to dodge the balls!",width/2,200);//Prints instructions slightly above the timer for the first 5 seconds
    }
  }

  //Movement and display of player. On death player is removed from the screen by the if statement
  if (p.dead==false) {
    p.move();
    p.display();
  }

  //Movement and display of hunter. On death the hunter is removed from the screen by if statement
  if (p.dead==false) {
    for (int j = hunter_array.size()-1; j >=0; j--) {
      Hunter hunter = hunter_array.get(j);
      hunter.topspeed = 2;
      hunter.hunting();
      hunter.display();
      hunter.collide();
    }
  }
  
  if(hunter_array.size() != 1){//Spawns 1 hunter
  hunter_array.add(new Hunter(16));
  }

  //Controls movement/display of every ball in the balls_array and detects collision. Balls continue to bounce after death
  for (int i = balls_array.size()-1; i >=0; i--) {
    Ball ball = balls_array.get(i);
    ball.move();
    ball.display();
    ball.collide();
  }
  
  //Controls spawning of ball. One ball is spawned every 1/2 second
  if (p.dead==false) {
    if (frameCount % 30==0) {//Counts frames and divides by 30 to spawn a new Ball every half a  second
      balls_array.add(new Ball(16));
    }
  }

  //Displays timer
  t.time();
  t.display();

  //Displays death screen
    if (p.dead==true) {//Checks if player is dead if so displays game over text and seconds survived
    score = balls_array.size();
    
    textSize(22);//Sets size of text to 22
    fill(22,22,222);//Sets colour of highscore to blue
    if(score > highscore3) highscore3 = score;//If score is greater then highscore then it sets score = to highscore.
    textAlign(CENTER);
    text("High Score = " + highscore3,width/2, 100 );//Displays the users High Score for this game mode and this session
    textAlign(CENTER);
    text("Your Score = "+ score, width/2, 150);//Displays the number of balls the player survived. Using .size to get the number of balls in the arraylist
    
    fill(255,0,0);//Sets game over text to red
    textAlign(CENTER);
    text("Game Over!", width/2, 300);// Displays the Game Over text.
    
    fill(35,255,20);//Sets colour of Options to the same green as the menu
    textAlign(CENTER);
    text("To Restart the Game Press R",width/2, 400);
    textAlign(CENTER);
    text("To Return to the Menu press M", width/2, 450);
    textAlign(CENTER);
    text("To Quit the Game Press Q", width/2, 500);
  }
}
public void drawForStateYouWillDie() {
  song.pause();
  song6.play();

  background(gamebackground); //Continuously sets background to gamebackground image
  
  //Movement and display of player. On death player is removed from the screen by the if statement
  if (p.dead==false) {
    p.move();
    p.display();
  }

  //Controls movement/display of every ball in the balls_array and detects collision. Balls continue to bounce after death
  for (int i = balls_array.size()-1; i >=0; i--) {
    Ball ball = balls_array.get(i);
    ball.move();
    ball.display();
    ball.collide();
  }
  
  //Controls spawning of balls. 20 balls spawn per second. This mode is impossible.
  if (p.dead==false) {
    if (millis() - t.start_time >= t.interval) {
      for (int b = 0; b < 20; b++){
        balls_array.add(new Ball(16));
    }
  }
}
 
  //Displays timer
  t.time();
  t.display();

  //Displays death screen
    if (p.dead==true) {//Checks if player is dead if so displays game over text and seconds survived
    score = balls_array.size();
    
    textSize(22);//Sets size of text to 22
    fill(22,22,222);//Sets colour of highscore to blue
    if(score > highscore6) highscore6 = score;//If score is greater then highscore then it sets score = to highscore.
    textAlign(CENTER);
    text("High Score = " + highscore6,width/2, 100 );//Displays the users High Score for this game mode and this session
    textAlign(CENTER);
    text("Your Score = "+ score, width/2, 150);//Displays the number of balls the player survived. Using .size to get the number of balls in the arraylist
    
    fill(255,0,0);//Sets game over text to red
    textAlign(CENTER);
    text("Game Over!", width/2, 300);// Displays the Game Over text.
    
    fill(35,255,20);//Sets colour of Options to the same green as the menu
    textAlign(CENTER);
    text("To Restart the Game Press R",width/2, 400);
    textAlign(CENTER);
    text("To Return to the Menu press M", width/2, 450);
    textAlign(CENTER);
    text("To Quit the Game Press Q", width/2, 500);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SurviBALL" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
