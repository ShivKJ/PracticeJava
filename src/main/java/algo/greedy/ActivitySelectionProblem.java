package algo.greedy;

import static java.util.Comparator.comparingLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ActivitySelectionProblem {
	private final static Comparator<? super ASP> FINISHER_COMP_ASP = comparingLong(ASP::getFinish);

	public static class ASP {
		private final long start , finish;

		public ASP(long start, long finish) {
			this.start = start;
			this.finish = finish;
		}

		public long getFinish() {
			return finish;
		}

		public long getStart() {
			return start;
		}
	}

	public static <T extends ASP> List<T> aspIterative(Collection<? extends T> activities) {
		List<T> acts = new ArrayList<>(activities);

		acts.sort(FINISHER_COMP_ASP);

		Iterator<T> iter = acts.iterator();
		T prev = iter.next();

		List<T> output = new ArrayList<>();
		output.add(prev);

		while (iter.hasNext()) {
			T curr = iter.next();

			if (curr.getStart() > prev.getFinish())
				output.add(prev = curr);

		}

		return output;
	}

	private final static <T extends ASP> void aspRecursive(List<T> activities, int k, int n, List<T> res) {
		int m = k + 1;

		T act = null , kAct = activities.get(k);
		while (m <= n && (act = activities.get(m)).getStart() < kAct.getFinish())
			m++;

		if (m <= n) {
			res.add(act);
			aspRecursive(activities, m, n, res);
		}
	}

	public static <T extends ASP> List<T> aspRecursive(Collection<? extends T> activities) {
		List<T> acts = new ArrayList<>(activities) , output = new ArrayList<>();
		acts.sort(FINISHER_COMP_ASP);

		output.add(acts.get(0));

		aspRecursive(acts, 0, activities.size() - 1, output);

		return output;
	}
}
