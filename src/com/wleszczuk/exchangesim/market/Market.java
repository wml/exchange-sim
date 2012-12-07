package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.CompId;
import com.wleszczuk.exchangesim.Money;
import com.wleszczuk.exchangesim.Symbol;

import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 18, 2011
 * Time: 10:16:53 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Market {
  /*final Map<Symbol, BidData> bids;
  final Map<Symbol, OfferData> offers; */
  final Map<Symbol, MarketMaker> marketMakers;
  final Map<CompId, Trader> registeredTraders;

  public Market(
    List<MarketMaker> marketMakers,
    List<Trader> traders
  ) throws IOException {

    /*bids = new HashMap<Symbol, BidData>();
    offers = new HashMap<Symbol, OfferData>(); */
    this.marketMakers = new HashMap<Symbol, MarketMaker>();
    registeredTraders = new HashMap<CompId, Trader>();

    for (MarketMaker current : marketMakers) {
      this.marketMakers.put(current.specialty().symbol(), current);
      /*bids.put(current.symbol, new BidData());
      offers.put(current.symbol, new OfferData());*/
    }

    for (Trader current : traders)
      { registeredTraders.put(current.compId, current); }
  }

  public Money highestBid(Symbol forSymbol)
    { return marketMakers.get(forSymbol).bid(); }

  public boolean symbolListed(Symbol inQuestion) {
    // every listed symbol will have a market maker
    return marketMakers.containsKey(inQuestion);
  }

  public ExecutionResult placeBuyMarketOrder(
    CompId acquireFor,
    Symbol toAcquire,
    int quantity
  ) {
    assert (marketMakers.containsKey(toAcquire));
    assert (registeredTraders.containsKey(acquireFor));

    Money strikePrice = marketMakers.get(toAcquire).sell(
      registeredTraders.get(acquireFor),
      quantity
    );

    // market orders are always filled
    return
      new ExecutionResult(ExecutionResult.OrderStatus.Filled, 20, strikePrice);
  }

  public boolean traderRegistered(CompId inQuestion)
    { return registeredTraders.containsKey(inQuestion); }


  /*public ExecutionResult placeBuyLimitOrder(
    CompId acquireFor,
    Symbol toAcquire,
    int quantity,
    Money bidPrice
  ) {
    assert (0 < quantity);

    ExecutionResult result = null;

    int qtyLeft = quantity;

    OfferData offerData = offers.get(toAcquire);
    synchronized(offerData) {
      SortedMap<Money, List<Order>> qualifiedOffers =
        offerData.orders.headMap(bidPrice.smallestIncrement());

      while (0 < qtyLeft && 0 < qualifiedOffers.size()) {
        Money greatestPriceLessThanBidPrice = qualifiedOffers.lastKey();
        List<Order> orders = qualifiedOffers.get(greatestPriceLessThanBidPrice);
        Iterator<Order> iter = orders.iterator();
        while (iter.hasNext()) {
          Order order = iter.next();
          int qtyToTrade = Math.min(order.quantity, qtyLeft);
          executeTrade(
            acquireFor,
            order.owner,
            toAcquire,
            qtyLeft,
            bidPrice
          );

          order.quantity -= qtyToTrade;
          qtyLeft -= qtyToTrade;

          if (0 == order.quantity) {
            iter.remove();
          }
        }

        if (0 == orders.size()) {
          offerData.orders.remove(greatestPriceLessThanBidPrice);
        }
      }
    }

    if (0 < qtyLeft) {
      synchronized(bids) {
        { bids.get(toAcquire).bid(qtyLeft, bidPrice); }
      }

      result = new ExecutionResult(
        (qtyLeft == quantity)
          ? ExecutionResult.OrderStatus.New
          : ExecutionResult.OrderStatus.PartiallyFilled,
        quantity - qtyLeft
      );
    }
    else {
      result = new ExecutionResult(ExecutionResult.OrderStatus.Filled, quantity);
    }

    assert (null != result);

    return result;
  }

  private void executeTrade(
    CompId buySide,
    CompId sellSide,
    Symbol toTrade,
    int quantity,
    Money price
  ) {
    MarketMaker target = marketMakers.get(toTrade);
    synchronized(target) {
      target.volume += quantity;
      //target.lastTrade = price;
    }
  }

  //public Collection<Stock> listedStocks()
  // { return null; } // { return marketMakers.values(); }

  public long volume(Symbol forSymbol)
    { return marketMakers.get(forSymbol).volume; }

  public Money lastTrade(Symbol forSymbol)
  { return null; } //{ return marketMakers.get(forSymbol).lastTrade; }

  public void placeSellLimitOrder(CompId compId, Symbol symbol, int quantity, Money price) {
    // TODO: immediate execution if outstanding bids
    
    OfferData offerData = offers.get(symbol);
    synchronized(offerData) {
      offerData.sell(price, compId, symbol, quantity);
    }
  }

  public Money lowestAsk(Symbol forSymbol) {
    return offers.get(forSymbol).lowestAsk;
  }

  static class BidData {
    Money highestBid = Money.NA;

    void bid(int quantity, Money bidPrice) {
      assert (null != bidPrice);
      assert (0 < quantity);
      highestBid = (Money.NA == highestBid)
        ? bidPrice
        : Money.max(highestBid, bidPrice);
    }
  }

  static class OfferData {
    final TreeMap<Money, List<Order>> orders = new TreeMap<Money, List<Order>>();
    Money lowestAsk = Money.NA;

    public void sell(Money offerPrice, CompId compId, Symbol symbol, int quantity) {
      if (!orders.containsKey(offerPrice)) {
        orders.put(offerPrice, new ArrayList<Order>());
      }
      orders.get(offerPrice).add(new Order(compId, symbol, quantity));
      lowestAsk = (Money.NA == lowestAsk)
        ? offerPrice
        : Money.min(lowestAsk, offerPrice);
    }
  }*/
}
