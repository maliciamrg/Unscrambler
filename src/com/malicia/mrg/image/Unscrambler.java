package com.malicia.mrg.image;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Unscrambler {

	private static JFrame buildFrame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setVisible(true);
		return frame;
	}

	public static void main(String[] args) {

		/*
		 * JFrame frame = buildFrame(); JPanel container = new JPanel();
		 * container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		 * frame.add(container); JLabel pane1 = new JLabel();
		 * container.add(pane1); JLabel panev = new JLabel();
		 * container.add(panev);panev.setText(" "); JLabel pane2 = new JLabel();
		 * container.add(pane2); JLabel paner = new JLabel();
		 * container.add(paner);
		 */
		// TODO Auto-generated method stub
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		try {
			InputStream resourceBuff1 = Unscrambler.class.getResourceAsStream("/clavierreference.jsf");
			InputStream resourceBuff2 = Unscrambler.class.getResourceAsStream("/claviermelanger.jsf");
			img1 = ImageIO.read(resourceBuff1);
			img2 = ImageIO.read(resourceBuff2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int w = 15;
		int h = 15;
		for (int d2 = -1; d2 <= 1; ++d2) {
			for (int y1 = 1; y1 <= 2; ++y1) {
				for (int x1 = 1; x1 <= 5; ++x1) {
					BufferedImage img1part = img1.getSubimage((x1 * 40) - w - ((40 - w) / 2),
							(y1 * 40) - h - ((40 - h) / 2), w, h);
					for (int y2 = 1; y2 <= 2; ++y2) {
						for (int x2 = 1; x2 <= 5; ++x2) {
							BufferedImage img2part = img2.getSubimage(d2 + (x2 * 40) - w - ((40 - w) / 2),
									(y2 * 40) - h - ((40 - h) / 2), w, h);

							double res = imgDiffPercent(img1part, img2part);
							/*
							 * pane1.setIcon(new ImageIcon(img1part));
							 * pane2.setIcon(new ImageIcon(img2part));
							 * paner.setText((int)res + " %");
							 * frame.revalidate();
							 * 
							 * if (x1==4 & y1==1 & x2==3 & y2==1){ try {
							 * Thread.sleep(5000); } catch (InterruptedException
							 * e) { // TODO Auto-generated catch block
							 * e.printStackTrace(); } }
							 */

							if (res < 1) {
								System.out.println(y1 + "-" + x1 + "-" + y2 + "-" + x2 + "-" + res);
							}
						}
					}
				}
			}
		}

	}

	public static double imgDiffPercent(BufferedImage img1, BufferedImage img2) {
		int width1 = img1.getWidth(null);
		int width2 = img2.getWidth(null);
		int height1 = img1.getHeight(null);
		int height2 = img2.getHeight(null);
		if ((width1 != width2) || (height1 != height2)) {
			System.err.println("Error: Images dimensions mismatch");
			return 0;
		}
		long diff = 0;
		for (int y = 0; y < height1; y++) {
			for (int x = 0; x < width1; x++) {
				int rgb1 = img1.getRGB(x, y);
				int rgb2 = img2.getRGB(x, y);
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >> 8) & 0xff;
				int b1 = (rgb1) & 0xff;
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >> 8) & 0xff;
				int b2 = (rgb2) & 0xff;
				diff += Math.abs(r1 - r2);
				diff += Math.abs(g1 - g2);
				diff += Math.abs(b1 - b2);
			}
		}
		double n = width1 * height1 * 3;
		double p = diff / n / 255.0;
		return (p * 100.0);
	}

}
