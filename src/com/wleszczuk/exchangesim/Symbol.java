package com.wleszczuk.exchangesim;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 18, 2011
 * Time: 10:23:26 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Symbol implements Comparable<Symbol> {
  public final String value;

  public Symbol(String value) {
    assert (null != value);
    assert (!"".equals(value));
    this.value = value;
  }

  @Override
  public boolean equals(Object other) {
    return null != other
      && other instanceof Symbol
      && value.equals(Symbol.class.cast(other).value);
  }

  @Override
  public int hashCode()
    { return value != null ? value.hashCode() : 0; }

  @Override
  public int compareTo(Symbol other) {
    assert (null != other);
    return value.compareTo(other.value);
  }

  @Override
  public String toString()
    { return String.format("Symbol[%s]", value); }
}
