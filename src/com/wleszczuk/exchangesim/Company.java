package com.wleszczuk.exchangesim;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 5, 2011
 * Time: 3:59:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Company {
  private final Symbol symbol;
  private final long sharesOutstanding;
  private final Sector sector;
  private final Industry industry;

  public static Company fromCsv(String csvData) {
    assert (!"".equals(csvData));

    String[] parts = csvData.split(",");
    assert (4 == parts.length);

    Symbol symbol = new Symbol(parts[0]);
    long sharesOutstanding = Long.valueOf(parts[1]);
    Sector sector = Sector.valueOf(parts[2]);
    Industry industry = Industry.valueOf(parts[3]);

    return new
      Company(symbol, sharesOutstanding, sector, industry);
  }

  public Company(
    Symbol symbol,
    long sharesOutstanding,
    Sector sector,
    Industry industry
  ) {
    this.symbol = symbol;
    this.sharesOutstanding = sharesOutstanding;
    this.sector = sector;
    this.industry = industry;
  }

  public Symbol symbol() { return symbol; }
  public long sharesOutstanding() { return sharesOutstanding; }
  public Sector sector() { return sector; }
  public Industry industry() { return industry; };

  public enum Sector {
    TECH("Technology"),
    CG("Consumer Goods"),
    SVC("Services"),
    FIN("Financial"),
    HC("Healthcare");

    public final String Name;
    Sector(String name) { Name = name; }
  }

  public enum Industry {
    AS("Application Software"),
    PC("Personal Computers"),
    DCS("Diversified Computer Systems"),
    IIP("Internet Information Providers"),
    BEV("Beverages"),
    CMOH("Catalog & Mail Order Houses"),
    MCB("Money Center Banks"),
    INS("Property & Casualty Insurance"),
    RXM("Drug Manufacturers");

    public final String Name;
    Industry(String name) { Name = name; }
  }
}
