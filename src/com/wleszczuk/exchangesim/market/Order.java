package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.CompId;
import com.wleszczuk.exchangesim.Symbol;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 20, 2011
 * Time: 10:53:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Order {
  public final CompId owner;
  public final Symbol symbol;
  public int quantity;

  Order(CompId owner, Symbol symbol, int quantity) {
    this.owner = owner;
    this.symbol = symbol;
    this.quantity = quantity;
  }
}
