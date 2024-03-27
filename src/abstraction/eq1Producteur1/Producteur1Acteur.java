package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.IProduit;

public class Producteur1Acteur implements IActeur {
	
	protected int cryptogramme;
	protected Journal journal;
	//new stuff I added : Abdo
	protected int nb_employees;
	protected int nb_enfants;
	protected int nb_equitables;
	private double coutStockage;
	//This /|\
	
	

	public Producteur1Acteur() {
		this.journal=new Journal(this.getNom()+"   journal",this);
	}
	
	public void initialiser() {
		this.coutStockage = Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur();
		
	}

	public String getNom() {// NE PAS MODIFIER
		return "EQ1";
	}
	
	public String toString() {// NE PAS MODIFIER
		return this.getNom();
	}

	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		double totalStock = 0;
		this.getJournaux().get(0).ajouter("Etape= "+Filiere.LA_FILIERE.getEtape());
		this.getJournaux().get(0).ajouter("Coût de stockage : "+this.coutStockage);
		this.getJournaux().get(0).ajouter("Stock= "+ totalStock);
		/*  I added this above there is no diff in between the two functions I just think the first is more professional/|\
		this.journal.ajouter("etape= "+Filiere.LA_FILIERE.getEtape());
		this.journal.ajouter("prix stockage= "+Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur());
		*/
	}

	public Color getColor() {// NE PAS MODIFIER
		return new Color(243, 165, 175); 
	}

	public String getDescription() {
		return "AfriKakao-Producteur Cacao";
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	// Renvoie les parametres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journal);
		return res;
	}

	////////////////////////////////////////////////////////
	//               En lien avec la Banque               //
	////////////////////////////////////////////////////////

	// Appelee en debut de simulation pour vous communiquer 
	// votre cryptogramme personnel, indispensable pour les
	// transactions.
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	// Appelee lorsqu'un acteur fait faillite (potentiellement vous)
	// afin de vous en informer.
	public void notificationFaillite(IActeur acteur) {
	}

	// Apres chaque operation sur votre compte bancaire, cette
	// operation est appelee pour vous en informer
	public void notificationOperationBancaire(double montant) {
	}
	
	// Renvoie le solde actuel de l'acteur
	protected double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}

	////////////////////////////////////////////////////////
	//        Pour la creation de filieres de test        //
	////////////////////////////////////////////////////////

	// Renvoie la liste des filieres proposees par l'acteur
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		return(filieres);
	}

	// Renvoie une instance d'une filiere d'apres son nom
	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme==cryptogramme) { // c'est donc bien un acteur assermente qui demande a consulter la quantite en stock
			return 0; // A modifier
		} else {
			return 0; // Les acteurs non assermentes n'ont pas a connaitre notre stock
		}
	}
}
