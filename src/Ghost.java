import processing.core.PImage;

import java.util.List;

public class Ghost extends AnimatedMovingEntity {
    public Ghost(Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(position, images, 0, actionPeriod, animationPeriod);
    }


    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }
}
