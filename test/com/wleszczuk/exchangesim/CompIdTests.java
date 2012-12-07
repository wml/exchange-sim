package com.wleszczuk.exchangesim;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 20, 2011
 * Time: 8:14:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompIdTests {
  @Test
  public void test_wleszczukEqualsWleszczuk() {
    assertEquals(new CompId("wleszczuk"), new CompId("wleszczuk"));
  }

  @Test
  public void test_wleszczukDoesNotEqualDkesler() {
    assertFalse(new CompId("wleszczuk").equals(new CompId("dkesler")));
  }

  @Test
  public void test_wleszczukDoesNotEqualNull() {
    assertFalse(new CompId("wleszczuk").equals(null));
  }

  @Test
  public void test_wleszczukDoesNotEqualEmptyString() {
    assertFalse(new CompId("wleszczuk").equals(new CompId("")));
  }

  @Test
  public void test_hashCodeOfTwoValueEqualCompIdsSame() {
    assertEquals(
      new CompId("wleszczuk").hashCode(),
      new CompId("wleszczuk").hashCode()
    );
  }

  @Test
  public void test_hashCodeOfTwoValueDifferentCompIdsDifferent() {
    assertFalse(
      new CompId("wleszczuk").hashCode() == new CompId("dan").hashCode()
    );
  }
}
