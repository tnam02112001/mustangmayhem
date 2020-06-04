import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class CarRight extends Car{


    public CarRight(Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(position, images, actionPeriod, animationPeriod);
        this.carBuffer = (int)(Math.random()*7 + 2);
        this.initialPosition = new Point(position.x, position.y);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        int nextPeriod = this.getActionPeriod() - Difficulty.DifficultyFactor;
        if (nextPeriod <1000)
            nextPeriod = 1000;
        if(this.getPosition().x>=world.getNumCols()-1)
        {
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
        }else
            move(world,scheduler, imageStore);

        if(this.getPosition().x - initialPosition.x == carBuffer)
        {
            CarRight carRight = EntityFactory.createCarRight(new Point(0, getPosition().y), this.getActionPeriod(), 16, imageStore.getImageList("carright"));
            world.addEntity(carRight);;
            carRight.scheduleActions(scheduler, world, imageStore);
        }
            scheduler.scheduleEvent(this,  Activity.createActivityAction(this, world, imageStore),nextPeriod );
    }

    protected Point nextPosition() {
        return new Point(this.getPosition().x+1, this.getPosition().y);
    }


    }

