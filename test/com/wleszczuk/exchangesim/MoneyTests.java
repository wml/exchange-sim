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
 * Time: 10:31:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoneyTests {
  @Test
  public void test_milliCentsReturnsProperValueWhenConstructedWithMilliCents() {
    Money underTest = Money.fromMilliCents(1000);
    assertEquals(1000, underTest.valueInMilliCents);
  }

  @Test
  public void test_threeDollarsEqualsThreeDollars() {
    assertEquals(Money.fromMilliCents(300000), Money.fromMilliCents(300000));
  }

  @Test
  public void test_threeDollarsDoesNotEqualTwoDollarsAndFiftyCents() {
    assertFalse(Money.fromMilliCents(300000).equals(Money.fromMilliCents(250000)));
  }

  @Test
  public void test_threeDollarsDoesNotEqualThreeHundredThousand() {
    assertFalse(Money.fromMilliCents(300000).equals(Integer.valueOf(300000)));
  }

  @Test
  public void test_threeDollarsDoesNotEqualNull() {
    assertFalse(Money.fromMilliCents(300000).equals(null));
  }

  @Test
  public void test_toString() {
    assertEquals("34.01839", Money.fromMilliCents(3401839).toString());
  }

  @Test
  public void test_maxReturnsHigherPrice() {
    Money low = Money.fromMilliCents(10000);
    Money high = Money.fromMilliCents(20000);
    assertEquals(high, Money.max(low, high));
  }

  @Test
  public void test_minReturnsLowerPrice() {
    Money low = Money.fromMilliCents(10000);
    Money high = Money.fromMilliCents(20000);
    assertEquals(low, Money.min(low, high));
  }

  @Test
  public void test_smallestIncrementIsOneMilliCentGreater() {
    assertEquals(
      102081,
      Money.fromMilliCents(102080).smallestIncrement().valueInMilliCents
    );
  }

  @Test
  public void test_hashCodeOfTwoValueEqualPriceInstancesSame() {
    assertEquals(
      Money.fromMilliCents(100100).hashCode(),
      Money.fromMilliCents(100100).hashCode()
    );
  }

  @Test
  public void test_hashCodeOfTwoValueDifferentPriceInstancesDifferent() {
    assertFalse(
         Money.fromMilliCents(100100).hashCode()
      == Money.fromMilliCents(200002).hashCode()
    );
  }

  @Test
  public void test_pricesSortBasedOnMillicentValuesNumerically() {
    List<Money> prices = new ArrayList<Money>();
    prices.add(Money.fromMilliCents(50000));
    prices.add(Money.fromMilliCents(25000));
    prices.add(Money.fromMilliCents(30000));

    Collections.sort(prices);
    assertEquals(25000, prices.get(0).valueInMilliCents);
    assertEquals(30000, prices.get(1).valueInMilliCents);
    assertEquals(50000, prices.get(2).valueInMilliCents);
  }

  @Test
  public void test_toStringIsNAForSentinelNAPrice()
    { assertEquals("-", Money.NA.toString()); }
  
  @Test
  public void test_subtractTwoRealPricesResultsInCorrectDifference() {
    assertEquals(
      10000,
      Money.fromMilliCents(30000)
        .subtract(Money.fromMilliCents(20000)).magnitudeInMilliCents
    );
  }

  @Test
  public void test_subtractNAFromRealPriceResultsInNADifference() {
    assertEquals(
      Money.Difference.NA,
      Money.fromMilliCents(30000).subtract(Money.NA)
    );
  }

  @Test
  public void test_subtractRealPriceFromNAResultsInNADifference() {
    assertEquals(
      Money.Difference.NA,
      Money.NA.subtract(Money.fromMilliCents(30000))
    );
  }

  @Test
  public void test_subtractLargerPriceFromSmallerPriceResultsInNegativeDifference() {
    Money.Difference difference =
      Money.fromMilliCents(10000).subtract(Money.fromMilliCents(20000));
    assertEquals(10000, difference.magnitudeInMilliCents);
    assertFalse(difference.positive);
  }

  @Test
  public void test_differenceToStringIsNAForNAPriceDifference() {
    assertEquals("-", Money.NA.subtract(Money.fromMilliCents(0)).toString());
  }

  @Test
  public void test_differenceToStringRendersCorrectlyForPositiveDifference() {
    assertEquals(
      "+24.15000",
      Money.fromMilliCents(2416000)
        .subtract(Money.fromMilliCents(1000)).toString()
    );
  }

  @Test
  public void test_differenceToStringRendersCorrectlyForNegativeDifference() {
    assertEquals(
      "-24.15000",
      Money.fromMilliCents(1000)
        .subtract(Money.fromMilliCents(2416000)).toString()
    );
  }

  @Test
  public void test_oneHundredLessThanTwoHundredDollars() {
    assertTrue(
      Money.fromMilliCents(10000000).lessThan(Money.fromMilliCents(20000000))
    );
  }

  @Test
  public void test_oneHundredNotLessThanOneHundredDollars() {
    assertFalse(
      Money.fromMilliCents(10000000).lessThan(Money.fromMilliCents(10000000))
    );
  }

  @Test
  public void test_oneHundredNotLessThanFiftyDollars() {
    assertFalse(
      Money.fromMilliCents(10000000).lessThan(Money.fromMilliCents(5000000))
    );
  }

  @Test
  public void test_oneHundredNotGreaterThanTwoHundredDollars() {
    assertFalse(
      Money.fromMilliCents(10000000).greaterThan(Money.fromMilliCents(20000000))
    );
  }

  @Test
  public void test_oneHundredNotGreaterThanOneHundredDollars() {
    assertFalse(
      Money.fromMilliCents(10000000).greaterThan(Money.fromMilliCents(10000000))
    );
  }

  @Test
  public void test_oneHundredGreaterThanFiftyDollars() {
    assertTrue(
      Money.fromMilliCents(10000000).greaterThan(Money.fromMilliCents(5000000))
    );
  }

  @Test
  public void test_valueFromDifferenceEqualsDifferenceInMagnitude() {
    assertEquals(
      150000,
      Money.fromDifference(
        Money.fromMilliCents(250000).subtract(Money.fromMilliCents(100000))
      ).valueInMilliCents
    );
  }

  @Test
  public void test_addFiveDollarsToTenDollarsEqualsFifteenDollars() {
    assertEquals(
      Money.fromMilliCents(1500000),
      Money.fromMilliCents(1000000).add(Money.fromMilliCents(500000))
    );
  }

  @Test
  public void test_thriceTenDollarsIsThirtyDollars() {
    assertEquals(
      Money.fromMilliCents(3000000),
      Money.fromMilliCents(1000000).multiply(3)
    );
  }
}
