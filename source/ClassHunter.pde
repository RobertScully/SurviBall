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
  
  void hunting() {
    PVector player = new PVector(p.x1Pos,p.y1Pos);
    PVector dir = PVector.sub(player,location); // Find vector pointing towards the player
    dir.normalize();//Normalize
    dir.mult(0.5);//Scale
    acceleration = dir;//Set to acceleration
    
    velocity.add(acceleration);//Velocity changes acceleration
    velocity.limit(topspeed);//Limits the top speed to 1, Except in Rapid fire wheres its custom set to 2
    location.add(velocity);//Velocity changes location
  }
  
  void display() {
    stroke(0);
    fill(255,0,0);//Sets colour of hunter to red
    ellipse(location.x,location.y,diamH,diamH);//Spawns Hunter circle
  }
  
  void collide() {//Detects if a collision has occurred and sets player to dead on collision ending the game
    if(sqrt (sq(p.x1Pos-location.x) + sq(p.y1Pos-location.y)) < (p.diamP + diamH)/2){ // If the squareroot, of players x and y position - hunters x and y position squared, 
     p.dead=true;                                                                     //is less than the diameter of the sum of both divided by 2, set dead to true.
    }
  }
}
