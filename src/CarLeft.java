import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class CarLeft extends Car{

    public CarLeft(Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(position, images, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        int nextPeriod = this.getActionPeriod() - Difficulty.DifficultyFactor;
        if (nextPeriod <1000)
            nextPeriod = 1000;
        if(this.getPosition().x<=0)
        {
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
        }else
            move(world,scheduler, imageStore);

        if(initialPosition.x - this.getPosition().x == carBuffer)
        {
            CarLeft carLeft = EntityFactory.createCarLeft(new Point(9, getPosition().y), this.getActionPeriod(), 16, imageStore.getImageList("carleft"));
            world.addEntity(carLeft);
            carLeft.scheduleActions(scheduler, world, imageStore);
        }
        scheduler.scheduleEvent(this,  Activity.createActivityAction(this, world, imageStore), nextPeriod);
    }





    protected Point nextPosition() {
        return new Point(this.getPosition().x-1, this.getPosition().y);
    }
}

