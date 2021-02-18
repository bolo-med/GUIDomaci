package view;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.ArrayList;

public class Slika implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Polja klase.
	private ArrayList<Object> listaFigura = new ArrayList<Object>();
	private ArrayList<Color> listaBojaFigura = new ArrayList<Color>();
	private ArrayList<Integer> listaDebljinaFigura = new ArrayList<Integer>();
	private ArrayList<Boolean> listaPraznePune = new ArrayList<Boolean>();
	
	// Konstruktor.
	public Slika(ArrayList<Object> listaFigura,
				 ArrayList<Color> listaBojaFigura,
				 ArrayList<Integer> listaDebljinaFigura,
				 ArrayList<Boolean> listaPraznePune) {
		super();
		this.listaFigura = listaFigura;
		this.listaBojaFigura = listaBojaFigura;
		this.listaDebljinaFigura = listaDebljinaFigura;
		this.listaPraznePune = listaPraznePune;
	}
	
	// Geteri.
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
	
}
