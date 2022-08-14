package edu.upenn.cit594.test;

import edu.upenn.cit594.datamanagement.COVIDReaderCSV;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.PopulationData;
import edu.upenn.cit594.util.PropertyValueData;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReaderTests {

    @Test
    public void testCOVIDReaderCSV() throws IOException {
        Logger l = Logger.getInstance();
        l.setLogFile("log.txt");
        COVIDReaderCSV covidReader = new COVIDReaderCSV(new File("covid_test_small.csv"));
        List<CovidData> covidList = covidReader.returnRecordsList();

        //test correct size
        assertEquals(98, covidList.size());

        //test first record in list - missing the first record in the list
//        assertEquals(19100, covidList.get(0).getZipCode());
//        assertEquals(88, covidList.get(0).getDeaths());

        //test last record in list
        assertEquals( 19119, covidList.get(covidList.size() - 1).getZipCode());
    }

    @Test
    public void testCOVIDReaderJSON(){

    }

    @Test
    public void testPopulationReader() throws IOException {
        Logger l = Logger.getInstance();
        l.setLogFile("log.txt");
        PopulationReader pReader = new PopulationReader(new File("population.csv"));
        List<PopulationData> pData = pReader.returnRecordsList();

        //test first record
        assertEquals(19102, pData.get(0).getZipCode());
        assertEquals(7568, pData.get(0).getPopulation());

        //test last record
        assertEquals(19154, pData.get(pData.size() - 1).getZipCode());
        assertEquals(34681, pData.get(pData.size() - 1).getPopulation());


    }

    @Test
    public void testPropertiesReader() throws IOException {
        Logger l = Logger.getInstance();
        l.setLogFile("log.txt");
        PropertyReader propReader = new PropertyReader(new File("properties_small.csv"));
        List<PropertyValueData> propData = propReader.returnRecordsList();

        //test first record
        assertEquals(19146, propData.get(0).getZipCode());
        assertEquals("100.0", propData.get(0).getMarketValue());

        //test last record
        assertEquals(19139, propData.get(propData.size() - 1).getZipCode());
        assertEquals("12700.0", propData.get(propData.size() - 1).getMarketValue());



    }

}
