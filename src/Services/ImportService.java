package Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import Singleton.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;

import Utils.*;
import entity.AnUniversitar;
import entity.Disciplina;
import entity.Grupa;
import entity.Modul;
import entity.Prezenta;
import entity.Profesor;
import entity.Student;
import entity.Subgrupa;
import sun.security.pkcs11.wrapper.Functions;

public final class ImportService{

		
		static FileInputStream fileInputStream ;
		static HSSFWorkbook workbook;
		static HSSFSheet an_universitar_wk;
		static HSSFSheet profesori_wk;
		static HSSFSheet discipline_wk;
		static HSSFSheet grupa_wk;
		static HSSFSheet subgrupa_wk;
		static HSSFSheet student_wk;
			
	
	public static boolean doImport(FileInputStream file){
		
		boolean done = false;
		
		try{
			workbook = new HSSFWorkbook(file);
			an_universitar_wk = workbook.getSheet("an_universitar");
			profesori_wk = workbook.getSheet("Profesori");
			discipline_wk = workbook.getSheet("discipline");
			grupa_wk = workbook.getSheet("grupa");
			subgrupa_wk = workbook.getSheet("subgrupa");
			student_wk = workbook.getSheet("studenti");
			
			deletePrezenta();
			deleteModul();
			//deleteFromProfesori();
			deleteFromDiscipline();
			deleteFromStudent();
			deleteFromSubgrupa();
			deleteFromGrupa();
			deleteFromAn();
			addAniUniversitari();					
			addGrupa();
			addSubgrupa();
			addStudent();
			//addProfesori();	
			addDiscipline();
			done = true;
			
		}catch(IOException e){
			e.printStackTrace();
		}			
		return done;
	}

	
	public static void addAniUniversitari(){				
		for(int k = 0; k<=an_universitar_wk.getLastRowNum();k++){	
			HSSFRow row1 = an_universitar_wk.getRow(k);
			HSSFCell cellA1 = row1.getCell((short) 0);	
			int an_universitar = (int) cellA1.getNumericCellValue();
						
			AnUniversitarService.addAn(an_universitar);
		}
		
	}
	
	public static void addProfesori(){
		deleteFromProfesori();
		
		for(int j = 0; j<=profesori_wk.getLastRowNum(); j++){
			
			HSSFRow row1 = profesori_wk.getRow(j);
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String nume_profesor = cellA1.getStringCellValue();
			
			
		ProfesorService.addProfesor(nume_profesor);
		}
	}
	
	public static void addDiscipline(){
		deleteFromDiscipline();
		
		for(int i = 0; i<=discipline_wk.getLastRowNum(); i++){
			HSSFRow row1 = discipline_wk.getRow(i);
			
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String disciplina = cellA1.getStringCellValue();
			
			HSSFCell cellB1 = row1.getCell((short) 1);
			String nume_scurt =  cellB1.getStringCellValue();
			
			HSSFCell cellC1 = row1.getCell((short) 2);
			int an_studiu =  (int) cellC1.getNumericCellValue();			
			
			HSSFCell cellE1 = row1.getCell((short) 3);
			int curs =  (int) cellE1.getNumericCellValue();
			
			HSSFCell cellF1 = row1.getCell((short) 4);
			int seminar =  (int) cellF1.getNumericCellValue();
					
		    HSSFCell cellG1 = row1.getCell((short) 5);
		    int laborator =  (int) cellG1.getNumericCellValue();	
		    
		    HSSFCell cellH1 = row1.getCell((short) 6);
		    int proiect =  (int) cellH1.getNumericCellValue();
		    
		    DisciplinaService.addDisciplina(disciplina, an_studiu, curs, laborator, seminar, proiect, nume_scurt);
		
	}
	
		}
	
	public static void addGrupa(){	
		for(int l = 0; l<= grupa_wk.getLastRowNum(); l++){
			
			HSSFRow row1 = grupa_wk.getRow(l);
			
			
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String nume_grupa = cellA1.getStringCellValue();
			
			HSSFCell cellB1 = row1.getCell((short) 1);
			int an_studiu =  (int) cellB1.getNumericCellValue();
			GrupaService.addGrupa(AnUniversitarService.getAnByID(an_studiu), nume_grupa);		
			
		}
	}
	
	public static void addSubgrupa(){		
		for(int m = 0; m<=subgrupa_wk.getLastRowNum(); m++){
			
			HSSFRow row1 = subgrupa_wk.getRow(m);
			
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String nume_grupa = cellA1.getStringCellValue();
			
			HSSFCell cellB1 = row1.getCell((short) 1);	
			int id_grupa = (int) cellB1.getNumericCellValue();
			
			SubgrupaService.addSubgrupa(GrupaService.getGrupaByID(id_grupa), nume_grupa);
		}
	}
	
	public static void addStudent(){
		for(int n = 0; n<=student_wk.getLastRowNum(); n++){
		HSSFRow row1 = student_wk.getRow(n);
		
		
		HSSFCell cellA1 = row1.getCell((short) 0);	
		String nume_student = cellA1.getStringCellValue();
		
		HSSFCell cellB1 = row1.getCell((short) 1);	
		int id_subgrupa = (int) cellB1.getNumericCellValue();
		
		StudentService.addStudent(SubgrupaService.getSubgrupaByID(id_subgrupa), nume_student);
		
		
		}
	}
		
	public static void deleteFromStudent(){
		deleteSequence("STUDENT_SEQ");
		addSequence("STUDENT_SEQ");	
		StudentService.deleteAllFromTable();		
	}
	
	public static void deleteFromSubgrupa(){
		deleteSequence("SUBGRUPA_SEQ");
		addSequence("SUBGRUPA_SEQ");
		SubgrupaService.deleteAllFromTable();
	}
	
	public static void deleteFromGrupa(){
		deleteSequence("GRUPA_SEQ");
		addSequence("GRUPA_SEQ");
		GrupaService.deleteAllFromTable();
	}
	
	public static void deleteFromDiscipline(){
		deleteSequence("DISCIPLINA_SEQ");
		addSequence("DISCIPLINA_SEQ");
		DisciplinaService.deleteAllFromTable();
	}
	
	public static void deleteFromProfesori(){
		deleteSequence("PROFESOR_SEQ");
		addSequence("PROFESOR_SEQ");
		ProfesorService.deleteAllFromTable();
	
	}
	
	public static void deleteFromAn(){
		deleteSequence("AN_SEQ");
		addSequence("AN_SEQ");
		AnUniversitarService.deleteAllFromTable();
	}
	
	public static void deleteModul(){
		
		deleteSequence("MODUL_SEQ");
		addSequence("MODUL_SEQ");
		ModulService.deleteAllFromTable();
	}
	
	public static void deletePrezenta(){
		deleteSequence("PREZENTA_SEQ");
		addSequence("PREZENTA_SEQ");
		PrezentaService.deleteAllFromTable();
	}

	public static void deleteSequence(String seqName){
		Session session = Singleton.getInstance().getNewSession();
		session.beginTransaction();
		String sql = String.format("DROP SEQUENCE "+seqName);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	public static void addSequence(String seqName){
		Session session = Singleton.getInstance().getNewSession();
		session.beginTransaction();
		String sql = String.format("CREATE SEQUENCE "+seqName + " "+"START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE" );
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
}


