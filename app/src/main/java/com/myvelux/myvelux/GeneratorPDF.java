package com.myvelux.myvelux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class GeneratorPDF {

    public GeneratorPDF() {
    }

    public Boolean write(String fname, ArrayList<Commande> fcontent, Client client) {
        try {
            String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));

            // step 3
            document.open();

            Paragraph title = new Paragraph("AVR Fenetre de toits \n");
            title.setAlignment(Element.ALIGN_CENTER);

            /*Paragraph garantie = new Paragraph("Garantie décennale\n" +
                "Garantie 20 ans sur le vitrage et 10 ans pièces et main-d’oeuvre sur les fenêtres, 5 ans sur les\n" +
                "volets roulants, déplacement compris (voir conditions sur www.velux.fr)\n" +
                "S.A.V. : intervention gratuite du Service Après-Ventes VELUX\n"
            );*/

            /*Paragraph duree_paiement_delai = new Paragraph(
                    "Durée de validité du devis : 3 mois (sous réserve d’augmentation du tarif des\n" +
                    "marchandises)\n" +
                    "Paiement : acompte de 30 % au démarrage des travaux, le reste à réception de la facture\n" +
                    "Délai de réalisation : à convenir dès l’acceptation de ce devis\n"
            );*/

            document.add(title);
            document.add(new Paragraph("\n"));
            document.add(createTableInfos(client));
            //document.add(garantie);
            //document.add(duree_paiement_delai);
            document.add(new Paragraph("\n"));
            document.add(createTableVelux(fcontent));
            document.add(new Paragraph("\n"));
            document.add(creatTableRules());
            document.add(new Paragraph("\n"));
            document.add(createTableFooter());

            /*Image image =Image.getInstance("sdcard/Dutoit.png");
            image.setWidthPercentage(15);
            document.add(image);
            Image image2 =Image.getInstance("sdcard/icoVelux.png");
            image2.setWidthPercentage(15);
            document.add(image2);*/

            document.close();

            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }


    private PdfPTable createTableInfos(Client client) throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(new float[] { 2, 2});
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        String coord =  "Client : "+client.getFirstName() + " " + client.getLastName() + "\n" +
                "Adresse : " +client.getAddress() + "\n" +
                "Code postal : "+client.getPostalCode() + "\n" +
                "Ville : " + client.getCity() +"\n";
                if(!client.getMobile().equals("")){
                    coord += "Portable : " +client.getMobile() + "\n";
                }
                if(!client.getPhone().equals("")){
                    coord += "Tél : " +client.getPhone() + "\n";
                }

        PdfPCell coordClient = new PdfPCell(new Phrase(10f, coord, normalFont));

        coordClient.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell coordEntrep = new PdfPCell(new Phrase(10f,
                "8 rue NEUVE \n 77410 VILLEROY \n Tél1 : 01 60 01 93 59\n Tél2 : 09 72 95 80 96\n"+
                        "Portable : 06 29 62 89 70\n Fax : 09 70 06 12 71\n"+
                        "Mail : avr.renovation@wanadoo.fr\n Site : http://avr-fenetres-de-toits77.fr/",
                normalFont));

        coordEntrep.setHorizontalAlignment(Element.ALIGN_RIGHT);
        coordClient.setBorder(Rectangle.NO_BORDER);
        coordEntrep.setBorder(Rectangle.NO_BORDER);

        table.addCell(coordClient);
        table.addCell(coordEntrep);

        return table;

    }

    private PdfPTable createTableVelux(ArrayList<Commande> fcontent) throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
        float subTotal = calculSubTotal(fcontent, false);
        float subTotalAction = calculSubTotal(fcontent,true);
        float subTotalTVA5 = calculTVA(subTotal, 5.5f);
        float subTotalTVA10 = calculTVA(subTotal,10.00f);

        DecimalFormat df = new DecimalFormat("#.##");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, (float) 0.5, (float) 0.5});
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        String[] listHeader = {"Description", "Référence", "Quantité", "Prix HT"};

        createHeader(table,listHeader, normalFont);
        //table.setHeaderRows(1);
        for (int i=0; i<fcontent.size();i++){
            String produit = fcontent.get(i).getAction() + " dans " +
                    fcontent.get(i).getRoom() + " \n\n "+
                    fcontent.get(i).getRange()+ " / "+
                    fcontent.get(i).getType()+ " / "+
                    fcontent.get(i).getVersion()+ " / "+
                    fcontent.get(i).getSize()+ "\n\n"+
                    fcontent.get(i).getFitting()+"\n";

            table.addCell(new Phrase(10f,produit, normalFont));
            table.addCell(new Phrase(10f,
                    fcontent.get(i).getRefArticle() + "\n\n"+
                    fcontent.get(i).getRefFitting()
                    , normalFont));
            table.addCell(new Phrase(10f,"1", normalFont));
            table.addCell(new Phrase(10f,
                    fcontent.get(i).getActionPrice()+"\n\n"+
                    fcontent.get(i).getPrixHTVelux()+"\n\n"+
                    fcontent.get(i).getPrixHTFitting(),
                    normalFont));

        }
        PdfPCell cellEmpty = new PdfPCell();
        PdfPCell cellSubTotalLabel = new PdfPCell(new Phrase(10f, "Sous-total", normalFont));
        PdfPCell cellSubTotal = new PdfPCell(new Phrase(10f,String.valueOf(df.format(subTotalAction)), normalFont));
        PdfPCell cellTransportLabel = new PdfPCell(new Phrase(10f,"Transport", normalFont));
        PdfPCell cellTransport = new PdfPCell(new Phrase(10f,"", normalFont));
        PdfPCell cellTVALabel = new PdfPCell(new Phrase(10f,"Taux de T.V.A", normalFont));
        PdfPCell cellTVA5Label = new PdfPCell(new Phrase(10f,"5.5 %", normalFont));
        PdfPCell cellTVA5 = new PdfPCell(new Phrase(10f,String.valueOf(df.format(subTotalTVA5)), normalFont));
        PdfPCell cellTVA10Label = new PdfPCell(new Phrase(10f,"10.00 %", normalFont));
        PdfPCell cellTVA10 = new PdfPCell(new Phrase(10f,String.valueOf(df.format(subTotalTVA10)), normalFont));
        PdfPCell cellTotalTTCLabel = new PdfPCell(new Phrase(10f, "Total T.T.C", normalFont));
        PdfPCell cellTotalTTC = new PdfPCell(new Phrase(10f,String.valueOf(calculTotalTTC(subTotalAction, subTotalTVA5, subTotalTVA10)), normalFont));

        cellEmpty.setBorder(Rectangle.NO_BORDER);

        cellSubTotalLabel.setBorder(Rectangle.NO_BORDER);
        cellSubTotalLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSubTotal.setBorder(Rectangle.BOX);
        cellSubTotal.setHorizontalAlignment(Element.ALIGN_CENTER);

        cellTransportLabel.setBorder(Rectangle.NO_BORDER);
        cellTransportLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTransport.setBorder(Rectangle.BOX);
        cellTransport.setHorizontalAlignment(Element.ALIGN_CENTER);

        cellTVALabel.setBorder(Rectangle.NO_BORDER);
        cellTVALabel.setHorizontalAlignment(Element.ALIGN_RIGHT);

        cellTVA5Label.setBorder(Rectangle.BOX);
        cellTVA5Label.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTVA5.setBorder(Rectangle.BOX);
        cellTVA5.setHorizontalAlignment(Element.ALIGN_CENTER);

        cellTVA10Label.setBorder(Rectangle.BOX);
        cellTVA10Label.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTVA10.setBorder(Rectangle.BOX);
        cellTVA10.setHorizontalAlignment(Element.ALIGN_CENTER);

        cellTotalTTCLabel.setBorder(Rectangle.NO_BORDER);
        cellTotalTTCLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTotalTTC.setBorder(Rectangle.BOX);
        cellTotalTTC.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cellEmpty);
        table.addCell(cellEmpty);
        table.addCell(cellSubTotalLabel);
        table.addCell(cellSubTotal);
        table.addCell(cellEmpty);
        table.addCell(cellEmpty);
        table.addCell(cellTransportLabel);
        table.addCell(cellTransport);
        table.addCell(cellEmpty);
        table.addCell(cellTVALabel);
        table.addCell(cellTVA5Label);
        table.addCell(cellTVA5);
        table.addCell(cellEmpty);
        table.addCell(cellEmpty);
        table.addCell(cellTVA10Label);
        table.addCell(cellTVA10);
        table.addCell(cellEmpty);
        table.addCell(cellEmpty);
        table.addCell(cellTotalTTCLabel);
        table.addCell(cellTotalTTC);

        return table;

    }

    private float calculSubTotal(ArrayList<Commande> fcontent, boolean action){
        float calcul=0;
        if(action) {
            for (int i = 0; i < fcontent.size(); i++) {
                calcul += Float.parseFloat(fcontent.get(i).getActionPrice())
                        + Float.parseFloat(fcontent.get(i).getPrixHTVelux())
                        + Float.parseFloat(fcontent.get(i).getPrixHTFitting());
            }
        }else{
            for (int i=0; i<fcontent.size();i++){
                calcul+=Float.parseFloat(fcontent.get(i).getPrixHTVelux())
                        +Float.parseFloat(fcontent.get(i).getPrixHTFitting());
            }
        }

        return calcul;

    }

    private float calculTVA(float subTotal,float tva){
        float taxes = (subTotal*(tva/100));
        return taxes;
    }


    private float calculTotalTTC(float subTotal, float tva5, float tva10){
       float ttc = subTotal + tva5 + tva10;
        return ttc;

    }

    private PdfPTable creatTableRules() throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd / MM / yyyy");
        String localTime = date.format(currentLocalTime);

        PdfPTable table = new PdfPTable(new float[] {(float) 1.5, (float) 1.5, (float) 1.5});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setHeaderRows(1);

        String[] listHeader = {"Règlements et conditions","Date : "+ localTime};
        PdfPCell headerOne = new PdfPCell(new Phrase(10f,listHeader[0], normalFont));
        PdfPCell headerTwo = new PdfPCell();
        PdfPCell headerThree = new PdfPCell(new Phrase(10f,listHeader[1], normalFont));

        headerTwo.setBorder(Rectangle.NO_BORDER);

        table.addCell(headerOne);
        table.addCell(headerTwo);
        table.addCell(headerThree);

        PdfPCell cellOne = new PdfPCell(new Phrase(10f," Chèque \n Acompte 30% à la commande \n Solde fin de chantier", normalFont));
        PdfPCell cellTwo = new PdfPCell();
        PdfPCell cellThree = new PdfPCell(new Phrase("Signature : ", normalFont));

        cellOne.setBorder(Rectangle.BOX);
        cellTwo.setBorder(Rectangle.NO_BORDER);
        cellThree.setBorder(Rectangle.BOX);

        table.addCell(cellOne);
        table.addCell(cellTwo);
        table.addCell(cellThree);

        return table;
    }

    private PdfPTable createTableFooter() throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.setFooterRows(1);

        PdfPCell cellOne = new PdfPCell(new Phrase(10f," SARL AVR au capital de 8000 €, SIRET n° 48842670100022, APE 4120A", normalFont));

        cellOne.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellOne);

        return table;
    }


    private static void createHeader(PdfPTable table, String[] listHeader, Font font){
        PdfPCell c1;

        for (int j = 0;j<listHeader.length;j++) {
            c1 = new PdfPCell(new Phrase(listHeader[j], font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

    }
}
