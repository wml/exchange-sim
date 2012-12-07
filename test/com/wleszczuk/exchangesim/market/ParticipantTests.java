package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 20, 2011
 * Time: 8:10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantTests {
  Balance payor, counterParty;

  @Before
  public void setup() {
    payor = new Balance(Money.fromMilliCents(1000000)){};
    counterParty = new Balance(Money.fromMilliCents(1000000)){};
  }

  @Test
  public void test_payResultsInPayorBalanceDecrement() {
    payor.transferMoneyTo(counterParty, Money.fromMilliCents(250000));
    assertEquals(Money.fromMilliCents(750000), payor.amount);
  }

  @Test
  public void test_payResultsInCounterPartyBalanceIncrement() {
    payor.transferMoneyTo(counterParty, Money.fromMilliCents(250000));
    assertEquals(Money.fromMilliCents(1250000), counterParty.amount);
  }
}
