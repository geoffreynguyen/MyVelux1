package com.myvelux.myvelux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
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

    public Boolean write(String fname, Reservation fcontent) {
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

            Paragraph title = new Paragraph("DUTOIT Couverture SARL\n");

            Paragraph coordClient = new Paragraph(fcontent.getClient().getFirstName() + " " + fcontent.getClient().getLastName() + "\n");
            coordClient.add(fcontent.getClient().getAddress() + "\n");
            coordClient.add(fcontent.getClient().getPostalCode() + " " + fcontent.getClient().getCity() + " OK\n");

            Paragraph coordEntrep = new Paragraph("1, rue de la réussite\n");
            coordEntrep.add("91000 MORANGIS\n");
            coordEntrep.add("Tél : 01.25.36.89.74 12\n");
            coordEntrep.add("Mail : contact@dutoit.fr\n");
            coordEntrep.add("Site : www.dutoit.fr\n");

            Paragraph garantie = new Paragraph("Garantie décennale\n" +
                "Garantie 20 ans sur le vitrage et 10 ans pièces et main-d’oeuvre sur les fenêtres, 5 ans sur les\n" +
                "volets roulants, déplacement compris (voir conditions sur www.velux.fr)\n" +
                "S.A.V. : intervention gratuite du Service Après-Ventes VELUX\n"
            );

            Paragraph duree_paiement_delai = new Paragraph(
                    "Durée de validité du devis : 3 mois (sous réserve d’augmentation du tarif des\n" +
                    "marchandises)\n" +
                    "Paiement : acompte de 30 % au démarrage des travaux, le reste à réception de la facture\n" +
                    "Délai de réalisation : à convenir dès l’acceptation de ce devis\n"
            );

            document.add(title);
           document.add(coordClient);
            document.add(coordEntrep);
            document.add(garantie);
            document.add(duree_paiement_delai);

            document.add(createTable(fcontent));

            document.add(new Paragraph("\n"));
            document.add(createTvaTable());
            document.add(new Paragraph("\n"));
            document.add(createTvaRecap());
            document.add(new Paragraph("\n"));
            document.add(createTableSignature());

            /*Image image =Image.getInstance("sdcard/Dutoit.png");
            image.setWidthPercentage(15);
            document.add(image);
            Image image2 =Image.getInstance("sdcard/icoVelux.png");
            image2.setWidthPercentage(15);
            document.add(image2);*/

            // step 5
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

    public String read(String fname) {
        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/" + fname + ".pdf";

            PdfReader reader = new PdfReader(new FileInputStream(fpath));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            StringWriter strW = new StringWriter();

            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i,
                        new SimpleTextExtractionStrategy());

                strW.write(strategy.getResultantText());

            }

            response = strW.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private PdfPTable createTable(Reservation fcontent) throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(7);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        String[] listHeader = {"Pièce","Main d'oeuvre","Gamme","Matériaux","Version","Taille","Raccord"};

        createHeader(table,listHeader, normalFont);
        table.setHeaderRows(1);
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        Log.d("tabeSize",""+fcontent.getCommandes().size());
        for (int i=0; i<fcontent.getCommandes().size();i++){
            table.addCell(fcontent.getCommandes().get(i).getRoom());
            table.addCell(fcontent.getCommandes().get(i).getAction()+" "+
                    fcontent.getCommandes().get(i).getActionPrice()+" €");
            table.addCell(fcontent.getCommandes().get(i).getRange());
            table.addCell(fcontent.getCommandes().get(i).getType());
            table.addCell(fcontent.getCommandes().get(i).getVersion());
            table.addCell(fcontent.getCommandes().get(i).getSize());
            table.addCell(fcontent.getCommandes().get(i).getFitting());
        }

        return table;

    }

    private PdfPTable createTableCommande(Reservation fcontent) throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(7);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        String[] listHeader = {"Désignation" , "Qté" , "PU" , "HT" , "Montant" , "H.T." , "TVA"};
        createHeader(table,listHeader, normalFont);
        table.setHeaderRows(1);

        PdfPCell cell = new PdfPCell( new Phrase("Chambre parentale – Version motorisée"));
        cell. setColspan(7) ;
        table.addCell(cell);

        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");
        table.addCell("hello world");

        Log.d("tabeSize",""+fcontent.getCommandes().size());
        for (int i=0; i<fcontent.getCommandes().size();i++){
            table.addCell(fcontent.getCommandes().get(i).getRoom());
           table.addCell(fcontent.getCommandes().get(i).getAction() + " " + fcontent.getCommandes().get(i).getActionPrice()+" €");
            table.addCell(fcontent.getCommandes().get(i).getRange());
            table.addCell(fcontent.getCommandes().get(i).getType());
            table.addCell(fcontent.getCommandes().get(i).getVersion());
            table.addCell(fcontent.getCommandes().get(i).getSize());
            table.addCell(fcontent.getCommandes().get(i).getFitting());
        }

        return table;

    }

    private PdfPTable createTvaTable() throws BadElementException {

        double baseHt5 = 1554;
        double baseTva5 = 85.47;
        double baseHt10 = 754;
        double baseTva10 = 75.40;
        double baseHt20 = 0;
        double baseTva20 = 0;

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(35);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        String[] listHeader = {"Taux de TVA applicable", "Base HT","Montant TVA"};

        createHeader(table,listHeader, normalFont);
        table.setHeaderRows(1);


        table.addCell(new Phrase("5,5 % (1)", normalFont ));
        table.addCell(new Phrase(baseHt5 + " €", normalFont ));
        table.addCell(new Phrase(baseTva5 + " €", normalFont ));

        table.addCell(new Phrase("10 % (2)", normalFont ));
        table.addCell(new Phrase(baseHt10 + " €", normalFont ));
        table.addCell(new Phrase(baseTva10 + " €", normalFont ));

        table.addCell(new Phrase("20 % (3)", normalFont ));
        table.addCell(new Phrase(baseHt20 + " €", normalFont ));
        table.addCell(new Phrase(baseTva20 + " €", normalFont ));

        return table;
    }

    private PdfPTable createTvaRecap() throws BadElementException {

        double totalTva = 160.87;
        double totalTtc = 2468.87;

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(30);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.setHeaderRows(1);

        table.addCell(new Phrase("Total TVA", normalFont ));
        table.addCell(new Phrase(totalTva+" €", normalFont ));

        table.addCell(new Phrase("TOTAL TTC", boldFont ));
        table.addCell(new Phrase(totalTtc+" €", boldFont ));

        return table;
    }

    private PdfPTable createTableSignature() throws BadElementException {

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(25);

        PdfPCell dateCell = new PdfPCell(new Phrase("Date", normalFont));
        dateCell.setFixedHeight(20);

        table.addCell(dateCell);

        PdfPCell signCell = new PdfPCell(new Phrase("Signature", normalFont));
        signCell.setFixedHeight(30);
        table.addCell(signCell);

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
