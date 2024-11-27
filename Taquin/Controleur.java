import java.awt.Color;

public class Controleur
{
	private FrameGrille ihm;
	private Grille      metier;

	private static int nbSkin;

	public Controleur()
	{
		this.metier = new Grille();
		this.ihm    = new FrameGrille ( this );

	}

	// Pour Version 1
	public Color getCouleur(int lig, int col)
	{
		switch ( metier.getVal(lig, col) )
		{
			case 'b' : return new Color (   0, 162, 232 );
			case 'o' : return new Color ( 239, 149,  15 );
			case 'm' : return new Color ( 151,  85, 148 );
			case 'r' : return new Color ( 237,  27,  35 );
			case 'v' : return new Color (  65, 170,  39 );
			case 'j' : return new Color ( 254, 242,   0 );
		}

		return null;
	}

	// Pour Version 2
	/*public String getImage(int lig, int col)
	{
		switch ( metier.getVal(lig, col) )
		{
			case 'b' : return "./images/bleu.gif";
			case 'o' : return "./images/orange.gif";
			case 'm' : return "./images/mauve.gif";
			case 'r' : return "./images/rouge.gif";
			case 'v' : return "./images/vert.gif";
			case 'j' : return "./images/jaune.gif";
		}

		return null;
	}*/

	public String getImage(int lig, int col)
	{
		return metier.getVal(lig, col) + ".png";
	}

	public int getNbLigne  () { return this.metier.getNbLigne (); }
	public int getNbColonne() { return this.metier.getNbColone(); }

	// Méthode appelée sur le clique d'un bouton
	public void permuter ( char type, char sens, int indice )
	{
		this.metier.permuter ( type, sens, indice );
		this.ihm.majIHM();
	}

	public void melanger()
	{
		this.metier.melanger();
		this.ihm.majIHM();
	}

	public void restorer()
	{
		this.metier.restorer();
		this.ihm.majIHM();
	}

	public int skin( String[] tabSkin )
	{
		Controleur.nbSkin ++;
		if (Controleur.nbSkin > tabSkin.length - 1) { Controleur.nbSkin = 0; }
		
		return Controleur.nbSkin;
	}

	public static int getSkin()
	{
		return Controleur.nbSkin;
	}

	public static void setSkin( int skin )
	{
		Controleur.nbSkin = skin;
	}

	public void sauvegarder(){ this.metier.sauvegarder();}

	public void majIHM()
	{
		this.ihm.majIHM();
	}

	public static void main(String[] a)
	{
		new Controleur();
	}
}
