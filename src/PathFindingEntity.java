import processing.core.PImage;

import java.util.List;

public abstract class PathFindingEntity extends AnimatedMovingEntity {
    public PathFindingEntity(Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(position, images, imageIndex, actionPeriod, animationPeriod);
    }
    protected abstract boolean moveTo(WorldModel world,
                                      Entity target, EventScheduler scheduler);
    protected abstract Point nextPosition(WorldModel world,
                                          Point destPos);

}
