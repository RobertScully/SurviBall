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

  void display() 
  {
    fill(255);//Sets colour of Player to White
    ellipse(x1Pos, y1Pos, diamP, diamP);//Sets position and size of Player
    x1Pos = constrain(x1Pos, 0, width);//Stops player from travelling through the sides of the screen
    y1Pos = constrain(y1Pos, 0, height);//Stops player from travelling through the top/bottom of the screen
  }

  void move() 
  {
    if (moveLeft) x1Pos -= speed;//Treats left as negative normal speed
    if (moveRight) x1Pos += speed;//Treats right as normal speed
    if (moveUp) y1Pos -= speed;//Treats up as negative normal speed
    if (moveDown) y1Pos += speed; //Treats down as normal speed
  }
}

