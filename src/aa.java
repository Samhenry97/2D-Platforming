import javax.swing.JFrame;

public class aa {
	public static ab f;
	public static int width = 800;
	public static int height = 600;
	
	public static void main(String[] args) {
		f = new ab();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(width, height);
		f.setTitle("2D Platform Game");
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
