package rapoarte;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

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

	public static int getNumberOfAbsencesForAStudentForModule(Student student,
			Disciplina disciplina) {
		int absences = 0;

		if (student != null && disciplina != null) {
			Session session = null;
			try {
				session = Singleton.getInstance().getNewSession();
				List<Modul> moduls = ModulService
						.getAllModulesForStudentAndDisciplina(student,
								disciplina);

				DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
						.add(Restrictions.eq("student", student))
						.add(Restrictions.eq("modul", moduls))
						.add(Restrictions.eq("prezent", new BigDecimal(0)));
				dc.setProjection(Projections.rowCount());
				Number result = (Number) dc.getExecutableCriteria(session)
						.uniqueResult();
				absences = result.intValue();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}

		}
		return absences;
	}

	public static List<Prezenta> getPrezentaStudentAndModul(Student student,
			Modul m) {
		List<Prezenta> list = null;
		Session session = null;

		try {
			session = Singleton.getInstance().getNewSession();

			DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
					.add(Restrictions.eq("modul", m))
					.add(Restrictions.eq("student", student))
					.addOrder(Order.asc("saptamana"));
			list = dc.getExecutableCriteria(session).list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return list;

	}

	public static List<Integer> getPrezenteStModuls(Modul modul) {
		List<Integer> list = new ArrayList<Integer>();
		for (Student student : StudentService.getStudentiFromParticipant(modul
				.getParticipanti())) {
			List<Prezenta> lista = RaportAn.getPrezentaStudentAndModul(student,
					modul);
			for (int i = 0; i < lista.size(); i++) {
				int x = (int) list.get(i)
						+ lista.get(i).getPrezent().intValue();
				list.add(i, x);
			}
		}

		return list;
	}

	public static int countPrezente(int an, Disciplina d) {
		Session session = null;
		int total = 0;
		try {
			session = Singleton.getInstance().getNewSession();
			// Query q =
			// session.createQuery("select count(p) from Prezenta p where prezent=1 and modul in (select m from Modul m where disciplina in(select d from Disciplina d where an=4))");
			// total = q.getFirstResult();

			for (Modul modul : RaportAn.getAllModulByDisciplina(d)) {
				DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
						.add(Restrictions.eq("modul", modul))
						.add(Restrictions.eq("prezent", new BigDecimal(1)));
				dc.setProjection(Projections.rowCount());
				Number result = (Number) dc.getExecutableCriteria(session)
						.uniqueResult();
				total += result.intValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return total;

	}

	public static int countAbsenteAn(int an, Disciplina d) {
		Session session = null;
		int total = 0;
		try {
			session = Singleton.getInstance().getNewSession();
			// Query q =
			// session.createQuery("select count(p) from Prezenta p where prezent=1 and modul in (select m from Modul m where disciplina in(select d from Disciplina d where an=4))");
			// total = q.getFirstResult();

			for (Modul modul : RaportAn.getAllModulByDisciplina(d)) {
				DetachedCriteria dc = DetachedCriteria.forClass(Prezenta.class)
						.add(Restrictions.eq("modul", modul))
						.add(Restrictions.eq("prezent", new BigDecimal(0)));
				dc.setProjection(Projections.rowCount());
				Number result = (Number) dc.getExecutableCriteria(session)
						.uniqueResult();
				total += result.intValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return total;

	}

	public static JFreeChart PieChart(String title, int an, Disciplina d) {
		int absente = RaportAn.countAbsenteAn(an, d);
		int prezente = RaportAn.countPrezente(an, d);
		JFreeChart chart;
		if (absente != 0 & prezente != 0) {
			int a = absente * 100 / (absente + prezente);
			int b = prezente * 100 / (absente + prezente);
			DefaultPieDataset dataSet = new DefaultPieDataset();
			dataSet.setValue(String.valueOf(a)+"%", absente);
			dataSet.setValue(String.valueOf(b)+"%", prezente);

			chart = ChartFactory.createPieChart(title, dataSet, false, false,
					false);

		} else {
			DefaultPieDataset dataSet = new DefaultPieDataset();
			dataSet.setValue("Absente", absente);
			dataSet.setValue("Prezente", prezente);

			chart = ChartFactory.createPieChart(title, dataSet, false, false,
					false);
		}

		return chart;
	}

	public static void MakeYearReport(int an) {

		int width = 300;
		int height = 275;
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("Diagrame_An_Studii_" + an + ".pdf"));
			document.open();
			
			Paragraph title = new Paragraph("Graficele raportului absente/prezenta \npentru disciplinele anului de studii "+an);
			title.setAlignment(Element.ALIGN_CENTER);
			
			document.add(title);
			for (Disciplina disciplina : DisciplinaService
					.getDisciplineByAn(an)) {
				JFreeChart chart = PieChart(disciplina.getDenumire(), an,
						disciplina);

				PiePlot P = (PiePlot) chart.getPlot();
				DefaultPieDataset data = (DefaultPieDataset) P.getDataset();
				Paragraph d = new Paragraph(disciplina.getDenumire());

				d.setSpacingAfter(15);
				d.setSpacingBefore(15);
				document.add(d);

				document.add(new Paragraph("Absente\t\t             "
						+ (int) Double.parseDouble(data.getValue(0).toString())));

				document.add(new Paragraph("Prezente\t\t             "
						+ (int) Double.parseDouble(data.getValue(1).toString())));

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
				img.setSpacingAfter(20);
				
				document.add(img);

			}

			document.close();
			writer.close();

			System.out.println("Raport created");
			JOptionPane.showMessageDialog(null, "Raport creat cu succes", "Banzai", JOptionPane.INFORMATION_MESSAGE);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Something stink, jimmy.....FIX IT", "Eroare", JOptionPane.ERROR_MESSAGE);
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