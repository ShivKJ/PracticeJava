package algo.greedy;

import static java.util.Comparator.comparingLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import algo.graphs.DataWrapper;

public class ActivitySelectionProblem {
	private final static Comparator<? super ASP<?>> FINISHER_COMP_ASP = comparingLong(ASP::getFinish);

	public abstract static class ASP<T> implements DataWrapper<T> {
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

	public static <T> List<ASP<T>> aspIterative(Collection<? extends ASP<T>> activities) {
		List<ASP<T>> acts = new ArrayList<>(activities);

		acts.sort(FINISHER_COMP_ASP);

		Iterator<ASP<T>> iter = acts.iterator();
		ASP<T> prev = iter.next();

		List<ASP<T>> output = new ArrayList<>();
		output.add(prev);

		while (iter.hasNext()) {
			ASP<T> curr = iter.next();
			if (curr.start > prev.finish)
				output.add(prev = curr);

		}

		return output;
	}

	private final static <T> void aspRecursive(List<ASP<T>> activities, int k, int n, List<ASP<T>> res) {
		int m = k + 1;

		ASP<T> act = null , kAct = activities.get(k);
		while (m <= n && (act = activities.get(m)).getStart() < kAct.getFinish())
			m++;

		if (m <= n) {
			res.add(act);
			aspRecursive(activities, m, n, res);
		}
	}

	public static <T> List<ASP<T>> aspRecursive(Collection<? extends ASP<T>> activities) {
		List<ASP<T>> acts = new ArrayList<>(activities) , output = new ArrayList<>();
		acts.sort(FINISHER_COMP_ASP);

		output.add(acts.get(0));

		aspRecursive(acts, 0, activities.size() - 1, output);

		return output;
	}
}
