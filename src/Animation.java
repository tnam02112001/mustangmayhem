public class Animation implements Action{


    private AnimatedMovingEntity entity;
    private int repeatCount;

    public Animation(AnimatedMovingEntity entity, int repeatCount)
    {
        this.entity = entity;

        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler)
    {
        this.entity.nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(entity, Animation.createAnimationAction(entity, Math.max(repeatCount - 1, 0)),
                    entity.getAnimationPeriod());
        }
    }
    public static Animation createAnimationAction(AnimatedMovingEntity entity, int repeatCount)
    {
        return new Animation( entity,  repeatCount);
    }


}
