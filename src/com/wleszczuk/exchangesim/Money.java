package com.wleszczuk.exchangesim;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 18, 2011
 * Time: 10:33:35 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Money implements Comparable<Money> {
  public static final Money Zero = Money.fromMilliCents(0);
  public static final Money NA = new Money(-1);

  public final long valueInMilliCents;

  public static Money max(Money a, Money b) {
    assert (null != a);
    assert (null != b);
    assert (Money.NA != a);
    assert (Money.NA != b);
    return a.compareTo(b) >= 0 ? a : b;
  }

  public static Money min(Money a, Money b) {
    assert (null != a);
    assert (null != b);
    assert (Money.NA != a);
    assert (Money.NA != b);
    return a.compareTo(b) <= 0 ? a : b;
  }

  public static Money fromMilliCents(long milliCents) {
    assert (0 < milliCents);
    return new Money(milliCents);
  }

  public static Money fromDifference(Difference difference) {
    assert (difference.positive);
    assert (Difference.NA != difference);
    return Money.fromMilliCents(difference.magnitudeInMilliCents);
  }

  private Money(long valueInMilliCents)
   { this.valueInMilliCents = valueInMilliCents; }

  public Money smallestIncrement()
    { return new Money(1 + valueInMilliCents); }

  @Override
  public int compareTo(Money other) {
    return Long.valueOf(valueInMilliCents).compareTo(other.valueInMilliCents);
  }

  @Override
  public boolean equals(Object other) {
    return null != other
      && other instanceof Money
      && valueInMilliCents == Money.class.cast(other).valueInMilliCents;
  }

  @Override
  public int hashCode()
    { return (int) (valueInMilliCents ^ (valueInMilliCents >>> 32)); }

  @Override
  public String toString() {
    return (Money.NA == this) ? "-" : String.format(
      "%d.%05d",
      valueInMilliCents / 100000,
      valueInMilliCents % 100000
    );
  }

  public Difference subtract(Money amount) {
    return (Money.NA == this || Money.NA == amount)
      ? Difference.NA
      : new Difference(valueInMilliCents - amount.valueInMilliCents);
  }

  public Money add(Money amount) {
    assert (Money.NA != this);
    assert (Money.NA != amount);

    return Money.fromMilliCents(valueInMilliCents + amount.valueInMilliCents);
  }

  public Money multiply(long by) {
    assert (Money.NA != this);
    return Money.fromMilliCents(valueInMilliCents * by);
  }

  public boolean lessThan(Money other)
    { return compareTo(other) < 0; }

  public boolean greaterThan(Money other)
    { return compareTo(other) > 0; }

  public static class Difference {
    public static Difference NA = new Difference(0);

    public final long magnitudeInMilliCents;
    public final boolean positive;

    Difference(long differenceInMilliCents) {
      magnitudeInMilliCents = Math.abs(differenceInMilliCents);
      positive = (0 <= differenceInMilliCents);
    }

    @Override
    public String toString() {
      return (Difference.NA == this) ? "-" : String.format(
        "%s%s",
        signString(),
        magnitudeString()
      );
    }

    public String magnitudeString() {
      return String.format(
        "%d.%05d",
        magnitudeInMilliCents / 100000,
        magnitudeInMilliCents % 100000
      );
    }

    public String signString()
      { return positive ? "+" : "-"; }
  }
}
