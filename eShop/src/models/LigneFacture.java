/*package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.SQLException;

public class LigneFacture {
        
        public int idLFacture ;
        public Produit leProduit;
	private int qteVendu = 1;
	private float prixVendu = 0;
        private int idFac;
        
        public LigneFacture(int idL, Produit prod, int idFac,float prix, int qteVendu){
             idLFacture = idL;
             leProduit = prod;
             this.idFac = idFac;
             this.qteVendu = qteVendu;
             prixVendu = prix;
        }
	
	public int getidLFacture() {
		return this.idLFacture;
	}

	public void setidLFacture(int idLFacture) {
                this.idLFacture = idLFacture;
	}
        
	public int getQteVendu() {
		return this.qteVendu;
	}

	public void setQteVendu(int qteVendu) {
                this.qteVendu = qteVendu;
	}

	public float getPrixVendu() {
		return this.prixVendu;
	}


	public void setPrixVendu(float prixVendu) {
		this.prixVendu = prixVendu;
	}
        
	public StringProperty getquantiteproperty(){
            return new SimpleStringProperty(""+qteVendu); }
        public StringProperty getprixproperty(){
               return new SimpleStringProperty(""+prixVendu);
        }
        
        public StringProperty gettotalproperty(){
               return new SimpleStringProperty(""+(qteVendu*prixVendu));
        }
        
        public StringProperty getnomProproperty(){
               return new SimpleStringProperty(leProduit.getNomPro());
        }
       
       public static void save_lignefacture(Facture fac,LigneFacture elt) throws SQLException{
       String[] attribut = {"idLFac","codePro","idFac","prix","qte"};
       Object[] valeur = {elt.getidLFacture(),elt.leProduit.getCodePro(),fac.getIdFacture(),elt.leProduit.getPrix(),elt.getQteVendu()};
       connexion.queryInsert("lignefacture",attribut,valeur);
       }
       
       public void update() {
           
        }
       
       public void rechercher() {
           
       }
 
  
           
}*/