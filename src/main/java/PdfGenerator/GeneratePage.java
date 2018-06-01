package PdfGenerator;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePage {
	Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
	Paragraph preface = new Paragraph();
	public Document main(){
		RetrieveData data = new RetrieveData();
		Document document = new Document();
		
		try{
			
			PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Douwe/Downloads"));
			 
			document.open();
			
			addTitlePage(document);
			//Chunk chunkAdres = new Chunk("adresinfo", font);
			//Chunk chunkUser = new Chunk("userinfo", font);
			//Chunk chunkLoan = new Chunk("loaninfo", font);
			
			//document.add(new Paragraph(data.getAdressData().getCountry()));
			//document.add(new Paragraph(data.getAdressData().getDescription()));
			//document.add(new Paragraph(data.getAdressData().getLocation()));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("userinfo"));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("loaninfo"));
			document.add(Chunk.NEWLINE);
			//document.add(chunkUser);
			//document.add(chunkLoan);
			document.close();
			return document;
		}
		catch(Exception e){
			System.out.println(e);
			return document;
		}
	}

	public void addTitlePage(Document document) 
		 throws DocumentException {
		        
		        // We add one empty line
		        addEmptyLine(preface, 1);
		        // Lets write a big header
		        preface.add(new Paragraph("Contract", font));

		        addEmptyLine(preface, 1);
		        // Will create: Report generated by: _name, _date
		        preface.add(new Paragraph(
		                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		                font));
		        addEmptyLine(preface, 3);
		        preface.add(new Paragraph(
		                "This document describes something which is very important ",
		                font));

		        addEmptyLine(preface, 8);

		        preface.add(new Paragraph(
		                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
		                font));

		        document.add(preface);
		        // Start a new page
		        document.newPage();
		
	}

	public void addEmptyLine(Paragraph paragraph, int number) {
		 for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
		
	}
}