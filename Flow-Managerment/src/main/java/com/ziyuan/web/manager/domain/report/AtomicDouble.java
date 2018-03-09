package com.ziyuan.web.manager.domain.report;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicDouble extends Number {
	private static final long serialVersionUID = 1L;

	private AtomicLong bits;

	public AtomicDouble() {
		this(0);
	}

	public AtomicDouble(double initialValue) {
		bits = new AtomicLong(Double.doubleToLongBits(initialValue));
	}

	public final double addAndGet(double delta) {
		double expect;
		double update;
		do {
			expect = get();
			update = expect + delta;
		} while (!this.compareAndSet(expect, update));

		return update;
	}

	public final double getAndAdd(double delta) {
		double expect;
		double update;
		do {
			expect = get();
			update = expect + delta;
		} while (!this.compareAndSet(expect, update));

		return expect;
	}

	public final double getAndDecrement() {
		return getAndAdd(-1);
	}

	public final double decrementAndGet() {
		return addAndGet(-1);
	}

	public final double getAndIncrement() {
		return getAndAdd(1);
	}

	public final double incrementAndGet() {
		return addAndGet(1);
	}

	public final double getAndSet(float newValue) {
		double expect;
		do {
			expect = get();
		} while (!this.compareAndSet(expect, newValue));

		return expect;
	}

	public final boolean compareAndSet(double expect, double update) {
		return bits.compareAndSet(Double.doubleToLongBits(expect), Double.doubleToLongBits((update)));
	}

	public final void set(double newValue) {
		bits.set(Double.doubleToLongBits(newValue));
	}

	public final double get() {
		return Double.longBitsToDouble(bits.get());
	}

	@Override
	public float floatValue() {
		return (float) get();
	}

	@Override
	public double doubleValue() {
		return get();
	}

	@Override
	public int intValue() {
		return (int) get();
	}

	@Override
	public long longValue() {
		return (long) get();
	}

	@Override
	public String toString() {
		return Double.toString(get());
	}

}
