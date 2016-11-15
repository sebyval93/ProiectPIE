package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Services.SaptamanaService;
import entity.Saptamana;

/**
 *
 * @author Nameless ^_^
 */
public class Functions {

	public static Saptamana GetTodayWeek() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int zi, an, luna;
		zi = calendar.get(Calendar.DAY_OF_MONTH);
		// System.out.println("day is "+day);
		an = calendar.get(Calendar.YEAR);
		// System.out.println("year is "+year);
		luna = calendar.get(Calendar.MONTH);
		for (Saptamana week : SaptamanaService.getAllFromSaptamana()) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(week.getEnddate());
			int startDay, endDay, startMonth, endMonth, year;

			endDay = cal.get(Calendar.DAY_OF_MONTH);
			endMonth = cal.get(Calendar.MONTH);
			year = cal.get(Calendar.YEAR);
			cal.setTime(week.getStartdate());
			startDay = cal.get(Calendar.DAY_OF_MONTH);
			startMonth = cal.get(Calendar.MONTH);

			// System.out.println("Month is "+(month+1));
			if ((zi <= endDay && zi > startDay)
					|| (zi >= startDay && zi < endDay)) {
				if (luna == endMonth || luna == startMonth) {
					if (an == year)
						System.out.println("Today is in week " + week.getId());
					return week;
				}
			}

		}

		return null;
	}

}
