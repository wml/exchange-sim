package com.wleszczuk.exchangesim;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 18, 2011
 * Time: 10:23:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SymbolTests {
  @Test
  public void test_valueReturnsProperStringRepresentationOfSymbol() {
    Symbol underTest = new Symbol("AAPL");
    assertEquals("AAPL", underTest.value);
  }

  @Test
  public void test_appleSymbolEqualsAppleSymbol() {
    assertEquals(new Symbol("AAPL"), new Symbol("AAPL"));
  }

  @Test
  public void test_bankOfAmericaSymbolDoesNotEqualAppleSymbol() {
    assertFalse(new Symbol("BAC").equals(new Symbol("AAPL")));
  }

  @Test
  public void test_appleSymbolDoesNotEqual3() {
    assertFalse(new Symbol("AAPL").equals(Integer.valueOf(3)));
  }

  @Test
  public void test_appleSymbolDoesNotEqualNull() {
    assertFalse(new Symbol("AAPL").equals(null));
  }

  @Test
  public void test_appleSortsAboveGoogle() {
    Symbol apple = new Symbol("AAPL");

    List<Symbol> symbols = new ArrayList<Symbol>();
    symbols.add(new Symbol("GOOG"));
    symbols.add(apple);

    Collections.sort(symbols);
    assertEquals(0, symbols.indexOf(apple));
  }

  @Test
  public void test_hashCodeOfTwoValueEqualSymbolInstancesSame() {
    assertEquals(
      new Symbol("AAPL").hashCode(),
      new Symbol("AAPL").hashCode()
    );
  }

  @Test
  public void test_hashCodeOfTwoValueDifferentSymbolInstancesDifferent() {
    assertFalse(
      new Symbol("AAPL").hashCode() == new Symbol("GOOG").hashCode()
    );
  }

  @Test
  public void test_toStringContainsValue()
    { assertTrue((new Symbol("BAC")).toString().contains("BAC")); }
}
