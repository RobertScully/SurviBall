//Codes keys for control of the player on the sketch
void keyReleased() {
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

void keyPressed() {
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



  



