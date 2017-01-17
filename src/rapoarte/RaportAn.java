package rapoarte;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

import Services.DisciplinaService;
import Services.ModulService;
import Services.PrezentaService;
import Services.SaptamanaService;
import Services.StudentService;
import Singleton.Singleton;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import entity.Disciplina;
import entity.Modul;
import entity.Prezenta;
import entity.Profesor;
import entity.Saptamana;
import entity.Student;

/**
 *
 * @author Nameless ^_^
 */
public class RaportAn {

	public static List<Modul> getAllModulByDisciplina(Disciplina disciplina) {
		List<Modul> list = null;
		Session session = null;
		try {
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery(
					("from Modul where ID_DISCIPLINA = " + disciplina.getId()))
					.getResultList();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
	
	public static int getNumberOfAbsencesForAStudentForModule(Student student,Disciplina disciplina){
		int absences = 0;

		if(student != null && disciplina != null){
			Session session = null;
			try{
				session = Singleton.getInstance().getNewSession();
				List<Modul> moduls = ModulService.getAllModulesForStudentAndDisciplina(student,disciplina);
				
				DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
											.add(Restrictions.eq("student", student))
											.add(Restrictions.eq("modul", moduls))
											.add(Restrictions.eq("prezent", new BigDecimal(0)));
				dc.setProjection(Projections.rowCount());
				Number result = (Number) dc.getExecutableCriteria(session).uniqueResult();
				absences = result.intValue();
				session.close();
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
	
	public static List<Integer> getPrezenteStModuls(Modul modul){
		List<Integer> list = new ArrayList<Integer>() ;
		for(Student student:StudentService.getStudentiFromParticipant(modul.getParticipanti())){
			List<Prezenta> lista = RaportAn.getPrezentaStudentAndModul(student, modul);
			for(int i=0;i<lista.size();i++){
				int x= (int) list.get(i)+lista.get(i).getPrezent().intValue();
				list.add(i, x);
			}
		}
		
		return list;
	}
	

	public static int countPrezente(int an,Disciplina d){
		Session session = null;
		int total=0;
		try {
			session = Singleton.getInstance().getNewSession();
			//Query q =  session.createQuery("select count(p) from Prezenta p where prezent=1 and modul in (select m from Modul m where disciplina in(select d from Disciplina d where an=4))");
			//total = q.getFirstResult();
			
			
				for (Modul modul : RaportAn.getAllModulByDisciplina(d)) {
					DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
							.add(Restrictions.eq("modul", modul))
							.add(Restrictions.eq("prezent", new BigDecimal(1)));
					dc.setProjection(Projections.rowCount());
					Number result = (Number) dc.getExecutableCriteria(session).uniqueResult();
					total += result.intValue();
				}

			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		
		return total;
		

	}
	public static int countAbsenteAn(int an,Disciplina d){
		Session session = null;
		int total=0;
		try {
			session = Singleton.getInstance().getNewSession();
			//Query q =  session.createQuery("select count(p) from Prezenta p where prezent=1 and modul in (select m from Modul m where disciplina in(select d from Disciplina d where an=4))");
			//total = q.getFirstResult();
			
			
				for (Modul modul : RaportAn.getAllModulByDisciplina(d)) {
					DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
							.add(Restrictions.eq("modul", modul))
							.add(Restrictions.eq("prezent", new BigDecimal(0)));
					dc.setProjection(Projections.rowCount());
					Number result = (Number) dc.getExecutableCriteria(session).uniqueResult();
					total += result.intValue();
				}

			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		
		return total;
		

	}


	public static JFreeChart PieChart(String title,int an,Disciplina d) {
		int absente = RaportAn.countAbsenteAn(an,d);
		int prezente = RaportAn.countPrezente(an,d);
		JFreeChart chart;
		
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("Absente", absente);
		dataSet.setValue("Prezente", prezente);
		
		chart = ChartFactory.createPieChart3D(title, dataSet, false,
				false, false);
		
		return chart;
	}

	public static void MakeYearReport(int an) {

		int width = 280;
		int height = 260;
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("Raport_An_"+an+".pdf"));
			document.open();
			for (Disciplina disciplina : DisciplinaService
					.getDisciplineByAn(an)) {
				JFreeChart chart = PieChart(disciplina.getDenumire(),an,disciplina);
				
				PiePlot3D P = (PiePlot3D) chart.getPlot();
				DefaultPieDataset data = (DefaultPieDataset) P.getDataset();
				Paragraph d = new Paragraph(disciplina.getDenumire());
				
				
				
				d.setSpacingAfter(10);
				d.setSpacingBefore(10);
				document.add(d);
				
				
				
				for (int i = 0; i < data.getItemCount(); i++) {
					
					document.add(new Paragraph(data.getKey(i).toString()
							+ "\t\t             " + (int)Double.parseDouble(data.getValue(i).toString())));
				}
				
				
				
				
				PdfContentByte contentByte = writer.getDirectContent();

				PdfTemplate template = contentByte
						.createTemplate(width, height);
				Graphics2D graphics2d = template.createGraphics(width, height,
						new DefaultFontMapper());
				Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
						height);

				chart.draw(graphics2d, rectangle2d);

				graphics2d.dispose();
				Image img = Image.getInstance(template);
				img.setSpacingAfter(10);
				img.setSpacingBefore(10);
				
				document.add(img);

			}
			
			/*
			 * Paragraph header = new Paragraph("Situatia scolara"); Paragraph
			 * footer = new Paragraph(RaportStudent.GenerateTime());
			 * 
			 * header.setSpacingBefore(50); header.setSpacingAfter(50);
			 * header.setAlignment(Element.ALIGN_CENTER); document.add(header);
			 * document.add(new Paragraph(""));
			 * 
			 * Student student = StudentService.getStudentByID(380);
			 * 
			 * document.add(new Paragraph("Student: " + student.getNume() +
			 * "\n")); document.add(new Paragraph("An universitar: " +
			 * student.getSubgrupa().getGrupa().getAnUniversitar().getAn() +
			 * "\n"));
			 * 
			 * PdfPTable table = new PdfPTable(2); // 3 columns.
			 * table.setWidthPercentage(100); //Width 100%
			 * table.setSpacingBefore(30f); //Space before table
			 * table.setSpacingAfter(30f); //Space after table
			 * 
			 * //Set Column widths float[] columnWidths = {1f, 1f};
			 * table.setWidths(columnWidths);
			 * 
			 * 
			 * 
			 * PdfPCell cell1 = new PdfPCell(new Paragraph("Disciplina" ));
			 * cell1.setBorderColor(BaseColor.BLUE); cell1.setPaddingLeft(10);
			 * cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * PdfPCell cell2 = new PdfPCell(new
			 * Paragraph("Absente/activitati*saptamani"));
			 * cell2.setBorderColor(BaseColor.GREEN); cell2.setPaddingLeft(10);
			 * cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * 
			 * 
			 * PdfPCell cell3 = new PdfPCell(new Paragraph("Nr activitati"));
			 * cell3.setBorderColor(BaseColor.RED); cell3.setPaddingLeft(10);
			 * cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * //To avoid having the cell border and the content overlap, if you
			 * are having thick cell borders cell1.setUseBorderPadding(true);
			 * cell2.setUseBorderPadding(true);
			 * //cell3.setUseBorderPadding(true);
			 * 
			 * table.addCell(cell1); table.addCell(cell2); //
			 * table.addCell(cell3);
			 * 
			 * 
			 * List module =
			 * RaportStudent.getModuleSubgrupaStudent(student.getId
			 * ().intValue());
			 * 
			 * for(Object materie:module){ String disciplina =
			 * String.valueOf(materie); PdfPCell cell11 = new PdfPCell(new
			 * Paragraph(disciplina)); cell1.setBorderColor(BaseColor.BLUE);
			 * cell1.setPaddingLeft(10);
			 * cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); Disciplina disc
			 * = DisciplinaService.getDisciplinaByDenumire(materie.toString());
			 * int absente =
			 * PrezentaService.getNumberOfAbsencesForAStudentForModule
			 * (student,disc); PdfPCell cell21 = new PdfPCell(new
			 * Paragraph(String.valueOf(absente))); //PdfPCell cell21 = new
			 * PdfPCell(new Paragraph("****"));
			 * cell2.setBorderColor(BaseColor.GREEN); cell2.setPaddingLeft(10);
			 * cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * table.addCell(cell11); table.addCell(cell21); }
			 * 
			 * document.add(table);
			 * 
			 * footer.setSpacingAfter(15); footer.setSpacingBefore(35);
			 * document.add(footer);
			 */

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
