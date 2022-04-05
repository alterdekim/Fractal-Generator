package com.alterdekim.fractals;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.Dialog.ModalityType;

public class GradientChoice extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	public boolean isOk = false;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			GradientChoice dialog = new GradientChoice();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public GradientChoice() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 350, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hue:");
		lblNewLabel.setBounds(6, 6, 338, 25);
		contentPanel.add(lblNewLabel);
		
		HuePanel huePanel = new HuePanel();
		huePanel.setBounds(6, 36, 338, 40);
		contentPanel.add(huePanel);
		huePanel.endX = 338;
		huePanel.repaint();
		
		RangeSlider hueSlider = new RangeSlider();
		hueSlider.addChangeListener(new ChangeListener() {
		  public void stateChanged(ChangeEvent e) {
		      huePanel.startX = (int) ((((double)hueSlider.getValue()) / ((double)100)) * ((double)huePanel.getWidth()));
		      huePanel.endX = (int) ((((double)hueSlider.getUpperValue()) / ((double)100)) * ((double)huePanel.getWidth()));
		      huePanel.repaint();
		    }
		  });
		hueSlider.setValue((int)Window.hueStart);
		hueSlider.setUpperValue((int)Window.hueEnd);
		hueSlider.setBounds(6, 81, 338, 29);
		contentPanel.add(hueSlider);
		huePanel.repaint();
		
		JLabel lblNewSat = new JLabel("Saturation:");
		lblNewSat.setBounds(6, 115, 338, 25);
		contentPanel.add(lblNewSat);
		
		SatPanel satPanel = new SatPanel();
		satPanel.setBounds(6, 140, 338, 40);
		contentPanel.add(satPanel);
		satPanel.endX = 338;
		satPanel.repaint();
		
		RangeSlider satSlider = new RangeSlider();
		satSlider.addChangeListener(new ChangeListener() {
		  public void stateChanged(ChangeEvent e) {
			  satPanel.startX = (int) ((((double)satSlider.getValue()) / ((double)100)) * ((double)satPanel.getWidth()));
			  satPanel.endX = (int) ((((double)satSlider.getUpperValue()) / ((double)100)) * ((double)satPanel.getWidth()));
			  satPanel.repaint();
		    }
		  });
		satSlider.setValue((int)Window.satStart);
		satSlider.setUpperValue((int)Window.satEnd);
		satSlider.setBounds(6, 180, 338, 29);
		contentPanel.add(satSlider);
		satPanel.repaint();
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Window.satStart = Canvas.rangeConvert(satPanel.startX, 0, 338, 0, 100);
						Window.satEnd = Canvas.rangeConvert(satPanel.endX, 0, 338, 0, 100);
						Window.hueStart = Canvas.rangeConvert(huePanel.startX, 0, 338, 0, 100);
						Window.hueEnd = Canvas.rangeConvert(huePanel.endX, 0, 338, 0, 100);
						isOk = true;
						dispose();
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
