 import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class Display3Pgame extends Application {
    
    private Stage stage;
    
    /*This start method creates and displays the stage for Pumpkin Pies to Paradise. It also
     * listens for when the window size changes and calls the windowResizeAdjustment method
     * accordingly.
     */
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
    
    public static void main(String[] args) {
    	
        launch(args);
        
    }
    
}

class Generate3Pgame extends Group {
	
	private Stage stage;
    
    public Generate3Pgame(Stage stage) {
    	
    	this.stage = stage;
    	//Calls paint3Pgame to generate all the visuals.
        paint3Pgame();
        //Renders the first level.
        gameEngine(levelOne, 0, 0);
        //Puts the StickMan in the right spot.
        StickMan(7, 6);
        
        //Animation that handles gravity for the StickMan.
        gameLoop = new AnimationTimer() {
            
        	@Override
            public void handle(long now) {
                
        		FallingStickMan();
        		
            }
        };
        gameLoop.start();
        
        //Animation that handles the spinning rays of light.
        raysOfLight = new AnimationTimer() {
        	
        	@Override
        	public void handle(long now) {
                
        		SpinningLightRays();
        		
            }
        };
        
        raysOfLight.start();
        
    }
    
    /*Game objects, constants, and variables. This is where almost all of the nodes that are
    * displayed are instantiated.
    */
    
    AnimationTimer gameLoop;
    AnimationTimer raysOfLight;
    
    static double place = 0;
    
    static long IntegerLimit = 4294967296L;
    static long piesProperty = 0;
    static int ClickConstant = 1;
    
    static ArrayList<Rectangle> Blocks = new ArrayList<>();
    
    static double moveX = 0;
    static double moveY = 0;
    
    static double minStickManX = 0;
    static double maxStickManX;
    
    static BooleanProperty StickManCanJump = new SimpleBooleanProperty(false);
    static BooleanProperty StickManCanMoveLeft = new SimpleBooleanProperty(false);
    static BooleanProperty StickManCanMoveRight = new SimpleBooleanProperty(false);
    
    static double speed = 0.25;
    static double jump = 3;
    static double gravity = 0.075;
    
    static double SIZE = 30;
    
    static Circle Head = new Circle(12);
    static Circle inSideHead = new Circle(9);
    static Line back = new Line();
    static Line leftArm = new Line();
    static Line rightArm = new Line();
    static Line leftLeg = new Line();
    static Line rightLeg = new Line();
    
    static Label AmountOfPies = new Label();
    static Label PumpkinPies = new Label();
    static Label PiesAdded = new Label();
    static Font PieCountFont = Font.font("Georgia", 50);
    static Font NumberLabel = Font.font("Georgia", FontPosture.ITALIC, 20);
    static Font ClickPie = Font.font("Georgia", 30);
    
    static Color colorOfPPMs = Color.rgb(235, 242, 17, 1.0);
    static Color colorOfPPC = Color.rgb(252, 108, 5, 1.0);
    static Color colorOfLevelArena = Color.rgb(5, 252, 231, 1.0);
    static Color colorOfPie = Color.rgb(163, 73, 8, 1.0);
    static Color colorOfPieCrust = Color.rgb(240, 198, 127, 1.0);
    
    static Color brownColor = Color.rgb(92, 42, 4, 1.0);
    
    static Pane background = new Pane();
    
    static Pane PumpkinPieCount = new Pane();
    static Pane LevelArena = new Pane();
    static Pane DisplayPPM = new Pane();
    static ScrollPane PumpkinPieMakers = new ScrollPane();
    
    static double opacityOfPPMs = 0.75;
    
    static Rectangle BakerDude = new Rectangle();
    static Rectangle PumpkinPatch = new Rectangle();
    static Rectangle PumpkinPieFactory = new Rectangle();
    static Rectangle NanoPumpkinPieMachine = new Rectangle();
    static Rectangle AIPumpkinPieMaximizer = new Rectangle();
    static Rectangle GlitchInMatrix = new Rectangle();

    static Circle PumpkinPie = new Circle(50);
    static Circle PumpkinPieHitBox = new Circle(55);
    
    static Circle[] CrustRidges = new Circle[60];
    
    static Polygon[] PieGlory = new Polygon[12];
    
    static Polygon MissingSlice = new Polygon();
    static Shape PumpkinPieWithSlice;
    
    static Shape[] CrustRidgesNoSlice = new Shape[60];

    
    /*This is the main method for creating the game visuals. It sets most of the configurations
     * for the nodes.
     */
    private void paint3Pgame() {
    	
    	AmountOfPies.setText("0");
    	AmountOfPies.setFont(PieCountFont);	
    	
    	PumpkinPies.setText("PumpkinPies");
    	PumpkinPies.setFont(NumberLabel);
    	
    	PiesAdded.setFont(ClickPie);
    	
    	PumpkinPieCount.setLayoutX(0);
        PumpkinPieCount.setLayoutY(0);
        PumpkinPieCount.setStyle("-fx-background-color: " + toHexString(colorOfPPC));
        
        LevelArena.setLayoutX(400);
        LevelArena.setLayoutY(0);
        LevelArena.setStyle("-fx-background-color: " + toHexString(colorOfLevelArena));
    	
    	PumpkinPieMakers.setLayoutX(400);
        PumpkinPieMakers.setLayoutY(300);
    	PumpkinPieMakers.setMinWidth(400);
    	PumpkinPieMakers.setFitToWidth(true);
        PumpkinPieMakers.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        PumpkinPieMakers.setContent(DisplayPPM);
        
        DisplayPPM.setMinWidth(400);
        
        BakerDude.setFill(colorOfPPMs);
        BakerDude.setStroke(Color.BLACK);
        BakerDude.setStrokeWidth(10);
        BakerDude.setOpacity(opacityOfPPMs);
        
        PumpkinPatch.setFill(colorOfPPMs);
        PumpkinPatch.setStroke(Color.BLACK);
        PumpkinPatch.setStrokeWidth(10);
        PumpkinPatch.setLayoutY(100);
        PumpkinPatch.setOpacity(opacityOfPPMs);
        
        PumpkinPieFactory.setFill(colorOfPPMs);
        PumpkinPieFactory.setStroke(Color.BLACK);
        PumpkinPieFactory.setStrokeWidth(10);
        PumpkinPieFactory.setLayoutY(200);
        PumpkinPieFactory.setOpacity(opacityOfPPMs);
        
        NanoPumpkinPieMachine.setFill(colorOfPPMs);
        NanoPumpkinPieMachine.setStroke(Color.BLACK);
        NanoPumpkinPieMachine.setStrokeWidth(10);
        NanoPumpkinPieMachine.setLayoutY(300);
        NanoPumpkinPieMachine.setOpacity(opacityOfPPMs);
        
        AIPumpkinPieMaximizer.setFill(colorOfPPMs);
        AIPumpkinPieMaximizer.setStroke(Color.BLACK);
        AIPumpkinPieMaximizer.setStrokeWidth(10);
        AIPumpkinPieMaximizer.setLayoutY(400);
        AIPumpkinPieMaximizer.setOpacity(opacityOfPPMs);
        
        GlitchInMatrix.setFill(colorOfPPMs);
        GlitchInMatrix.setStroke(Color.BLACK);
        GlitchInMatrix.setStrokeWidth(10);
        GlitchInMatrix.setLayoutY(500);
        GlitchInMatrix.setOpacity(opacityOfPPMs);
        
        //Everything between here and the next comment are for rendering the nodes on the Panes.
        this.getChildren().addAll(background);
        background.getChildren().addAll(LevelArena, PumpkinPieCount, PumpkinPieMakers);
        
        DisplayPPM.getChildren().addAll(BakerDude, PumpkinPatch, PumpkinPieFactory, NanoPumpkinPieMachine, AIPumpkinPieMaximizer, GlitchInMatrix);
        
        for (int j = 0; j < PieGlory.length; j++) {
        	
        	PieGlory[j] = new Polygon();        	
        	
        	PieGlory[j].setFill(colorOfPPMs);
        	PieGlory[j].setOpacity(0.5);
        	
        	PieGlory[j].getPoints().addAll(new Double[]{
        		
        		PumpkinPie.getCenterX(), PumpkinPie.getCenterY(),
        		
        		(PumpkinPie.getCenterX() + (((PumpkinPie.getRadius() * 6) * Math.cos((2 * Math.PI * j) / 12)))),
        		(PumpkinPie.getCenterY() + (((PumpkinPie.getRadius() * 6) * Math.sin((2 * Math.PI * j) / 12)))),
        		
        		(PumpkinPie.getCenterX() + (((PumpkinPie.getRadius() * 6) * Math.cos((2 * Math.PI * (j + 0.5)) / 12)))),
        		(PumpkinPie.getCenterY() + (((PumpkinPie.getRadius() * 6) * Math.sin((2 * Math.PI * (j + 0.5)) / 12))))
        	});
        	
        	PumpkinPieCount.getChildren().add(PieGlory[j]);
        	
        }
        
        PumpkinPieCount.getChildren().addAll(AmountOfPies, PumpkinPies);
        
        PumpkinPieHitBox.setOnMouseClicked(event -> {
        	
        	piesProperty = piesProperty + ClickConstant;
        	
        	stage.setTitle(AmountOfPies(piesProperty) + " PumpkinPies");
        	
        	AmountOfPies.setText(AmountOfPies(piesProperty));
        	
        	PiesAdded.setLayoutX(event.getX());
        	PiesAdded.setLayoutY(event.getY());
        	
        	PiesAdded.setText("+" + AmountOfPies(piesProperty));
        	
        });
        
        //The game doesn't work without this, ok? Don't touch it unless you're smarter than me.
        LevelArena.setFocusTraversable(true);
        LevelArena.requestFocus();
        
        /* This handles all the key pressing events for the StickMan and moves it accordingly
         * if the StickMan is able to do so.
         */
        LevelArena.setOnKeyPressed(move -> {
            if (move.getCode() == KeyCode.A) {
            	
            	CanThatStickManMoveLeft();
            	
            	if (StickManCanMoveLeft.get() == true) {
            		if (moveX < -0.2) {
            			moveX = moveX + speed;

            			gameEngine(levelOne, moveX, 0);
            		} else {
            			double newStickManX = ((Head.getCenterX() - (SIZE / 2)) / SIZE) - speed;
            			if (newStickManX >= minStickManX) {

            				StickMan(newStickManX, (Head.getCenterY() - (SIZE / 2)) / SIZE);
            			}
            		}
            	}
            }

            if (move.getCode() == KeyCode.D) {
            	
            	CanThatStickManMoveRight();
            	
            	if (StickManCanMoveRight.get() == true) {
            		if (moveX > (-1 * levelOne[0].length) + 28) {
                    	moveX = moveX - speed;

                    	gameEngine(levelOne, moveX, 0);
                	} else {
                    	double newStickManX = ((Head.getCenterX() - (SIZE / 2)) / SIZE) + speed;
                    	if (newStickManX <= maxStickManX) {

                        	StickMan(newStickManX, (Head.getCenterY() - (SIZE / 2)) / SIZE);
                    	}
                	}
            	}
            }
            
            if (move.getCode() == KeyCode.W) {
            	
            	CanThatStickManJump();
            	
            	if (StickManCanJump.get() == true) {
            		
                	StickMan(((Head.getCenterX() - (SIZE / 2))) / SIZE, ((Head.getCenterY() - (SIZE / 2)) / SIZE) - jump);
            		
            	}
            	
            }
            
        });
        
        
    }
    
    /*Whenever the window is resized, this method is called and it resets all the node sizes and
    * locations to fit the new window size. VERY IMPORTANT.
    */
    static void windowResizeAdjustment(double width, double height) {

        background.setPrefSize(width, height);

        PumpkinPieCount.setPrefSize(width / 3, height);
        
        PumpkinPie.setRadius((width / 6) - 50);
        PumpkinPie.setCenterX(width / 6);
        PumpkinPie.setCenterY(((width / 6) - 12.5) + 50);
        PumpkinPie.setFill(colorOfPie);
        
        for (int r = 0; r < PieGlory.length; r++) {
        
        	PumpkinPieCount.getChildren().remove(PieGlory[r]);
        	
        }
        
        background.getChildren().remove(LevelArena);
        
        PumpkinPieCount.getChildren().removeAll(PumpkinPie, AmountOfPies, PumpkinPies);
        
        for (int j = 0; j < PieGlory.length; j++) {
        	
        	PieGlory[j] = new Polygon();        	
        	
        	PieGlory[j].setFill(colorOfPPMs);
        	PieGlory[j].setOpacity(0.5);
        	
        	PieGlory[j].getPoints().addAll(new Double[]{
        		
        		PumpkinPie.getCenterX(), PumpkinPie.getCenterY(),
        		
        		(PumpkinPie.getCenterX() + (((PumpkinPie.getRadius() * 6) * Math.cos((2 * Math.PI * j) / 12)))),
        		(PumpkinPie.getCenterY() + (((PumpkinPie.getRadius() * 6) * Math.sin((2 * Math.PI * j) / 12)))),
        		
        		(PumpkinPie.getCenterX() + (((PumpkinPie.getRadius() * 6) * Math.cos((2 * Math.PI * (j + 0.5)) / 12)))),
        		(PumpkinPie.getCenterY() + (((PumpkinPie.getRadius() * 6) * Math.sin((2 * Math.PI * (j + 0.5)) / 12))))
        	});
        	
        	PumpkinPieCount.getChildren().add(PieGlory[j]);
        	
        }
        
        background.getChildren().add(LevelArena);
        
        PumpkinPieCount.getChildren().addAll(AmountOfPies, PumpkinPies);
        
        MissingSlice.getPoints().clear();
        
        PumpkinPieCount.getChildren().remove(PumpkinPieWithSlice);
        
        MissingSlice.getPoints().addAll(new Double[]{
        		PumpkinPie.getCenterX(), PumpkinPie.getCenterY(),
        		PumpkinPieCount.getWidth() - 10, (PumpkinPie.getCenterY() - (width / 18)),
        		PumpkinPie.getCenterX() + (width / 18), (PumpkinPie.getCenterY() - PumpkinPie.getRadius() - (width / 27))
        		});
        
        MissingSlice.setFill(Color.TRANSPARENT);
        
        PumpkinPieWithSlice = Shape.subtract(PumpkinPie, MissingSlice);
        PumpkinPieWithSlice.setFill(colorOfPie);
        
        PumpkinPieCount.getChildren().add(PumpkinPieWithSlice);
        
        PumpkinPieCount.getChildren().remove(MissingSlice);
        
        for (int i = 0; i < CrustRidges.length; i++) {
        	
        	PumpkinPieCount.getChildren().remove(CrustRidgesNoSlice[i]);
        	
        	CrustRidges[i] = new Circle(PumpkinPie.getRadius() / 18);
        	CrustRidges[i].setFill(colorOfPieCrust);
        	
        	CrustRidges[i].setCenterX(PumpkinPie.getCenterX() + ((PumpkinPie.getRadius() * Math.cos((2 * Math.PI * i) / 60))));
        	CrustRidges[i].setCenterY(PumpkinPie.getCenterY() + ((PumpkinPie.getRadius() * Math.sin((2 * Math.PI * i) / 60))));
        	
        	CrustRidgesNoSlice[i] = Shape.subtract(CrustRidges[i], MissingSlice);
        	CrustRidgesNoSlice[i].setFill(colorOfPieCrust);
        	
        	PumpkinPieCount.getChildren().add(CrustRidgesNoSlice[i]);
        	
        }
        
        PumpkinPieCount.getChildren().add(MissingSlice);
        
        PumpkinPieCount.getChildren().remove(PumpkinPieHitBox);
        
        PumpkinPieHitBox.setFill(Color.TRANSPARENT);
        PumpkinPieHitBox.setCenterX(PumpkinPie.getCenterX());
        PumpkinPieHitBox.setCenterY(PumpkinPie.getCenterY());
        PumpkinPieHitBox.setRadius(PumpkinPie.getRadius() * 1.1);
        
        PumpkinPieCount.getChildren().add(PumpkinPieHitBox);
        
        double levelArenaHeight = 300;
        LevelArena.setLayoutX(width / 3);
        LevelArena.setLayoutY(0);
        LevelArena.setPrefSize(width * 2 / 3, levelArenaHeight);
        
        maxStickManX = (LevelArena.getPrefWidth() - SIZE) / SIZE;

        double pumpkinPieMakersY = levelArenaHeight;
        double pumpkinPieMakersHeight = height - levelArenaHeight;
        PumpkinPieMakers.setLayoutX(width / 3);
        PumpkinPieMakers.setLayoutY(pumpkinPieMakersY);
        PumpkinPieMakers.setPrefSize(width * 2 / 3, pumpkinPieMakersHeight);

        double rectWidth = (width * 2 / 3) - 20;
        double rectHeight = 100;

        double totalRectHeight = ((rectHeight * 6) + 40);
        DisplayPPM.setPrefSize(rectWidth + 20, totalRectHeight);

        BakerDude.setWidth(rectWidth);
        BakerDude.setHeight(rectHeight);

        PumpkinPatch.setWidth(rectWidth);
        PumpkinPatch.setHeight(rectHeight);

        PumpkinPieFactory.setWidth(rectWidth);
        PumpkinPieFactory.setHeight(rectHeight);

        NanoPumpkinPieMachine.setWidth(rectWidth);
        NanoPumpkinPieMachine.setHeight(rectHeight);

        AIPumpkinPieMaximizer.setWidth(rectWidth);
        AIPumpkinPieMaximizer.setHeight(rectHeight);

        GlitchInMatrix.setWidth(rectWidth);
        GlitchInMatrix.setHeight(rectHeight);
        
        AmountOfPies.setLayoutX(PumpkinPie.getCenterX() - (width / 9));
        AmountOfPies.setLayoutY(PumpkinPie.getCenterY() - (width / 6) - 40);
        
        PumpkinPies.setLayoutX(PumpkinPie.getCenterX() - (width / 9));
        PumpkinPies.setLayoutY(AmountOfPies.getLayoutY() + 50);
        
    }

    //This method simply returns the Hex value of a color
    static String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                           	(int) (color.getRed() * 255),
                            (int) (color.getGreen() * 255),
                            (int) (color.getBlue() * 255));
    }
    
    //Returns the amount of Pumpkin Pies the player has as a string.
    static String NumberOfPies() {
    	
    	return String.valueOf(piesProperty);
    	
    }
    
    /*This method shortens long numbers. For example, "1200000" becomes "1.2 million". And it 
     * returns it as a String, of course. Anything above the 32bit integer limit just becomes
     * "A LOT OF".
     */
    static String AmountOfPies(long pies) {
    	
    	String statement = String.valueOf(pies);
    	
    	if (pies > 999 && 1000000 > pies) {
    		
    		int commaPlacement = (statement.length() - 3);
    		StringBuilder addComma = new StringBuilder(statement);
    		addComma.insert(commaPlacement, ',');
    		statement = addComma.toString();
    		
    	}
    	
    	if (pies > 999999 && 1000000000 > pies) {
    		
    		int decimalPlacement = (statement.length() - 6);
    		StringBuilder addDecimal = new StringBuilder(statement);
    		addDecimal.insert(decimalPlacement, ".");
    		addDecimal.delete(decimalPlacement + 3, addDecimal.length());
    		addDecimal.append(" million");
    		statement = addDecimal.toString();
    		
    	}
    	
    	if (pies > 999999999 && IntegerLimit > pies) {
    		
    		int decimalPlacement = (statement.length() - 9);
    		StringBuilder addDecimal = new StringBuilder(statement);
    		addDecimal.insert(decimalPlacement, ".");
    		addDecimal.delete(decimalPlacement + 3, addDecimal.length());
    		addDecimal.append(" billion");
    		statement = addDecimal.toString();
    		
    	}
    	
    	if (pies > IntegerLimit) {
    		
    		statement = ("A LOT (of)");
    		
    	}
    	
    	return (statement);
    	
    }
    
    /*This 2-dimensional array is where the level is laid out. Change the values in this and 
     * you'll change what the level looks like.
     */
	int[][] levelOne = {
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 2, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 2, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
    		{0, 0, 1, 0, 1, 1, 2, 0, 0, 1, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 2, 0, 0, 1, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 0},
    		{0, 1, 2, 1, 2, 2, 0, 0, 0, 0, 2, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 1, 0, 1, 2, 1, 2, 2, 0, 0, 0, 0, 2, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 1},
    		{1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 1, 2, 2}
    		};
    
	/*This method takes values from a 2-dimensional array and an offset value to render all
	 * the blocks and pieces of the level.
	 */
    static void gameEngine(int[][] level, double moveX, double moveY) {
    	
    	LevelArena.getChildren().clear();
    	Blocks.clear();
    	
    	int i = 0;
    	
    	for (int y = 0; y < level.length; y++) {
    		
    		for (int x = 0; x < level[y].length; x++) {
    			if (level[y][x] == 1) {
    				PumpkinPieGrass(x + moveX, y + moveY, SIZE, Color.GREEN, brownColor);
    				i++;
    				}
    			if (level[y][x] == 2) {
    				PumpkinPieDirt(x + moveX, y + moveY, SIZE, brownColor);
    				i++;
    				}
    			if (level[y][x] == 3) {
    				
    				//I'm still working on this, ok?
    				
    			}
    			
    		}
    		
    	}
    	
    	StickMan(((Head.getCenterX() - (SIZE / 2))) / SIZE, (Head.getCenterY() - (SIZE / 2)) / SIZE);
    }
    
    /*Due to the StickMan being able to move independently of the level, it must be rendered
     * differently and thus has it's own method. 
     */
    static void StickMan(double X, double Y) {
    	
    	LevelArena.getChildren().removeAll(Head, back, leftArm, rightArm, leftLeg, rightLeg, inSideHead);
    	
    	Head.setFill(Color.BLACK);
    	inSideHead.setFill(colorOfLevelArena);
    	back.setFill(Color.BLACK);
    	back.setStrokeWidth(3);
    	leftArm.setFill(Color.BLACK);
    	leftArm.setStrokeWidth(3);
    	rightArm.setFill(Color.BLACK);
    	rightArm.setStrokeWidth(3);
    	leftLeg.setFill(Color.BLACK);
    	leftLeg.setStrokeWidth(3);
    	rightLeg.setFill(Color.BLACK);
    	rightLeg.setStrokeWidth(3);
    	
    	Head.setCenterX(X * SIZE + (SIZE / 2));
    	Head.setCenterY(Y * SIZE + (SIZE / 2));
    	
    	inSideHead.setCenterX(Head.getCenterX());
    	inSideHead.setCenterY(Head.getCenterY());
    	
    	back.setStartX(Head.getCenterX());
    	back.setStartY(Head.getCenterY() + inSideHead.getRadius());
    	back.setEndX(Head.getCenterX());
    	back.setEndY(Head.getCenterY() + 25);
    	
    	leftArm.setStartX(back.getStartX());
    	leftArm.setStartY(back.getStartY());
    	leftArm.setEndX(back.getStartX() - 10);
    	leftArm.setEndY(back.getStartY() + 10);
    	
    	rightArm.setStartX(back.getStartX());
    	rightArm.setStartY(back.getStartY());
    	rightArm.setEndX(back.getStartX() + 10);
    	rightArm.setEndY(back.getStartY() + 10);
    	
    	leftLeg.setStartX(back.getEndX());
    	leftLeg.setStartY(back.getEndY());
    	leftLeg.setEndX(back.getEndX() - 10);
    	leftLeg.setEndY(back.getEndY() + 22);
    	
    	rightLeg.setStartX(back.getEndX());
    	rightLeg.setStartY(back.getEndY());
    	rightLeg.setEndX(back.getEndX() + 10);
    	rightLeg.setEndY(back.getEndY() + 22);
    	
    	LevelArena.getChildren().addAll(Head, back, leftArm, rightArm, leftLeg, rightLeg, inSideHead);
    	
    }
    
    //This method creates the grass blocks.
    static void PumpkinPieGrass(double X, double Y, double blockSize, Color grass, Color dirt) {
    	
    	Rectangle thing = new Rectangle(blockSize, blockSize);
    	thing.setFill(dirt);
    	thing.setLayoutX(0 + (X * blockSize));
    	thing.setLayoutY(Y * blockSize);
    	
    	Blocks.add(thing);
    	
    	Rectangle thing2 = new Rectangle(blockSize, blockSize / 4);
    	thing2.setFill(grass);
    	thing2.setLayoutX(X * blockSize);
    	thing2.setLayoutY(Y * blockSize);
    	
    	if (thing.getLayoutX() >= 0) {
    		LevelArena.getChildren().addAll(thing, thing2);
    	}
    	
    }
    
    //This method creates the Dirt Blocks
    static void PumpkinPieDirt(double X, double Y, double blockSize, Color dirt) {
    	
    	Rectangle thing = new Rectangle(blockSize, blockSize);
    	thing.setFill(dirt);
    	thing.setLayoutX(X * blockSize);
    	thing.setLayoutY(Y * blockSize);
    	
    	Blocks.add(thing);
    	
    	if (thing.getLayoutX() >= 0) {
    		LevelArena.getChildren().addAll(thing);
    	}
    	
    }
    
    //This method is going to create the cash that the player must collect to unlock the BakerDude.
    static void PumpkinPieDollars(double X, double Y, double blockSize, Color cash) {
    	
    	//Still working on this
    	
    }
    
    //Tests if the StickMan is standing on a block and sets StickManCanJump to "true" if it is.
    static void CanThatStickManJump() {
    	
    	StickManCanJump.set(false);
    	
    	for (int i = 0; i < Blocks.size(); i++) {
    		
    		if(Blocks.get(i).getLayoutY() <= back.getEndY() + 25 ) {
    			
    			if(Blocks.get(i).getLayoutX() - leftLeg.getEndX() <= (SIZE - (SIZE / 4)) &&
    			Blocks.get(i).getLayoutX() - leftLeg.getEndX() >= (-SIZE)){
    				
    				StickManCanJump.set(true);
    				
    			}
    			
    		}
    		
    	}
    	
    }
    
    //Sends the StickMan downward to the ground if its not standing on anything.
    static void FallingStickMan() {
    	
    	CanThatStickManJump();
    	
    	if (StickManCanJump.get() == false) {
    		
    		StickMan(((Head.getCenterX() - (SIZE / 2))) / SIZE, ((Head.getCenterY() - (SIZE / 2)) / SIZE) + gravity);
    		
    	}
    	
    }
    
    /*Adds to an offset value to create a spinning affect and appropriately calls the methods
     * that render the light rays while also recreating the CrustRidges to ensure the light rays
     * aren't displayed ontop of the crust.
     */
    static void SpinningLightRays() {
    	
    	place = place + 0.0125;
    	
    	MovingLightRays(0, place);
    	MovingLightRays(1, place);
    	MovingLightRays(2, place);
    	MovingLightRays(3, place);
    	MovingLightRays(4, place);
    	MovingLightRays(5, place);
    	MovingLightRays(6, place);
    	MovingLightRays(7, place);
    	MovingLightRays(8, place);
    	MovingLightRays(9, place);
    	MovingLightRays(10, place);
    	MovingLightRays(11, place);
    	
    	PieCrustCreation(0);
    	PieCrustCreation(1);
    	PieCrustCreation(2);
    	PieCrustCreation(3);
    	PieCrustCreation(4);
    	PieCrustCreation(5);
    	PieCrustCreation(6);
    	PieCrustCreation(7);
    	PieCrustCreation(8);
    	PieCrustCreation(9);
    	PieCrustCreation(10);
    	PieCrustCreation(11);
    	PieCrustCreation(12);
    	PieCrustCreation(13);
    	PieCrustCreation(14);
    	PieCrustCreation(15);
    	PieCrustCreation(16);
    	PieCrustCreation(17);
    	PieCrustCreation(18);
    	PieCrustCreation(19);
    	PieCrustCreation(20);
    	PieCrustCreation(21);
    	PieCrustCreation(22);
    	PieCrustCreation(23);
    	PieCrustCreation(24);
    	PieCrustCreation(25);
    	PieCrustCreation(26);
    	PieCrustCreation(27);
    	PieCrustCreation(28);
    	PieCrustCreation(29);
    	PieCrustCreation(30);
    	PieCrustCreation(31);
    	PieCrustCreation(32);
    	PieCrustCreation(33);
    	PieCrustCreation(34);
    	PieCrustCreation(35);
    	PieCrustCreation(36);
    	PieCrustCreation(37);
    	PieCrustCreation(38);
    	PieCrustCreation(39);
    	PieCrustCreation(40);
    	PieCrustCreation(41);
    	PieCrustCreation(42);
    	PieCrustCreation(43);
    	PieCrustCreation(44);
    	PieCrustCreation(45);
    	PieCrustCreation(46);
    	PieCrustCreation(47);
    	PieCrustCreation(48);
    	PieCrustCreation(49);
    	PieCrustCreation(50);
    	PieCrustCreation(51);
    	PieCrustCreation(52);
    	PieCrustCreation(53);
    	PieCrustCreation(54);
    	PieCrustCreation(55);
    	PieCrustCreation(56);
    	PieCrustCreation(57);
    	PieCrustCreation(58);
    	PieCrustCreation(59);
    	
    	PumpkinPieHitBox.toFront();
    	
    }

    /*Tests if there is a block directly left of the StickMan and sets StickManCanMoveLeft to
     * "false" if there is.
     */
    static void CanThatStickManMoveLeft() {

        StickManCanMoveLeft.set(true);

        double stickManTopY = Head.getCenterY() - Head.getRadius();
        double stickManBottomY = Math.max(leftLeg.getEndY(), rightLeg.getEndY());
        double stickManLeftX = leftLeg.getEndX();
        double stickManRightX = rightLeg.getEndX();
        double movementAmount = speed * SIZE;

        double newStickManLeftX = stickManLeftX - movementAmount;

        for (Rectangle block : Blocks) {

            double blockLeftX = block.getLayoutX();
            double blockRightX = blockLeftX + block.getWidth();
            double blockTopY = block.getLayoutY();
            double blockBottomY = blockTopY + block.getHeight();

            boolean verticalOverlap = stickManBottomY > blockTopY && stickManTopY < blockBottomY;

            if (verticalOverlap) {
                if (newStickManLeftX < blockRightX && stickManLeftX >= blockRightX) {
                    StickManCanMoveLeft.set(false);
                    break;
                }
            }
        }
    }

    /*Tests if there is a block directly right of the StickMan and sets StickManCanMoveRight to
     * "false" if there is. Yes, I'm pretty sure they needed to be separate methods.
     */
    static void CanThatStickManMoveRight() {

        StickManCanMoveRight.set(true);

        double stickManTopY = Head.getCenterY() - Head.getRadius();
        double stickManBottomY = Math.max(leftLeg.getEndY(), rightLeg.getEndY());
        double stickManLeftX = leftLeg.getEndX();
        double stickManRightX = rightLeg.getEndX();
        double movementAmount = speed * SIZE;

        double newStickManRightX = stickManRightX + movementAmount;

        for (Rectangle block : Blocks) {

            double blockLeftX = block.getLayoutX();
            double blockRightX = blockLeftX + block.getWidth();
            double blockTopY = block.getLayoutY();
            double blockBottomY = blockTopY + block.getHeight();

            boolean verticalOverlap = stickManBottomY > blockTopY && stickManTopY < blockBottomY;

            if (verticalOverlap) {
                if (newStickManRightX > blockLeftX && stickManRightX <= blockLeftX) {
                    StickManCanMoveRight.set(false);
                    break;
                }
            }
        }
    }

    //Renders the light rays individually.
    static void MovingLightRays(int index, double offset) {
    	
    	PumpkinPieCount.getChildren().removeAll(PumpkinPieWithSlice);
    	
    	PumpkinPieCount.getChildren().remove(PieGlory[index]);
    	
    	PieGlory[index].getPoints().clear();
        PieGlory[index].getPoints().addAll(new Double[]{
        		
        	PumpkinPie.getCenterX(), PumpkinPie.getCenterY(),
        		
        	(PumpkinPie.getCenterX() + (((PumpkinPie.getRadius() * 6) * Math.cos((2 * Math.PI * (index + offset)) / 12)))),
        	(PumpkinPie.getCenterY() + (((PumpkinPie.getRadius() * 6) * Math.sin((2 * Math.PI * (index + offset)) / 12)))),
        		
        	(PumpkinPie.getCenterX() + (((PumpkinPie.getRadius() * 6) * Math.cos((2 * Math.PI * (index + offset + 0.5)) / 12)))),
        	(PumpkinPie.getCenterY() + (((PumpkinPie.getRadius() * 6) * Math.sin((2 * Math.PI * (index + offset + 0.5)) / 12))))
        });
        	
        PumpkinPieCount.getChildren().add(PieGlory[index]);
        PumpkinPieCount.getChildren().addAll(PumpkinPieWithSlice);
        
    }
    
    //Recreates the pie crust by rerendering an individual CrustRidge.
    static void PieCrustCreation(int i) {
    	
    	
    	
    	PumpkinPieCount.getChildren().removeAll(CrustRidgesNoSlice[i]);
    	
    	CrustRidges[i] = new Circle(PumpkinPie.getRadius() / 18);
    	CrustRidges[i].setFill(colorOfPieCrust);
    	
    	CrustRidges[i].setCenterX(PumpkinPie.getCenterX() + ((PumpkinPie.getRadius() * Math.cos((2 * Math.PI * i) / 60))));
    	CrustRidges[i].setCenterY(PumpkinPie.getCenterY() + ((PumpkinPie.getRadius() * Math.sin((2 * Math.PI * i) / 60))));
    	
    	CrustRidgesNoSlice[i] = Shape.subtract(CrustRidges[i], MissingSlice);
    	CrustRidgesNoSlice[i].setFill(colorOfPieCrust);
    	
    	PumpkinPieCount.getChildren().addAll(CrustRidgesNoSlice[i]);
    }
    
    
}