package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.Company;
import com.wleszczuk.exchangesim.Money;
import com.wleszczuk.exchangesim.Symbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 5, 2011
 * Time: 1:01:10 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarketMakerTests {
  Money opening = Money.fromMilliCents(100 * 100 * 1000); // $100
  @Mock Trader counter;
  @Mock Balance balance;
  @Mock Balance counterPartyBalance;
  MarketMaker underTest;

  @Before
  public void setup() {
    underTest = MarketMaker.forInstrumentsOfCompany(
      new Company(
        new Symbol("AAPL"),
        100000,
        Company.Sector.TECH,
        Company.Industry.PC
      ),
      balance,
      opening,
      50000
    );
    when(counter.balance()).thenReturn(counterPartyBalance);
  }

  @Test
  public void test_bidLessThanAsk()
    { assertTrue(underTest.bid().lessThan(underTest.ask())); }

  @Test
  public void test_bidLessThanOpen()
    { assertTrue(underTest.bid().lessThan(opening)); }

  @Test
  public void test_askGreaterThanOpen()
    { assertTrue(underTest.ask().greaterThan(opening)); }

  @Test
  public void test_sellExecutesAtAskPrice()
    { assertEquals(underTest.ask(), underTest.sell(counter, 1000)); }

  @Test
  public void test_buyExecutesAtBidPrice()
    { assertEquals(underTest.bid(), underTest.buy(counter, 1000)); }

  @Test
  public void test_buyIncreasesVolumeByQuantity() {
    underTest.buy(counter, 5000);
    assertEquals(5000, underTest.volume());
  }

  @Test
  public void test_sellIncreasesVolumeByQuantity() {
    underTest.sell(counter, 2500);
    assertEquals(2500, underTest.volume());
  }

  @Test
  public void test_buyAndSellResultInAggregateVolume() {
    underTest.buy(counter, 5000);
    underTest.sell(counter, 2500);
    assertEquals(7500, underTest.volume());
  }

  @Test
  public void test_buyResultsInTransferOfAdequateFundsToCounterParty() {
    underTest.buy(counter, 500);
    verify(balance)
      .transferMoneyTo(counterPartyBalance, underTest.bid().multiply(500));
  }

  @Test
  public void test_sellResultsInTransferOfAdequateFundsFromCounterParty() {
    underTest.sell(counter, 500);
    verify(counterPartyBalance)
      .transferMoneyTo(balance, underTest.ask().multiply(500));
  }
}
