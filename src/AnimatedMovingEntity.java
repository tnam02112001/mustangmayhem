import processing.core.PImage;

import java.util.List;

public abstract class AnimatedMovingEntity extends ActiveEntity {
    private int animationPeriod;

    public AnimatedMovingEntity(Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(position, images, imageIndex, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    protected int getAnimationPeriod() {
        return animationPeriod;
    }

    protected void nextImage() {
        setImageIndex((getImageIndex() + 1) % getImages().size());
    }
    protected void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }
}

