package Services;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class ImportService{

		
		static FileInputStream fileInputStream ;
		static HSSFWorkbook workbook;
		static HSSFSheet an_studiu_wk;
		static HSSFSheet profesori_wk;
		static HSSFSheet discipline_wk;
		static HSSFSheet grupa_wk;
		static HSSFSheet subgrupa_wk;
		static HSSFSheet student_wk;
			
	
	public static boolean doImport(FileInputStream file){
		if(file == null)
			return false;
		
		boolean done = false;
		
		try{
			workbook = new HSSFWorkbook(file);
			an_studiu_wk = workbook.getSheet("an_studiu");
			profesori_wk = workbook.getSheet("Profesori");
			discipline_wk = workbook.getSheet("discipline");
			grupa_wk = workbook.getSheet("grupa");
			subgrupa_wk = workbook.getSheet("subgrupa");
			student_wk = workbook.getSheet("studenti");
			
			deletePrezenta();
			deleteModul();
			deleteUtilizatori();
			deleteProfesori();
			deleteDiscipline();
			deleteStudent();
			deleteSubgrupa();
			deleteGrupa();
			deleteAnStudiu();
			addAniStudiu();					
			addGrupa();
			addSubgrupa();
			addStudent();
			addProfesori();	
			addUtilizatori();
			addDiscipline();
			done = true;
			
		}catch(IOException e){
			e.printStackTrace();
		}			
		return done;
	}
	
	public static void addAniStudiu(){	
		
		for(int k = 1; k<=an_studiu_wk.getLastRowNum();k++){	
			HSSFRow row1 = an_studiu_wk.getRow(k);
			HSSFCell cellA1 = row1.getCell((short) 0);			
			int an_studiu = (int) cellA1.getNumericCellValue();
			AnUniversitarService.addAn(an_studiu);	
		}		
	}
	
	public static void addProfesori(){
		
		for(int j = 1; j<=profesori_wk.getLastRowNum(); j++){
			
			HSSFRow row1 = profesori_wk.getRow(j);
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String nume_profesor = cellA1.getStringCellValue();
						
		ProfesorService.addProfesor(nume_profesor);
		}
	}
	
	public static void addDiscipline(){
		
		for(int i = 1; i<=discipline_wk.getLastRowNum(); i++){
			HSSFRow row1 = discipline_wk.getRow(i);
			
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String disciplina = cellA1.getStringCellValue();
			
			HSSFCell cellB1 = row1.getCell((short) 1);
			String nume_scurt =  cellB1.getStringCellValue();
			
			HSSFCell cellC1 = row1.getCell((short) 2);
			int an_studiu =  (int) cellC1.getNumericCellValue();

			HSSFCell cellD1 = row1.getCell((short) 3);
			int id_semestru =   (int) cellD1.getNumericCellValue();
			
			HSSFCell cellE1 = row1.getCell((short) 4);
			int curs =  (int) cellE1.getNumericCellValue();
			
			HSSFCell cellF1 = row1.getCell((short) 5);
			int seminar =  (int) cellF1.getNumericCellValue();
					
		    HSSFCell cellG1 = row1.getCell((short) 6);
		    int laborator =  (int) cellG1.getNumericCellValue();
		    
		    HSSFCell cellH1 = row1.getCell((short) 7);
		    int proiect =  (int) cellH1.getNumericCellValue();
		    
		    DisciplinaService.addDisciplina(disciplina,SemestruService.getSemestruByID(id_semestru),an_studiu, curs, laborator, seminar, proiect, nume_scurt);
	}
	
		}
	
	public static void addGrupa(){	
		for(int l = 1; l<= grupa_wk.getLastRowNum(); l++){
			
			HSSFRow row1 = grupa_wk.getRow(l);
			
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String nume_grupa = cellA1.getStringCellValue();
			
			HSSFCell cellB1 = row1.getCell((short) 1);
			int an_studiu =  (int) cellB1.getNumericCellValue();
			GrupaService.addGrupa(AnUniversitarService.getAnByID(an_studiu), nume_grupa);		
			
		}
	}
	
	public static void addSubgrupa(){		
		for(int m = 1; m<=subgrupa_wk.getLastRowNum(); m++){
			
			HSSFRow row1 = subgrupa_wk.getRow(m);
			
			HSSFCell cellA1 = row1.getCell((short) 0);	
			String nume_grupa = cellA1.getStringCellValue();
			
			HSSFCell cellB1 = row1.getCell((short) 1);	
			int id_grupa = (int) cellB1.getNumericCellValue();
			
			SubgrupaService.addSubgrupa(GrupaService.getGrupaByID(id_grupa), nume_grupa);
		}
	}
	
	public static void addStudent(){
		for(int n = 1; n<=student_wk.getLastRowNum(); n++){
		HSSFRow row1 = student_wk.getRow(n);
		
		
		HSSFCell cellA1 = row1.getCell((short) 0);	
		String nume_student = cellA1.getStringCellValue();
		
		HSSFCell cellB1 = row1.getCell((short) 1);	
		int id_subgrupa = (int) cellB1.getNumericCellValue();
		
		StudentService.addStudent(SubgrupaService.getSubgrupaByID(id_subgrupa), nume_student);
		
		}
	}
	
	public static void addUtilizatori(){		
			UtilizatorService.generateAccounts();
	}
		
	public static void deleteStudent(){
		Utils.Functions.resetSequence("STUDENT_SEQ");
		StudentService.deleteAllFromTable();		
	}
	
	public static void deleteSubgrupa(){
		Utils.Functions.resetSequence("SUBGRUPA_SEQ");
		SubgrupaService.deleteAllFromTable();
	}
	
	public static void deleteGrupa(){
		Utils.Functions.resetSequence("GRUPA_SEQ");
		GrupaService.deleteAllFromTable();
	}
	
	public static void deleteDiscipline(){
		Utils.Functions.resetSequence("DISCIPLINA_SEQ");
		DisciplinaService.deleteAllFromTable();
	}
	
	public static void deleteProfesori(){
		Utils.Functions.resetSequence("PROFESOR_SEQ");
		ProfesorService.deleteAllFromTable();
	
	}
	
	public static void deleteAnStudiu(){
		Utils.Functions.resetSequence("AN_SEQ");
		AnUniversitarService.deleteAllFromTable();
	}
	
	public static void deleteModul(){
		Utils.Functions.resetSequence("MODUL_SEQ");
		ModulService.deleteAllFromTable();
	}
	
	public static void deletePrezenta(){
		Utils.Functions.resetSequence("PREZENTA_SEQ");
		PrezentaService.deleteAllFromTable();
	}
	
	public static void deleteUtilizatori(){
		Utils.Functions.resetSequence("UTILIZATOR_SEQ");
		UtilizatorService.deleteAllFromUtilizator();
	}
	
}


