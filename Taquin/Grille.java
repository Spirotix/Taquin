import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import iut.algo.Decomposeur;

public class Grille
{
	private char[][] grille;
	private char[][] backup;

	public Grille()
	{
		/*this.grille = new char[][]  { {'m', 'm', 'm','m', 'm', 'm' },
		                              {'b', 'b', 'b','b', 'b', 'b' },
		                              {'v', 'v', 'v','v', 'v', 'v' },
		                              {'j', 'j', 'j','j', 'j', 'j' },
		                              {'o', 'o', 'o','o', 'o', 'o' },
		                              {'r', 'r', 'r','r', 'r', 'r' }  };
		*/
		this.grille = new char[][]  { {'A', 'B', 'C','D', 'E', 'F' },
		                              {'G', 'H', 'I','J', 'K', 'L' },
		                              {'M', 'N', 'O','P', 'Q', 'R' },
		                              {'S', 'T', 'U','V', 'W', 'X' },
		                              {'Y', 'Z', '0','1', '2', '3' },
		                              {'4', '5', '6','7', '8', '9' }  };
		
		
		this.backup = new char[this.grille.length][this.grille[0].length];
		for ( int lig = 0; lig < this.grille.length; lig++)
		{
			for ( int col = 0 ; col < this.grille[0].length; col++)
				this.backup[lig][col] = this.grille[lig][col];
		}

		Scanner scFic;
		Decomposeur dec;

		try
		{
			scFic = new Scanner ( new FileInputStream ( "grille.data" ), "UTF8" );

			while ( scFic.hasNextLine() )
			{

				for( int lig = 0; lig < grille.length; lig ++)
				{
					dec = new Decomposeur(scFic.nextLine());

					for ( int col = 0; col < grille[0].length; col ++)
					{
						this.grille[lig][col] = dec.getString(col).charAt(0);
					}
				}
				
				dec = new Decomposeur(scFic.nextLine());

				Controleur.setSkin( Integer.parseInt(dec.getString(0)));
			}

			scFic.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	public char getVal (int lig, int col)
	{
		return this.grille[lig][col];
	}

	public int getNbLigne () { return this.grille   .length; }
	public int getNbColone() { return this.grille[0].length; }

	// type 'l' : ligne                    'c' : colonne
	// sens '+' : droite --> | bas \/      '-' : gauche <-- | haut /\
	// indice  numéro de ligne ou de colonne
	public void permuter ( char type, char sens, int indice )
	{
		char tmp;

		if ( type == 'l' )
		{
			if ( sens =='+' )
			{	

				tmp = this.grille[this.grille.length - 1][indice];
				for (int cpt = this.grille.length - 1 ; cpt > 0; cpt--)
				{
					this.grille[cpt][indice] = this.grille[cpt - 1][indice];
				}

				this.grille[0][indice] = tmp;
			}
			else
			{

				tmp = this.grille[0][indice];
				for (int cpt = 0; cpt < this.grille.length - 1; cpt++)
				{
					this.grille[cpt][indice] = this.grille[cpt + 1][indice];
				}

				this.grille[this.grille.length - 1][indice] = tmp;
			}
		}
		else
		{
			if (sens == '+')
			{

				tmp = this.grille[indice][this.grille[0].length - 1];
				for (int cpt = this.grille[0].length - 1; cpt > 0; cpt--)
				{
					this.grille[indice][cpt] = this.grille[indice][cpt - 1];
				}

				this.grille[indice][0] = tmp;
			}
			else
			{

				tmp = this.grille[indice][0];
				for (int cpt = 0; cpt < this.grille[0].length - 1; cpt++)
				{
					this.grille[indice][cpt] = this.grille[indice][cpt + 1];
				}

				this.grille[indice][this.grille[0].length - 1] = tmp;
			}

		}

	}

	public void melanger()
	{
		int indice11;
		int indice12;

		int indice21;
		int indice22;

		char temp  ;
		for ( int cpt=0; cpt < 100; cpt ++)
		{
			indice11 = (int) (Math.random() * grille.length);
			indice12 = (int) (Math.random() * grille.length);
			indice21 = (int) (Math.random() * grille.length);
			indice22 = (int) (Math.random() * grille.length);

			temp = grille[indice11][indice12];

			grille[indice11][indice12] = grille[indice21][indice22];
			grille[indice21][indice22] = temp;
		}
	}

	public void restorer()
	{
		for (int lig = 0; lig < this.grille.length; lig++)
		{
			for (int col = 0; col < this.grille[0].length ; col++)
				this.grille[lig][col] = this.backup[lig][col];
		}
	}

	public void sauvegarder()
	{

		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("grille.data"), "UTF8" ));

			for (int lig = 0; lig < this.grille.length; lig++ )
			{
				for (int col = 0; col < this.grille.length; col++)
				{
					pw.print ( this.grille[lig][col] + "\t" );
				}
				pw.print("\n");
			}

			pw.print( Controleur.getSkin() );
			pw.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

}
