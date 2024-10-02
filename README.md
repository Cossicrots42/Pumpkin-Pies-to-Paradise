# Pumpkin-Pies-to-Paradise

# Synopsis
This project is currently a **WORK IN PROGRESS** JavaFX program that will soon be a simple level-based idler game inspired by Cookie Clicker. In Pumpkin Pies to Paradies, the player will
start by clicking a pumpkin pie to earn pumpkin pies. They will then have to go through a level to collect enough cash to unlock the ability to spend some of their pumpkin pies to hire 
Baker Dudes who will make pumpkin pies for them automatically. The player will then go onto more levels to unlock better "PumpkinPie Makers" while passively increasing the amount of pumpkin
pies earned when they click on the pumpkin pie. The overall objective of Pumpkin Pies to paradise is to reach 4,294,967,296 (the 32-bit integer limit) pumpkin pies. Once the player has
reached that amount, the game ends and the score, which will be calculated mostly by the final pumpkin pies earned per second and the time the player took, will then be displayed with a
"YOU WIN!" graphic. But as I said earlier, there is still a lot of features and functionalities that I need to develop.

# Motivation
I've been studying Java for about 2 and a half years now. And just 2 months ago, I realized I could make a game with JavaFX. Cookie Clicker has been close to my heart for a long time, and
I really just wanted to create something that was kind of cool, and that would allow the uesr to satisfy their urge to click as much as they possibly could. So, after some thinking, I came
up with my ideas for Pumpkin Pies to Paradise. Somewhat like Cookie Clicker, but with a lot of twists. And I hope that soon, when I finish building the game, some of my friends will play it,
they'll maybe like it a little bit, and I'll be able to be like, "Oh yeah, I made that."

# How to Run
Eventually, I'd like to have this on a website when it's completed, but if you really want to run it right now, here's how:
1. Make sure you have a jdk installed. (The program was written for Java 16, but Java 17 will probably work too). If you don't already have one installed you can get one from here:
https://www.oracle.com/java/technologies/downloads/#jdk17-windows
2. Download JavaFX. I recomend getting javafx-sk 17.0.12 as that's what I built the program with. You can download it from here:
https://gluonhq.com/products/javafx/
3. Open Command Prompt (on Windows) or Terminal (on MacOS). Change the directory to where you saved Dsiplay3Pgame.java.
4. Enter this command: "javac --module-path $PATH_TO_JAVAFX --add-modules javafx.controls,javafx.fxml Display3Pgame.java" but replace $PATH_TO_JAVAFX with the file path to where the javafx
jars are stored inside the lib folder. This should compile the code and create a few .class files.
5. Finally, enter the command "java --module-path $PATH_TO_JAVAFX --add-modules javafx.controls,javafx.fxml Display3Pgame.java" and again, replace $PATH_TO_JAVAFX with the path to the
javafx jars. So, literally the same command but it starts with "java" instead of "javac".
6. If you do all that right, something like what is shown in "What it does.PNG" should pop up.

"Command Prompt Example.PNG" shows what your Command Prompt or Terminal input may look like if done properly.

# Code Example

I am quite proud of this piece of code here, it took me a while to figure out, but essentially what it does is detects when the size of the window the game is in changes and calls the 
methods to resize all of the visual nodes accordingly.

    //THIS IS NOT THE ENTIRE CODE IT'S JUST A SNIPET
    @Override
    public void start(Stage guiStage) {
    	
    	this.stage = guiStage;
    	
    	guiStage.setMinWidth(800);
    	guiStage.setMinHeight(400);
    	
    	Generate3Pgame gameContent = new Generate3Pgame(guiStage);
        Scene GAME = new Scene(gameContent, 1200, 600);
        
        Generate3Pgame.windowResizeAdjustment(GAME.getWidth(), GAME.getHeight());
        
        guiStage.widthProperty().addListener((observable, oldValue, newValue) -> {
        	double width = newValue.doubleValue();
        	Generate3Pgame.windowResizeAdjustment(width, GAME.getHeight());
        });

        guiStage.heightProperty().addListener((observable, oldValue, newValue) -> {
        	double height = newValue.doubleValue();
        	Generate3Pgame.windowResizeAdjustment(GAME.getWidth(), height);
        });
        
        guiStage.setTitle("0 Pumpkin Pies");
        guiStage.setScene(GAME);
        guiStage.show();

    }

# Contributors
As I am writting this, this program was soley developed by myself, Malachi Mock.
If you feel the need to contribute to this project, you can do so by copying and pasting my code and modifying it yourself in your prefered IDE. Currently, the window resizability feature
works for the most part, but there are significant issues occuring when the usre goes in and out of fullscreen. If you are able and willing to fix that it would be appreciated.
