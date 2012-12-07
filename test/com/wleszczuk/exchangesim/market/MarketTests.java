package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.CompId;
import com.wleszczuk.exchangesim.Company;
import com.wleszczuk.exchangesim.Money;
import com.wleszczuk.exchangesim.Symbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 18, 2011
 * Time: 10:16:47 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarketTests {
  Symbol apple = new Symbol("AAPL");
  Trader will = new Trader(new CompId("wleszczuk"));
  Trader david = new Trader(new CompId("dkesler"));
  
  @Mock Company appleInc;
  @Mock MarketMaker appleSpecialist;

  Market underTest;

  // TODO: registration phase, trading start, trading end
  // TODO: market orders
  // TODO: resolve orders in timestamp priority after price priority (this should happen as-is, but may want to track timestamp)
  // TODO: lock around all placeBuyLimitOrder / placeSellLimitOrder operations
  // NOTE/TODO: all orders are limit orders now
  // TODO: market makers: 1% spread over average of mid-points of max spreads (opening price weighted to initial qty outstanding)
  // TODO: model and enforce lot sizes

  @Before
  public void setup() throws Exception {
    when(appleSpecialist.specialty()).thenReturn(appleInc);
    when(appleInc.symbol()).thenReturn(apple);

    underTest = new Market(
      Arrays.asList(appleSpecialist),
      Arrays.asList(will, david)
    );
  }

  @Test
  public void test_highestBidReturnsMarketMakerBid() {
    when(appleSpecialist.bid()).thenReturn(Money.fromMilliCents(252525));
    assertEquals(Money.fromMilliCents(252525), underTest.highestBid(apple));
  }

  @Test
  public void test_symbolListedReturnsFalseForUnlistedSymbol()
    { assertFalse(underTest.symbolListed(new Symbol("WHUT"))); }

  @Test
  public void test_symbolListedReturnsTrueIfSymbolListed()
    { assertTrue(underTest.symbolListed(apple)); }

  @Test
  public void test_buyMarketOrdersAlwaysFilled() {
    ExecutionResult result = underTest.placeBuyMarketOrder(will.compId, apple, 20);

    assertEquals(ExecutionResult.OrderStatus.Filled, result.status);
    assertEquals(20, result.numExecutions);
  }
  
  @Test
  public void test_buyMarketOrderResultsInFillAtMarketPrice() {
    when(appleSpecialist.sell(will, 20))
      .thenReturn(Money.fromMilliCents(28723));

    ExecutionResult result = underTest.placeBuyMarketOrder(will.compId, apple, 20);

    assertEquals(Money.fromMilliCents(28723), result.executionPrice);
  }

  @Test
  public void test_traderRegisteredReturnsFalseForUnregisteredTrader()
    { assertFalse(underTest.traderRegistered(new CompId("FOOB"))); }

  @Test
  public void test_traderRegisteredReturnsTrueForRegisteredTrader()
    { assertTrue(underTest.traderRegistered(will.compId)); }

  /*
  @Test
  public void test_highestBidReturnsHighestOfThreeBids() {
    Symbol apple = new Symbol("AAPL");
    underTest.placeBuyLimitOrder(new CompId("wleszczuk"), apple, 100, Money.fromMilliCents(10000));
    underTest.placeBuyLimitOrder(new CompId("wleszczuk"), apple, 100, Money.fromMilliCents(10500));
    underTest.placeBuyLimitOrder(new CompId("wleszczuk"), apple, 100, Money.fromMilliCents(9000));
    assertEquals(Money.fromMilliCents(10500), underTest.highestBid(apple));
  }
  */

/*
  @Test
  public void test_listedStocksReturnsAllStocksAndOpeningPrices() {
    Collection<Stock> listedStocks = underTest.listedStocks();
    assertEquals(3, listedStocks.size());

    Map<Symbol, Money> expectedPrices = new HashMap<Symbol, Money>();
    expectedPrices.put(new Symbol("AAPL"), Money.fromMilliCents(33605000));
    expectedPrices.put(new Symbol("BAC"), Money.fromMilliCents(701000));
    expectedPrices.put(new Symbol("AIG"), Money.fromMilliCents(2270000));

    for (Symbol symbol : expectedPrices.keySet()) {
      boolean foundSymbol = false;
      for (Stock current : listedStocks) {
        if (current.symbol.equals(symbol)) {
          foundSymbol = true;
          assertEquals(expectedPrices.get(symbol), current.openingPrice);
          break;
        }
      }
      assertTrue(
        String.format("Failed to locate %s by symbol", symbol),
        foundSymbol
      );
    }
  }
*/

  /*
  @Test
  public void test_buyWithExactMatchOnPriceAndQuantityResultsInFilledOrder() {
    underTest.placeSellLimitOrder(
      new CompId("dkesler"),
      new Symbol("AAPL"),
      100,
      Money.fromMilliCents(10000000)
    );

    assertEquals(
      ExecutionResult.OrderStatus.Filled,
      underTest.placeBuyLimitOrder(
        new CompId("wleszczuk"),
        new Symbol("AAPL"),
        100,
        Money.fromMilliCents(10000000)
      ).status
    );
  }

  @Test
  public
  void test_buyWithExactMatchOnPriceButNotEnoughQuantityResultsInPartialFill() {
    underTest.placeSellLimitOrder(
      new CompId("dkesler"),
      new Symbol("AAPL"),
      100,
      Money.fromMilliCents(10000000)
    );

    assertEquals(
      ExecutionResult.OrderStatus.PartiallyFilled,
      underTest.placeBuyLimitOrder(
        new CompId("wleszczuk"),
        new Symbol("AAPL"),
        200,
        Money.fromMilliCents(10000000)
      ).status
    );
  }

  @Test
  public void test_buyWithNoOffersResultsInNewOrder() {
    assertEquals(
      ExecutionResult.OrderStatus.New,
      underTest.placeBuyLimitOrder(
        new CompId("wleszczuk"),
        new Symbol("AAPL"),
        100,
        Money.fromMilliCents(10000000)
      ).status
    );
  }

  @Test
  public void test_buyWithExactMatchOnPriceAndQuantitySplitAcrossTwoOrdersResultsInFilledOrder() {
    for (int i = 0; i < 2; ++i) {
      underTest.placeSellLimitOrder(
        new CompId("dkesler"),
        new Symbol("AAPL"),
        50,
        Money.fromMilliCents(10000000)
      );
    }

    assertEquals(
      ExecutionResult.OrderStatus.Filled,
      underTest.placeBuyLimitOrder(
        new CompId("wleszczuk"),
        new Symbol("AAPL"),
        100,
        Money.fromMilliCents(10000000)
      ).status
    );
  }

  @Test
  public void test_tradesResultInVolumeIncrease() {
    Money oneDollar = Money.fromMilliCents(100000);
    underTest.placeSellLimitOrder(new CompId("dkesler"), new Symbol("AAPL"), 300, oneDollar);
    underTest.placeBuyLimitOrder(new CompId("wleszczuk"), new Symbol("AAPL"), 10, oneDollar);
    underTest.placeBuyLimitOrder(new CompId("wleszczuk"), new Symbol("AAPL"), 200, oneDollar);
    underTest.placeBuyLimitOrder(new CompId("wleszczuk"), new Symbol("BAC"),  30, oneDollar);
    assertEquals(210, underTest.volume(new Symbol("AAPL")));
  }

  @Test
  public void test_lowestAskReturnsOnlyOffer() {
    Symbol apple = new Symbol("AAPL");

    underTest.placeSellLimitOrder(
      new CompId("dkesler"),
      apple,
      100,
      Money.fromMilliCents(100000)
    );

    assertEquals(
      Money.fromMilliCents(100000),
      underTest.lowestAsk(apple)
    );
  }

  @Test
  public void test_lowestAskReturnsNAIfNoOffers() {
    assertEquals(
      Money.NA,
      underTest.lowestAsk(new Symbol("AAPL"))
    );
  }

  @Test
  public void test_lowestAskReturnsLowestOfThreeOffers() {
    Symbol apple = new Symbol("AAPL");

    underTest.placeSellLimitOrder(
      new CompId("dkesler"),
      apple,
      100,
      Money.fromMilliCents(300000)
    );
    underTest.placeSellLimitOrder(
      new CompId("dkesler"),
      apple,
      100,
      Money.fromMilliCents(100000)
    );
    underTest.placeSellLimitOrder(
      new CompId("dkesler"),
      apple,
      100,
      Money.fromMilliCents(200000)
    );

    assertEquals(
      Money.fromMilliCents(100000),
      underTest.lowestAsk(apple)
    );
  }

  @Test
  public void test_lastTradePriceReturnsNAIfNoTrades()
    { assertEquals(Money.NA, underTest.lastTrade(new Symbol("AAPL"))); }

  @Test
  public void test_lastTradePriceReturnsOnlyTradePriceOnExactMatch() {
    Symbol apple = new Symbol("AAPL");
    underTest.placeSellLimitOrder(
      new CompId("dkesler"), apple, 10, Money.fromMilliCents(100000)
    );
    underTest.placeBuyLimitOrder(
      new CompId("wleszczuk"), apple, 10, Money.fromMilliCents(100000)
    );
    assertEquals(Money.fromMilliCents(100000), underTest.lastTrade(apple));
  }

  @Test
  public void test_lastTradePriceFavorsOlderOrders() {
    Symbol apple = new Symbol("AAPL");
    underTest.placeSellLimitOrder(
      new CompId("dkesler"), apple, 10, Money.fromMilliCents(50000)
    );
    underTest.placeSellLimitOrder(
      new CompId("dkesler"), apple, 10, Money.fromMilliCents(100000)
    );
    underTest.placeBuyLimitOrder(
      new CompId("wleszczuk"), apple, 20, Money.fromMilliCents(120000)
    );
    assertEquals(Money.fromMilliCents(120000), underTest.lastTrade(apple));
  }

  // TODO: make sure you don't trade with yourself
  // TODO: handle unlisted symbol
  // TODO: handle unknown participant
  */
}
