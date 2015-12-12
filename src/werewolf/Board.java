package werewolf;

import javax.swing.*;
import javax.swing.Timer;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class Board extends JPanel {
	
	JPanel topPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JLabel screenCard = new JLabel();
	JLabel screenCard2 = new JLabel();
	RoleCard rolecard = new RoleCard();
	JButton startButton = new JButton();
	JButton player1 = new JButton();
	JButton player2;
	JButton player3;
	JButton center1;
	JButton center2;
	JButton center3;
	JButton player4;
	JButton player5;
	RoleCountdown rc;
	boolean flip = false;
	int player;
//	RoleCountdown rc;

	public Board () {
		topPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new FlowLayout());
		bottomPanel.setLayout(new FlowLayout());

		player1.setIcon(new ImageIcon("werewolfcard.jpg"));
		player1.setText("Player 1");
		player1.addActionListener(new playerAL());
		topPanel.add(player1);
		
		player2 = new JButton();
		player2.setText("Player 2");
		player2.setIcon(new ImageIcon("werewolfcard.jpg"));
		player2.addActionListener(new playerAL());
		topPanel.add(player2);
		
		center1 = new JButton();
		center1.setIcon(new ImageIcon("werewolfcard.jpg"));
		center1.addActionListener(new playerAL());
		centerPanel.add(center1);
		center2 = new JButton();
		center2.setIcon(new ImageIcon("werewolfcard.jpg"));
		center2.addActionListener(new playerAL());
		centerPanel.add(center2);
		center3 = new JButton();
		center3.setIcon(new ImageIcon("werewolfcard.jpg"));
		center3.addActionListener(new playerAL());
		centerPanel.add(center3);
		
		player3 = new JButton();
		player3.setIcon(new ImageIcon("werewolfcard.jpg"));
		player3.addActionListener(new playerAL());
		bottomPanel.add(player3);

		setLayout(new BorderLayout());

		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		startButton.setText("Start Game");
		startButton.addActionListener(new startButtonAL());
		add(startButton, BorderLayout.EAST);
		
		setVisible(true);
		
	}

	private class startButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startButton.setVisible(false);
			revealRoles();
		}
	}
	
	private class playerAL implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			if (f.getSource().equals(player1)) {
				player = 1;
			} else if (f.getSource().equals(player2)) {
				player = 2;
			} else if (f.getSource().equals(player3)) {
				player = 3;
			} else if (f.getSource().equals(player4)) {
				player = 4;
			} else if (f.getSource().equals(player5)) {
				player = 5;
			}
			flip(player);
		}
	}
	
//	public void addPlayerCardTop() {
//		screenCard = new JLabel(new ImageIcon("werewolfcard.jpg"));
//		topPanel.add(screenCard);
//	}
	
	public void flip(int player) {
		flip = !flip;
		
		switch(player) {
			case 1:
				if (flip) {
					player1.setIcon(new ImageIcon("Villager-Werewolves.jpg"));
				} else {
					player1.setIcon(new ImageIcon("werewolfcard.jpg"));
				}
				break;
			case 2:
				if (flip) {
					player2.setIcon(new ImageIcon("Villager-Werewolves.jpg"));
				} else {
					player2.setIcon(new ImageIcon("werewolfcard.jpg"));
				}
				break;
			case 3:
				if (flip) {
					player3.setIcon(new ImageIcon("Villager-Werewolves.jpg"));
				} else {
					player3.setIcon(new ImageIcon("werewolfcard.jpg"));
				}
				break;
			case 4:
				if (flip) {
					player4.setIcon(new ImageIcon("Villager-Werewolves.jpg"));
				} else {
					player4.setIcon(new ImageIcon("werewolfcard.jpg"));
				}
				break;
			case 5:
				if (flip) {
					player5.setIcon(new ImageIcon("Villager-Werewolves.jpg"));
				} else {
					player5.setIcon(new ImageIcon("werewolfcard.jpg"));
				}
				break;
		}

	}
	public void addPlayerCardBottom(int numPlayers) {
		if (numPlayers >= 4) {
			player4 = new JButton();
			player4.setIcon(new ImageIcon("werewolfcard.jpg"));
			player4.addActionListener(new playerAL());
			bottomPanel.add(player4);
		} 
		if (numPlayers == 5) {
			player5 = new JButton();
			player5.setIcon(new ImageIcon("werewolfcard.jpg"));
			player5.addActionListener(new playerAL());
			bottomPanel.add(player5);
		}
	
	}
	
	public void revealRoles() {
		rc = new RoleCountdown();
		add(rc, BorderLayout.EAST);
//		while(rc.running = true) {
		if(rc.seconds == 9000) {
			flip(rc.turn);
		} else if(rc.seconds == 2000) {
			flip(rc.turn);
		}
		System.out.println(rc.seconds);
//		}
	}
	
	public class RoleCountdown extends JPanel {
		public long seconds = 10000;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm:ss");
		JLabel clock = new JLabel(sdf.format(new Date(seconds)));
		Timer rcountdown;
		int turn = 1;
		int players = 2;
		String sound = "player1.wav";
		InputStream in;
		AudioStream audioStream;
//		Board b = new Board();
		
		public boolean reveal = false;
		public boolean running = true;
		
		
		public RoleCountdown() {
			setLayout(new BorderLayout());
			
			
			add(clock, BorderLayout.CENTER);
			
			rcountdown = new Timer(1000, new RoleTimer());
			rcountdown.start();
			
			setVisible(true);
		}
		
		
		public void play(String fileName) {
			try {
				in = new FileInputStream(fileName);
				audioStream = new AudioStream(in);
				AudioPlayer.player.start(audioStream);
			} catch (FileNotFoundException k) {
			    k.printStackTrace(); 
			} catch (IOException k) {
				k.printStackTrace(); 
			}
		}
		
		public class RoleTimer implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				clock.setText(sdf.format(new Date(seconds)));
				seconds -= 1000;
				
				
				if(seconds == 9000) {
					play(sound);
//					b.flip(turn);
					flip(turn);
				} else if(seconds == 2000) {
					flip(turn);
					sound = "close.wav";
					play(sound);
				}
				
				if (seconds >= 1000) {
					clock.setText(sdf.format(new Date(seconds)));
				} else {
					clock.setText(sdf.format(new Date(seconds)));
					rcountdown.stop();
					
					seconds = 10000;
					if(turn <= players) {
						rcountdown.restart();
						switch(turn) {
							case 1:
								sound = "player2.wav";
								break;
							case 2:
								sound = "player3.wav";
								break;
							case 3:
								sound = "player4.wav";
								break;
							case 4:
								sound = "player5.wav";
								break;
						}
						
						turn++;
					} else {
						running = false;
					}
					
				}
			}
		}

	}
	
	

}