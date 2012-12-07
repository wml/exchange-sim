package com.wleszczuk.exchangesim;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 20, 2011
 * Time: 8:11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public final class CompId {
  public final String value;

  public CompId(String value) {
    assert (null != value);
    this.value = value;
  }

  @Override
  public boolean equals(Object other) {
    return null != other
      && other instanceof CompId
      && value.equals(CompId.class.cast(other).value);
  }

  @Override
  public int hashCode()
    { return value != null ? value.hashCode() : 0; }
}
