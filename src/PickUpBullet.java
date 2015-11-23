import java.awt.Graphics;
import java.awt.Rectangle;


public class PickUpBullet {
	private int x;
	private int y;
	private ac game;
	private int width;
	private int height;
	private Rectangle rect;
	
	public PickUpBullet(final int x, final int y, ac game) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.width = game.bulletWidth;
		this.height = game.bulletHeight;
		this.rect = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		g.fillRect(x - game.xs, y - game.ys, width, height);
	}
	
	public void update() {
		rect = new Rectangle(x, y, width, height);
		if(game.rect.intersects(rect)) {
			game.totalBullets += 20;
			game.pickupbullets.remove(this);
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

	public ac getGame() {
		return game;
	}

	public void setGame(ac game) {
		this.game = game;
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
}
