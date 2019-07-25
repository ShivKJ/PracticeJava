package algo.greedy;

import static java.util.Comparator.comparingLong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Activity Selection Problem: Given some activity having start and end time, selecting max number of
 * non overlapping activity.
 * 
 * @author shiv
 */
public class ActivitySelectionProblem {
    private final static Comparator<? super ASP> FINISHER_COMP_ASP = comparingLong(ASP::getFinish);

    public static class ASP {
        private final long start, finish;

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

    /**
     * We approach the problem greedily. This problem can be solved with both
     * iterative and recursive method.
     * 
     * First sorting activities in increasing order of their finishing time.
     * 
     * Picking first activity. It has to be in optimal list of non overlapping activities(P).
     * Because if it is not taken, then either we decrease number of activities in P or 
     * leaving less reason for next activity to search.
     * 
     * Now we search for next activity which start after the previously taken activity.
     * 
     * This process is repeated until we have examined all activities.
     * 
     * @param activities
     * @return optimal list of non overlapping activities.
     */
    public static <T extends ASP> List<T> aspIterative(Collection<T> activities) {
        List<T> acts = new ArrayList<>(activities), output = new ArrayList<>();

        acts.sort(FINISHER_COMP_ASP);

        Iterator<T> iter = acts.iterator();

        T prev = iter.next();
        output.add(prev);

        while (iter.hasNext()) {
            T curr = iter.next();

            if (curr.getStart() > prev.getFinish())
                output.add(prev = curr);

        }

        return output;
    }

    private final static <T extends ASP> void aspRecursive(List<T> activities, int k, int n, List<T> output) {
        int m = k + 1;

        T act = null, addedAct = activities.get(k);
        while (m <= n && (act = activities.get(m)).getStart() < addedAct.getFinish())
            // any act which start before added act, can not be in output list.
            // So searching for next activity. i.e. m -> m + 1
            m++;

        if (m <= n) {
            output.add(act);
            aspRecursive(activities, m, n, output);
        }
    }

    public static <T extends ASP> List<T> aspRecursive(Collection<T> activities) {
        List<T> acts = new ArrayList<>(activities), output = new ArrayList<>();

        acts.sort(FINISHER_COMP_ASP);

        output.add(acts.get(0));

        aspRecursive(acts, 0, activities.size() - 1, output);

        return output;
    }
}
