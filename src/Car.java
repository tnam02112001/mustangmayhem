import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public  abstract class Car extends AnimatedMovingEntity {

    protected int carBuffer;
    protected Point initialPosition;

    public Car(Point position, List<PImage> images,int actionPeriod, int animationPeriod) {
        super(position, images, 0, actionPeriod, animationPeriod);
        this.carBuffer = (int)(Math.random()*7 + 2);
        this.initialPosition = new Point(position.x, position.y);
    }
    protected abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    protected boolean move(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Point nextPos = this.nextPosition();

        if (!world.isOccupied(nextPos)) {
            world.moveEntity(this, nextPos);
        } else {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent() && occupant.get().getClass() == Horse.class) {
                scheduler.unscheduleAllEvents(occupant.get());
                world.moveEntity(this, nextPos);
                Ghost ghost = EntityFactory.createGhost(nextPos, 1, 14, imageStore.getImageList("ghost"));
                world.addGhostEntity(ghost);
                ghost.scheduleActions(scheduler, world, imageStore);
                MustangMayhem.DrawGameOver = true;
            }

        }
        return false;
    }
    protected abstract Point nextPosition();
    protected void scheduleActions(EventScheduler scheduler,
                                   WorldModel world, ImageStore imageStore) {
        int nextPeriod = this.getActionPeriod() - Difficulty.DifficultyFactor;
        if (nextPeriod <1000)
            nextPeriod = 1000;
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }


}
