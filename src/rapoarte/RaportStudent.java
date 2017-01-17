package rapoarte;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;

import Services.DisciplinaService;
import Services.PrezentaService;
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
import entity.Student;

/**
 *
 * @author Nameless ^_^
 */
public class RaportStudent {

	public static List getModuleSubgrupaStudent(int id) {
		List list = new ArrayList();
		Session session = null;

		try {
			session = Singleton.getInstance().getNewSession();

			String subgrupa = (String) session
					.createQuery(
							"select nume from Subgrupa where id=(select subgrupa from Student where id="
									+ id + ")").getResultList().get(0);
			//System.out.println("subgrupa este: " + subgrupa);
			String grupa = (String) session
					.createQuery(
							"select nume from Grupa where id=(select grupa from Subgrupa where id=(select subgrupa from Student where id=381))")
					.getResultList().get(0);
			//System.out.println("grupa este: " + grupa);
			

			Query query2 = session
					.createQuery("select distinct d.denumire from Disciplina d,Modul m where d.id=m.disciplina "
							+ "and participanti in('" + grupa + "', '"+subgrupa+"')");

			list = query2.list();
			//System.out.println(list.size());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return list;
	}

	public static void MakeSingleStudentReport(Student student) {

		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("Raport_Student"+student.getNume()+".pdf"));
			document.open();
			
			Paragraph header = new Paragraph("Situatia scolara");
			Paragraph footer = new Paragraph(RaportStudent.GenerateTime());
			
			header.setSpacingBefore(50);
			header.setSpacingAfter(50);
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);
			document.add(new Paragraph(""));

			

			document.add(new Paragraph("Student: " + student.getNume() + "\n"));
			document.add(new Paragraph("An universitar: " + student.getSubgrupa().getGrupa().getAnUniversitar().getAn() + "\n"));
			
			  PdfPTable table = new PdfPTable(2); // 3 columns.
			  table.setWidthPercentage(100); //Width 100%
			  table.setSpacingBefore(30f); //Space before table
			  table.setSpacingAfter(30f); //Space after table
			  
			  //Set Column widths 
			  float[] columnWidths = {1f, 1f};
			  table.setWidths(columnWidths);
			  
			  
			  
			  PdfPCell cell1 = new PdfPCell(new Paragraph("Disciplina" ));
			  cell1.setBorderColor(BaseColor.BLUE); 
			  cell1.setPaddingLeft(10);
			  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  
			  PdfPCell cell2 = new PdfPCell(new Paragraph("Absente/activitati*saptamani"));
			  cell2.setBorderColor(BaseColor.GREEN); 
			  cell2.setPaddingLeft(10);
			  cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			  cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  
			  
			  
			 /* PdfPCell cell3 = new PdfPCell(new Paragraph("Nr activitati"));
			  cell3.setBorderColor(BaseColor.RED); cell3.setPaddingLeft(10);
			  cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			  cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  */
			  //To avoid having the cell border and the content overlap, if you are having thick cell borders 
			  cell1.setUseBorderPadding(true);
			  cell2.setUseBorderPadding(true);
			  //cell3.setUseBorderPadding(true);
			  
			  table.addCell(cell1); 
			  table.addCell(cell2); 
			 // table.addCell(cell3);
			  
			  
			  List module = RaportStudent.getModuleSubgrupaStudent(student.getId().intValue());
			  
			  for(Object materie:module){
				  String disciplina = String.valueOf(materie);
				  PdfPCell cell11 = new PdfPCell(new Paragraph(disciplina));
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
				  table.addCell(cell21);
			  }
			  
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
