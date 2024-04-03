package abstraction.eq6Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eqXRomu.filiere.*;
import abstraction.eqXRomu.general.*;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;

public class TransformateurContratCadre implements IActeur {
	private static int NB_INSTANCES = 0; // Afin d'attribuer un nom different a toutes les instances
	protected int numero;
	protected Variable stock;
	protected Integer cryptogramme;
	protected IProduit produit;
	protected Journal journal;
	protected SuperviseurVentesContratCadre supCCadre;

	public TransformateurContratCadre(IProduit produit) {	
		if (produit==null) {
			throw new IllegalArgumentException("creation d'une instance de ExempleTransformateurContratCadre avec produit==null");
		}		
		NB_INSTANCES++;
		this.numero=NB_INSTANCES;
		this.produit=produit;
		this.stock=new Variable(getNom()+" stock ", null, this, 0, 1000, 300);
		this.journal = new Journal(this.getNom()+" activites", this);
	}
	
	public String getNom() {
		return "TCC"+this.numero+""+produit.toString();
	}

	public String getDescription() {
		return "TransformateurContratCadre "+this.numero+" "+this.produit.toString();
	}

	public Color getColor() {
		return new Color(128+((numero)*(127/NB_INSTANCES)), 64+((numero)*(191/NB_INSTANCES)), 0);
	}

	public void initialiser() {
		this.supCCadre = (SuperviseurVentesContratCadre) (Filiere.LA_FILIERE.getActeur("Sup.CCadre"));
	}

	public void next() {
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stock);
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
	
	public String toString() {
		return this.getNom();
	}
	
	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme==cryptogramme) {
			if (p.equals(this.produit)) {
				return this.stock.getValeur();
			} else{
				return 0;
			}
		} else {
			return 0;
		}
	}
}
