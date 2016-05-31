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
            Log.i("test : ",fname);
            String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            // step 3
            document.open();

            document.add(new Paragraph("Client : " +
                    fcontent.getClient().getFirstName() +
                    " " + fcontent.getClient().getLastName()));

            document.add(new Paragraph(" "));

            document.add(createTable(fcontent));
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

    private PdfPTable createTable(Reservation fcontent)
            throws BadElementException {

        PdfPTable table = new PdfPTable(7);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        String[] listHeader = {"Pièce","Main d'oeuvre","Gamme","Matériaux","Version","Taille","Raccord"};
        createHeader(table,listHeader);
        table.setHeaderRows(1);

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

    private static void createHeader(PdfPTable table, String[] listHeader){
        PdfPCell c1;

        for (int j = 0;j<listHeader.length;j++) {
            c1 = new PdfPCell(new Phrase(listHeader[j]));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

    }
}
