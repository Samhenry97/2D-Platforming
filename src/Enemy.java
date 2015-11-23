import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public class Enemy {
	private float x;
	private float y;
	private int width;
	private int height;
	private Rectangle rect;
	private ac game;
	private int hurt;
	private float enemySpeed;
	private boolean falling;
	private boolean checker;
	private int fallingSpeed;
	private int fallingFrame;
	private Color enemyColor;
	public int colorHold = 10;
	private boolean intersects;
	
	public Enemy(final float x, final float y, final int width, final int height, ac game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect = new Rectangle((int)x, (int)y, width, height);
		this.game = game;
		this.hurt = 0;
		this.enemySpeed = (float) .15;
		this.falling = true;
		this.fallingSpeed = 1;
		this.fallingFrame = 0;
		this.enemyColor = Color.black;
		this.intersects = false;
	}
	
	public void draw(Graphics g) {
		g.fillRect((int)x - game.xs, (int)y - game.ys, width, height);
	}
	
	public void update() {
		game.playerColor = Color.DARK_GRAY;
		if(colorHold > 30) {
			enemyColor = Color.black;
			colorHold = 0;
		}
		rect = new Rectangle((int)x, (int)y, width, height);
		for(Bullet bullet : game.bullets) {
			if(bullet.getRect().intersects(rect)) {
				hurt++;
				enemyColor = Color.pink;
				if(hurt >= 2) {
					game.score += 100;
					game.enemies.remove(this);
					game.enemiesLeft--;
					if(game.enemies.isEmpty()) {
						game.won = true;
					}
				}
				game.bullets.remove(bullet);
			}
		}
		for(Enemy enemy : game.enemies) {
			if(enemy.rect.intersects(game.rect)) {
				intersects = true;
			}
		}
		if(intersects) {
			game.health -= .001;
			game.playerColor = Color.RED;
			intersects = false;
		}
		if(game.character.x < 0) {
			if(game.character.x - x > 0) { // Enemy is to left
				x += enemySpeed;
			} else if(game.character.x - x < 0) {// Enemy is to right
				x -= enemySpeed;
			} else {;}
		} else if(game.character.x > 0) {
			if(game.character.x - x > 0) {//Enemy is to left
				x += enemySpeed;
			} else if(game.character.x - x < 0) {//Enemy is to right
				x -= enemySpeed;
			} else {;}
		}
		
		//Falling
		Point pt1 = new Point((int)x, (int)y + height);
		Point pt2 = new Point((int)x + width, (int)y + height);
		if(fallingFrame >= fallingSpeed) {
			for(int i = 0; i < game.totalPlatforms; i++) {
				if(game.platforms[i].contains(pt1) || game.platforms[i].contains(pt2) || game.floor.contains(pt1) || game.floor.contains(pt2)) {
					falling = false;
					checker = true;
				} else {
					if(!checker){
						falling = true;
					}
				}
			}
			if(checker) {
				checker = false;
			}
			
			if(falling) {
				if(y >= aa.height + 20) {
					game.enemies.remove(this);
					game.enemiesLeft--;
					game.score += 100;
					if(game.enemies.isEmpty()) {
						game.won = true;
						enemySpeed += 0.3;
					}
				}
				y += 1;
			}
			
			fallingFrame = 0;
		} else {
			fallingFrame += 1;
		}
		colorHold++;
	}

	public int getX() {
		return (int)x;
	}

	public Color getEnemyColor() {
		return enemyColor;
	}

	public void setEnemyColor(Color enemyColor) {
		this.enemyColor = enemyColor;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
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
	
	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public int getFallingSpeed() {
		return fallingSpeed;
	}

	public void setFallingSpeed(int fallingSpeed) {
		this.fallingSpeed = fallingSpeed;
	}

	public int getFallingFrame() {
		return fallingFrame;
	}

	public void setFallingFrame(int fallingFrame) {
		this.fallingFrame = fallingFrame;
	}

	public float getEnemySpeed() {
		return enemySpeed;
	}

	public void setEnemySpeed(float enemySpeed) {
		this.enemySpeed = enemySpeed;
	}

	public boolean isIntersects() {
		return intersects;
	}

	public void setIntersects(boolean intersects) {
		this.intersects = intersects;
	}
}
