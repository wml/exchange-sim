package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.CompId;
import com.wleszczuk.exchangesim.Money;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 20, 2011
 * Time: 8:09:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Trader extends Participant {
  public final CompId compId;

  public static Trader fromCsv(String csvData)
    { return new Trader(new CompId(csvData)); }
  
  public Trader(CompId compId) {
    super(new Balance(Money.fromMilliCents(0))); // TODO
    this.compId = compId;
  }
}
