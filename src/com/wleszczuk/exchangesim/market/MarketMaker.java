package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.Company;
import com.wleszczuk.exchangesim.Money;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 5, 2011
 * Time: 1:01:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarketMaker extends Participant {
  private final Company specialty;
  long quantity;
  Money bid;
  Money ask;
  long volume;
  Money lastTrade;
  long quantityChange;

  public static MarketMaker forInstrumentsOfCompany(
    Company specialty,
    Balance balance,
    Money openingPrice,
    long quantity
  )
    { return new MarketMaker(specialty, balance, openingPrice, quantity); }

  private MarketMaker(
    Company specialty,
    Balance balance,
    Money openingPrice,
    long quantity
  ) {
    super(balance);

    this.specialty = specialty;
    this.quantity = quantity;

    volume = 0;
    // TODO: configurable spread and adjustment strategy
    bid = Money.fromMilliCents((long)(openingPrice.valueInMilliCents * .99));
    ask = Money.fromMilliCents((long)(openingPrice.valueInMilliCents * 1.01));
    lastTrade = Money.NA;
  }

  public Company specialty() { return specialty; }
  public Money bid() { return bid; }
  public Money ask() { return ask; }
  public long volume() { return volume; }

  // TODO: check against quantity avail. may need to naked short
  // TODO: synchronization
  public Money sell(Trader counterParty, int quantity) {
    volume += quantity;
    // TODO: actually exchange stocks
    counterParty.balance().transferMoneyTo(balance(), ask.multiply(quantity));
    lastTrade = ask;
    return ask;
  }

  public Money buy(Trader counterParty, int quantity) {
    volume += quantity;
    balance().transferMoneyTo(counterParty.balance(), bid.multiply(quantity));
    lastTrade = bid;
    return bid;
  }
}
