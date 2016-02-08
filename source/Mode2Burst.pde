void drawForStateBurst() {
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
