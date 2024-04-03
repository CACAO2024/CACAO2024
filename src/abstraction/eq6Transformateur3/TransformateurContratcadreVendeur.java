package abstraction.eq6Transformateur3;

import java.util.LinkedList;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.ExempleTransformateurContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.produits.IProduit;

public class TransformateurContratcadreVendeur extends ExempleTransformateurContratCadre implements IVendeurContratCadre{

	public TransformateurContratcadreVendeur(IProduit produit) {
		super(produit);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public boolean vend(IProduit produit) {
		/**
		 * Methode appelee par le superviseur afin de savoir si l'acheteur
		 * est pret a faire un contrat cadre sur le produit indique.
		 * @param produit
		 * @return Retourne false si le vendeur ne souhaite pas etablir de contrat 
		 * a cette etape pour ce type de produit (retourne true si il est pret a
		 * negocier un contrat cadre pour ce type de produit).
		 */
		return true;
	}

	@Override
	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		/**
		 * Methode appelee par le SuperviseurVentesContratCadre lors de la phase de negociation
		 * sur l'echeancier afin de connaitre la contreproposition du vendeur. Le vendeur
		 * peut connaitre les precedentes propositions d'echeanciers via un appel a la methode
		 * getEcheanciers() sur le contrat. Un appel a getEcheancier() sur le contrat retourne 
		 * le dernier echeancier que l'acheteur a propose.
		 * @param contrat
		 * @return Retourne null si le vendeur souhaite mettre fin aux negociations en renoncant a
		 * ce contrat. Retourne l'echeancier courant du contrat (contrat.getEcheancier()) si il est
		 * d'accord avec cet echeancier. Sinon, retourne un autre echeancier qui est une contreproposition.
		 */
		return contrat.getEcheancier();
	}

	@Override
	public double propositionPrix(ExemplaireContratCadre contrat) {
		/**
		 * Methode appele par le SuperviseurVentesContratCadre apres une negociation reussie
		 * sur l'echeancier afin de connaitre le prix a la tonne que le vendeur propose.
		 * @param contrat
		 * @return La proposition initale du prix a la tonne.
		 */
		return 0;
	}

	@Override
	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		/**
		 * Methode appelee par le SuperviseurVentesContratCadre apres une contreproposition
		 * de prix different de la part de l'acheteur, afin de connaitre la contreproposition
		 * de prix du vendeur.
		 * @param contrat
		 * @return Retourne un nombre inferieur ou egal a 0.0 si le vendeur souhaite mettre fin
		 * aux negociation en renoncant a ce contrat. Retourne le prix actuel a la tonne du 
		 * contrat (contrat.getPrix()) si le vendeur est d'accord avec ce prix.
		 * Sinon, retourne une contreproposition de prix.
		 */
		 return 1;
	}

	@Override
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		/**
		 * Methode appelee par le SuperviseurVentesContratCadre afin de notifier le
		 * vendeur de la reussite des negociations sur le contrat precise en parametre
		 * qui a ete initie par l'acheteur.
		 * Le superviseur veillera a l'application de ce contrat (des appels a livrer(...) 
		 * seront effectues lorsque le vendeur devra livrer afin d'honorer le contrat, et
		 * des transferts d'argent auront lieur lorsque l'acheteur paiera les echeances prevues)..
		 * @param contrat
		 */
	}

	@Override
	public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
		/**
		 * Methode appelee par le SuperviseurVentesContratCadre lorsque le vendeur doit livrer 
		 * quantite tonnes de produit afin d'honorer le contrat precise en parametre. 
		 * @param produit
		 * @param quantite
		 * @param contrat
		 * @return Retourne la quantite livree. Une penalite est prevue si cette quantite
		 *  est inferieure a celle precisee en parametre
		 */
		return quantite;
	}

}
