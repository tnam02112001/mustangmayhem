import processing.core.PImage;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hunter extends PathFindingEntity {

    private HashMap<Point, Node> visited;
    private PriorityQueue<Node> openList;
    private HashMap<Point, Node> closedHash;
    static final Function<Point, Stream<Point>> CARDINAL_NEIGHBORS =
            point ->
                    Stream.<Point>builder()
                            .add(new Point(point.x, point.y - 1))
                            .add(new Point(point.x, point.y + 1))
                            .add(new Point(point.x - 1, point.y))
                            .add(new Point(point.x + 1, point.y))
                            .build();

    public Hunter(Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(position, images, 0, actionPeriod, animationPeriod);
    }

    protected void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> HunterTarget = world.findNearest(this.getPosition(), Horse.class);
        long nextPeriod = this.getActionPeriod();

        if(HunterTarget.isPresent())
        {
            Point tgtPos = HunterTarget.get().getPosition();
            if (this.moveTo(world, HunterTarget.get(), scheduler)) {
                Ghost ghost = EntityFactory.createGhost(tgtPos, 1, 14, imageStore.getImageList("ghost"));
                world.addGhostEntity(ghost);
                ghost.scheduleActions(scheduler, world, imageStore);
                MustangMayhem.DrawGameOver = true;
            }
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
        }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()) ) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point desPos) {
        List<Point> path = computePath(this.getPosition(), desPos, p ->  world.withinBounds(p) && !world.isOccupied(p),
                Point::adjacent,
                CARDINAL_NEIGHBORS);
        if(path.isEmpty()) {
            return this.getPosition();
        }
        return path.get(0);
    }


    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();
        openList = new PriorityQueue<>();
        visited = new HashMap<>();
        closedHash = new HashMap<>();
        int h = Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
        if(h == 0)
            return new LinkedList<>();
        Node startNode = new Node(start, 0, 0, 0, null);
        visited.put(start, startNode);
        List<Point> neighbors = potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)).filter(pt -> !closedHash.containsKey(pt))
                .collect(Collectors.toList());
        for(int i = 0; i < neighbors.size(); i++)
        {
            if(neighbors.get(i).adjacent(end))
            {
                path.add(neighbors.get(i));
                return path;
            }
            Node neighbor = new Node(neighbors.get(i), 0, 1, 1, startNode);
            visited.put(neighbors.get(i), neighbor);
            openList.add(neighbor);
        }
        closedHash.put(start, startNode);
        visited.remove(start);
        while(!visited.isEmpty())
        {
            if(openList.size() > 1000)
            {
                return path;
            }
            Node current = openList.poll();
            List<Point> newNeighbors = potentialNeighbors.apply(current.getPoint())
                    .filter(canPassThrough)
                    .filter(pt -> !closedHash.containsKey(pt))
                    .collect(Collectors.toList());

            for(int i = 0; i < newNeighbors.size(); i++)
            {
                Node neighbor = new Node(newNeighbors.get(i), 0, current.getG() + 1, current.getG() + 1, current);
                if(withinReach.test(neighbor.getPoint(), end))
                {

                    Node endNode = new Node(end, 0, neighbor.getG() + 1, neighbor.getG() + 1, neighbor);
                    Node retrace = new Node(endNode.getPoint(), 0, endNode.getG(), endNode.getF(), neighbor);
                    while(retrace.getParent() != null)
                    {
                        path.add(retrace.getParent().getPoint());
                        retrace = retrace.getParent();
                    }
                    path.remove(path.size() - 1);
                    Collections.reverse(path);
                    return path;
                }
                visited.put(neighbor.getPoint(), neighbor);
                openList.add(neighbor);
                //}
            }
            closedHash.put(current.getPoint(), current);
            visited.remove(current.getPoint());
        }

        return path;
    }
    protected void scheduleActions(EventScheduler scheduler,
                                   WorldModel world, ImageStore imageStore) {
        int nextPeriod = this.getActionPeriod() - Difficulty.DifficultyFactor;
        if (nextPeriod <5000)
            nextPeriod = 5000;
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }
}



