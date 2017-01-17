
package rapoarte;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import Services.AnUniversitarService;
import Services.DisciplinaService;
import Services.ModulService;
import Services.PrezentaService;
import Services.StudentService;
import Singleton.Singleton;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.orsonpdf.Page;

import entity.Disciplina;
import entity.Modul;
import entity.Prezenta;
import entity.Student;

/**
 *
 *@author Nameless ^_^ 
 */
public class RaportAnTables {
	
	public static int getNumberOfAbsencesForAStudentForModule(Student student,Disciplina disciplina){
		int absences = 0;

		if(student != null && disciplina != null){
			Session session = null;
			try{
				session = Singleton.getInstance().getNewSession();
				List<Modul> moduls = ModulService.getAllModulesForStudentAndDisciplina(student,disciplina);
				if(moduls.size()==0) return 0;
				else{
				DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
											.add(Restrictions.eq("student", student))
											.add(Restrictions.in("modul", moduls))
											.add(Restrictions.eq("prezent", new BigDecimal(0)));
				dc.setProjection(Projections.rowCount());
				Number result = (Number) dc.getExecutableCriteria(session).uniqueResult();
				absences = result.intValue();
				session.close();}
			}catch (Exception e) {
	            e.printStackTrace();        
	        }finally{
	        	session.close();
	        }

		}
		return absences;
	}
	
	public static List<Prezenta> getPrezentaStudentAndModul(Student student, Modul m){
		List<Prezenta> list = null;
		Session session = null;
		
		try{
			session = Singleton.getInstance().getNewSession();
			
			
			DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class).add(Restrictions.eq("modul", m))
					.add(Restrictions.eq("student", student)).addOrder(Order.asc("saptamana"));
			list =  dc.getExecutableCriteria(session).list();
			
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		
		return list;
		
	}
	
	public static List<Modul> getModuleStudent(int id_student,int id_disciplina) {
		List<Modul> list = new ArrayList<Modul>();
		Session session = null;

		try {
			session = Singleton.getInstance().getNewSession();
			list = ModulService.getAllModulesForStudentAndDisciplina(StudentService.getStudentByID(id_student), DisciplinaService.getDisciplinaByID(id_disciplina));
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return list;
	}

	public static void MakeYearReport(int year) {

		Document document = new Document(PageSize.A4);
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(new File("Tabel_an_"+year+".pdf")));
			
			
			//special font sizes
			   Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
			   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);
			document.open();
			

			Paragraph header = new Paragraph("Situatia scolara");
			Paragraph footer = new Paragraph(RaportAnTables.GenerateTime());
			
			header.setSpacingBefore(50);
			header.setSpacingAfter(50);
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);
			document.add(new Paragraph(""));

			
			Paragraph an = new Paragraph("An universitar: " + year + "\n");
			an.setSpacingAfter(15);
			an.setSpacingBefore(20);
			
			document.add(an);
			  
			List<Disciplina> discipline = DisciplinaService.getDisciplineByAn(year);
			
			
			PdfPTable table = new PdfPTable(discipline.size()+1); // 3 columns.
			table.setWidthPercentage(100); //Width 100%
			table.setSpacingBefore(30f); //Space before table
			table.setSpacingAfter(30f); //Space after table
			
			
			  //Set Column widths 
			if(year==4){
			  float[] columnWidths = {2f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f};
			  table.setWidths(columnWidths);			  
			}
			  
			  
			  PdfPCell cell1 = new PdfPCell(new Phrase("".trim(),bf12));
			  cell1.setBorderColor(BaseColor.BLUE); 
			  cell1.setPaddingLeft(10);
			  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  cell1.setUseBorderPadding(true);
			  table.addCell(cell1); 
			  
			  
				  
				  
				  for(Disciplina disciplina:discipline){
				  
				  PdfPCell cell2 = new PdfPCell(new Phrase(disciplina.getNumeScurt().trim(),bf12));
				  cell2.setBorderColor(BaseColor.BLUE); 
				  cell2.setPaddingLeft(10);
				  cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				  cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  cell2.setUseBorderPadding(true);
				  table.addCell(cell2); 
				  }
				  
				  
				  
				  for(Student student:StudentService.getAllStudentsByAn(AnUniversitarService.getAnByID(year))){
					  PdfPCell cell3 = new PdfPCell(new Phrase(student.getNume().trim(),bf12));
					  
					  
					  cell3.setBorderColor(BaseColor.BLUE); 
					  cell3.setPaddingLeft(10);
					  cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					  cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
					  cell3.setUseBorderPadding(true);
					  table.addCell(cell3);
					  
					  
		
					  
					  for(Disciplina disc:discipline){
						  PdfPCell cell4;
						  int absente= 0;
						 try{
						   absente = RaportAnTables.getNumberOfAbsencesForAStudentForModule(student,disc);
						 }
						 finally{
						
						  cell4 = new PdfPCell(new Paragraph(String.valueOf(absente)));
						  //PdfPCell cell21 = new PdfPCell(new Paragraph("****"));
						  cell4.setBorderColor(BaseColor.GREEN); 
						  cell4.setPaddingLeft(10);
						  cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						  cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						  table.addCell(cell4);}
						  
					  
					  
					  }
						  
					  }
					  
					  
				  
				  
				  
				  
				  /*List<Prezenta> prezente = RaportStudentDisciplina.getPrezentaStudentAndModul(student, modul);
				  System.out.println(":sddddd******"+prezente.size());
				  if(prezente.size()==0){
					  for(int i=0;i<14;i++){
					  PdfPCell cell1 = new PdfPCell(new Paragraph("0"));
					  cell1.setBorderColor(BaseColor.BLUE); 
					  cell1.setPaddingLeft(10);
					  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					  cell1.setUseBorderPadding(true);
					  table.addCell(cell1); 
					  }
				  }
				  else {
					  for(int i=0;i<prezente.size();i++){
					  PdfPCell cell1;
					  int prezenta = prezente.get(i).getPrezent().intValue();
					  cell1 = new PdfPCell(new Paragraph(Integer.toString(prezenta)));
					  cell1.setBorderColor(BaseColor.BLUE); 
					  cell1.setPaddingLeft(10);
					  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					  cell1.setUseBorderPadding(true);
					  table.addCell(cell1); 
					  }
					  for(int i=prezente.size()+1;i<=14;i++){
						  PdfPCell cell1 = new PdfPCell(new Paragraph("0"));
						  cell1.setBorderColor(BaseColor.BLUE); 
						  cell1.setPaddingLeft(10);
						  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						  cell1.setUseBorderPadding(true);
						  table.addCell(cell1);
					  }
				  }*/
				  
				 /* PdfPCell cell11 = new PdfPCell(new Paragraph(disciplina));
				  cell1.setBorderColor(BaseColor.BLUE); 
				  cell1.setPaddingLeft(10);
				  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  Disciplina disc = DisciplinaService.getDisciplinaByDenumire(materie.toString());
				  int absente = PrezentaService.getNumberOfAbsencesForAStudentForModule(student,disc);
				  PdfPCell cell21 = new PdfPCell(new Paragraph(String.valueOf(absente)));
				  //PdfPCell cell21 = new PdfPCell(new Paragraph("****"));
				  cell2.setBorderColor(BaseColor.GREEN); 
				  cell2.setPaddingLeft(10);
				  cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				  cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  table.addCell(cell11);
				  table.addCell(cell21);*/
				  document.add(table);
			  
			  
			  
			 
			footer.setSpacingAfter(15);
			footer.setSpacingBefore(35);
			document.add(footer);

			document.close();
			writer.close();

			System.out.println("Raport created");
		} catch (Exception e) {
			e.printStackTrace();
		}
}

	public static String GenerateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
		return format1.format(cal.getTime()) + "  "
				+ format2.format(cal.getTime());
	}
	
	
}
