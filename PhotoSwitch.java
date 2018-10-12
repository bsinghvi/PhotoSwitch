import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PhotoSwitch extends JFrame implements ActionListener, ChangeListener
{
	JLabel picture; 
	JButton revert, noRed, noGreen, noBlue, grayScale, aboutX, aboutY, flipY, flipX, puzzle, print;
	BufferedImage img;
	String filePath = "";
	BufferedImage original = null; 
	JSlider reddy, greeny, bluey;
	int width = 912;
	int height = 504;
	double percentR = 0.0;
	double percentG = 0.0;
	double percentB = 0.0;
	int r = 4; //rows of puzzle
	int c = 8; //columns of puzzle
	
	public static void main(String[] args) throws IOException 
	{
		PhotoDriver obj= new PhotoDriver();
		obj.setVisible(true);
	}
	public PhotoSwitch() throws IOException
	{
		super("");
		setSize(2000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
			northPanel.setBackground(new Color(154, 205, 50));
			JLabel title = new JLabel("PhotoFilter ");
			Font newFont1 = new Font("Impact", Font.ITALIC, 26);
			title.setFont(newFont1);
			northPanel.add(title);
		add(northPanel,BorderLayout.NORTH);
		JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());
			JPanel westCenterPanel = new JPanel();
				westCenterPanel.setBackground((new Color(255,250,205)));
					filePath = JOptionPane.showInputDialog("Please enter the filepath to your image. This should be in the form such as: C:\\\\Users\\Bob\\Desktop\\image.jpg");
					try {
						File initialImage = new File(filePath);
						original = ImageIO.read(initialImage);
					}
					catch (IOException r){
						 System.out.println("Exception occured :" + r.getMessage());
					}
					original = clone(original); //cloning it here allows the image to be resized, which is also carried out by the clone method
					img =  (BufferedImage) original;
					ImageIcon icon = new ImageIcon(original);
					picture = new JLabel("", icon, JLabel.CENTER);
				westCenterPanel.add(picture);
			centerPanel.add(westCenterPanel,BorderLayout.WEST);
			JPanel eastCenterPanel = new JPanel();
				Font newFont3 = new Font("Chalkduster", Font.BOLD, 18);
				eastCenterPanel.setLayout(new GridLayout(11,1));
				eastCenterPanel.setBackground(new Color(154, 205, 50));
				revert = new JButton("Original");
					revert.addActionListener(this);
					revert.setFont(newFont3);
				eastCenterPanel.add(revert);
				noRed = new JButton("Delete Red");
					noRed.addActionListener(this);
					noRed.setFont(newFont3);
				eastCenterPanel.add(noRed);
				noGreen = new JButton("Delete Green");
					noGreen.addActionListener(this);
					noGreen.setFont(newFont3);
				eastCenterPanel.add(noGreen);
				noBlue = new JButton("Delete Blue");
					noBlue.addActionListener(this);
					noBlue.setFont(newFont3);
				eastCenterPanel.add(noBlue);
				grayScale = new JButton("Gray it");
					grayScale.addActionListener(this);
					grayScale.setFont(newFont3);
				eastCenterPanel.add(grayScale);
				flipX = new JButton("Flip X");
					flipX.addActionListener(this);
					flipX.setFont(newFont3);
				eastCenterPanel.add(flipX);
				flipY = new JButton("Flip Y");
					flipY.addActionListener(this);
					flipY.setFont(newFont3);
				eastCenterPanel.add(flipY);
				aboutX = new JButton("Mirror about X");
					aboutX.addActionListener(this);
					aboutX.setFont(newFont3);
				eastCenterPanel.add(aboutX);
				aboutY = new JButton("Mirror about Y");
					aboutY.addActionListener(this);
					aboutY.setFont(newFont3);
				eastCenterPanel.add(aboutY);
				puzzle = new JButton("Puzzle it");
					puzzle.addActionListener(this);
					puzzle.setFont(newFont3);
				eastCenterPanel.add(puzzle);
				print = new JButton("Print it");
					print.addActionListener(this);
					print.setFont(newFont3);
				eastCenterPanel.add(print);
			centerPanel.add(eastCenterPanel,BorderLayout.CENTER);
		add(centerPanel,BorderLayout.CENTER);
		JPanel eastPanel = new JPanel();
			eastPanel.setLayout(new GridLayout(3,2,16,16));
			eastPanel.setBackground(new Color(154, 205, 50));
			reddy = new JSlider();
				reddy.setOrientation(SwingConstants.VERTICAL);
				reddy.setMajorTickSpacing(10);
				reddy.setMinorTickSpacing(1);
				reddy.setValue(100);
				reddy.setPaintTicks(true);
				reddy.setPaintLabels(true);
				reddy.addChangeListener(this);
			eastPanel.add(reddy);
			JLabel r = new JLabel("% Red");
			eastPanel.add(r);
			greeny = new JSlider();
				greeny.setOrientation(SwingConstants.VERTICAL);
				greeny.setMajorTickSpacing(10);
				greeny.setMinorTickSpacing(1);
				greeny.setPaintTicks(true);
				greeny.setPaintLabels(true);
				greeny.setValue(100);
				greeny.addChangeListener(this);
			eastPanel.add(greeny);
			JLabel g1 = new JLabel("% Green");
			eastPanel.add(g1);
			bluey = new JSlider();
				bluey.setOrientation(SwingConstants.VERTICAL);
				bluey.setMajorTickSpacing(10);
				bluey.setMinorTickSpacing(1);
				bluey.setPaintTicks(true);
				bluey.setPaintLabels(true);
				bluey.setValue(100);
				bluey.addChangeListener(this);
			eastPanel.add(bluey);
			JLabel b = new JLabel("% Blue");
			eastPanel.add(b);
		add(eastPanel,BorderLayout.EAST);
	}
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		if(action.equals("Delete Red")) //Removes all red from the picture
		{
			BufferedImage inEdit = clone(original);
			
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color is = new Color(inEdit.getRGB(x,y));
					int blue= (int)(is.getBlue());
					int green= (int)(is.getGreen());
					Color newColor = new Color(0, green, blue);
					inEdit.setRGB(x, y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			picture.setIcon(icon);
			reddy.setValue(0);
			bluey.setValue(100);
			greeny.setValue(100);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
		}
		if(action.equals("Delete Green")) //Removes all green from the picture
		{
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color is = new Color(inEdit.getRGB(x,y));
					int red= (int)(is.getRed());
					int blue= (int)(is.getBlue());
					Color newColor = new Color(red, 0, blue);
					inEdit.setRGB(x, y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			picture.setIcon(icon);
			reddy.setValue(100);
			greeny.setValue(0);
			bluey.setValue(100);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
		}
		if(action.equals("Delete Blue")) //Removes all blue from the picture
		{
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing 

			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color is = new Color(inEdit.getRGB(x,y));
					int red= (int)(is.getRed());
					int green= (int)(is.getGreen());
					Color newColor = new Color(red, green, 0);
					inEdit.setRGB(x, y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			picture.setIcon(icon);
			reddy.setValue(100);
			greeny.setValue(100);
			bluey.setValue(0);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
		}
		if(action.equals("Original")) //Resets the picture to the original
		{
			orig(); //Sets the color sliders back to 100.
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);	
		}
		if(action.equals("Gray it")) //Implements grayscale on the picture
		{
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			int sum = 0;
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color is = new Color(inEdit.getRGB(x,y));
					int red= (int)(is.getRed());
					int blue= (int)(is.getBlue());
					int green= (int)(is.getGreen());
					sum = (red + blue + green)/3; 
					Color newColor = new Color(sum, sum, sum);
					inEdit.setRGB(x, y, newColor.getRGB());
				}
			}
			reddy.setValue(sum);
			greeny.setValue(sum);
			bluey.setValue(sum);
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);
		}
		if(action.equals("Flip X")) //Flips the picture about the x-axis
		{
			orig(); //Sets the color sliders back to 100. 
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			
			Color[][] is = new Color[inEdit.getWidth()][inEdit.getHeight()];
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					is[x][inEdit.getHeight()-1-y] = new Color(inEdit.getRGB(x,y));
				}
			}
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color tis = is[x][y];
					int red= (int)(tis.getRed());
					int blue= (int)(tis.getBlue());
					int green= (int)(tis.getGreen());
					Color newColor = new Color(red, green, blue);
					inEdit.setRGB(x, y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);
		}
		if(action.equals("Flip Y")) //Flips the picture about the y-axis
		{
			orig(); //Sets the color sliders back to 100. 
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			Color[][] is = new Color[inEdit.getWidth()][inEdit.getHeight()];
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					is[inEdit.getWidth()-1-x][y] = new Color(inEdit.getRGB(x,y));
				}
			}
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color tis = is[x][y];
					int red= (int)(tis.getRed());
					int blue= (int)(tis.getBlue());
					int green= (int)(tis.getGreen());
					Color newColor = new Color(red, green, blue);
					inEdit.setRGB(x, y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);
		}
		if(action.equals("Mirror about Y")) //Mirrors the picture about the y-axis
		{
			orig(); //Sets the color sliders back to 100. 
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color is = new Color(inEdit.getRGB(x,y));
					int red= (int)(is.getRed());
					int blue= (int)(is.getBlue());
					int green= (int)(is.getGreen());
					Color newColor = new Color(red, green, blue);
					inEdit.setRGB(inEdit.getWidth()-1-x, y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);
		}
		if(action.equals("Mirror about X")) //Mirrors the picture about the x-axis
		{
			orig(); //Sets the color sliders back to 100. 
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			for(int x=0; x<inEdit.getWidth(); x++)
			{
				for(int y=0; y<inEdit.getHeight(); y++)
				{
					Color is = new Color(inEdit.getRGB(x,y));
					int red= (int)(is.getRed());
					int blue= (int)(is.getBlue());
					int green= (int)(is.getGreen());
					Color newColor = new Color(red, green, blue);
					inEdit.setRGB(x, inEdit.getHeight()-1-y, newColor.getRGB());
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);
		}
		if(action.equals("Puzzle it")) //Creates a tile puzzle out of original picture
		{
			orig(); //Sets the color sliders back to 100. 
			BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
			int blockX = inEdit.getWidth()/c;
			int blockY = inEdit.getHeight()/r;
			int number = blockX*blockY;
			Color[][][] is = new Color[r][c][number];
			int count = 0;
			for(int i=0; i<r; i++)
			{
				for(int j=0; j<c; j++)
				{
					count = 0;
					for(int x=j*blockX; x<(j*blockX)+blockX; x++)
					{
						for(int y=i*blockY; y<(i*blockY)+blockY; y++)
						{
							is[i][j][count] = new Color(inEdit.getRGB(x,y));
							count+=1;
						}
					}
				}
			}
			int[][][] ar = new int[r][c][2]; 
			ar = randomize(r,c); //randomizes what blocks of pictures go in what rows and columns
			for(int i=0; i<r; i++)
			{
				for(int j=0; j<c; j++)
				{
					count = 0;
					int a = ar[i][j][0];
					int b = ar[i][j][1];
					for(int x=j*blockX; x<(j*blockX)+blockX; x++)
					{
						for(int y=i*blockY; y<(i*blockY)+blockY; y++)
						{
							Color tis = is[a][b][count];
							int red= (int)(tis.getRed());
							int blue= (int)(tis.getBlue());
							int green= (int)(tis.getGreen());
							Color newColor = new Color(red, green, blue);
							inEdit.setRGB(x, y, newColor.getRGB());
							count++;
						}
					}
				}
			}
			ImageIcon icon = new ImageIcon(inEdit);
			Image im = icon.getImage();
			img =  (BufferedImage) im;
			picture.setIcon(icon);
		}
		if(action.equals("Print it"))
		{
			 BufferedImage bImage = img;
			 String usr = JOptionPane.showInputDialog("File will be saved to Desktop. What is your user name? ");
			 String name = JOptionPane.showInputDialog("File will be saved to Desktop. What do you want the image to be called? ");
			 try {
	             ImageIO.write(bImage, "jpg", new File("C://Users/" + usr + "/Desktop/" + name + ".jpg"));
	         } catch (IOException x) {
	               System.out.println("Exception occured :" + x.getMessage());
	         }
	         System.out.println("Images were written succesfully.");
		}
	}
	/**
	 * Checks if the color sliders have been moved manually, and if they have, the pixels are adjusted accordingly
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		percentR = (reddy.getValue()+0.0)/100;
		percentG = (greeny.getValue()+0.0)/100;
		percentB = (bluey.getValue()+0.0)/100;
		BufferedImage inEdit = clone(original);  //Resets the picture to original for editing
		for(int x=0; x<inEdit.getWidth(); x++)
		{
			for(int y=0; y<inEdit.getHeight(); y++)
			{
				Color is = new Color(inEdit.getRGB(x,y));
				int red= (int)(is.getRed()*percentR);
				int blue= (int)(is.getBlue()*percentB);
				int green= (int)(is.getGreen()*percentG);
				Color newColor = new Color(red, green, blue);
				inEdit.setRGB(x, y, newColor.getRGB());
			}
		}
		
		ImageIcon icon = new ImageIcon(inEdit);
		picture.setIcon(icon);
	}
	/**
	 * Resets the color sliders back to 100%
	 */
	public void orig()
	{
		reddy.setValue(100);
		greeny.setValue(100);
		bluey.setValue(100);
	}
	//C:\Users\Bhaskar Singhvi\Pictures\Saved Pictures\windows_xp.jpg
	/**
	 * Clones the BufferedImage so that the original file remains unchanged, 
	 * and a new file is created according to the specified dimensions
	 * @param image
	 * @return image
	 */
	public BufferedImage clone(BufferedImage image)
	{
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		Graphics2D g = newImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(),image.getHeight(), null);
		g.dispose();
		return newImage;
	}
	/**
	 * Randomizes row and columns for the blocks in the tile puzzle
	 * @param r
	 * @param c
	 * @return integer 3-d array
	 */
	public int[][][] randomize(int r, int c)
	{
		int[][][] array = new int[r][c][2];
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		for(int i=0; i<r; i++)
		{
			for(int j=0; j<c; j++)
			{
				x.add(i);
				y.add(j);
			}
		}
		ArrayList<Integer> x1 = new ArrayList<Integer>();
		ArrayList<Integer> y1 = new ArrayList<Integer>();
		Random r1 = new Random();
		for(int i=0; i<r*c; i++)
		{
			int number = r1.nextInt(x.size());
			x1.add(x.get(number));
			x.remove(number);
			y1.add(y.get(number));
			y.remove(number);
		}
		int count = 0;
		for(int i=0; i<r; i++)
		{
			for(int j=0; j<c; j++)
			{
				array[i][j][0] = x1.get(count);
				array[i][j][1] = y1.get(count);
				count+=1;
			}
		}
		return array;
	}
}
