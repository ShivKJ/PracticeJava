package basics;

public class A1 {
	static class A<T extends Comparable<T>> {
		private final T data;

		public A(T data) {
			this.data = data;
		}

		public T getData() {
			return data;
		}
	}

	static class B<T extends A<?>> {
		private final T t;

		public B(T t) {
			this.t = t;
		}

		public T getT() {
			return t;
		}

	}

	static class C<T extends B<? extends A<?>>> {
		T t;

		public C(T t) {
			this.t = t;
		}

		public void print() {
			System.out.println(t.getT().getData());
		}
	}

	public static void main(String[] args) {
		A<String> a = new A<>("shiv");
		B<A<String>> name = new B<>(a);

		System.out.println(name);

		C<B<A<String>>> name2 = new C<>(name);
		name2.print();
	}
}
