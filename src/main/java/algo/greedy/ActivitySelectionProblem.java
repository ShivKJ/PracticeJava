package algo.greedy;

import static java.util.Comparator.comparingLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import algo.graphs.DataWrapper;

public class ActivitySelectionProblem {

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

	public static <T> List<ASP<T>> asp(Collection<? extends ASP<T>> activities) {
		List<ASP<T>> acts = new ArrayList<>(activities);

		acts.sort(comparingLong(ASP<T>::getFinish));

		Iterator<ASP<T>> iter = acts.iterator();
		ASP<T> prev = iter.next();

		List<ASP<T>> output = new ArrayList<>();
		output.add(prev);

		while (iter.hasNext()) {
			ASP<T> curr = iter.next();
			if (curr.start > prev.finish) {
				output.add(curr);
				prev = curr;
			}
		}

		return output;
	}
}
