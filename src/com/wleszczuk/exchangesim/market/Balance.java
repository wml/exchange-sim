package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.Money;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 8, 2011
 * Time: 12:35:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Balance {
  Money amount;

  public Balance(Money startingAmount) { this.amount = startingAmount; }

  void transferMoneyTo(Balance counterPartyBalance, Money amount) {
    assert (this.amount.valueInMilliCents >= amount.valueInMilliCents);
    this.amount = Money.fromDifference(this.amount.subtract(amount));
    counterPartyBalance.amount = counterPartyBalance.amount.add(amount);
  }
}
