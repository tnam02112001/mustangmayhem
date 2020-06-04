import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */
public abstract class Entity
{
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    public Entity(Point position, List<PImage> images, int imageIndex)
    {
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }
    protected Point getPosition(){
        return position;
    }
    protected void setPosition(Point p)
    {
        position = p;
    }
    protected PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }
    protected int getImageIndex()
    {
        return imageIndex;
    }
    protected void setImageIndex(int c)
    {
        imageIndex = c;
    }
    protected List<PImage> getImages()
    {
        return images;
    }
    protected void setImage(List<PImage> images){this.images = images;}


}
