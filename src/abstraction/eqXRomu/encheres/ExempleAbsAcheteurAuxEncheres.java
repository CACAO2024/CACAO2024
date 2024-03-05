package abstraction.eqXRomu.encheres;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.IProduit;

public class ExempleAbsAcheteurAuxEncheres implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Integer cryptogramme;
	protected Journal journal;
	protected HashMap<IProduit, Double> stock;
	protected Chocolat choco;
	protected String marque;
	protected double prixInit;


	public ExempleAbsAcheteurAuxEncheres(double prixInit) {	
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.stock=new HashMap<IProduit, Double>();
		this.prixInit = prixInit;
		this.journal = new Journal(this.getNom()+" activites", this);
	}

	public String toString() {
		return this.getNom();
	}
	public String getNom() {
		return "A.Ench"+this.numero;
	}

	public String getDescription() {
		return "Acheteur aux encheres "+this.numero;
	}

	public Color getColor() {
		return new Color(64+((numero)*(157/NB_INSTANCES)), 128+((numero)*(64/NB_INSTANCES)), 0);
	}

	public void initialiser() {
	}

	public void next() {
		this.journal.ajouter("== ETAPE "+Filiere.LA_FILIERE.getEtape()+" ==");
		if (this.stock.keySet().size()>0) {
			for (IProduit c : this.stock.keySet()) {
				this.journal.ajouter("stock de "+c+" : "+this.stock.get(c));
			}
		}
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> j= new ArrayList<Journal>();
		j.add(this.journal);
		return j;
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public void notificationFaillite(IActeur acteur) {
	}

	public void notificationOperationBancaire(double montant) {
	}
	
	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme==cryptogramme) {
			if (this.stock.keySet().contains(p)) {
				return this.stock.get(p);
			} else{
				return 0;
			}
		} else {
			return 0;
		}
	}
}