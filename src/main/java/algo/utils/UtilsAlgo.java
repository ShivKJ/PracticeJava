package algo.utils;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public final class UtilsAlgo {
	private UtilsAlgo() {}

	public static <E> Optional<E> best(Collection<? extends E> points, Predicate<? super E> filteration, Comparator<? super E> comp) {
		E best = null;

		for (E tmp : points)
			if (filteration.test(tmp))
				if (best == null)
				best = tmp;
				else if (comp.compare(tmp, best) > 0)
					best = tmp;

		return ofNullable(best);
	}

	public static <E> Optional<E> first(Collection<? extends E> points, Predicate<? super E> filteration) {
		for (E e : points)
			if (filteration.test(e))
				return of(e);

		return empty();

	}
}
