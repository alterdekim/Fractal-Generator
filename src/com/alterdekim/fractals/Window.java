package com.alterdekim.fractals;

import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Window {

	private JFrame frame;
	
	public static float hueStart = 0f;
	public static float hueEnd = 100f;
	
	public static float satStart = 80f;
	public static float satEnd = 100f;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Fractal generator");
		Canvas panel = new Canvas();
		frame.addKeyListener(panel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JMenuBar mainMenu = new JMenuBar();
		frame.getContentPane().add(mainMenu, BorderLayout.NORTH);
		
		JMenu formula_menu = new JMenu("Formula");
		mainMenu.add(formula_menu);
		
		JMenuItem mntmmandelbrot = new JMenuItem("Mandelbrot");
		formula_menu.add(mntmmandelbrot);
		
		mntmmandelbrot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.paintMandel();
			}
		});
		
		JMenuItem juliamntm = new JMenuItem("Julia");
		formula_menu.add(juliamntm);
		
		juliamntm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String arg = JOptionPane.showInputDialog("Enter arg0: ");
				String _arg = JOptionPane.showInputDialog("Enter arg1: ");
				if( arg != null && _arg != null ) {
					panel.paintJulia(arg, _arg);
				}
			}
		});
		
		JMenuItem bshipmntm = new JMenuItem("Burning ship");
		formula_menu.add(bshipmntm);
		
		bshipmntm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.paintBurningship();
			}
		});
		
		JMenuItem cmmntm = new JMenuItem("Custom mandelbrot");
		formula_menu.add(cmmntm);
		
		JMenu mnNewMenu = new JMenu("GUI");
		mainMenu.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Choose");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnPos = new JMenu("Position");
		mainMenu.add(mnPos);
		
		JMenuItem mntmRes = new JMenuItem("Reset");
		mnPos.add(mntmRes);
		
		mntmRes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setInterval(-2, 0.5, -1, 1, 1, 1);
				panel.repaint();
			}
		});
		
		JMenuItem mntmPosJump = new JMenuItem("Jump into...");
		mnPos.add(mntmPosJump);
		
		JMenu mnExport = new JMenu("Export");
		mainMenu.add(mnExport);
		
		JMenuItem mntmExportPNG = new JMenuItem("Export to PNG");
		mnExport.add(mntmExportPNG);
		mntmExportPNG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				panel.printAll(g);
				g.dispose();
				try { 
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Choose file");   
					 
					int userSelection = fileChooser.showSaveDialog(frame);
					 
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						ImageIO.write(image, "png", fileChooser.getSelectedFile());
					} 
				} catch (Exception ex) {
				    ex.printStackTrace();
				}
			}
		});
		
		mntmPosJump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String arg = JOptionPane.showInputDialog("Enter Rez: ");
				String _arg = JOptionPane.showInputDialog("Enter Imz: ");
				String __arg = JOptionPane.showInputDialog("Enter zoom: ");
				if( arg != null && _arg != null && __arg != null ) {
					double centerX = Double.parseDouble(arg);
					double centerY = Double.parseDouble(_arg);
					double size = Double.parseDouble(__arg);
					panel.setInterval(centerX-size, centerX+size, centerY-size, centerY+size, 1, 1);
					panel.repaint();
				}
			}
		});
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GradientChoice dialog = new GradientChoice();   
				dialog.setVisible(true);
				if( dialog.isOk ) {
					panel.repaint();
				}
			}
		});
		
		cmmntm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String arg = JOptionPane.showInputDialog("Enter arg0: ");
				if( arg != null ) {
					panel.paintCustom(arg);
				}
			}
		});
	}

}
