import java.awt.event.WindowAdapter;

import javax.swing.*;

import java.awt.event.WindowEvent;

public class FrameGrille extends JFrame
{

	private PanelGrille panelGrille;

	public FrameGrille(Controleur ctrl)
	{
		this.panelGrille = new PanelGrille ( ctrl );

		this.setTitle   ( "Permuter" );
		this.setLocation( 100, 100 );
		this.setSize    ( 700, 700 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				// Votre code ici. Par exemple :
				ctrl.sauvegarder();
			}
		});
		this.add ( this.panelGrille );

		this.setVisible ( true );
	}

	public void majIHM()
	{
		this.panelGrille.majIHM();
	}

}