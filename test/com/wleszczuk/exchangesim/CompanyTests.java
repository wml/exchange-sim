package com.wleszczuk.exchangesim;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: will
 * Date: Sep 5, 2011
 * Time: 3:59:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompanyTests {
  Company underTest;

  @Before
  public void setup()
    { underTest = Company.fromCsv("ORCL,5060000000,TECH,AS"); }

  @Test
  public void test_parseFromCsvCapturesSymbol()
    { assertEquals(new Symbol("ORCL"), underTest.symbol()); }

  @Test
  public void test_parseFromCsvCapturesSharesOutstanding()
    { assertEquals(5060000000L, underTest.sharesOutstanding()); }

  @Test
  public void test_parseFromCsvCapturesSector()
    { assertEquals(Company.Sector.TECH, underTest.sector()); }

  @Test
  public void test_parseFromCsvCapturesIndustry()
    { assertEquals(Company.Industry.AS, underTest.industry()); }
}
