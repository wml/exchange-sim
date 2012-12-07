package com.wleszczuk.exchangesim.market;

import com.wleszczuk.exchangesim.Money;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 20, 2011
 * Time: 8:36:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutionResult {
  public enum OrderStatus {
    New,
    PartiallyFilled,
    Filled,
    DoneForDay,
    Canceled,
    Replaced,
    PendingCancel,
    Stopped,
    Rejected,
    Suspended,
    PendingNew,
    Calculated,
    Expired,
    AcceptedForBidding,
    PendingReplace
  }

  public final OrderStatus status;
  public final int numExecutions;
  public final Money executionPrice;

  ExecutionResult(OrderStatus status, int numExecutions, Money executionPrice) {
    this.status = status;
    this.numExecutions = numExecutions;
    this.executionPrice = executionPrice;
  }
}
