public class Activity implements Action {
    private ActiveEntity entity;
    private WorldModel world;
    private ImageStore imageStore;


    public Activity(ActiveEntity entity, WorldModel world,
                    ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;

    }
    public void execute(EventScheduler scheduler) {
        entity.executeActivity(world, imageStore, scheduler);
    }

    public static Activity createActivityAction(ActiveEntity entity, WorldModel world,
                                                ImageStore imageStore) {
        return new Activity(entity, world, imageStore);
    }


}
