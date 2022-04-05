package com.alterdekim.fractals;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	private static final int MAX_ITER = 200;
	
	private static double RE_START = -2;
	private static double RE_END = 0.5;
	private static double IM_START = -1;
	private static double IM_END = 1;
	
	private double juliaRez = 0;
	private double juliaImz = 0;
	
	private int mArg = 3;
	
	private double sizeX = 1;
	private double sizeY = 1;
	
	private int type = 0;
	
	public Canvas() {
		this.addKeyListener(this);
	}
	
	public void setInterval( double r_s, double r_e, double i_s, double i_e, double sX, double sY ) {
		Canvas.RE_START = r_s;
		Canvas.RE_END = r_e;
		Canvas.IM_START = i_s;
		Canvas.IM_END = i_e;
		this.sizeX = sX;
		this.sizeY = sY;
	}
	
	private int mandelbrot( Complex c ) {
		Complex z = new Complex(0,0);
		int n = 0;
		while ( ComplexUtils.abs(z) <= 2 && n < MAX_ITER) {
			z = ComplexUtils.pow(z);
			z = ComplexUtils.sum(z, c);
			n++;
		}
		return n;
	}
	
	private int bship( Complex c ) {
		Complex z = new Complex(0,0);
		int n = 0;
		while ( ComplexUtils.abs(z) <= 2 && n < MAX_ITER) {
			z = new Complex( Math.abs(z.Rez), Math.abs(z.Imz) );
			z = ComplexUtils.pow(z);
			z = ComplexUtils.sum(z, c);
			n++;
		}
		return n;
	}
	
	private int customMandelbrot( Complex c ) {
		Complex z = new Complex(0,0);
		int n = 0;
		while( ComplexUtils.abs(z) <= 2 && n < MAX_ITER ) {
			z = ComplexUtils.pow(z, mArg);
			z = ComplexUtils.sum(z, c);
			n++;
		}
		return n;
	}
	
	private int julia( Complex c, Complex j ) {
		Complex z = c;
		c = j;
		int n = 0;
		while ( ComplexUtils.abs(z) <= 2 && n < MAX_ITER) {
			z = ComplexUtils.pow(z);
			z = ComplexUtils.sum(z, c);
			n++;
		}
		return n;
	}
	
	public void paintMandel() {
		this.type = 0;
		this.repaint();
	}
	
	public void paintJulia( String arg, String _arg ) {
		this.type = 1;
		this.juliaRez = Double.parseDouble(arg);
		this.juliaImz = Double.parseDouble(_arg);
		this.repaint();
	}
	
	public void paintBurningship() {
		this.type = 2;
		this.repaint();
	}
	
	public void paintCustom( String arg ) {
		this.mArg = Integer.parseInt(arg);
		this.type = 3;
		this.repaint();
	}

	@Override
	public void paintComponent( Graphics g ) {
		int width = this.getWidth();
		int height = this.getHeight();
		for( int x = 0; x < width; x++ ) {
			for( int y = 0; y < height; y++ ) {
				Complex c = new Complex( ((((x) * (RE_END-RE_START)) / (width) ) + RE_START) / sizeX, ((((y) * (IM_END-IM_START)) / (height)) + IM_START) / sizeY );
				float color = 0.0f;
				if( this.type == 1 ) {
					color = ((float) julia(c, new Complex(juliaRez, juliaImz))) / ((float) MAX_ITER);
				} else if( this.type == 0 ) {
					color = ((float) mandelbrot(c)) / ((float) MAX_ITER);
				} else if( this.type == 2 ) {
					color = ((float) bship(c)) / ((float) MAX_ITER);
				} else if( this.type == 3 ) {
					color = ((float) customMandelbrot(c)) / ((float) MAX_ITER);
				}
				
				float val = rangeConvert( color, 0f, 1f, Window.hueStart, Window.hueEnd ) * 0.01f;
				float sat = rangeConvert( color, 0f, 1f, Window.satStart, Window.satEnd ) * 0.01f;
				g.setColor(Color.getHSBColor(val, sat, 1 - val));
				g.fillRect(x, y, 1, 1);
			}
		}
	}
	
	public static float rangeConvert( float value, float leftMin, float leftMax, float rightMin, float rightMax ) {
	    float leftSpan = leftMax - leftMin;
	    float rightSpan = rightMax - rightMin;
	    float valueScaled = (value - leftMin) / (leftSpan);
	    return rightMin + (valueScaled * rightSpan);
	}
	
	private static class ComplexUtils {
		public static double abs( Complex c ) {
			return Math.sqrt( Math.pow(c.Rez, 2) + Math.pow(c.Imz, 2) );
		}
		
		public static Complex sum( Complex f, Complex s ) {
			return new Complex( f.Rez + s.Rez, f.Imz + s.Imz );
		}
		
		public static Complex pow( Complex c ) {
			return new Complex((c.Rez * c.Rez) + ((-1) * (c.Imz * c.Imz)), (2 * c.Rez * c.Imz));
		}
		
		public static Complex pow( Complex c, int num ) {
			Complex n = c;
			for( int i = 0; i < num; i++ ) {
				n = ComplexUtils.multiply(n, c);
			}
			return n;
		}
		
		public static Complex multiply( Complex f, Complex s ) {
			return new Complex(((f.Rez * s.Rez)-(f.Imz*s.Imz)), (f.Rez * s.Imz) + (f.Imz * s.Rez));
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			RE_START += 0.01/sizeX;
			RE_END += 0.01/sizeY;
			this.repaint();
		} else if( e.getKeyCode() == KeyEvent.VK_LEFT ) {
			RE_START -= 0.01/sizeX;
			RE_END -= 0.01/sizeY;
			this.repaint();
		} else if( e.getKeyCode() == KeyEvent.VK_UP ) {
			IM_START -= 0.01/sizeX;
			IM_END -= 0.01/sizeY;
			this.repaint();
		} else if( e.getKeyCode() == KeyEvent.VK_DOWN ) {
			IM_START += 0.01/sizeX;
			IM_END += 0.01/sizeY;
			this.repaint();
		} else if( e.getKeyCode() == KeyEvent.VK_P ) {
			sizeX += 0.01;
			sizeY += 0.01;
			this.repaint();
		} else if( e.getKeyCode() == KeyEvent.VK_O ) {
			sizeX -= 0.01;
			sizeY -= 0.01;
			this.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
