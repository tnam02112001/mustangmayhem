import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Horse extends AnimatedMovingEntity {


    public Horse(Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(position, images, 0, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        long nextPeriod = this.getActionPeriod();
        if (MustangMayhem.x_moving != 0 || MustangMayhem.y_moving != 0) {
            this.moveTo(world, scheduler);
            nextPeriod += this.getActionPeriod();
        }
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    protected boolean moveTo(WorldModel world,
                              EventScheduler scheduler) {

            Point nextPos = this.nextPosition();
                if(!world.isOccupied(nextPos) && nextPos.y>= MustangMayhem.line_moving)
                {
                    Optional<Entity> occupant = world.getOccupant(nextPos);
                    if (occupant.isPresent()) {
                        scheduler.unscheduleAllEvents(occupant.get());
                    }
                    if(nextPos.y <this.getPosition().y)
                    {
                        MustangMayhem.score -=10;
                    }

                    else if (nextPos.y >this.getPosition().y)
                    {
                        MustangMayhem.score+=10;
                    }

                    world.moveEntity(this, nextPos);


                }
            return false;

    }

    protected Point nextPosition() {
        Point newPos = new Point(this.getPosition().x + MustangMayhem.x_moving,
                this.getPosition().y + MustangMayhem.y_moving);
        if (MustangMayhem.x_moving == -1)
            this.setImage(MustangMayhem.imageStore.getImageList("horseleft"));
        else if (MustangMayhem.x_moving == 1)
            this.setImage(MustangMayhem.imageStore.getImageList("horseright"));
        MustangMayhem.x_moving = 0;
        MustangMayhem.y_moving = 0;
        return newPos;
    }




}
