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
	
	/*
	 * Instance Variables 
	 */
	JPanel topPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JTextArea instruction = new JTextArea(5,12);
//	JLabel screenCard = new JLabel();
//	JLabel screenCard2 = new JLabel();
	RoleCard rolecard = new RoleCard();
	JButton startButton = new JButton();
	JButton player1, player2, player3, player4, player5;
	ArrayList<JButton> playerButtons = new ArrayList<JButton>();
	ArrayList<JButton> centerButtons = new ArrayList<JButton>();
	JButton center1, center2, center3;
	JButton seerChoice1 = new JButton();
	JButton seerChoice2 = new JButton();
	RoleCountdown rc;
	private boolean flip = false;
	private int player, numPlayers;
	
	private ArrayList<Role> roles = new ArrayList<Role>();
	private ArrayList<Role> center = new ArrayList<Role>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private int turnDuringGame = 1;
	private ArrayList<Integer> choice = new ArrayList<Integer>();
	private int c, c2;
	private Role temp, temp2;
	private String name;
	private int switchClicker = 0;
	private boolean status = true;
	Timer robberTimer;
	private boolean gameInProgress = true;
	
	/**
	 * The constructor of this class 
	 */
	public Board () {
		
		//Set layouts of each panel
		topPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new FlowLayout());
		bottomPanel.setLayout(new FlowLayout());
		leftPanel.setLayout(new FlowLayout());
		
		//Add two cards to the top
		player1 = new JButton();
		player1.setIcon(new ImageIcon("werewolfcard.jpg"));
		player1.setText("Player 1");
		player1.addActionListener(new playerAL());
//		player1.setDisabledIcon(new ImageIcon("werewolfcard.jpg"));
		player1.setEnabled(false);
		topPanel.add(player1);
		playerButtons.add(player1);
		
		player2 = new JButton();
		player2.setText("Player 2");
		player2.setIcon(new ImageIcon("werewolfcard.jpg"));
		player2.addActionListener(new playerAL());
		player2.setEnabled(false);
		topPanel.add(player2);
		playerButtons.add(player2);
		
		//Add three cards to the center 
		center1 = new JButton();
		center1.setIcon(new ImageIcon("werewolfcard.jpg"));
		center1.addActionListener(new playerAL());
		center1.setEnabled(false);
		centerPanel.add(center1);
		centerButtons.add(center1);
		center2 = new JButton();
		center2.setIcon(new ImageIcon("werewolfcard.jpg"));
		center2.addActionListener(new playerAL());
		center2.setEnabled(false);
		centerPanel.add(center2);
		centerButtons.add(center2);
		center3 = new JButton();
		center3.setIcon(new ImageIcon("werewolfcard.jpg"));
		center3.addActionListener(new playerAL());
		center3.setEnabled(false);
		centerPanel.add(center3);
		centerButtons.add(center3);
		
		//Add card to the bottom
		player3 = new JButton();
		player3.setText("Player 3");
		player3.setIcon(new ImageIcon("werewolfcard.jpg"));
		player3.addActionListener(new playerAL());
		player3.setEnabled(false);
		bottomPanel.add(player3);
		playerButtons.add(player3);

		//Set layout of and add components to main content panel
		setLayout(new BorderLayout());

		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		//Add start button 
		startButton.setText("Start Game");
		startButton.addActionListener(new startButtonAL());
		add(startButton, BorderLayout.EAST);
		
		//Add Seer's choice buttons
		leftPanel.setPreferredSize(new Dimension(150, 480));
		instruction.setText("Would you like to look at another player's card, or two cards in the center?");
		instruction.setLineWrap(true);
		leftPanel.add(instruction);
		seerChoice1.setText("A Player's card");
		seerChoice1.addActionListener(new seerAL());
		leftPanel.add(seerChoice1);
		seerChoice2.setText("Two in the center");
		seerChoice2.addActionListener(new seerAL());
		leftPanel.add(seerChoice2);
		add(leftPanel, BorderLayout.WEST);
		leftPanel.setVisible(false);
		
		System.out.println(playerButtons.size());
		//Add ActionListeners for Robber and Troublemaker
		for (JButton b : playerButtons) {
			robberSwitch rs = new robberSwitch();
			troublemakerSwitch ts = new troublemakerSwitch();
			if (status == true) {
				b.addActionListener(rs);
				System.out.println(b.getText());
			} else {
				b.removeActionListener(rs);
				b.addActionListener(ts);
			}
		}
		
		if (gameInProgress = false) {
			//WerewolfTimer 
			WerewolfCountdown wc = new WerewolfCountdown();
			//Disable Buttons 
		}
			
	}
	
	public void setRobberButton () {
		for (Player p : players) {
			if (p.getRoleStr().equals("Robber")) {
//				int 
			}
		}
	}

	private class startButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			roles.add(new Werewolf());
			roles.add(new Werewolf());
			roles.add(new Seer());
			roles.add(new Robber());
			roles.add(new Troublemaker());
			roles.add(new Villager());
			if(numPlayers == 4) {
				roles.add(new Villager());
			} else if(numPlayers == 5) {
				roles.add(new Villager());
				roles.add(new Villager());
			}
			
			//generate players & assign cards
			Collections.shuffle(roles);
			for(int i = 0; i < numPlayers; i++) {
				players.add(new Player());
				players.get(i).assignRole(roles.get(i));
			}
//			System.out.println(players.size());
				
			//put 3 remaining cards at center of table
			for(int i = 0; i < 3; i++) {
				center.add(roles.get(numPlayers));
				roles.remove(numPlayers);
			}
			
			startButton.setVisible(false);
			rc = new RoleCountdown();
			add(rc, BorderLayout.EAST);
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
			} else if (f.getSource().equals(center1)) {
				player = 6;
			} else if (f.getSource().equals(center2)) {
				player = 7;
			} else if (f.getSource().equals(center3)) {
				player = 8;
			}
			flip(player);
		}
	}
	

	private class seerAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String type = "";
			int centerCount = 0;
			
			if (e.getSource().equals(seerChoice1)) {
				type = "players";
				enableButtons(type, "Seer");
				instruction.setVisible(false);
			} else if (e.getSource().equals(seerChoice2)) {
				type = "center";
				enableButtons(type, "Seer");
				instruction.setVisible(false);
			} else {
				if(type.equals("players")) {
					for(int i = 0; i < playerButtons.size(); i++) {
						if (e.getSource().equals(playerButtons.get(i))) {
							flip(i + 1);
							enableButtons("disable", "");
							break;
						}
					}
				} else if(type.equals("center")) {
					for(int i = 0; i < centerButtons.size(); i++) {
						if (e.getSource().equals(centerButtons.get(i))) {
							if(centerCount < 2) {
								flip(i + 1);
								centerCount++;
								if(centerCount == 2) enableButtons("disable", "");
							}
							break;
						}
					}
				}
			}
		}
	}
	
	private class troublemakerSwitch implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			int one = 0;
			int two = 0;
			switchClicker++;
			
			for (int i = 0; i < playerButtons.size(); i++ ) {
				if (e.getSource().equals(playerButtons.get(i))) {
					one = i;
				}
			}
			if (switchClicker == 2) {
				for (int i = 0; i < playerButtons.size(); i++ ) {
					if (e.getSource().equals(playerButtons.get(i))) {
						two = i;
					}
				}
				temp = players.get(one).getRole();
				temp2 = players.get(two).getRole();
				players.get(one).assignRole(temp2);
				players.get(two).assignRole(temp);
				switchClicker = 0;
			}
		}
	}
	
	public class robberSwitch implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			int one = 0;
			int two = 0;
			int j;
			for (int i = 0; i < playerButtons.size(); i++) {
				if (e.getSource().equals(playerButtons.get(i))) {
					one = i;
					for (j = 0; j < players.size(); j++) {
						if (players.get(j).getOrigRoleStr() == "Robber") {
							two = j;
						}
					}
					temp = players.get(one).getRole();
					temp2 = players.get(two).getRole();
					players.get(one).assignRole(temp2);
					players.get(two).assignRole(temp);
					flip(j + 1);
					
					int count = 5;
					RobberCountdown r = new RobberCountdown(count, j+1);
					robberTimer = new Timer(1000, r);
					robberTimer.start();
					
					
				}
			}
			//switch roles
			//view new card
		}
	}
	
	public class RobberCountdown implements ActionListener {
		int counter;
		int robberPosition;
			
		public RobberCountdown(int counter, int robberInt) {
			this.counter = counter;
			this.robberPosition = robberInt;
		}
		
		public void actionPerformed (ActionEvent e) {
			counter--;
			if (counter == 0) {
				robberTimer.stop();
				flip(robberPosition);
				status = false;
			}
	}
	}
	
//	public void addPlayerCardTop() {
//		screenCard = new JLabel(new ImageIcon("werewolfcard.jpg"));
//		topPanel.add(screenCard);
//	}
	
	public void setNumPlayers(int n) {
		numPlayers = n;
	}
	
	public void flip(int player) {
		flip = !flip;
		
		switch(player) {
			case 1:
				if (flip) player1.setIcon(new ImageIcon(players.get(0).getImage()));
				else player1.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 2:
				if (flip) player2.setIcon(new ImageIcon(players.get(1).getImage()));
				else player2.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 3:
				if (flip) player3.setIcon(new ImageIcon(players.get(2).getImage()));
				else player3.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 4:
				if (flip) player4.setIcon(new ImageIcon(players.get(3).getImage()));
				else player4.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 5:
				if (flip) player5.setIcon(new ImageIcon(players.get(4).getImage()));
				else player5.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 6:
				if (flip) center1.setIcon(new ImageIcon(center.get(0).imageFile()));
				else center1.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 7:
				if (flip) center2.setIcon(new ImageIcon(center.get(1).imageFile()));
				else center2.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
			case 8:
				if (flip) center3.setIcon(new ImageIcon(center.get(2).imageFile()));
				else center3.setIcon(new ImageIcon("werewolfcard.jpg"));
				break;
		}

	}
	
	
	public void addPlayerCardBottom() {
		if (numPlayers >= 4) {
			player4 = new JButton();
			player4.setText("Player 4");
			player4.setIcon(new ImageIcon("werewolfcard.jpg"));
			player4.addActionListener(new playerAL());
			player4.setEnabled(false);
			bottomPanel.add(player4);
		} 
		if (numPlayers == 5) {
			player5 = new JButton();
			player5.setText("Player 5");
			player5.setIcon(new ImageIcon("werewolfcard.jpg"));
			player5.addActionListener(new playerAL());
			player5.setEnabled(false);
			bottomPanel.add(player5);
		}
	
	}
	
	public void enableButtons(String type, String role) {
		if(type.equals("disable")) {
			for(int j = 0; j < playerButtons.size(); j++) {
				playerButtons.get(j).setEnabled(false);
			}
			for(int j = 0; j < centerButtons.size(); j++) {
				centerButtons.get(j).setEnabled(false);
			}
		} else if (type.equals("center")) {
			for(int j = 0; j < centerButtons.size(); j++) {
				centerButtons.get(j).setEnabled(true);
			}
		} else if (type.equals("players")) {
			int player = 0;
			for(int j = 0; j < players.size(); j++) {
				if(players.get(j).getOrigRoleStr().equals(role)) {
					player = j;
				}
			}
			for(int j = 0; j < playerButtons.size(); j++) {
				if(j != player) playerButtons.get(j).setEnabled(true);
			}
		}
	}
	
	public class RoleCountdown extends JPanel {
		private long begin = 4000;
		private long showCard = 9000;
		private long debate = 2 * 60000;
		private long seconds = begin;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm:ss");
		JLabel clock = new JLabel(sdf.format(new Date(seconds)));
		Timer rcountdown;
		int phase = 1;
		int timerTurn = 1;
		int players = numPlayers;
		String sound = "everyone.wav";
		InputStream in;
		AudioStream audioStream;
		
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
				
				if(phase == 1) {
					if(timerTurn == 1) {
						if(seconds == begin - 1000) play(sound);
					} else {
						if(seconds == showCard - 1000) {
							play(sound);
							flip(timerTurn - 1);
						} else if(seconds == 2000) {
							flip(timerTurn - 1);
							sound = "close.wav";
							play(sound);
						}
					}
				
				} else if(phase == 2) {
					if(seconds == showCard) {
						play(sound);
						if(timerTurn == 3 || timerTurn == 4) {
							player1.setEnabled(true);
						}
					}
				} else if(phase == 3) {
					if(seconds == debate - 1000) {
						sound = "e_wakeup.wav";
						play(sound);
					} else if(seconds == 0) {
						for(int i = 0; i < 8; i++) {
							flip(i);
						}
					}
				}
				
				if (seconds >= 1000) {
					clock.setText(sdf.format(new Date(seconds)));
				} else {
					clock.setText(sdf.format(new Date(seconds)));
					rcountdown.stop();
					
					if(phase < 3) {
						seconds = showCard;
						if(timerTurn <= players ) {
							rcountdown.restart();
							switch(timerTurn) {
								case 1:
									if(phase == 1) sound = "player1.wav";
									else sound = "s_wakeup.wav";
									break;
								case 2:
									if(phase == 1) sound = "player2.wav";
									else sound = "r_wakeup";
									break;
								case 3:
									if(phase == 1) sound = "player3.wav";
									else sound = "t_wakeup.wav";
									break;
								case 4:
									if(phase == 1) sound = "player4.wav";
									break;
								case 5:
									if(phase == 1) sound = "player5.wav";
									break;
							}
						
							timerTurn++;
//							if((phase == 1 && timerTurn == players) || (phase == 2 && timerTurn == 4)) {
//								timerTurn = 1;
//								phase++;
//								if(phase == 2) sound = "w_wakeup.wav";
//							}
						}
					}
					
				}
			}
		}

	}
	
	

}
