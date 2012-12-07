package com.wleszczuk.exchangesim.market;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 10, 2011
 * Time: 7:25:03 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class Participant {
  private final Balance balance;

  Participant(Balance balance) { this.balance = balance; }

  protected Balance balance() { return balance; }
}
