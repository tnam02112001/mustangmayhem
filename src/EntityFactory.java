import processing.core.PImage;

import java.util.List;

public class EntityFactory {

    private static final String CAR_RIGHT_KEY = "carright";
    private static final String CAR_LEFT_KEY = "carleft";
    private static final int CAR_NUM_PROPERTIES = 7;
    private static final int CAR_COL = 2;
    private static final int CAR_ROW = 3;
    private static final int CAR_LIMIT = 4;
    private static final int CAR_ACTION_PERIOD = 5;
    private static final int CAR_ANIMATION_PERIOD = 6;

    private static final String HUNTER_KEY = "hunter";
    private static final int HUNTER_NUM_PROPERTIES = 6;
    private static final int HUNTER_COL = 2;
    private static final int HUNTER_ROW = 3;
    private static final int HUNTER_ACTION_PERIOD = 4;
    private static final int HUNTER_ANIMATION_PERIOD = 5;

    private static final String HORSE_KEY = "horseleft";
    private static final int HORSE_NUM_PROPERTIES = 6;
    private static final int HORSE_COL = 2;
    private static final int HORSE_ROW = 3;
    private static final int HORSE_ACTION_PERIOD = 4;
    private static final int HORSE_ANIMATION_PERIOD = 5;

    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;

    //ADDED
    private static final String BENCH_KEY = "bench";
    private static final String ROCK_KEY = "rock";
    //

    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;

    public EntityFactory()
    {
    }

    public static boolean parseBackground(WorldModel world, String[] properties,
                                          ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
            Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         world.setBackground(pt,
            new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public static boolean parseObstacle(WorldModel world, String [] properties,
      ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
          double rand = Math.random();
          if(rand < .5) {
              Point pt = new Point(
                      Integer.parseInt(properties[OBSTACLE_COL]),
                      Integer.parseInt(properties[OBSTACLE_ROW]));
              Obstacle entity = createObstacle(pt, imageStore.getImageList(BENCH_KEY));
              world.tryAddEntity(entity);
          }
          else
          {
              Point pt = new Point(
                      Integer.parseInt(properties[OBSTACLE_COL]),
                      Integer.parseInt(properties[OBSTACLE_ROW]));
              Obstacle entity = createObstacle(pt, imageStore.getImageList(ROCK_KEY));
              world.tryAddEntity(entity);
          }
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

    public static boolean parseHorse( WorldModel world, String[] properties,
                                     ImageStore imageStore)
    {
        if (properties.length == HORSE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[HORSE_COL]),
                    Integer.parseInt(properties[HORSE_ROW]));

            Horse entity = EntityFactory.createHorse(pt,
                    Integer.parseInt(properties[HORSE_ACTION_PERIOD]),
                    Integer.parseInt(properties[HORSE_ANIMATION_PERIOD]),
                    imageStore.getImageList(HORSE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == HORSE_NUM_PROPERTIES;

    }


   public static boolean parseCarRight(WorldModel world, String[] properties, ImageStore imageStore)
   {
       if (properties.length == CAR_NUM_PROPERTIES)
       {
           Point pt = new Point(Integer.parseInt(properties[CAR_COL]), Integer.parseInt(properties[CAR_ROW]));
           CarRight car = EntityFactory.createCarRight(pt, Integer.parseInt(properties[CAR_ACTION_PERIOD]),
                   Integer.parseInt(properties[CAR_ANIMATION_PERIOD]), imageStore.getImageList(CAR_RIGHT_KEY));
           world.tryAddEntity(car);
       }
       return properties.length == CAR_NUM_PROPERTIES;
   }

    public static boolean parseCarLeft(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == CAR_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[CAR_COL]), Integer.parseInt(properties[CAR_ROW]));
            CarLeft car = EntityFactory.createCarLeft(pt, Integer.parseInt(properties[CAR_ACTION_PERIOD]),
                    Integer.parseInt(properties[CAR_ANIMATION_PERIOD]), imageStore.getImageList(CAR_LEFT_KEY));
            world.tryAddEntity(car);
        }
        return properties.length == CAR_NUM_PROPERTIES;
    }

    public static boolean parseHunter(WorldModel world, String[] properties, ImageStore imageStore)
    {
        if (properties.length == HUNTER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[HUNTER_COL]), Integer.parseInt(properties[HUNTER_ROW]));
            Hunter hunter = EntityFactory.createHunter(pt, Integer.parseInt(properties[HUNTER_ACTION_PERIOD]), Integer.parseInt(properties[HUNTER_ANIMATION_PERIOD]), imageStore.getImageList(HUNTER_KEY));
            world.tryAddEntity(hunter);
        }
        return properties.length == HUNTER_NUM_PROPERTIES;
    }

    public static Hunter createHunter(Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Hunter(position, images, actionPeriod, animationPeriod);
    }

    public static Ghost createGhost(Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Ghost(position, images, actionPeriod, animationPeriod);
    }
   public static CarRight createCarRight(Point position, int actionPeriod, int animationPeriod, List<PImage> images)
   {
       return new CarRight(position, images, actionPeriod, animationPeriod);
   }

    public static CarLeft createCarLeft(Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new CarLeft(position, images, actionPeriod, animationPeriod);
    }

    public static Horse createHorse(Point position,
                                  int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Horse(position,images, actionPeriod, animationPeriod);
    }


   public static Obstacle createObstacle( Point position,
                                       List<PImage> images)
   {
      return new Obstacle(position, images );


      }

}
