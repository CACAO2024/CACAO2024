package abstraction.eq7Transformateur4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.appelDOffre.AppelDOffre;
import abstraction.eqXRomu.appelDOffre.IVendeurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.appelDOffre.SuperviseurVentesAO;
import abstraction.eqXRomu.bourseCacao.BourseCacao;
import abstraction.eqXRomu.encheres.SuperviseurVentesAuxEncheres;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.Gamme;
import abstraction.eqXRomu.produits.IProduit;

//classe 'codé' par Eliott, néanmoins ce n'est pour l'instant qu'un copé-collé de l'exemple de Vendeur Appel d'offre fourni


public class Transformateur4VendeurAppelDOffre extends Transformateur4VendeurContratCadre implements IVendeurAO {
	private HashMap<ChocolatDeMarque, List<Double>> prixAO;
	protected Journal journalAO;

	public Transformateur4VendeurAppelDOffre() {
		super();
		this.journalAO = new Journal(this.getNom()+" journal A.O.", this);
	}

	public void initialiser() {
		super.initialiser();
		this.prixAO = new HashMap<ChocolatDeMarque, List<Double>>();
		for (ChocolatDeMarque cm : this.chocosProduits) {
			this.prixAO.put(cm, new LinkedList<Double>());
		}			
	}
	
	public double prixMoyen(ChocolatDeMarque cm) {
		List<Double> prix=prixAO.get(cm);
		if (prix.size()>0) {
			double somme =0.0;
			
			for (Double d : prix) {
				somme+=d;
			}
			return somme/prix.size();
		} else {
			return 0.0;
		}
	}

	public List<Journal> getJournaux() {
		List<Journal> jx=super.getJournaux();
		jx.add(journalAO);
		return jx;
	}


	public OffreVente proposerVente(AppelDOffre offre) { 
		IProduit p = offre.getProduit();
		if (!(p instanceof ChocolatDeMarque)) {
			return null;
		}
		ChocolatDeMarque cm = (ChocolatDeMarque)p;
		
		if (cm.isEquitable()) { //On ne fait pas de ventes en AO pour des chocolats équitables
			return null;
		}
		
		if ((this.chocolatCocOasis.contains(cm))  && (stockChocoMarque.get(cm) > restantALivrer(cm)+ 10000)) { //Nous ne vendons que nos sur-stocks en faisant attention a ne pas vendre ce que nous devons fournir en CC
			if (prixAO.get(cm).size()==0) {
				BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
				double px = bourse.getCours(Feve.F_MQ).getMax()*1.75;
				if (cm.getChocolat().getGamme()==Gamme.HQ) {
					px = bourse.getCours(Feve.F_MQ).getMax()*2;
				} else if (cm.getChocolat().getGamme()==Gamme.BQ) {
					px = bourse.getCours(Feve.F_BQ).getMax()*1.75;
				}
				return new OffreVente(offre, this, cm, px);
			} else {
				return new OffreVente(offre, this, cm, prixMoyen(cm)*1.05);
			}
			
		} else if ((this.chocolatDistributeur.contains(cm))  && (stockChoco.get(cm.getChocolat()) > restantALivrer(cm)+ 10000)){
		
			if (prixAO.get(cm).size()==0) {
				BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
				double px = bourse.getCours(Feve.F_MQ).getMax()*1.75;
				if (cm.getChocolat().getGamme()==Gamme.HQ) {
					px = bourse.getCours(Feve.F_HQ).getMax()*2;
				} else if (cm.getChocolat().getGamme()==Gamme.BQ) {
					px = bourse.getCours(Feve.F_BQ).getMax()*1.75;
				}
				return new OffreVente(offre, this, cm, px);
			} else {
				return new OffreVente(offre, this, cm, prixMoyen(cm)*1.05);
			}

		} else {
			if (this.getChocolatsProduits().contains(cm)) {
				journalAO.ajouter("Chocolat " + cm + " en trop faible stock pour satisfaire un AO");
			}
			return null;
		}
		
	}

	public void notifierVenteAO(OffreVente propositionRetenue) {
		ChocolatDeMarque cm = (ChocolatDeMarque)(propositionRetenue.getProduit());
		double px = propositionRetenue.getPrixT();
		double quantite = propositionRetenue.getQuantiteT();
		prixAO.get(cm).add(px); // on fait comme si on avait accepte avec 5% d'augmentation afin que lors des prochains echanges on accepte des prix un peu plus eleves
		journalAO.ajouter("   Vente par AO de "+quantite+" T de "+cm+" au prix de  "+px + " à " + propositionRetenue.getOffre().getAcheteur());
		if (prixAO.get(cm).size()>10) {
			prixAO.get(cm).remove(0); // on ne garde que les dix derniers prix
		}
	}


	public void notifierPropositionNonRetenueAO(OffreVente propositionRefusee) {
		ChocolatDeMarque cm = (ChocolatDeMarque)(propositionRefusee.getProduit());
		double px = propositionRefusee.getPrixT();
		double quantite = propositionRefusee.getQuantiteT();
		prixAO.get(cm).add(px*0.92); // on fait comme si on avait accepte avec 8% de baisse afin que lors des prochains echanges on fasse une meilleure offre
		journalAO.ajouter("   Echec de vente par AO de "+quantite+" T de "+cm+" au prix de  "+px);
		if (prixAO.get(cm).size()>10) {
			prixAO.get(cm).remove(0); // on ne garde que les dix derniers prix
		}
	}
	
	public void next() {
		super.next();
		this.journalAO.ajouter("=== STEP " + Filiere.LA_FILIERE.getEtape() + "==================");
	}
}