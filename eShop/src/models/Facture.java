/*package models;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Facture {

	private int idFacture = 0;
	private String dateFac = "22-11-2017";
	private float remise = 0;
	private float montant = 0;
	private String tel = "695463868";
	private int typeFac = 0;
	public Gestionnaire laCaissiere = new Gestionnaire();
	public ArrayList<LigneFacture> listeFacture;
        
        public Facture(int idFacture, String date, float remise, float montant, String tel, int typeFac, Gestionnaire gest){
            this.idFacture = idFacture;
            dateFac = date;
            this.remise = remise;
            this.montant = montant;
            this.tel = tel;
            this.typeFac = typeFac;
            laCaissiere = gest;
        }
	
        public String gettel() {
		return this.tel;
	}
        
        public void settel(String tel){
            this.tel = tel;
        }
        
	public int getIdFacture() {
		return this.idFacture;
	}

	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
	}

	public String getDateFac() {
		return this.dateFac;
	}

	public void setDateFac(String dateFac) {
		this.dateFac = dateFac;
	}

	public int getTypeFac() {
		return this.typeFac;
	}

	public void setTypeFac(int typeFac) {
		this.typeFac = typeFac;
	}

	public float getRemise() {
		return this.remise;
	}

	public void setRemise(int remise) {
		this.remise = remise;
	}

	public float getMontant() {
		return this.montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}
        
        public StringProperty getdateFacproperty(){
               return new SimpleStringProperty(dateFac);
        }
        
        public StringProperty getremiseproperty(){
               return new SimpleStringProperty(""+remise);
        }
        
        public StringProperty getmontantproperty(){
               return new SimpleStringProperty(""+montant);
        }
        
        public StringProperty getNomCaproperty(){
               return new SimpleStringProperty(laCaissiere.getNomGest());
        }
        
        
        
public static boolean check_facture(int idFac) throws SQLException
{     boolean bol = false;
    try{
        ResultSet rs = connexion.querySelectAll("facture");
        
        
        while(rs.next())
        {
           if(rs.getInt(1)==idFac){
               bol = true;
               break;
           }
        }

    }catch(Exception e){
            e.printStackTrace();
    }
    
     return bol;
}


public static boolean check_lignefacture(Facture fac, String nomPro) throws SQLException
{     boolean bol = false;
    try{
           for(int i = 0 ; i < fac.listeFacture.size(); i++){
                if(fac.listeFacture.get(i).leProduit.getNomPro().equals(nomPro)){
                    bol = true;
                    break;
                }
           }

    }catch(Exception e){
            e.printStackTrace();
    }
    
     return bol;
}

        public static void update_montantfacture(Facture fac) throws SQLException
{ 
        fac.setMontant(0);

        for(int i = 0; i < fac.listeFacture.size(); i++)
        {
            fac.setMontant(fac.getMontant()+fac.listeFacture.get(i).getQteVendu()*fac.listeFacture.get(i).leProduit.getPrix());   
        }
}
        

       
       public static void save_facture(Facture fac) throws SQLException{
       
       for(int i = 0; i < fac.listeFacture.size(); i++){
            LigneFacture.save_lignefacture(fac,fac.listeFacture.get(i));
       }
                         
                         Date date = new Date();
                         DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                         fac.setDateFac(dateformat.format(date));
                         fac.setMontant((float)(1 - 0.01*fac.getRemise())*fac.getMontant());
                         String[] col = {"quantite"};
                         String[] con = {""};
                         for(int i = 0; i < fac.listeFacture.size(); i++){
                            con[0] = ""+fac.listeFacture.get(i).leProduit.getQuantite();
                            connexion.queryUpdate("produit",col,con, "codePro = '"+fac.listeFacture.get(i).leProduit.getCodePro()+"'");
                         }
                         
       String[] attribut = {"idFac","dateFac","remise","montant","tel","typeFac","idCaissière",};
       Object[] valeur = {fac.getIdFacture(),fac.getDateFac(),fac.getRemise(),fac.getMontant(),fac.gettel(),fac.getTypeFac(),fac.laCaissiere.getIdGest()};
       connexion.queryInsert("facture",attribut,valeur);
       }
       
       public static void print_facture(Facture fac) throws SQLException, IOException{
                                      
                         Document doc = new Document();
                         
                         try{
                             PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\LONLA GATIEN JORDAN\\Desktop\\"+fac.getIdFacture()+".pdf"));
                             PdfPTable table = new PdfPTable(4);
                             PdfPCell cell ;
                             table.setWidthPercentage(100);
                             
                             doc.open();
                             
                             doc.add(new Paragraph("Facture : "+fac.getIdFacture()));
                             doc.add(new Paragraph("Fait le : "+fac.getDateFac()));
                             doc.add(new Paragraph("\n\n"));
                             
                             cell = new PdfPCell(new Phrase("QteVendu",FontFactory.getFont("Comic Sans MS",12)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cell.setBackgroundColor(BaseColor.GRAY);
                             table.addCell(cell);

                             cell = new PdfPCell(new Phrase("prixUnitaire",FontFactory.getFont("Comic Sans MS",12)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cell.setBackgroundColor(BaseColor.GRAY);
                             table.addCell(cell);

                             cell = new PdfPCell(new Phrase("Produit",FontFactory.getFont("Comic Sans MS",12)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cell.setBackgroundColor(BaseColor.GRAY);
                             table.addCell(cell);

                             cell = new PdfPCell(new Phrase("Totaux",FontFactory.getFont("Comic Sans MS",12)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             cell.setBackgroundColor(BaseColor.GRAY);
                             table.addCell(cell);
                             
                             float montant = 0;
                             for(int i = 0; i < fac.listeFacture.size(); i++){
                             
                             cell = new PdfPCell(new Phrase(""+fac.listeFacture.get(i).getQteVendu(),FontFactory.getFont("Arial",11)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             table.addCell(cell);

                             cell = new PdfPCell(new Phrase(""+fac.listeFacture.get(i).getPrixVendu(),FontFactory.getFont("Arial",11)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             table.addCell(cell);

                             cell = new PdfPCell(new Phrase(""+fac.listeFacture.get(i).leProduit.getNomPro(),FontFactory.getFont("Arial",11)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             table.addCell(cell);

                             cell = new PdfPCell(new Phrase(""+(fac.listeFacture.get(i).getQteVendu()*fac.listeFacture.get(i).getPrixVendu()),FontFactory.getFont("Arial",11)));
                             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                             table.addCell(cell);
                             
                             montant += fac.listeFacture.get(i).getQteVendu()*fac.listeFacture.get(i).getPrixVendu();
                             }
                             
                             doc.add(table);
                        doc.add(new Paragraph("\nMontant Total : "+montant+" FCFA"));
                        doc.add(new Paragraph("Remise : "+fac.getRemise()*100+" %"));
                        doc.add(new Paragraph("Net à Payer : "+fac.getMontant()+" FCFA"));
                             doc.close();
                             Desktop.getDesktop().open(new File("C:\\Users\\LONLA GATIEN JORDAN\\Desktop\\"+fac.getIdFacture()+".pdf"));
                         }catch(FileNotFoundException e){
                              e.printStackTrace();
                         }catch(DocumentException e){
                             e.printStackTrace();
                         } 
                             
       }
       
public static void load_facture() throws SQLException
{ 
    try{
        ResultSet rs = connexion.executionQuery("SELECT * FROM facture ORDER BY dateFac");
        int compteur = 0;
        
        System.out.println("+-----------+----------------+------------------+-------------------+----------------+-------------+--------------------+");
        System.out.println("|   idFac   |     dateFac    |      remise      |    montantReel    |      tel       |   typeFac   |     idCaissière    |");
        System.out.println("+-----------+----------------+------------------+-------------------+----------------+-------------+--------------------+");
    
        while(rs.next())
        {
            compteur++;
            System.out.printf("|   %5d   |   %10s   |       %2.2f       |      %10.2f      |   %10s      |    %2d       |   %5d      |\n",rs.getInt(1),rs.getString(2),rs.getFloat(3),rs.getFloat(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
            
        }
           if(compteur==0){
        System.out.println("\n                                         <Aucune facture >");
           }
        System.out.println("+-----------+----------------+------------------+------------------+----------------+-------------+--------------------+");
    }catch(Exception e){
            e.printStackTrace();
    }
     
}
        
    public static ResultSet search_facture(String cri, String rech) throws SQLException {
        return connexion.querySelectAll("facture",cri+" LIKE '%"+rech+"%'");
}
    
    public static void load_elementfacture(Facture fac) throws SQLException
     {  

        ResultSet rs = connexion.querySelectAll("lignefacture","idFac = '"+fac.getIdFacture()+"'");
        fac.listeFacture = new ArrayList<LigneFacture>();
    
        while(rs.next())
        {   
            Produit prod = new Produit();
            prod.setCodePro(rs.getInt(2));
            ResultSet rs1 = connexion.querySelectAll("produit","codePro = '"+rs.getInt(2)+"'");
            while(rs1.next()){
                prod.setNomPro(rs1.getString(2));
            }
            fac.listeFacture.add(new LigneFacture(rs.getInt(1),prod,rs.getInt(3),rs.getFloat(4),rs.getInt(5)));    
        }
     }
   
}*/