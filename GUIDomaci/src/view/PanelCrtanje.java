package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class PanelCrtanje extends JPanel implements MouseListener, MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Prozor glavniProzor;
	private int xPocetno, yPocetno;
	
	private ArrayList<Object> listaFigura = new ArrayList<Object>();
	private ArrayList<Color> listaBojaFigura = new ArrayList<Color>();
	private ArrayList<Integer> listaDebljinaFigura = new ArrayList<Integer>();
	private ArrayList<Boolean> listaPraznePune = new ArrayList<Boolean>();
	
	private Ellipse2D elipsa = new Ellipse2D.Double(0, 0, 0, 0);
	private Rectangle2D pravougaonik = new Rectangle2D.Double(0, 0, 0, 0);
	private Line2D linija = new Line2D.Double(0, 0, 0, 0);
	private Object clanListe;
	private Boolean praznePune;
	
	private int brojPomjeraja = 0;
	private Color boja = Color.BLACK;
	private int debljinaLinije = 1;
	private boolean pritisnutoCtrl = false;
	private int x = 0, y = 0, s = 0, v = 0, sv = 0;
	
	// Konstruktor.
	public PanelCrtanje() {
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				pritisnutoCtrl = e.isControlDown();
				System.out.println(pritisnutoCtrl);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				pritisnutoCtrl = e.isControlDown();
				System.out.println(pritisnutoCtrl);
			}
		});
	}
	
	// Seteri (mutatori).
	public void setListaFigura(ArrayList<Object> listaFigura) {
		this.listaFigura.clear();
		this.listaFigura = listaFigura;
	}
	
	public void setListaBojaFigura(ArrayList<Color> listaBojaFigura) {
		this.listaBojaFigura.clear();
		this.listaBojaFigura = listaBojaFigura;
	}
	
	public void setListaDebljinaFigura(
			ArrayList<Integer> listaDebljinaFigura) {
		this.listaDebljinaFigura.clear();
		this.listaDebljinaFigura = listaDebljinaFigura;
	}
	
	public void setListaPraznePune(ArrayList<Boolean> listaPraznePune) {
		this.listaPraznePune.clear();
		this.listaPraznePune = listaPraznePune;
	}
	
	public void setGlavniProzor(Prozor p) {
		glavniProzor = p;
	}
	
	public void setBoja(Color b) {
		boja = b;
	}
	
	public void setDebljinaLinije(int d) {
		debljinaLinije = d;
	}
	
	// Geteri (inspektori).
	public ArrayList<Object> getListaFigura() {
		return listaFigura;
	}
	
	public ArrayList<Color> getListaBojaFigura() {
		return listaBojaFigura;
	}
	
	public ArrayList<Integer> getListaDebljinaFigura() {
		return listaDebljinaFigura;
	}
	
	public ArrayList<Boolean> getListaPraznePune() {
		return listaPraznePune;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(boja);
		
		// Crta sve figure.
		Iterator<Object> itLF = listaFigura.iterator();
		Iterator<Color> itLBF = listaBojaFigura.iterator();
		Iterator<Integer> itLDF = listaDebljinaFigura.iterator();
		Iterator<Boolean> itPP = listaPraznePune.iterator();
		while (itLF.hasNext()) {
			g2.setColor(itLBF.next());
			clanListe = itLF.next(); 
			
			if (clanListe.getClass() == elipsa.getClass()) {
				if (itPP.next()) {
					g2.fill((Ellipse2D)clanListe);
				}
				else {
					g2.setStroke(new BasicStroke(itLDF.next()));
					g2.draw((Ellipse2D)clanListe);
				}
			}
			else if (clanListe.getClass() == pravougaonik.getClass()) {
				if (itPP.next()) {
					g2.fill((Rectangle2D)clanListe);
				}
				else {
					g2.setStroke(new BasicStroke(itLDF.next()));
					g2.draw((Rectangle2D)clanListe);
				}
			}
			else if (clanListe.getClass() == linija.getClass()) {
				g2.setStroke(new BasicStroke(itLDF.next()));
				g2.draw((Line2D)clanListe);
			}
			
		} // Kraj petlje while.
		
	} // Kraj metode paintComponent.

	// Metoda crta prazne figure.
	private void crtajPrazne(int x, int y, int sirina, int visina) {
		if (glavniProzor.isRdElipsa()) {
			if (brojPomjeraja == 0) {
				listaFigura.add(new Ellipse2D.Double(x, y, (double)sirina, visina));
				listaBojaFigura.add(boja);
				listaDebljinaFigura.add(debljinaLinije);
				listaPraznePune.add(false);
				brojPomjeraja = 1;
				
			}
			else if (brojPomjeraja == 1) {
				listaFigura.remove(listaFigura.size() - 1);
				listaBojaFigura.remove(listaBojaFigura.size() - 1);
				listaDebljinaFigura.remove(listaDebljinaFigura.size()-1);
				listaPraznePune.remove(listaPraznePune.size() - 1);
				
				listaFigura.add(new Ellipse2D.Double(x, y, sirina, visina));
				listaBojaFigura.add(boja);
				listaDebljinaFigura.add(debljinaLinije);
				listaPraznePune.add(false);
			}
		}
		else if (glavniProzor.isRdPravougaonik()) {
			if (brojPomjeraja == 0) {
				listaFigura.add(new Rectangle2D.Double(x, y, sirina, visina));
				listaBojaFigura.add(boja);
				listaDebljinaFigura.add(debljinaLinije);
				listaPraznePune.add(false);
				brojPomjeraja = 1;
			}
			else if (brojPomjeraja == 1) {
				listaFigura.remove(listaFigura.size()-1);
				listaBojaFigura.remove(listaBojaFigura.size()-1);
				listaDebljinaFigura.remove(listaDebljinaFigura.size()-1);
				listaPraznePune.remove(listaPraznePune.size() - 1);
				
				listaFigura.add(new Rectangle2D.Double(x, y, sirina, visina));
				listaBojaFigura.add(boja);
				listaDebljinaFigura.add(debljinaLinije);
				listaPraznePune.add(false);
			}
		}
		
		repaint();
		
	} // Kraj metode crtajPrazne.
	
	// Metoda crta pune figure.
		private void crtajPune(int x, int y,  int sirina, int visina) {
			if (glavniProzor.isRdElipsa()) {
				if (brojPomjeraja == 0) {
					listaFigura.add(new Ellipse2D.Double(x, y, sirina, visina));
					listaBojaFigura.add(boja);
					
					listaPraznePune.add(true);
					brojPomjeraja = 1;
				}
				else if (brojPomjeraja == 1) {
					listaFigura.remove(listaFigura.size()-1);
					listaBojaFigura.remove(listaBojaFigura.size()-1);
					//
					listaPraznePune.remove(listaPraznePune.size() - 1);
					
					listaFigura.add(new Ellipse2D.Double((double)x, (double)y, sirina, visina));
					listaBojaFigura.add(boja);
					//
					listaPraznePune.add(true);
				}
			}
			else if (glavniProzor.isRdPravougaonik()) {
				if (brojPomjeraja == 0) {
					listaFigura.add(new Rectangle2D.Double(x, y, sirina, visina));
					listaBojaFigura.add(boja);
					//
					listaPraznePune.add(true);
					brojPomjeraja = 1;
				}
				else if (brojPomjeraja == 1) {
					listaFigura.remove(listaFigura.size()-1);
					listaBojaFigura.remove(listaBojaFigura.size()-1);
					//
					listaPraznePune.remove(listaPraznePune.size() - 1);
					
					listaFigura.add(new Rectangle2D.Double(x, y, sirina, visina));
					listaBojaFigura.add(boja);
					//
					listaPraznePune.add(true);
				}
			}
			
			repaint();
			
		} // Kraj metode crtajPune.
		
		// Metoda crta linije.
		private void crtajLinije(int x1, int y1, int x2, int y2) {
			if (brojPomjeraja == 0) {
				listaFigura.add(new Line2D.Double(x1, y1, x2, y2));
				listaBojaFigura.add(boja);
				listaDebljinaFigura.add(debljinaLinije);
				brojPomjeraja = 1;
			}
			else if (brojPomjeraja == 1) {
				listaFigura.remove(listaFigura.size()-1);
				listaBojaFigura.remove(listaBojaFigura.size()-1);
				listaDebljinaFigura.remove(listaDebljinaFigura.size()-1);
				
				listaFigura.add(new Line2D.Double(x1, y1, x2, y2));
				listaBojaFigura.add(boja);
				listaDebljinaFigura.add(debljinaLinije);
			}
			
			// repaint();
			
		} // Kraj metode crtajLinije.
		
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		this.requestFocus();
		xPocetno = e.getX();
		yPocetno = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int xTrenutno = e.getX();
		int yTrenutno = e.getY();
		int sirina, visina;
		
		sirina = xTrenutno - xPocetno;
		visina = yTrenutno - yPocetno;
		
		if (glavniProzor.isRdLinija()) {
			crtajLinije(xPocetno, yPocetno, xTrenutno, yTrenutno);
		}
		
		if (glavniProzor.isRdSlobodnoCrtanje()) {
			listaFigura.add(new Line2D.Double(xPocetno, yPocetno, xTrenutno, yTrenutno));
			listaBojaFigura.add(boja);
			listaDebljinaFigura.add(debljinaLinije);
			xPocetno = xTrenutno;
			yPocetno = yTrenutno;
		}
		
		if (glavniProzor.isCbIspuna()) {
			if (!pritisnutoCtrl) {
				odrediXYSV(xPocetno, yPocetno, xTrenutno, yTrenutno);
				crtajPune(x, y, s, v);
			}
			else if (pritisnutoCtrl) {
				odrediXYSVctrl(xPocetno, yPocetno, xTrenutno, yTrenutno);
				crtajPune(x, y, sv, sv);
			}
		}
		else if (!glavniProzor.isCbIspuna()) {
			if (!pritisnutoCtrl) {
				odrediXYSV(xPocetno, yPocetno, xTrenutno, yTrenutno);
				crtajPrazne(x, y, s, v);
			}
			else if (pritisnutoCtrl) {
				odrediXYSVctrl(xPocetno, yPocetno, xTrenutno, yTrenutno);
				crtajPrazne(x, y, sv, sv);
				
			}
		}
		
	} // Kraj metode mouseDragged.
	
	@Override
	public void mouseReleased(MouseEvent e) {
		brojPomjeraja = 0;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	// Metoda odredjuje pocetnu tacku, sirinu i visinu pravougaonika/elipse.
	private void odrediXYSV(int xP, int yP, int xT, int yT) {
		if ((xT-xP >= 0) && (yT-yP >= 0)) {
			// Dolje desno.
			x = xP;
			y = yP;
			s = xT - xP;
			v = yT - yP;
		}
		else if ((xT-xP >= 0) && (yT-yP < 0)) {
			// Gore desno.
			x = xP;
			y = yT;
			s = xT - xP;
			v = yP - yT;
		}
		else if ((xT-xP < 0) && (yT-yP < 0)) {
			// Gore lijevo.
			x = xT;
			y = yT;
			s = xP - xT;
			v = yP - yT;
		}
		else if ((xT-xP < 0) && (yT-yP >= 0)) {
			// Dolje lijevo.
			x = xT;
			y = yP;
			s = xP - xT;
			v = yT - yP;
		}
	} // Kraj metode odrediXYSV.
	
	// Metoda odredjuje pocetnu tacku, sirinu i visinu pravougaonika/elipse.
	// Kad je pritisnuto Ctrl.
		private void odrediXYSVctrl(int xP, int yP, int xT, int yT) {
			if ((xT-xP >= 0) && (yT-yP >= 0)) {
				// Dolje desno.
				x = xP;
				y = yP;
				s = xT - xP;
				v = yT - yP;
				if (s <= v) {
					sv = s;
				}
				else if (s > v) {
					sv = v;
				}
			}
			else if ((xT-xP >= 0) && (yT-yP < 0)) {
				// Gore desno.
				x = xP;
				s = xT - xP;
				v = yP - yT;
				if (s <= v) {
					y = yP - s;
					sv = s;
				}
				else if (s > v) {
					y = yP - v;
					sv = v;
				}
			}
			else if ((xT-xP < 0) && (yT-yP < 0)) {
				// Gore lijevo.
				s = xP - xT;
				v = yP - yT;
				if (s <= v) {
					sv = s;
					x = xP - s;
					y = yP - s;
				}
				else if (s > v) {
					sv = v;
					x = xP - v;
					y = yP - v;
				}
			}
			else if ((xT-xP < 0) && (yT-yP >= 0)) {
				// Dolje lijevo.
				y = yP;
				s = xP - xT;
				v = yT - yP;
				if (s <= v) {
					sv = s;
					x = xP - s;
				}
				else if (s > v) {
					sv = v;
					x = xP - v;
				}
			}
		} // Kraj metode odrediXYSVctrl.
}
