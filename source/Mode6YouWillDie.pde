void drawForStateYouWillDie() {
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
