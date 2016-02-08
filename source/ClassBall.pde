class Ball {
  float diamB; //Ball size
  float xB,yB;//Balls location variable 
  float xspeed,yspeed; // Speed variable
  color ball_colour = color(random(50,250),random(50,250),random(50,250));//Randomizes the colour of each ball that is spawned
  
  //Constructor
  Ball(float temp) {
    diamB = temp;
    xB = width/2;//Ball location variable initiated at the centre of the sketch
    yB = height/2;//Ball location variable initiated at the centre of the sketch
    xspeed = random(-4,4) ;//Set speed on the x and y plane as random between -4,4
    yspeed = random(-4,4) ;//This causes the ball to spawn travelling in a random direction
  }
  
  //Updates balls position by incrementing x/y position by speed and detects walls and reverses direction causing bouncing
  void move() {
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
  
  void collide() {//Detects if a collision has occurred and sets player to dead on collision ending the game
    if(sqrt (sq(p.x1Pos-xB) + sq(p.y1Pos-yB)) < (p.diamP + diamB)/2){// If the squareroot of players x and y position - balls x and y position squared is less than the diameter of player + ball set dead to true.
     p.dead=true;
    }
  }
  
  //Balls drawn  
  void display() {
    smooth();
    stroke(0);//Sets edge of ball colour to black
    fill(ball_colour);//Sets colour of ball randomly
    ellipse(xB,yB,diamB,diamB);
  }
}
