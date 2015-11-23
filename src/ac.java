import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

public class ac extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	//Bullets and Enemies.
	public CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
	public CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
	public CopyOnWriteArrayList<Coin> coins = new CopyOnWriteArrayList<Coin>();
	public CopyOnWriteArrayList<PickUpBullet> pickupbullets = new CopyOnWriteArrayList<PickUpBullet>();
	
	//character, floor, star, platforms.
	public Rectangle character;
	public Rectangle floor;
	public Rectangle[] star;
	public Rectangle[] platforms;
	
	//Character rectangle.
	public Rectangle rect;
	
	//Bullet
	public Bullet bullet;
	
	//Background colors for the objects in game.
	public Color epicWaveColor = Color.red;
	public Color backgroundColor = Color.blue;
	public Color eyeColor = Color.cyan;
	public Color groundColor = Color.green;
	public Color starColor = Color.white;
	public Color playerColor = Color.darkGray;
	public Color bigColor = Color.orange;
	public Color midColor = Color.red;
	public Color smallColor = Color.gray;
	public Color bulletColor = Color.pink;
	public Color pausedStringColor = Color.red;
	public Color bulletStringColor = Color.white;
	public Color playerDeadColor = Color.red;
	public Color enemyColor = Color.black;
	public Color coinColor = Color.yellow;
	
	//Keys pressed.
	public int keyJump = KeyEvent.VK_UP;
	public int keyLeft = KeyEvent.VK_LEFT;
	public int keyRight = KeyEvent.VK_RIGHT;
	public int keyPause = KeyEvent.VK_P;
	public int keyA = KeyEvent.VK_A;
	public int keyR = KeyEvent.VK_R;
	public int keyB = KeyEvent.VK_B;
	public int keyD = KeyEvent.VK_D;
	public int keyE = KeyEvent.VK_E;
	public int keyP = KeyEvent.VK_P;
	public int keyI = KeyEvent.VK_I;
	public int keyC = KeyEvent.VK_C;
	public int keySpace = KeyEvent.VK_SPACE;
	
	//Platform Sizes.
	public int bigHeight = 30;
	public int bigWidth = 400;
	public int midHeight = 10;
	public int midWidth = 250;
	public int smallHeight = 5;
	public int smallWidth = 100;
	
	//Integer variables for game.
	public int characterWidth = 24;
	public int characterHeight = 36;
	public int fps = 1000;
	public int fallingSpeed = 1;
	public int fallingFrame = 0;
	public int floorHeight = 200;
	public int movementSpeed = 1;
	public int movementFrame = -1;
	public int movementFallingSpeed = 5;
	public int movementResetSpeed = 1;
	public int noseHeight = 10;
	public int noseWidth = 10;
	public int eyeHeight = 4;
	public int eyeWidth = 4;
	public int jumpingLength = 130; //Length in pixels.
	public int jumpingFrame = 0;
	public int jumpingCountFrame = 0;
	public int jumpingCountSpeed = fallingSpeed;
	public int currentStarSize;
	public int totalPlatforms = 30;
	public int bulletWidth = 7;
	public int bulletHeight = 4;
	public int totalBullets = 300;
	public int enemyWidth = 10;
	public int enemyHeight = 30;
	public int coinWidth = 10;
	public int coinHeight = 10;
	public int score = 0;
	public int timesReset = 0;
	public int level = 1;
	public int enemiesLeft = 0;
	public int topRightText = 100;
	public int randX = 1800;
	public int randY = 380;
	public int xs = 0;
	public int ys = 0;
	
	//Player health
	public float health = 100;
	
	//Booleans for game.
	public boolean objectsDefined = true;
	public boolean falling = false;
	public boolean running = true;
	public boolean leftNose = false;
	public boolean rightNose = true;
	public boolean leftEye = false;
	public boolean rightEye = true;
	public boolean leftMouth = false;
	public boolean rightMouth = true;
	public boolean jumping = false;
	public boolean inGame = true;
	public boolean checker = false;
	public boolean dead = false;
	public boolean won = false;
	
	//Static booleans
	public static boolean mebbeleft = false;
	public static boolean mebberight = true;
	public static boolean right = false;
	public static boolean left = false;
	
	//For game loop and sleeping.
	public Thread game;
	
	/**
	 * 
	 * @param f Frame
	 */
	
	public ac(ab f) {
		setBackground(backgroundColor);
		
		defineObjects();
		
		game = new Thread(this);
		game.start();
		
		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == keyLeft) {
					left = true;
					mebbeleft = true;
					mebberight = false;
					rightNose = false;
					leftNose = true;
					rightEye = false;
					leftEye = true;
				}
				if(e.getKeyCode() == keyRight) {
					right = true;
					mebbeleft = false;
					mebberight = true;
					leftNose = false;
					rightNose = true;
					leftEye = false;
					rightEye = true;
				}
				if(e.getKeyCode() == keyJump && !falling) {
					jumping = true;
				}
				if(e.getKeyCode() == keyPause) {
					if(inGame) {
						inGame = false;
					} else {
						inGame = true;
					}
				}
			}
			
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == keyLeft) {
					left = false;
				}
				if(e.getKeyCode() == keyRight) {
					right = false;
				}
				if(e.getKeyCode() == keyA) {
					if(totalBullets != 0) {
						if(inGame) {
							addBullet();
							bullets.add(bullet);
							totalBullets--;
						}
					}
				}
				if(e.getKeyCode() == keyR) {
					if(dead) {
						dead = false;
					}
					if(!inGame) {
						inGame = true;
					}
					xs = 0;
					ys = 0;
					character.x = aa.width/2;
					character.y = aa.height/2;
					for(Enemy enemy : enemies) {
						enemies.remove(enemy);
						enemy.setEnemySpeed((float)0.2);
					}
					for(Coin coin : coins) {
						coins.remove(coin);
					}
					addCoins();
					addEnemies();
					health = 100;
					score = 0;
					totalBullets = 300;
					level = 1;
					timesReset = 0;
				}
			}
			
			public void keyTyped(KeyEvent e) {
				
			}
		});
	}
	
	public Bullet addBullet() {
		bullet = new Bullet(character.x + 10, character.y + 20, bulletWidth, bulletHeight, this);
		return bullet;
	}
	
	public void addEnemies() {
		Random rand = new Random();
		if(won) {
			won = false;
		}
		for(int i = 0; i <= timesReset; i++) {
			enemies.add(new Enemy(rand.nextInt(randX), rand.nextInt(randY), enemyWidth, enemyHeight, this));
			enemiesLeft++;
		}
	}
	
	public void addCoins() {
		for(Coin coin : coins) {
			coins.remove(coin);
		}
		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(10); i++) {
			coins.add(new Coin(rand.nextInt(randX), rand.nextInt(randY), coinWidth, coinHeight, this));
		}
	}
	
	public void addPickUpBullets() {
		Random rand = new Random();
		
		if(timesReset % 2 == 0) {
			pickupbullets.add(new PickUpBullet(rand.nextInt(randX), rand.nextInt(randY), this));
		}
	}
	
	public void resetCoins() {
		addCoins();
	}
	
	public void resetEnemies() {
		addEnemies();
		timesReset += 20;
		level += 1;
	}
	
	public void defineObjects() {
		character = new Rectangle((aa.width/2) - (characterWidth/2), (aa.height/2) - (characterHeight/2), characterWidth, characterHeight);
		floor = new Rectangle(-10, aa.height - floorHeight, aa.width+10, floorHeight);
		
		star = new Rectangle[1000];
		Random ra = new Random();
		for(int i = 0; i < star.length; i++){
			currentStarSize = ra.nextInt(5);
			star[i] = new Rectangle(ra.nextInt(randX), ra.nextInt(randY + 500), currentStarSize, currentStarSize);
		}
		
		platforms = new Rectangle[totalPlatforms];
		for(int i = 0; i < (platforms.length/3); i++) {
			switch(i + 1) {
				case 1: platforms[i] = new Rectangle(150, 350, smallWidth, smallHeight);break;
				case 2: platforms[i] = new Rectangle(1000, 200, smallWidth, smallHeight);break;
				case 3: platforms[i] = new Rectangle(300, 260, smallWidth, smallHeight);break;
				case 4: platforms[i] = new Rectangle(900, 300, smallWidth, smallHeight);break;
				case 5: platforms[i] = new Rectangle(0, 350, smallWidth, smallHeight);break;
				case 6: platforms[i] = new Rectangle(-300, 260, smallWidth, smallHeight);break;
				case 7: platforms[i] = new Rectangle(0, 100, smallWidth, smallHeight);break;
				case 8: platforms[i] = new Rectangle(1300, 310, smallWidth, smallHeight);break;
				case 9: platforms[i] = new Rectangle(300, -200, smallWidth, smallHeight);break;
				case 10: platforms[i] = new Rectangle(1400, 70, smallWidth, smallHeight);break;
			}
		}
		for(int i = (totalPlatforms/3); i < (platforms.length/3)*2; i++) {
			switch(i + 1) {
				case 11: platforms[i] = new Rectangle(450, 180, midWidth, midHeight);break;
				case 12: platforms[i] = new Rectangle(800, 350, midWidth, midHeight);break;
				case 13: platforms[i] = new Rectangle(-200, 300, midWidth, midHeight);break;
				case 14: platforms[i] = new Rectangle(0, 0, midWidth, midHeight);break;
				case 15: platforms[i] = new Rectangle(-200, 180, midWidth, midHeight);break;
				case 16: platforms[i] = new Rectangle(1500, 180, midWidth, midHeight);break;
				case 17: platforms[i] = new Rectangle(1300, 500, midWidth, midHeight);break;
				case 18: platforms[i] = new Rectangle(300, -300, midWidth, midHeight);break;
				case 19: platforms[i] = new Rectangle(600, -10, midWidth, midHeight);break;
				case 20: platforms[i] = new Rectangle(1000, -20, midWidth, midHeight);break;
			}
		}
		for(int i = (totalPlatforms/3)*2; i < (platforms.length); i++) {
			switch(i + 1) {
				case 21: platforms[i] = new Rectangle(600, 100, bigWidth, bigHeight);break;
				case 22: platforms[i] = new Rectangle(1000, 400, bigWidth, bigHeight);break;
				case 23: platforms[i] = new Rectangle(-700, 200, bigWidth, bigHeight);break;
				case 24: platforms[i] = new Rectangle(1200, 200, bigWidth, bigHeight);break;
				case 25: platforms[i] = new Rectangle(1500, 400, bigWidth, bigHeight);break;
				case 26: platforms[i] = new Rectangle(-50, 100, bigWidth, bigHeight);break;
				case 27: platforms[i] = new Rectangle(150, -150, bigWidth, bigHeight);break;
				case 28: platforms[i] = new Rectangle(1000, -100, bigWidth, bigHeight);break;
				case 29: platforms[i] = new Rectangle(1200, 50, bigWidth, bigHeight);break;
				case 30: platforms[i] = new Rectangle(-500, 400, bigWidth, bigHeight);break;
			}
		}
		rect = new Rectangle(character.x, character.y, character.width, character.height);
		
		addCoins();
		
		addEnemies();
		
		addPickUpBullets();
		
		objectsDefined = true;
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(objectsDefined) {
			//Draw the Player
			if(!dead) {
				g.setColor(playerColor);
				g.fillRect(character.x - xs, character.y - ys, character.width, character.height);
			} else {
				g.setColor(playerDeadColor);
				g.fillRect(character.x - xs, character.y - ys, character.width - 20, character.height - 30);
				falling = false;
				g.drawString("You died!", aa.width/2, aa.height/2);
			}
			
			if(leftNose) {
				g.fillRect(character.x - noseWidth - xs, character.y + (character.height/4) - ys, noseWidth, noseHeight);
			} else if(rightNose) {
				g.fillRect(character.x + character.width - xs, character.y + (character.height/4) - ys, noseWidth, noseHeight);
			}
			g.setColor(eyeColor);
			if(leftEye) {
				g.fillRect(character.x + eyeWidth - xs, character.y + (character.height / 4) - 2 - ys, eyeWidth, eyeHeight);
			} else if(rightEye) {
				g.fillRect(character.x + (character.width/2) + 6 - xs, character.y + (character.height / 4) - 2 - ys, eyeWidth, eyeHeight);
			}
			
			//Draw the Stars.
			g.setColor(starColor);
			for(int i=0;i<star.length;i++){
				g.fill3DRect(star[i].x - xs, star[i].y - ys, star[i].width, star[i].height, true);
			}
			
			//Draw the Ground.
			g.setColor(groundColor);
			g.fillRect(floor.x - xs, floor.y - ys, floor.width, floor.height);
			
			//Draw the Bullets.
			g.setColor(bulletColor);
			for(Bullet bullet : bullets) {
				bullet.draw(g);
				bullet.update();
			}
			
			//Draw the PickUpBullets.
			for(PickUpBullet pickup : pickupbullets) {
				pickup.draw(g);
				pickup.update();
			}
			
			//Draw the Enemies.
			for(Enemy enemy : enemies) {
				g.setColor(enemy.getEnemyColor());
				enemy.draw(g);
				enemy.update();
			}
			
			g.setColor(coinColor);
			for(Coin coin : coins) {
				coin.draw(g);
				coin.update();
			}
			
			//Draw the Platforms.
			for(int i = 0; i < totalPlatforms; i++){
				if(i >= 0 && i < (totalPlatforms/3)) {
					g.setColor(smallColor);
				} else if(i >= (totalPlatforms/3) && i < (totalPlatforms/3)*2) {
					g.setColor(midColor);
				} else if(i >= ((totalPlatforms/3)*2) && i < totalPlatforms) {
					g.setColor(bigColor);
				}
				g.fillRect(platforms[i].x - xs, platforms[i].y - ys, platforms[i].width, platforms[i].height);
			}
			
			//Make the string to say game is paused.
			if(!inGame) {
				if(!dead) {
					g.setColor(pausedStringColor);
					g.drawString("PAUSED: P TO UNPAUSE", aa.width/2 - 50, aa.height/2);
				}
			}
			
			//Bullets left.
			if(inGame) {
				g.setColor(bulletStringColor);
			}
			g.drawString("Health: " + Integer.toString(((int)health)), aa.width - topRightText, 20);
			g.drawString("Bullets: " + Integer.toString(totalBullets), aa.width - topRightText, 40);
			g.drawString("Press R to Restart", 10, 20);
			g.drawString("Score: " + Integer.toString(score), aa.width/2, 20);
			g.drawString("Level " + Integer.toString(level), aa.width - 60, aa.height - 40);
			g.drawString("Enemies Left: " + Integer.toString(enemiesLeft), aa.width - topRightText, 60);
			
			//Update character rectangle.
			rect.x = character.x;
			rect.y = character.y;
			rect.width = character.width;
			rect.height = character.height;
			
			//If you won.
			if(won) {
				addPickUpBullets();
				resetEnemies();
				resetCoins();
			}
		}
	}

	@Override
	public void run() {
		while(running) {
				if(inGame) {
					
					//Character feet.
					Point pt1 = new Point(character.x, character.y + character.height);
					Point pt2 = new Point(character.x + character.width, character.y + character.height);
					
					//Falling.
					if(!jumping) {
					if(fallingFrame >= fallingSpeed) {
						for(int i = 0; i < totalPlatforms; i++) {
							if(platforms[i].contains(pt1) || platforms[i].contains(pt2) || floor.contains(pt1) || floor.contains(pt2)) {
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
							character.y += 1;
							ys += 1;
							if(character.y >= aa.height) {
								dead = true;
								health = 0;
								inGame = false;
							}
						}
						
						fallingFrame = 0;
					} else {
						fallingFrame += 1;
					}
					}
					
					if(jumpingCountFrame >= jumpingCountSpeed) {
						if(jumping) {
							if(jumpingFrame <= jumpingLength) {
								character.y -= 1;
								ys -= 1;
									
								jumpingFrame += 1;
							} else {
								jumpingFrame = 0;
								jumping = false;
							}
						}
								
						jumpingCountFrame = 0;
					} else {
						jumpingCountFrame += 1;
					}
					
					//Movement speed check.
					if(falling) {
						movementSpeed = movementFallingSpeed;
					} else {
						movementSpeed = movementResetSpeed;
					}
					
					//Movement.
					if(movementFrame >= movementSpeed) {
						if(right) {
							character.x += 1;
							xs += 1;
						}
						
						if(left) {
							character.x -= 1;
							xs -= 1;
						}
						
						movementFrame = -1;
					} else {
						movementFrame += 1;
					}
					
					if(health <= 0) {
						dead = true;
						inGame = false;
						health = 0;
					}
					
					fpsSetter();
					
					repaint();
			}
		}
	}
	
	public void fpsSetter() {
		try {
			Thread.sleep(fps/1000);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
