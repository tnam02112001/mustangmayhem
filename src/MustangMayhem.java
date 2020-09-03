import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import processing.core.*;

import javax.sound.midi.Soundbank;


/*
MustangMayhem is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class MustangMayhem
   extends PApplet {
   public static final int TIMER_ACTION_PERIOD = 100;
   public static final int VIEW_WIDTH = 640;
   public static final int VIEW_HEIGHT = 832;
   public static final int TILE_WIDTH = 64;
   public static final int TILE_HEIGHT = 64;
   public static final int WORLD_WIDTH_SCALE = 1;
   public static final int WORLD_HEIGHT_SCALE = 12;
   public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;
   public static final String IMAGE_LIST_FILE_NAME = "imagelist";
   public static final String DEFAULT_IMAGE_NAME = "background_default";
   public static final int DEFAULT_IMAGE_COLOR = 0x808080;

   public static double timeScale = 0.1;
   public static int score = 0;

   public static ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   public static Timer t1 = new Timer();
   public static Timer t2 = new Timer();
   ScheduledExecutorService GameOverscheduler = Executors.newSingleThreadScheduledExecutor();

   //Background
   PImage menubackground = loadImage("images/Background.jpg");

   PImage img = loadImage("images/Message1.bmp");
   PImage img1 = loadImage("images/Message2.bmp");


   private boolean run = false;
   private boolean AutoShiftingRunning = false;
   private boolean ActionRunning = false;
   private boolean IncreaseDifficultyRunning = false;
   private boolean DrawMenu = true;
   private boolean DrawGame = false;
   public static boolean DrawGameOver = false;

   public PFont t;
   public static int x_moving = 0;
   public static int y_moving = 0;
   public static int line_moving = 0;


   public long next_time;

   public void settings() {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup() {
      this.imageStore = new ImageStore(
              createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
              createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
              TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, imageStore);
       t = createFont("Font/UTM Showcard.ttf", 32);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;

   }
   public void draw() {
      if (DrawGameOver);
         DrawGameOver();
      if (DrawMenu)
         DrawMenu();
      if(DrawGame) {
         DrawGame();
         DrawFont();
      }


   }

   public void keyPressed() {
      if (key == CODED) {
         switch (keyCode) {
            case UP:
               y_moving = -1;
               DrawFont();
               break;
            case DOWN:
               y_moving = 1;
               DrawFont();
               break;
            case LEFT:
               x_moving = -1;
               break;
            case RIGHT:
               x_moving = 1;
               break;
         }
      } else if (key == ' ')  //Pressing space bar, start playing
      {
         if (DrawMenu)
         {
            DrawMenu = false;
            DrawGame = true;
            redraw();
         }
         if (!run) {
            if (!ActionRunning) {
               scheduleActions(world, scheduler, imageStore);
               ActionRunning = true;
            }

            if (!AutoShiftingRunning) {
               AutoShift();
               AutoShiftingRunning = true;
            }

            if (!IncreaseDifficultyRunning) {
               IncreaseDifficulty();
               IncreaseDifficultyRunning = true;
            }
            run = true;
            loop();
         } else {
            noLoop();
            run = false;
         }
      }
   }

   public static Background createDefaultBackground(ImageStore imageStore) {
      return new Background(DEFAULT_IMAGE_NAME,
              imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color) {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++) {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
                                  PApplet screen) {
      try {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      } catch (FileNotFoundException e) {
         System.err.println(e.getMessage());
      }
   }


   public static void loadWorld(WorldModel world, ImageStore imageStore) {
      //Add a Horse
      String[] propertiesHorse = {"horseleft", "horseleft", String.valueOf(4), String.valueOf(2), String.valueOf(1), String.valueOf(25)};
      EntityFactory.parseHorse(world, propertiesHorse, imageStore);

      //Add a Hunter
      String[] propertiesHunter = {"hunter", "hunter", String.valueOf(0), String.valueOf(2), String.valueOf(6000), String.valueOf(25)};
      EntityFactory.parseHunter(world, propertiesHunter, imageStore);

      //I'm trying some stuff
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < WORLD_ROWS; j++) {
            String[] properties = {"background", "grass", String.valueOf(j), String.valueOf(i)};
            EntityFactory.parseBackground(world, properties, imageStore);
         }
      }
      for (int i = 3; i < WORLD_ROWS; i++) {
         double whichBackground = Math.random();
         String imageType = "background"; //Grass Case
         if (whichBackground < .5) {
            for (int j = 0; j < WORLD_COLS; j++) {
               String[] properties = {imageType, "grass", String.valueOf(j), String.valueOf(i)};
               EntityFactory.parseBackground(world, properties, imageStore);
            }

            int numObstaclesInRow = (int) (Math.random() * 6);
            int[] columnOfObstacles = new int[numObstaclesInRow];
            HashSet<Integer> mapOfIntegers = new HashSet<Integer>();
            for (int x = 0; x < numObstaclesInRow; x++) {
               int newRandom;
               do {
                  newRandom = (int) (Math.random() * 10);
               } while (mapOfIntegers.contains(newRandom));
               columnOfObstacles[x] = newRandom;
               mapOfIntegers.add(newRandom);
            }
            for (int c = 0; c < columnOfObstacles.length; c++) {
               String[] properties = {"obstacle", "obstacle", String.valueOf(columnOfObstacles[c]), String.valueOf(i)};
               EntityFactory.parseObstacle(world, properties, imageStore);
            }
         } else //Street case
         {
            for (int j = 0; j < WORLD_COLS; j++) {
               String[] properties = {imageType, "road", String.valueOf(j), String.valueOf(i)};
               EntityFactory.parseBackground(world, properties, imageStore);
            }

            double leftOrRight = Math.random();
            if(leftOrRight < .5)
            {
               String[] properties = {"carright", "carright", String.valueOf((int) (Math.random() * 5)), String.valueOf(i), String.valueOf(10), String.valueOf(10000), String.valueOf(16)};
               EntityFactory.parseCarRight(world, properties, imageStore);
            }
            else
            {
               String[] properties = {"carleft", "carleft", String.valueOf(9 - (int)(Math.random() * 5)), String.valueOf(i), String.valueOf(10), String.valueOf(10000), String.valueOf(16)};
               EntityFactory.parseCarLeft(world, properties, imageStore);
            }

         }
      }


   }

   public static void scheduleActions(WorldModel world,
                                      EventScheduler scheduler, ImageStore imageStore) {
      for (Entity entity : world.getEntities()) {
         //Only start actions for entities that include action (not those with just animations)
         if (entity instanceof ActiveEntity) {
            if (((ActiveEntity) entity).getActionPeriod() > 0) {
               ((ActiveEntity) entity).scheduleActions(scheduler, world, imageStore);
            }

         }
      }
   }

   public void AutoShift() {
      t1.schedule(
              new TimerTask() {
                 public void run() {
                    if(DrawGameOver)
                       ScheduleGameOver();
                    else {
                       view.shiftView(0, 1);
                       checkHorse();
                    }

                 }
              },
              0,       // run first occurrence immediatetly
              1000);

}


   public void checkHorse()
   {
      Optional<Entity> Horse = world.findNearest(new Point(0, line_moving), Horse.class);
      if(Horse.isPresent()) {
         Point HorsePos = Horse.get().getPosition();
         if (HorsePos.y <= line_moving) {
            world.removeEntity(Horse.get());
            scheduler.unscheduleAllEvents(Horse.get());
            Ghost ghost = EntityFactory.createGhost(new Point(HorsePos.x, line_moving + 1), 1, 14, imageStore.getImageList("ghost"));
            world.addGhostEntity(ghost);
            ghost.scheduleActions(scheduler, world, imageStore);
            DrawGameOver = true;


         }
      }
      line_moving ++;
   }
   public void ScheduleGameOver()
   {
      MustangMayhem.t1.cancel();
      MustangMayhem.t2.cancel();
      GameOverscheduler.schedule(() -> {
         DrawGame = false;
         redraw();
      }, 2, TimeUnit.SECONDS);
      GameOverscheduler.shutdown();
   }
   public void DrawMenu()
   {
      menubackground.resize(900, 900);
      image(menubackground, 0,0);
      img.resize(500,500);
      imageStore.setAlpha(img, color(1024, 1024, 1024), 0);
      image(img, 90,15);
      textFont(t);
      text("Mustang Mayhem",10, 50 );
   }

   public void DrawGame()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time) {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }
      view.drawViewport();

   }
   public void DrawGameOver()
   {
      menubackground.resize(900, 900);
      image(menubackground, 0,0);
      img1.resize(500,500);
      imageStore.setAlpha(img1, color(1024, 1024, 1024), 0);
      image(img1, 90,15);
      textFont(t,40);
      text("Your score is: "+ score,10, 50 );

   }

   public void IncreaseDifficulty()
   {
      t2.scheduleAtFixedRate(new Difficulty(),4000,500);
   }

   public static void main(String [] args)
   {
      

      PApplet.main(MustangMayhem.class);
   }
   public void DrawFont()
   {
      textFont(t,60);
      text(score,10, 50 );
   }
}
