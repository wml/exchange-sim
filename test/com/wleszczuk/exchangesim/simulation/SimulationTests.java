package com.wleszczuk.exchangesim.simulation;

import com.wleszczuk.exchangesim.Money;
import com.wleszczuk.exchangesim.Symbol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 5, 2011
 * Time: 7:19:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimulationTests {
  @Test
  public void test_openingPriceParsesFromCsv() {
    Simulation.OpeningPrice result =
      Simulation.OpeningPrice.fromCsv("AAPL,1000000");
    assertEquals(new Symbol("AAPL"), result.symbol);
    assertEquals(Money.fromMilliCents(1000000), result.price);
  }
}
