void drawForStateMenu() {
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
