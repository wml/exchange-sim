package com.wleszczuk.exchangesim.simulation;

import com.wleszczuk.exchangesim.Company;
import com.wleszczuk.exchangesim.Money;
import com.wleszczuk.exchangesim.Symbol;
import com.wleszczuk.exchangesim.market.Balance;
import com.wleszczuk.exchangesim.market.Market;
import com.wleszczuk.exchangesim.market.MarketMaker;
import com.wleszczuk.exchangesim.market.Trader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 5, 2011
 * Time: 7:18:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Simulation {
  public final Market market;

  public Simulation(
    InputStream listedCompaniesCsv,
    InputStream openingPricesCsv,
    InputStream tradersCsv
  ) throws IOException {
    List<Company> listedCompanies = parseCompanies(listedCompaniesCsv);
    Map<Symbol, Money> openingPrices = parseOpeningPrices(openingPricesCsv);

    List<MarketMaker> marketMakers =
      new ArrayList<MarketMaker>(listedCompanies.size());

    for (Company current : listedCompanies) {
      assert (openingPrices.containsKey(current.symbol()));

      marketMakers.add(
        MarketMaker.forInstrumentsOfCompany(
          current,
          new Balance(Money.fromMilliCents(1000)), // TODO
          openingPrices.get(current.symbol()),
          (long)(current.sharesOutstanding() * .5)
        )
      );
    }

    market = new Market(
      marketMakers,
      parseTraders(tradersCsv)
    );
  }

  List<Company> parseCompanies(InputStream listedCompaniesCsv)
    throws IOException {

    return generateCollectionFromNonblankLines(
      listedCompaniesCsv,
      new Generator<Company>() {
        @Override
        public Company generate(String line)
          { return Company.fromCsv(line); }
      }
    );
  }

  Map<Symbol, Money> parseOpeningPrices(InputStream openingPricesCsv)
    throws IOException {
    Map<Symbol, Money> result = new HashMap<Symbol, Money>();

    List<OpeningPrice> openingPrices = generateCollectionFromNonblankLines(
      openingPricesCsv,
      new Generator<OpeningPrice>() {
        @Override
        public OpeningPrice generate(String line)
          { return OpeningPrice.fromCsv(line); }
      }
    );

    for (OpeningPrice current : openingPrices)
      { result.put(current.symbol, current.price); }

    return result;
  }

  private List<Trader> parseTraders(InputStream tradersCsv) throws IOException {
    return generateCollectionFromNonblankLines(
      tradersCsv,
      new Generator<Trader>() {
        @Override
        public Trader generate(String line)
          { return Trader.fromCsv(line); }
      }
    );
  }

  <T> List<T> generateCollectionFromNonblankLines(
    InputStream rawData,
    Generator<T> generator
  ) throws IOException {

    List<T> result = new ArrayList<T>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(rawData));

    String line;
    while (null != (line = reader.readLine())) {
      line = line.trim();
      if (!"".equals(line))
        { result.add(generator.generate(line)); }
    }

    reader.close();
    return result;
  }

  interface Generator<T>
    { T generate(String line); }

  static class OpeningPrice {
    public final Symbol symbol;
    public final Money price;

    public static OpeningPrice fromCsv(String line) {
      String[] parts = line.split(",");
      assert (2 == parts.length);

      return new OpeningPrice(
        new Symbol(parts[0]),
        Money.fromMilliCents(Long.valueOf(parts[1]))
      );
    }

    private OpeningPrice(Symbol symbol, Money price){
      this.symbol = symbol;
      this.price = price;
    }
  }
}
