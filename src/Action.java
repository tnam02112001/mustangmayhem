/*
Action: ideally what our various entities might do in our virutal world
 */

public interface  Action
{
   void execute(EventScheduler scheduler);
}

//final class Action
//{
//   private ActionKind kind;
//   private Entity entity;
//   private WorldModel world;
//   private ImageStore imageStore;
//   private int repeatCount;
//
//   public Action(ActionKind kind, Entity entity, WorldModel world,
//      ImageStore imageStore, int repeatCount)
//   {
//      this.kind = kind;
//      this.entity = entity;
//      this.world = world;
//      this.imageStore = imageStore;
//      this.repeatCount = repeatCount;
//   }
//
//   public void executeAction( EventScheduler scheduler)
//   {
//      switch (this.kind)
//      {
//         case ACTIVITY:
//            executeActivityAction(scheduler);
//            break;
//
//         case ANIMATION:
//            executeAnimationAction(scheduler);
//            break;
//      }
//   }
//
//   private void executeAnimationAction(EventScheduler scheduler)
//   {
//      this.entity.nextImage();
//
//      if (repeatCount != 1)
//      {
//         scheduler.scheduleEvent(entity, this.entity.createAnimationAction(Math.max(repeatCount - 1, 0)),
//                 entity.getAnimationPeriod());
//      }
//   }
//
//   private void executeActivityAction(EventScheduler scheduler)
//   {
//      switch (this.entity.getKind())
//      {
//         case OCTO_FULL:
//            entity.executeOctoFullActivity(world,
//                    imageStore, scheduler);
//            break;
//
//         case OCTO_NOT_FULL:
//            entity.executeOctoNotFullActivity(world,
//                    imageStore, scheduler);
//            break;
//
//         case FISH:
//            entity.executeFishActivity(world, imageStore,
//                    scheduler);
//            break;
//
//         case CRAB:
//            entity.executeCrabActivity(world,
//                    imageStore, scheduler);
//            break;
//
//         case QUAKE:
//            entity.executeQuakeActivity(world, imageStore,
//                    scheduler);
//            break;
//
//         case SGRASS:
//            entity.executeSgrassActivity( world, imageStore,
//                    scheduler);
//            break;
//
//         case ATLANTIS:
//            entity.executeAtlantisActivity(world, imageStore,
//                    scheduler);
//            break;
//
//         default:
//            throw new UnsupportedOperationException(
//                    String.format("executeActivityAction not supported for %s",this.entity.getKind()));
//      }
//   }
//
//
//}
