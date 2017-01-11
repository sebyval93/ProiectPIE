package Utils;

import Services.SaptamanaService;
import entity.Saptamana;

public class Week {
	
	private Saptamana week;
	private int num;
	private int semestru;
	
	public Week() {
		week = null;
	}
	
	public Week(Saptamana week) {
		this.week = week;
		num = Integer.parseInt(week.getDenumire().split(" ")[1]);
		semestru = Integer.parseInt(week.getSemestru().getNumeSem().split(" ")[1]);
	}
	
	public void setWeek(Saptamana week) {
		this.week = week;
		num = Integer.parseInt(week.getDenumire().split(" ")[1]);
		semestru = Integer.parseInt(week.getSemestru().getNumeSem().split(" ")[1]);
	}
	
	public int getSaptamanaNumber() {
		return num;
	}
	
	public int getSemestruNumber() {
		return semestru;
	}
	
	public void nextWeek() {		
		if (week.getDenumire().equals("saptamana 14")) {
			if (week.getSemestru().getNumeSem().equals("semestru 1")) {
				num = 1;
				semestru = 2;
				String weekStr = "saptamana " + num;
				String semestruStr = "semestru " + semestru;
				//week = SaptamanaService.getSaptamanaByDenumireAndSemestru(weekStr, semestruStr);
				week = Singleton.Singleton.getInstance().ListOfWeeks.stream()
			            .filter(e -> e.getId().intValue() == 15)
			            .findFirst()
			            .get();
				return;
			}
			else if (week.getSemestru().getNumeSem().equals("semestru 2"))
				return;
		}
		
		String nextWeekStr = "saptamana " + (num + 1);
		String semestruStr = "semestru " + semestru;
		//week = SaptamanaService.getSaptamanaByDenumireAndSemestru(nextWeekStr, semestruStr);
		week = Singleton.Singleton.getInstance().ListOfWeeks.stream()
	            .filter(e -> e.getId().intValue() == week.getId().intValue() + 1)
	            .findFirst()
	            .get();
		num = Integer.parseInt(week.getDenumire().split(" ")[1]);
		semestru = Integer.parseInt(week.getSemestru().getNumeSem().split(" ")[1]);
	}
	
	public void prevWeek() {
		if (week.getDenumire().equals("saptamana 1")) {
			if (week.getSemestru().getNumeSem().equals("semestru 1")) {
				return;
			}
			else if (week.getSemestru().getNumeSem().equals("semestru 2"))
				num = 14;
				semestru = 1;
				String weekStr = "saptamana " + num;
				String semestruStr = "semestru " + semestru;
				//week = SaptamanaService.getSaptamanaByDenumireAndSemestru(weekStr, semestruStr);
				week = Singleton.Singleton.getInstance().ListOfWeeks.stream()
			            .filter(e -> e.getId().intValue() == 14)
			            .findFirst()
			            .get();
				return;
		}
		
		String prevWeekStr = "saptamana " + (num - 1);
		String semestruStr = "semestru " + semestru;
		//week = SaptamanaService.getSaptamanaByDenumireAndSemestru(prevWeekStr, semestruStr);
		week = Singleton.Singleton.getInstance().ListOfWeeks.stream()
	            .filter(e -> e.getId().intValue() == week.getId().intValue() - 1)
	            .findFirst()
	            .get();
		num = Integer.parseInt(week.getDenumire().split(" ")[1]);
		semestru = Integer.parseInt(week.getSemestru().getNumeSem().split(" ")[1]);
	}
	
	public Saptamana getSaptamana() {
		return week;
	}
}
