
package rapoarte;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import Services.DisciplinaService;
import Services.ModulService;
import Services.StudentService;
import Singleton.Singleton;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import entity.Disciplina;
import entity.Modul;
import entity.Prezenta;
import entity.Student;

/**
 *
 *@author Nameless ^_^ 
 */

public class RaportStudentDisciplina {
	
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

	public static void MakeSingleStudentReport(Student student, Disciplina disc) {

		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(new File("Raport_Student_Disciplina.pdf")));
			document.open();

			Paragraph header = new Paragraph("Situatia scolara pentru studentul "+student.getNume()+" la disciplina "+disc.getDenumire());
			Paragraph footer = new Paragraph(RaportStudent.GenerateTime());
			
			header.setSpacingBefore(50);
			header.setSpacingAfter(50);
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);
			document.add(new Paragraph(""));


			Paragraph namestud = new Paragraph("Student: " + student.getNume() + "\n");
			Paragraph an = new Paragraph("An universitar: " + student.getSubgrupa().getGrupa().getAnUniversitar().getAn() + "\n");
			an.setSpacingAfter(15);
			an.setSpacingBefore(20);
			namestud.setSpacingBefore(35);
			document.add(namestud);
			document.add(an);
			  
			  
			  
			 
			  
			  List<Modul> module = RaportStudentDisciplina.getModuleStudent(student.getId().intValue(), disc.getId().intValue());
			  
			  for(Modul modul:module){
				  document.add(new Paragraph(modul.getDisciplina().getDenumire()));
				  document.add(new Paragraph(modul.getActivitate()));
				  
				  PdfPTable table = new PdfPTable(14); // 3 columns.
				  table.setWidthPercentage(100); //Width 100%
				  table.setSpacingBefore(30f); //Space before table
				  table.setSpacingAfter(30f); //Space after table
				  
				  //Set Column widths 
				  float[] columnWidths = {1f, 1f,1f, 1f,1f, 1f,1f, 1f,1f, 1f,1f, 1f,1f, 1f};
				  table.setWidths(columnWidths);
				  
				  for(int i=1;i<=14;i++){
				  
				  PdfPCell cell1 = new PdfPCell(new Paragraph("S"+i ));
				  cell1.setBorderColor(BaseColor.BLUE); 
				  cell1.setPaddingLeft(10);
				  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  cell1.setUseBorderPadding(true);
				  table.addCell(cell1); 
				  }
				  
				  List<Prezenta> prezente = RaportStudentDisciplina.getPrezentaStudentAndModul(student, modul);
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
				  }
				  
				 
				  document.add(table);
			  }
			  
			  
			 
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
