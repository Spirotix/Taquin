import java.awt.GridLayout;

import javax.lang.model.util.ElementScanner14;
import javax.swing.*;

import java.awt.event.*;

public class PanelGrille extends JPanel implements ActionListener
{
	Controleur ctrl;

	JLabel[][] tabLblCase;
	JButton[]  tabButton ;

	JButton    melanger  ;
	JButton    restorer  ;
	JButton    skin      ;
	JButton    save      ;

	String[] tabSkin = { "asterix/", "couleur/" };

	String choix ;

	public PanelGrille(Controleur ctrl)
	{
		String[][] modele;

		int cas;
		int cptBtn=0;


		this.ctrl = ctrl;
		this.setLayout ( new GridLayout ( this.ctrl.getNbLigne()+2, this.ctrl.getNbColonne()+2) );

		modele = this.getModele();

		this.choix = tabSkin[Controleur.getSkin()];
		/*------------------------------*/
		/*    Création des composants   */
		/*------------------------------*/

		// Création des Labels
		this.tabLblCase  = new JLabel [ this.ctrl.getNbLigne() ] [ this.ctrl.getNbColonne() ];


		for (int lig=0;lig<tabLblCase.length; lig++ )
			for (int col=0;col<tabLblCase[lig].length; col++ )
			{
				this.tabLblCase[lig][col] = new JLabel( new ImageIcon( "./images/" + choix + ctrl.getImage(lig, col)) );
				//this.tabLblCase[lig][col] = new JLabel( new ImageIcon ( this.ctrl.getImage(lig, col)));
				//this.tabLblCase[lig][col].setBackground(this.ctrl.getCouleur(lig, col));
				this.tabLblCase[lig][col].setOpaque(true);
			}


		// Création des Boutons
		this.tabButton = new JButton[ 2*this.ctrl.getNbLigne() + 2*this.ctrl.getNbColonne() ];

		cptBtn = 0;

		for (int lig=0;lig<modele.length; lig++ )
			for (int col=0;col<modele[lig].length; col++ )
				if ( modele[lig][col] != null && modele[lig][col].startsWith ("fl_" ) )
				{
					this.tabButton[cptBtn++] = new JButton(  new ImageIcon("./images/icone/" + modele[lig][col] + ".gif")  );
				}
		
		this.melanger = new JButton( new ImageIcon("./images/icone/melanger.png") );
		this.restorer = new JButton( new ImageIcon("./images/icone/restorer.png") );
		this.skin     = new JButton( new ImageIcon("./images/icone/skin.png"    ) );
		this.save     = new JButton( "sauvegarder" );

		/*------------------------------*/
		/* Postionnement des composants */
		/*------------------------------*/
		cptBtn = 0;

		for (int lig=0; lig<modele.length; lig++ )
			for (int col=0; col<modele[lig].length; col++ )
			{
					 if ( modele[lig][col] == null                   ) cas=0;
				else if ( modele[lig][col].startsWith ("fl_" )) cas=1;
				else if ( modele[lig][col] == "mel"                  ) cas=2;
				else if ( modele[lig][col] == "res"                  ) cas=3;
				else if ( modele[lig][col] == "ski"                  ) cas=4;
				else if ( modele[lig][col] == "sau"                  ) cas=5;
				else                                                   cas=6;


				switch ( cas )
				{
					case 0 -> this.add ( new JLabel()                 );
					case 1 -> this.add ( this.tabButton[cptBtn++]     );
					case 2 -> this.add ( this.melanger                );
					case 3 -> this.add ( this.restorer                );
					case 4 -> this.add ( this.skin                    );
					case 5 -> this.add ( this.save                    );
					case 6 -> this.add ( this.tabLblCase[lig-1][col-1]);
				}
			}


		/*------------------------------*/
		/* Activation des composants    */
		/*------------------------------*/
		for (int cpt=0; cpt<this.tabButton.length; cpt++)
			this.tabButton[cpt].addActionListener( this );

		this.melanger.addActionListener(this);
		this.restorer.addActionListener(this);
		this.skin.addActionListener(this);
		this.save.addActionListener(this);

	}


	public void majIHM()
	{
		for ( int lig=0; lig< this.tabLblCase.length; lig++)
			for ( int col=0; col< this.tabLblCase[lig].length; col++)
			{
				this.tabLblCase[lig][col].setIcon( new ImageIcon( "./images/" + choix + ctrl.getImage(lig, col)));
				//this.tabLblCase[lig][col].setIcon(new ImageIcon (this.ctrl.getImage(lig, col)));
				//this.tabLblCase[lig][col].setBackground(this.ctrl.getCouleur(lig, col));
			}

	}


	public void actionPerformed ( ActionEvent e)
	{
		/* Appel de la méthode permuter de Controleur avec les bons paramètres */
		for ( int cpt = 0; cpt < tabButton.length; cpt ++)
		{
			if (e.getSource() == this.tabButton[cpt])
			{

				if ( ((JButton) e.getSource()).getIcon().toString().contains("fl_haut")   ) 
				{ 
					ctrl.permuter( 'l', '-', cpt ); 
				}
				
				if ( ((JButton) e.getSource()).getIcon().toString().contains("fl_bas")    ) 
				{ 
					ctrl.permuter( 'l', '+', cpt - 18); 
				}
				
				if ( ((JButton) e.getSource()).getIcon().toString().contains("fl_gauche") ) 
				{
					 ctrl.permuter('c', '-', (cpt - 6) / 2);
					  
				}
				
				if ( ((JButton) e.getSource()).getIcon().toString().contains("fl_droite") ) 
				{ 
					ctrl.permuter('c', '+', (cpt - 7) / 2);
				}
			}
		}

		if( e.getSource() == this.melanger ){ ctrl.melanger(); }

		if( e.getSource() == this.restorer ){ ctrl.restorer(); }

		if (e.getSource() == this.skin     )
		{ 
			choix = tabSkin[ctrl.skin( tabSkin )];
			this.ctrl.majIHM();
		}

		if (e.getSource() == this.save){ ctrl.sauvegarder(); }

	}

	private String[][] getModele()
	{
		/* Voici un exemple de Modele généré pour une grille de 6 x 6

		{ {"res",        "fl_haut", "fl_haut", "fl_haut", "fl_haut", "fl_haut", "fl_haut", "mel"        },
	     {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"fl_gauche", "val",     "val",     "val",     "val",     "val",     "val",     "fl_droite" },
		  {"ski",        "fl_bas",  "fl_bas",  "fl_bas",  "fl_bas",  "fl_bas",  "fl_bas",   "sau"       }  };
		*/


		// Construction du Modele correspondant à la taille de notre Grille.
		String[][] tabModele = new String[ctrl.getNbLigne()+2][ctrl.getNbColonne()+2];

		for (int lig = 1; lig < tabModele.length-1; lig++ )
		{
			tabModele[lig][0]                          = "fl_gauche";
			tabModele[lig][tabModele[lig].length - 1 ] = "fl_droite";
		}

		for (int col = 1; col < tabModele[0].length-1; col++ )
		{
			tabModele[0]                     [col] = "fl_haut";
			tabModele[tabModele.length - 1 ] [col] = "fl_bas";
		}

		for (int lig=1; lig < tabModele.length-1; lig++ )
			for (int col = 1; col < tabModele[0].length-1; col++ )
				tabModele[lig][col] = "val";

		tabModele[0][tabModele.length - 1] = "mel";

		tabModele[0][0]                    = "res";

		tabModele[tabModele.length - 1][0] = "ski";

		tabModele[tabModele.length - 1][tabModele.length - 1] = "sau";

		return tabModele;
	}
}
