package com.wleszczuk.exchangesim.console;

import com.wleszczuk.exchangesim.simulation.Simulation;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Aug 18, 2011
 * Time: 11:22:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleRunner {
  static int columnWidth = 13; // Toolkit.getScreenWidth() / 6;

  public static void main(String[] args) throws Exception {
    Window w = new Window(Toolkit.getScreenWidth(), Toolkit.getScreenHeight(), false, "");
    w.getRootPanel().setPanelColors(new CharColor(CharColor.BLACK, CharColor.BLACK));
    w.setShadow(false);
    DefaultLayoutManager mgr = new DefaultLayoutManager();
    mgr.bindToContainer(w.getRootPanel());

    Simulation simulation = new Simulation(
      getResource("listed-companies.csv"),
      getResource("opening-prices.csv"),
      getResource("registered-traders.csv")
    );

    int i = 0;
    for (String header :
      new String[] { "Sym", "Opn", "Lst", "Chg", "Bid", "Ask", "Vol"}
    )
     { addHeader(mgr, header, i++); }

    // TODO
   /* m.placeSellLimitOrder(new CompId("dkesler"), new Symbol("AAPL"), 100, Money.fromMilliCents(36705480));
    m.placeBuyLimitOrder(new CompId("wleszczuk"), new Symbol("AAPL"), 50, Money.fromMilliCents(36800333));
    m.placeBuyLimitOrder(new CompId("wleszczuk"), new Symbol("GOOG"), 50, Money.fromMilliCents(50200000));
    
    List<Stock> sortedStocks = new ArrayList<Stock>(m.listedStocks());
    Collections.sort(sortedStocks);

    i = 0;
    for (Stock stock : sortedStocks) {
      addRow(mgr, m, stock, ++i);
    }
                               */
    w.setFocusChangeChar(null);
    w.show();

    byte[] chars = new byte[1];
    chars[0] = ' ';
    while ('q' != chars[0]) {
      System.in.read(chars, 0, 1);
    }

    w.close(); // reset the native console
  }

  private static InputStream getResource(String filename) {
    return ConsoleRunner.class.getClassLoader().getResourceAsStream(filename);
  }

  static void addHeader(DefaultLayoutManager mgr, String label, int col) {
    addLabel(mgr, label, 0, col, new CharColor(CharColor.WHITE, CharColor.BLACK));
  }

  /* static void addRow(DefaultLayoutManager mgr, Market m, Stock stock, int row) {
    Money open = stock.openingPrice;
    Money last = m.lastTrade(stock.symbol);
    Money.Difference difference = last.subtract(open);
    String differenceString;

    CharColor diffColor = new CharColor(CharColor.BLACK, CharColor.WHITE);
    if (Money.Difference.NA == difference)
      { differenceString = rightShift(difference.toString()); }
    else {
      differenceString = difference.signString() + rightShift(difference.magnitudeString(), 10);
      if (0 != difference.magnitudeInMilliCents) {
        diffColor = new CharColor(
          CharColor.BLACK,
          difference.positive ? CharColor.GREEN : CharColor.RED
        );
      }
    }

    addLabel(mgr, " " + stock.symbol.value, row, 0);
    addLabel(mgr, " " + rightShift(open.toString()), row, 1);
    addLabel(mgr, " " + rightShift(String.valueOf(last)), row, 2, diffColor);
    addLabel(mgr, " " + differenceString, row, 3, diffColor);
    addLabel(mgr, " " + rightShift(m.highestBid(stock.symbol).toString()), row, 4);
    addLabel(mgr, " " + rightShift(m.lowestAsk(stock.symbol).toString()), row, 5);
    addLabel(mgr, " " + rightShift(String.valueOf(m.volume(stock.symbol))), row, 6);
  }

*/

  static String rightShift(String toShift)
    { return rightShift(toShift, 11);}

  static String rightShift(String toShift, int amt)
    { return String.format(String.format("%%%d.%ds", amt, amt), toShift); }

  static void addLabel(DefaultLayoutManager mgr, String text, int row, int col) {
    addLabel(
      mgr,
      text,
      row,
      col,
      new CharColor(
        1 == row % 2 ? CharColor.BLACK : CharColor.BLUE,
        CharColor.WHITE
      )
    );
  }

  static void addLabel(DefaultLayoutManager mgr, String text, int row, int col, CharColor color) {
    mgr.addWidget(
      new Label(String.format("%-12.12s", text), color),
      2 + columnWidth * col,
      1 + row,
      columnWidth - 1,
      1,
      WidgetsConstants.ALIGNMENT_LEFT,
      WidgetsConstants.ALIGNMENT_TOP
    );
  }
}
