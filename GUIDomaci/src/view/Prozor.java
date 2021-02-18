package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Prozor extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panSadrzaj, panTulbarovi;
	private PanelCrtanje panCtranje;
	private JToolBar tbBojaDebljina, tbOblikIspuna;
	private JLabel lblBoja, lblDebljina;
	private JComboBox<String> spisakBoja;
	private JSpinner spinerDebljine;
	private JButton btnBoje;
	private JRadioButton rdElipsa, rdPravougaonik, rdLinija, rdSlobodnoCrtanje;
	private JCheckBox cbIspuna;
	private JMenuBar trakaSaMenijima;
	
	// Konstruktor.
	public Prozor() {
		setTitle("GUI domaci");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()*0.7), (int)(d.getHeight()*0.7));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Traka sa menijima.
		trakaSaMenijima = new JMenuBar();
		setJMenuBar(trakaSaMenijima);
		
		// Meni - Fajl.
		JMenu meniFajl = new JMenu("Fajl");
		JMenuItem stavkaOtvori = new JMenuItem("Otvori...");
		stavkaOtvori.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filterFajlova = 
						new FileNameExtensionFilter("Samo .llg fajlovi", "llg");
						jfc.setFileFilter(filterFajlova);
				if (jfc.showOpenDialog(Prozor.this) == JFileChooser.APPROVE_OPTION) {
					File f = jfc.getSelectedFile();
					FileInputStream fis = null;
					ObjectInputStream ois = null;
					try {
						fis = new FileInputStream(f);
						ois = new ObjectInputStream(fis);
						Slika slika = (Slika)ois.readObject();
						System.out.println("Objekat je ucitan iz fajla.");
						ois.close();
						panCtranje.setListaFigura(slika.getListaFigura());
						panCtranje.setListaBojaFigura(slika.getListaBojaFigura());
						panCtranje.setListaDebljinaFigura(slika.getListaDebljinaFigura());
						panCtranje.setListaPraznePune(slika.getListaPraznePune());
						
						panCtranje.repaint();
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		meniFajl.add(stavkaOtvori);
		
		JMenuItem stavkaSacuvaj = new JMenuItem("Sacuvaj...");
		stavkaSacuvaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setSelectedFile(new File("*.llg"));
				FileNameExtensionFilter filterFajlova = 
							new FileNameExtensionFilter("Samo .llg fajlovi", "llg");
				jfc.setFileFilter(filterFajlova);
				if (jfc.showSaveDialog(Prozor.this) == JFileChooser.APPROVE_OPTION) {
					Slika slika = new Slika(panCtranje.getListaFigura(),
											panCtranje.getListaBojaFigura(),
											panCtranje.getListaDebljinaFigura(),
											panCtranje.getListaPraznePune());
					File f = jfc.getSelectedFile();
					FileOutputStream fos = null;
					ObjectOutputStream oos = null;
					try {
						fos = new FileOutputStream(f);
						oos = new ObjectOutputStream(fos);
						oos.writeObject(slika);
						oos.close();
						System.out.println("Objekat je upisan u fajl.");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}
				} // Kraj if-a.
			}
		});
		meniFajl.add(stavkaSacuvaj);
		trakaSaMenijima.add(meniFajl);
		
		// Meni - Boja.
		JMenu meniBoja = new JMenu("Boja");
		JMenuItem stavkaCrvena = new JMenuItem("Cvena");
		stavkaCrvena.addActionListener(new PromijeniBoju(Color.RED));
		meniBoja.add(stavkaCrvena);
		JMenuItem stavkaPlava = new JMenuItem("Plava");
		stavkaPlava.addActionListener(new PromijeniBoju(Color.BLUE));
		meniBoja.add(stavkaPlava);
		JMenuItem stavkaBijela = new JMenuItem("Bijela");
		stavkaBijela.addActionListener(new PromijeniBoju(Color.WHITE));
		meniBoja.add(stavkaBijela);
		JMenuItem stavkaCrna = new JMenuItem("Crna");
		stavkaCrna.addActionListener(new PromijeniBoju(Color.BLACK));
		meniBoja.add(stavkaCrna);
		JMenuItem stavkaSiva = new JMenuItem("Siva");
		stavkaSiva.addActionListener(new PromijeniBoju(Color.GRAY));
		meniBoja.add(stavkaSiva);
		JMenuItem stavkaSivaT = new JMenuItem("Siva (tamna)");
		stavkaSivaT.addActionListener(new PromijeniBoju(Color.DARK_GRAY));
		meniBoja.add(stavkaSivaT);
		JMenuItem stavkaSivaS = new JMenuItem("Siva (svijetla)");
		stavkaSivaS.addActionListener(new PromijeniBoju(Color.LIGHT_GRAY));
		meniBoja.add(stavkaSivaS);
		JMenuItem stavkaZelena = new JMenuItem("Zelena");
		stavkaZelena.addActionListener(new PromijeniBoju(Color.GREEN));
		meniBoja.add(stavkaZelena);
		JMenuItem stavkaZuta = new JMenuItem("Zuta");
		stavkaZuta.addActionListener(new PromijeniBoju(Color.YELLOW));
		meniBoja.add(stavkaZuta);
		JMenuItem stavkaNarandzasta = new JMenuItem("Narandzasta");
		stavkaNarandzasta.addActionListener(new PromijeniBoju(Color.ORANGE));
		meniBoja.add(stavkaNarandzasta);
		trakaSaMenijima.add(meniBoja);
		
		// Meni - Debljina.
		JMenu meniDebljina = new JMenu("Debljina");
		JMenuItem stavkaJedan = new JMenuItem("1 px");
		stavkaJedan.addActionListener(new PromijeniDebljinu(1));
		meniDebljina.add(stavkaJedan);
		JMenuItem stavkaDva = new JMenuItem("2 px");
		stavkaDva.addActionListener(new PromijeniDebljinu(2));
		meniDebljina.add(stavkaDva);
		JMenuItem stavkaTri = new JMenuItem("3 px");
		stavkaTri.addActionListener(new PromijeniDebljinu(3));
		meniDebljina.add(stavkaTri);
		JMenuItem stavkaCetiri = new JMenuItem("4 px");
		stavkaCetiri.addActionListener(new PromijeniDebljinu(4));
		meniDebljina.add(stavkaCetiri);
		JMenuItem stavkaPet = new JMenuItem("5 px");
		stavkaPet.addActionListener(new PromijeniDebljinu(5));
		meniDebljina.add(stavkaPet);
		trakaSaMenijima.add(meniDebljina);
		
		// Meni - oblik.
		JMenu meniOblik = new JMenu("Oblik");
		JMenuItem stavkaElipsa = new JMenuItem("Elipsa");
		stavkaElipsa.addActionListener(new IzborOblika("el"));
		meniOblik.add(stavkaElipsa);
		JMenuItem stavkaPravougaonik = new JMenuItem("Pravougaonik");
		stavkaPravougaonik.addActionListener(new IzborOblika("pr"));
		meniOblik.add(stavkaPravougaonik);
		JMenuItem stavkaLinija = new JMenuItem("Linija");
		stavkaLinija.addActionListener(new IzborOblika("li"));
		meniOblik.add(stavkaLinija);
		JMenuItem stavkaSlobodnoCrtanje = new JMenuItem("Slobodno crtanje");
		stavkaSlobodnoCrtanje.addActionListener(new IzborOblika("sc"));
		meniOblik.add(stavkaSlobodnoCrtanje);
		trakaSaMenijima.add(meniOblik);
		
		// Meni - Ispuna.
		JMenu meniIspuna = new JMenu("Ispuna");
		JMenuItem stavkaPopunjeno = new JMenuItem("Popunjeno");
		stavkaPopunjeno.addActionListener(new IzborOblika("it"));
		meniIspuna.add(stavkaPopunjeno);
		JMenuItem stavkaPrazno = new JMenuItem("Prazno");
		stavkaPrazno.addActionListener(new IzborOblika("if"));
		meniIspuna.add(stavkaPrazno);
		trakaSaMenijima.add(meniIspuna);
		
		// Tulbar za odabir boje i debljine
		tbBojaDebljina = new JToolBar();
		
		lblBoja = new JLabel("Boje: ");
		lblDebljina = new JLabel("Debljina: ");
		spisakBoja = new JComboBox<String>();
		spisakBoja.addItem("Odaberite boju.");
		spisakBoja.addItem("Crvena");
		spisakBoja.addItem("Plava");
		spisakBoja.addItem("Bijela");
		spisakBoja.addItem("Crna");
		spisakBoja.addItem("Siva");
		spisakBoja.addItem("Siva (tamna)");
		spisakBoja.addItem("Siva (svijetla)");
		spisakBoja.addItem("Zelena");
		spisakBoja.addItem("Zuta");
		spisakBoja.addItem("Narandzasta");
		spisakBoja.setSelectedIndex(0);
		spisakBoja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int indeks = spisakBoja.getSelectedIndex();
				if (indeks == 1)
					panCtranje.setBoja(Color.RED);
				else if (indeks == 2)
					panCtranje.setBoja(Color.BLUE);
				else if (indeks == 3)
					panCtranje.setBoja(Color.WHITE);
				else if (indeks == 4)
					panCtranje.setBoja(Color.BLACK);
				else if (indeks == 5)
					panCtranje.setBoja(Color.GRAY);
				else if (indeks == 6)
					panCtranje.setBoja(Color.DARK_GRAY);
				else if (indeks == 7)
					panCtranje.setBoja(Color.LIGHT_GRAY);
				else if (indeks == 8)
					panCtranje.setBoja(Color.GREEN);
				else if (indeks == 9)
					panCtranje.setBoja(Color.YELLOW);
				else if (indeks == 10)
					panCtranje.setBoja(Color.ORANGE);
				spisakBoja.setSelectedIndex(0);
			}
		});
		
		SpinnerModel slm1 = new SpinnerNumberModel(1, 1, 5, 1); // (poc. vrij., min vrij., max vrij., korak)
		spinerDebljine = new JSpinner(slm1);
		spinerDebljine.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				panCtranje.setDebljinaLinije((int)spinerDebljine.getValue());
			}
		});
		
		btnBoje = new JButton("Boje");
		btnBoje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JColorChooser jcc = new JColorChooser();
				//Color b = jcc.showDialog(new JPanel(), "Boje", Color.GREEN);
				Color b = JColorChooser.showDialog(new JPanel(), "Boje", Color.GREEN);
				panCtranje.setBoja(b);
			}
		});
		
		//tbBojaDebljina.add(lblBoja);
		tbBojaDebljina.add(spisakBoja);
		tbBojaDebljina.add(new JLabel("  "));
		tbBojaDebljina.add(lblDebljina);
		tbBojaDebljina.add(spinerDebljine);
		tbBojaDebljina.add(new JLabel("   "));
		tbBojaDebljina.add(btnBoje);
		tbBojaDebljina.add(new JLabel("  "));
		
		// Tulbar za odabir oblika i ispune.
		tbOblikIspuna = new JToolBar();
		
		rdElipsa = new JRadioButton("Elipsa");
		rdElipsa.setSelected(true);
		rdPravougaonik = new JRadioButton("Pravougaonik");
		rdLinija = new JRadioButton("Linija");
		rdSlobodnoCrtanje = new JRadioButton("Sl. crtanje");
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdElipsa);
		bg.add(rdPravougaonik);
		bg.add(rdLinija);
		bg.add(rdSlobodnoCrtanje);
		
		cbIspuna = new JCheckBox("Fill");
		
		tbOblikIspuna.add(rdElipsa);
		tbOblikIspuna.add(rdPravougaonik);
		tbOblikIspuna.add(rdLinija);
		tbOblikIspuna.add(rdSlobodnoCrtanje);
		tbOblikIspuna.add(cbIspuna);
		
		// Paneli.
		panSadrzaj = new JPanel();
		panSadrzaj.setLayout(new BorderLayout());
		
		panTulbarovi = new JPanel();
		panTulbarovi.setLayout(new BorderLayout());
		panTulbarovi.add(tbBojaDebljina, BorderLayout.WEST);
		panTulbarovi.add(tbOblikIspuna, BorderLayout.CENTER);
		panSadrzaj.add(panTulbarovi, BorderLayout.NORTH);
		
		panCtranje = new PanelCrtanje();
		panCtranje.setFocusable(true);
		//panCtranje.requestDefaultFocus(true);
		panCtranje.setGlavniProzor(this);
		panSadrzaj.add(panCtranje, BorderLayout.CENTER);
		
		setContentPane(panSadrzaj);
	} // Kraj konstruktora.
	
	// Geteri (inspektori).
	public boolean isRdElipsa() {
		return rdElipsa.isSelected();
	}
	public boolean isRdPravougaonik() {
		return rdPravougaonik.isSelected();
	}
	public boolean isRdLinija() {
		return rdLinija.isSelected();
	}
	public boolean isRdSlobodnoCrtanje() {
		return rdSlobodnoCrtanje.isSelected();
	}
	public boolean isCbIspuna() {
		return cbIspuna.isSelected();
	}
	// Kraj getera.
		
	// Metoda main.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Prozor p = new Prozor();
				p.setVisible(true);
			}
		});
	} // Kraj metode main.
	
	// Klasa osluskivaca - Podesavanje boje olovke.
	private class PromijeniBoju implements ActionListener {
		private Color novaBoja;
		public PromijeniBoju(Color b) {
			novaBoja = b;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			panCtranje.setBoja(novaBoja);
		}
	} // Kraj klase PromijeniBoju.
	
	// Klasa osluskivaca - Podesavanje debljine olovke.
	private class PromijeniDebljinu implements ActionListener {
		private int novaDebljina;
		public PromijeniDebljinu(int d) {
			novaDebljina = d;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			panCtranje.setDebljinaLinije(novaDebljina);
			spinerDebljine.setValue(novaDebljina);
		}
		
	} // Kraj klase PromijeniDebljinu.
	
	// Klasa osluskivaca - Odabir oblika i tipa ispune.
	private class IzborOblika implements ActionListener {
		String izbor;
		public IzborOblika(String i) {
			izbor = i;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (izbor.equals("el"))
				rdElipsa.setSelected(true);
			else if (izbor.equals("pr"))
				rdPravougaonik.setSelected(true);
			else if (izbor.equals("li"))
				rdLinija.setSelected(true);
			else if (izbor.equals("sc"))
				rdSlobodnoCrtanje.setSelected(true);
			else if (izbor.equals("it"))
				cbIspuna.setSelected(true);
			else if (izbor.equals("if"))
				cbIspuna.setSelected(false);
		}
	} // Kraj klase IzborOblika.
	
} // Kraj glavne klase.
