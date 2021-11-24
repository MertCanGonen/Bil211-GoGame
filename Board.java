//Mert Can Gönen
//181101039
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Board extends JFrame implements MouseListener,MouseMotionListener,ActionListener{    //pop-up 
	
	String [][] locations = new String [10][10]; //tahta uzerindeki taslarin kaldirma islemi icin kullaniliyor.
	
	JPanel board ;
	JPanel pas ;
	
	ArrayList <String> siyah_loc = new ArrayList<>(); //x,y seklinde koordinatlari tutuyor.
	ArrayList <String> beyaz_loc = new ArrayList<>();
	
	boolean check_bos=true;
	boolean check_sarili=true;
	
	boolean check_x=false; //tiklanan yerin tahtanin icerisinde olup olmadigini kontrol eder. Ancak tahtanin disina da tiklandiginda tahta sinirina en yakin yere tasi koyuyor.
	boolean check_y=false;
	
	int loc_x = 0;  //noktalarin array deki yeri
	int loc_y = 0;
	
	static int x=0; //tikladigimiz noktalarin yerleri
	static int y=0;
	
	static int hamleSayisi=1; //tek ise user1 , cift ise user2
	int pasSayisi=0;
	
	String tas = "X";  //kullaniciya gore degisiyor
	
	int user1=0;  //tas sayilari
	int user2=0;
	
	String kazanan="";
	
	public Board()  { 
		
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				locations[i][j]="-";
			}
		}
		
		if (hamleSayisi == 1) { //degisecek siyah/beyaz olarak.ilk basta siyah cunku onlar basliyor
			setTitle("Siyah");
		}
	    setSize(1200, 1100);
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    
	    board = new JPanel ();
	    pas = new JPanel();
	    
	    JButton pasB = new JButton("PAS");
	    
	    pas.setPreferredSize(new Dimension(100,100));
	    board.setPreferredSize(new Dimension(900,900));
	    
	    pas.add(pasB); 
	    add(pas,BorderLayout.EAST);
	    add(board,BorderLayout.CENTER); 
	    
	    pasB.addActionListener(this);
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    setVisible(true);
	    
	}
	
	private class OurWindowListener implements WindowListener{
		public void windowActivated(WindowEvent e) {	}
		public void windowClosed(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			new ConfirmWindow();
		}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
		
	}
	public class ConfirmWindow extends JFrame implements ActionListener{
		public ConfirmWindow() {
			setTitle("Cikis");
			setSize(300,200);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setLayout(new GridLayout(2,1));
			JLabel cikis = new JLabel("Cikis yapmak istediginize emin misiniz?");
			JButton evet = new JButton("Evet");
			JButton hayir = new JButton("Hayir");
			JPanel tuslar = new JPanel (new FlowLayout());
			tuslar.add(evet);
			tuslar.add(hayir);
			add(cikis);
			add(tuslar);
			evet.addActionListener(this);
			hayir.addActionListener(this);
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Evet")) {
				System.exit(0);
			}else if (command.contentEquals("Hayir")) {
				dispose();
			}
		}
	}
	
	public class Kazanan extends JFrame{
		public Kazanan() {
			setTitle("Sonuc");
			setSize(500,300);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			for (int i=0;i<10;i++) {
				for (int j=0;j<10;j++) {
					if (locations[i][j].equals("X")) {
						user1++;
					}else if (locations[i][j].equals("O")) {
						user2++;
					}
				}
			}
			
			if (user1<user2) {
				kazanan="                                      KAZANAN     =      BEYAZ\n"
						+"									   Siyah:"+user1+"\n"
								+ "							   Beyaz:"+user2;
						;
			}else if (user1>user2) {
				kazanan="                                      KAZANAN     =      SIYAH\n"
						+"									   Siyah:"+user1+"\n"
								+ "							   Beyaz:"+user2;
						;
			}else if (user1==user2) {
				kazanan="                                      KAZANAN     =      BERABERE\n"
						+"									   Siyah:"+user1+"\n"
								+ "							   Beyaz:"+user2;
						;
			}
			JLabel sonuc = new JLabel (kazanan);
			add(sonuc,BorderLayout.CENTER);
			addWindowListener(new OurWindowListener());
			setVisible(true);
		}
	}
	
	public void paint (Graphics g) {
		
		if (pasSayisi!=2) {
			g.setColor(Color.orange);
			g.fillRect(0, 0, 1100, 1100);
			
			g.setColor(Color.black);
			g.drawLine(100, 100, 1000, 100);
			g.drawLine(100, 1000, 1000, 1000);
			g.drawLine(100, 100, 100, 1000);
			g.drawLine(1000, 100, 1000, 1000);//cevre
			
			g.drawLine(100, 200, 1000, 200);  //yatay
			g.drawLine(100, 300, 1000, 300);
			g.drawLine(100, 400, 1000, 400);
			g.drawLine(100, 500, 1000, 500);
			g.drawLine(100, 600, 1000, 600);
			g.drawLine(100, 700, 1000, 700);
			g.drawLine(100, 800, 1000, 800);
			g.drawLine(100, 900, 1000, 900);
			
			g.drawLine(200, 100, 200, 1000);  //dikey
			g.drawLine(300, 100, 300, 1000);
			g.drawLine(400, 100, 400, 1000);
			g.drawLine(500, 100, 500, 1000);
			g.drawLine(600, 100, 600, 1000);
			g.drawLine(700, 100, 700, 1000);
			g.drawLine(800, 100, 800, 1000);
			g.drawLine(900, 100, 900, 1000);
			
			int index=0;
			int x_boya=0;
			int y_boya=0;
				
			if (check_x && check_y) {
				for (int i=0;i<siyah_loc.size();i++) {
					index = siyah_loc.get(i).indexOf(",");
					x_boya = Integer.valueOf(siyah_loc.get(i).substring(0, index));
					y_boya = Integer.valueOf(siyah_loc.get(i).substring(index+1));
					g.setColor(Color.black);
					g.fillOval(x_boya, y_boya, 60, 60);
				}
				for (int i=0;i<beyaz_loc.size();i++) {
					index = beyaz_loc.get(i).indexOf(",");
					x_boya = Integer.valueOf(beyaz_loc.get(i).substring(0, index));
					y_boya = Integer.valueOf(beyaz_loc.get(i).substring(index+1));
					g.setColor(Color.white);
					g.fillOval(x_boya, y_boya, 60, 60);
				}
				
				int sayac=0;
				for (int i=0;i<10;i++) {
					for (int j=0;j<10;j++) {
						if (!(locations[i][j].equals("-"))) {
							sayac++;
						}
					}
				}
				if (sayac == 100) {
					new Kazanan();
				}else {
					sayac=0;
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) { 
		if (pasSayisi==2) {
			new Kazanan();
		}else {
			pasSayisi=0;
		}
		repaint();
		x = e.getX();
		y = e.getY();
		if (x<=1000 && x>=100) {  //en yakin x koordinati (Eger iki noktanin tam ortasina tiklanirsa soldaki yere koyar. O sekilde tasarladim.)
			check_x=true;
			if (x==100 || x==150) {
				x=100-30;
				loc_y=0;
			}else if (x==200 || x==250) {
				x=200-30;
				loc_y=1;
			}else if (x==300 || x==350) {
				x=300-30;
				loc_y=2;
			}else if (x==400 || x==450) {
				x=400-30;
				loc_y=3;
			}else if (x==500 || x==550) {
				x=500-30;
				loc_y=4;
			}else if (x==600 || x==650) {
				x=600-30;
				loc_y=5;
			}else if (x==700 || x==750) {
				x=700-30;
				loc_y=6;
			}else if (x==800 || x==850) {
				x=800-30;
				loc_y=7;
			}else if (x==900 || x==950) {
				x=900-30;
				loc_y=8;
			}else if (x==1000) {
				x=1000-30;
				loc_y=9;
			}
			if (x<200 && x>100 && x-100<200-x) {
				x=100-30;
				loc_y=0;
			}else if (x<200 && x>100 && x-100>200-x) {
				x=200-30;
				loc_y=1;
			}else if (x<300 && x>200 && x-200<300-x) {
				x=200-30;
				loc_y=1;
			}else if (x<300 && x>200 && x-200>300-x) {
				x=300-30;
				loc_y=2;
			}else if (x<400 && x>300 && x-300<400-x) {
				x=300-30;
				loc_y=2;
			}else if (x<400 && x>300 && x-300>400-x) {
				x=400-30;
				loc_y=3;
			}else if (x<500 && x>400 && x-400<500-x) {
				x=400-30;
				loc_y=3;
			}else if (x<500 && x>400 && x-400>500-x) {
				x=500-30;
				loc_y=4;
			}else if (x<600 && x>500 && x-500<600-x) {
				x=500-30;
				loc_y=4;
			}else if (x<600 && x>500 && x-500>600-x) {
				x=600-30;
				loc_y=5;
			}else if (x<700 && x>600 && x-600<700-x) {
				x=600-30;
				loc_y=5;
			}else if (x<700 && x>600 && x-600>700-x) {
				x=700-30;
				loc_y=6;
			}else if (x<800 && x>700 && x-700<800-x) {
				x=700-30;
				loc_y=6;
			}else if (x<800 && x>700 && x-700>800-x) {
				x=800-30;
				loc_y=7;
			}else if (x<900 && x>800 && x-800<900-x) {
				x=800-30;
				loc_y=7;
			}else if (x<900 && x>800 && x-800>900-x) {
				x=900-30;
				loc_y=8;
			}else if (x<1000 && x>900 && x-900<1000-x) {
				x=900-30;
				loc_y=8;
			}else if (x<1000 && x>900 && x-900>1000-x) {
				x=1000-30;
				loc_y=9;
			}
		}
		if (y<=1000 && y>=100) {  //en yakin y koordinati (Eger iki noktanin tam ortasina tiklanirsa ustteki yere koyar. O sekilde tasarladim.)
			check_y=true;
			if (y==100 || y==150) {
				y=100-30;
				loc_x=0;
			}else if (y==200 || y==250) {
				y=200-30;
				loc_x=1;
			}else if (y==300 || y==350) {
				y=300-30;
				loc_x=2;
			}else if (y==400 || y==450) {
				y=400-30;
				loc_x=3;
			}else if (y==500 || y==550) {
				y=500-30;
				loc_x=4;
			}else if (y==600 || y==650) {
				y=600-30;
				loc_x=5;
			}else if (y==700 || y==750) {
				y=700-30;
				loc_x=6;
			}else if (y==800 || y==850) {
				y=800-30;
				loc_x=7;
			}else if (y==900 || y==950) {
				y=900-30;
				loc_x=8;
			}else if (y==1000) {
				y=1000-30;
				loc_x=9;
			}
			if (y<200 && y>100 && y-100<200-y) {
				y=100-30;
				loc_x=0;
			}else if (y<200 && y>100 && y-100>200-y) {
				y=200-30;
				loc_x=1;
			}else if (y<300 && y>200 && y-200<300-y) {
				y=200-30;
				loc_x=1;
			}else if (y<300 && y>200 && y-200>300-y) {
				y=300-30;
				loc_x=2;
			}else if (y<400 && y>300 && y-300<400-y) {
				y=300-30;
				loc_x=2;
			}else if (y<400 && y>300 && y-300>400-y) {
				y=400-30;
				loc_x=3;
			}else if (y<500 && y>400 && y-400<500-y) {
				y=400-30;
				loc_x=3;
			}else if (y<500 && y>400 && y-400>500-y) {
				y=500-30;
				loc_x=4;
			}else if (y<600 && y>500 && y-500<600-y) {
				y=500-30;
				loc_x=4;
			}else if (y<600 && y>500 && y-500>600-y) {
				y=600-30;
				loc_x=5;
			}else if (y<700 && y>600 && y-600<700-y) {
				y=600-30;
				loc_x=5;
			}else if (y<700 && y>600 && y-600>700-y) {
				y=700-30;
				loc_x=6;
			}else if (y<800 && y>700 && y-700<800-y) {
				y=700-30;
				loc_x=6;
			}else if (y<800 && y>700 && y-700>800-y) {
				y=800-30;
				loc_x=7;
			}else if (y<900 && y>800 && y-800<900-y) {
				y=800-30;
				loc_x=7;
			}else if (y<900 && y>800 && y-800>900-y) {
				y=900-30;
				loc_x=8;
			}else if (y<1000 && y>900 && y-900<1000-y) {
				y=900-30;
				loc_x=8;
			}else if (y<1000 && y>900 && y-900>1000-y) {
				y=1000-30;
				loc_x=9;
			}
		}
		if (x<100) {  //alt alta 4 tane if-else if statement lari tahtanin disina tiklandiginda taslari sinir cizgisine koymamizi sagliyor.
			x=100-30;
			loc_y=0;
		}else if (x>1000) {
			x=1000-30;
			loc_y=9;
		}
		if (y<100) {
			y=100-30;
			loc_x=0;
		}else if (y>1000) {
			y=1000-30;
			loc_x=9;
		}

		if (locations[loc_x][loc_y].equals("-")) {  //bos olup olmadigini kontrol ediyor.
			check_bos=true;
		}else {
			check_bos=false;
		}
		
		if (hamleSayisi % 2 == 1) {
			tas = "X";
		}else {
			tas = "O";
		}
		
		if (check_x && check_y && check_bos ) {
			if (hamleSayisi % 2 == 1) {
				if (locations[loc_x][loc_y].equals("-") && !(beyaz_loc.contains(""+x+","+y))) {	 //arraydeki koordinatlara gore bossa ve de beyazin koordinatlarinda yoksa siyahi ekler.
					siyah_loc.add(""+x+","+y);
					locations[loc_x][loc_y] = tas;  
					hamleSayisi++;
				}
			}else {
				if (locations[loc_x][loc_y].equals("-") && !(siyah_loc.contains(""+x+","+y)))  {
					beyaz_loc.add(""+x+","+y); 
					locations[loc_x][loc_y] = tas;  
					hamleSayisi++;
				}
			}
		}

			int tmp_sol=0;
			int tmp_sag=0;
			int tmp_alt=0;
			int tmp_ust=0;
			int ask1=0;
			int usk1=0;
			int sagk=0;
			int solk=0;
			for (int i=0;i<10;i++) {   //tum tahtayi gezerek etrafi sarili alanlara bakiyor.
				for (int j=0;j<10;j++) {
						if (j==0) {
						if (i==0) { //sol en ustteki tasin etrafina bakiyor.etrafi cevrili ise rakip tasi oraya koyuyor.(alti ve sagi)
								ask1=i+1;
								sagk=j+1;
								if (!(locations[ask1][j].equals("-")) && !(locations[i][sagk].equals("-"))) { //komsulari bos degil ise
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[ask1][j].equals("O") && locations[i][sagk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										beyaz_loc.add(""+70+","+70);	
										siyah_loc.remove(""+70+","+70);
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[ask1][j].equals("X") && locations[i][sagk].equals("X")) {
										locations[i][j]="X";
										siyah_loc.add(""+70+","+70);
										beyaz_loc.remove(""+70+","+70);
									}
								}
								ask1=0;
								sagk=0;
							}else if (i==9) { //sol en alttaki tasin etrafina bakiyor (ustu ve sagi)
								usk1=i-1;
								sagk=j+1;
								if (!(locations[usk1][j].equals("-")) && !(locations[i][sagk].equals("-"))) {  //komsulari bos degil ise
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[usk1][j].equals("O") && locations[i][sagk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										beyaz_loc.add(""+70+","+970); 
										siyah_loc.remove(""+70+","+970); 
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[usk1][j].equals("X") && locations[i][sagk].equals("X")) {
										locations[i][j]="X";
										siyah_loc.add(""+70+","+970);
										beyaz_loc.remove(""+70+","+970);
									}
								}
								usk1=0;
								sagk=0;
							}else if (i!=0 && i!=9){ //sol tarafa dayali ama kosede olmayan taslarin etrafina bakiyor. (alti, ustu ve sagi)
								usk1=i-1;
								ask1=i+1;
								sagk=j+1;
								if (!(locations[usk1][j].equals("-")) && !(locations[i][sagk].equals("-")) && !(locations[ask1][j].equals("-"))) {
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[usk1][j].equals("O") && locations[ask1][j].equals("O") && locations[i][sagk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										tmp_sol = (i*100) + 70;
										beyaz_loc.add(""+70+","+tmp_sol); 
										siyah_loc.remove(""+70+","+tmp_sol);
										tmp_sol=0;
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[usk1][j].equals("X") && locations[ask1][j].equals("X") && locations[i][sagk].equals("X")) {
										locations[i][j]="X";
										tmp_sol = (i*100) + 70;
										siyah_loc.add(""+70+","+tmp_sol);
										beyaz_loc.remove(""+70+","+tmp_sol);
										tmp_sol=0;
									}
								}
								usk1=0;
								ask1=0;
								sagk=0;
							}
						}
						else if (i==0) {  //uste dayali taslarin etrafini inceliyor.
							if (j==9) { //sag en ust kosedeki tasin etrafina bakiyor (alti ve solu)
								ask1=i+1;
								solk=j-1;
								if (!(locations[ask1][j].equals("-")) && !(locations[i][solk].equals("-"))) {
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[ask1][j].equals("O") && locations[i][solk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										beyaz_loc.add(""+970+","+70); 
										siyah_loc.remove(""+970+","+70);
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[ask1][j].equals("X") && locations[i][solk].equals("X")) {
										locations[i][j]="X";
										siyah_loc.add(""+70+","+70);
										beyaz_loc.remove(""+970+","+70);
									}
								}
								ask1=0;
								solk=0;
							}else if (j!=0 && j!=9){ //uste dayali ve kose olmayan kismin etrafini inceliyor.
								ask1=i+1;
								solk=j-1;
								sagk=j+1;
								if (!(locations[ask1][j].equals("-")) && !(locations[i][sagk].equals("-")) && !(locations[i][solk].equals("-"))) {
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[ask1][j].equals("O") && locations[i][solk].equals("O") && locations[i][sagk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										tmp_ust = (j*100) + 70; 
										beyaz_loc.add(""+tmp_ust+","+70);
										siyah_loc.remove(""+tmp_ust+","+70);
										tmp_ust=0;
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[ask1][j].equals("X") && locations[i][solk].equals("X") && locations[i][sagk].equals("X")) {
										locations[i][j]="X";
										tmp_ust = (j*100) +70;
										siyah_loc.add(""+tmp_ust+","+70);
										beyaz_loc.remove(""+tmp_ust+","+70);
										tmp_ust=0;
									}
								}
								ask1=0;
								solk=0;
								sagk=0;
							}
						}
						else if (j==9) {  //saga dayali taslarin etrafini inceliyor.
							if (i==9) {  //sag en alt kosenin etrafini inceliyor (solu ve ustu)
								solk=j-1;
								usk1=i-1;
								if (!(locations[usk1][j].equals("-")) && !(locations[i][solk].equals("-"))) {
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[usk1][j].equals("O") && locations[i][solk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										beyaz_loc.add(""+970+","+970); 
										siyah_loc.remove(""+970+","+970);
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[usk1][j].equals("X") && locations[i][solk].equals("X")) {
										locations[i][j]="X";
										siyah_loc.add(""+970+","+970);
										beyaz_loc.remove(""+970+","+970);
									}
								}
								solk=0;
								usk1=0;
							}else if (i!=0 && i!=9) { //saga dayali ve kose olmayan kismin etrafini inceliyor.
								solk=j-1;
								ask1=i+1;
								usk1=i-1;
								if (!(locations[ask1][j].equals("-")) && !(locations[i][solk].equals("-")) && !(locations[usk1][j].equals("-"))) {
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[ask1][j].equals("O") && locations[usk1][j].equals("O") && locations[i][solk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										tmp_sag = (i*100) + 70;
										System.out.println(tmp_sag);
										beyaz_loc.add(""+970+","+tmp_sag); 
										siyah_loc.remove(""+970+","+tmp_sag);
										tmp_sag=0;
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[ask1][j].equals("X") && locations[usk1][j].equals("X") && locations[i][solk].equals("X")) {
										locations[i][j]="X";
										tmp_sag = (i*100) + 70;
										System.out.println(tmp_sag);
										siyah_loc.add(""+970+","+tmp_sag);
										beyaz_loc.remove(""+970+","+tmp_sag);
										tmp_sag=0;
									}
								}
								solk=0;
								ask1=0;
								usk1=0;
							}
						}else if (i==9) {  //en alt satira dayali taslarin etrafini inceler.
							if (j!=0 && j!=9) {  //en alt satirda ve kosede olmayan kisimlarin etrafini inceler
								solk=j-1;
								usk1=i-1;
								sagk=j+1;
								if (!(locations[usk1][j].equals("-")) && !(locations[i][sagk].equals("-")) && !(locations[i][solk].equals("-"))) {
									if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[usk1][j].equals("O") && locations[i][sagk].equals("O") && locations[i][solk].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
										locations[i][j]="O";
										tmp_alt = (j*100) + 70 ;
										beyaz_loc.add(""+tmp_alt+","+970);
										siyah_loc.remove(""+tmp_alt+","+970);
										tmp_alt=0;
									}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[usk1][j].equals("X") && locations[i][sagk].equals("X") && locations[i][solk].equals("X")) {
										locations[i][j]="X";
										tmp_alt = (j*100) + 70;
										siyah_loc.add(""+tmp_alt+","+970);
										beyaz_loc.remove(""+tmp_alt+","+970);
										tmp_alt=0;
									}
								}
								solk=0;
								usk1=0;
								sagk=0;
							}
						}else if (i!=0 && i!=9 && j!=0 && j!=9) {  //ortadaki kisim
							usk1=i-1;
							ask1=i+1;
							solk=j-1;
							sagk=j+1;
							if (!(locations[usk1][j].equals("-")) && !(locations[i][sagk].equals("-")) && !(locations[i][solk].equals("-")) && !(locations[ask1][j].equals("-"))) {
								if ((locations[i][j].equals("X") || locations[i][j].equals("-")) && locations[usk1][j].equals("O") && locations[i][sagk].equals("O") && locations[i][solk].equals("O") && locations[ask1][j].equals("O")) {  //ve komsularý kendinden farklý ise rakip tasi koyar
									locations[i][j]="O";
									tmp_sol = (i*100) + 70;
									tmp_alt = (j*100) + 70;
									beyaz_loc.add(""+tmp_alt+","+tmp_sol);
									siyah_loc.remove(""+tmp_alt+","+tmp_sol);
									tmp_sol=0;
									tmp_alt=0;
								}else if ((locations[i][j].equals("O") || locations[i][j].equals("-")) && locations[usk1][j].equals("X") && locations[i][sagk].equals("X") && locations[i][solk].equals("X") && locations[ask1][j].equals("X")) {
									locations[i][j]="X";
									tmp_sol = (i*100) + 70;
									tmp_alt = (j*100) + 70;
									siyah_loc.add(""+tmp_alt+","+tmp_sol);
									beyaz_loc.remove(""+tmp_alt+","+tmp_sol);
									tmp_sol=0;
									tmp_alt=0;
								}
							}
							usk1=0;
							ask1=0;
							solk=0;
							sagk=0;
						}	
				}
			}
			if (hamleSayisi % 2 == 1) {
				setTitle("Siyah");
			}else {
				setTitle("Beyaz");
			}
	}
	public void actionPerformed(ActionEvent arg0) {
		pasSayisi++;
		if (pasSayisi==2){
			new Kazanan();
		}
		hamleSayisi++;
		check_bos=true;
		check_sarili=true;
		if (hamleSayisi % 2 == 1) {
			setTitle("Siyah");
		}else {
			setTitle("Beyaz");
		}
		
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {} 
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}

	public static void main(String[] args) {
		new Board();
	}
}
