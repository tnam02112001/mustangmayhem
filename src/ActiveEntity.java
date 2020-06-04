import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends Entity {
    private int actionPeriod;
    public ActiveEntity(Point position, List<PImage> images, int imageIndex, int actionPeriod)
    {
        super(position, images, imageIndex);
        this.actionPeriod = actionPeriod;
    }
    protected int getActionPeriod()
    {
        return actionPeriod;
    }
    protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    protected void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }
}
