import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet {
	private int x;
	private int y;
	private int width;
	private int height;
	private int collisions;
	private Rectangle rect;
	private boolean left = false;
	private boolean right = false;
	private int totalDistance;
	public int farthest = 600;
	public int bulletSpeed = 3;
	private ac game;
	
	public Bullet(final int x, final int y, final int width, final int height, final ac game) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		if(ac.mebberight) {
			right = true;
		} else if(ac.mebbeleft) {
			left = true;
		}
		this.totalDistance = 0;
		this.rect = new Rectangle(x, y, width, height);
		this.game = game;
	}
	
	public void draw(Graphics g) {
		g.fillRect(x - game.xs, y - game.ys, width, height);
	}
	
	public void update() {
		rect = new Rectangle(x, y, width, height);
		if(totalDistance >= farthest) {
			game.bullets.remove(this);
		}
		if(right) {
			x += bulletSpeed;
			totalDistance += bulletSpeed;
		} else if(left) {
			x -= bulletSpeed;
			totalDistance += bulletSpeed;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCollisions() {
		return collisions;
	}

	public void setCollisions(int collisions) {
		this.collisions = collisions;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
}
