import java.awt.Graphics;
import java.awt.Rectangle;


public class Coin {
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle rect;
	private ac game;
	
	public int healthScore = 5;
	public int coinScore = 10;
	
	public Coin(final int x, final int y, final int width, final int height, ac game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect = new Rectangle(x, y, width, height);
		this.game = game;
	}
	
	public void draw(Graphics g) {
		g.fillRect(x - game.xs, y - game.ys, width, height);
	}
	
	public void update() {
		rect = new Rectangle(x, y, width, height);
		if(game.rect.intersects(rect)) {
			game.coins.remove(this);
			game.score += coinScore;
			game.health += healthScore;
		}
	}
}
