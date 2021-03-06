package dbUnitConf;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

import java.io.InputStream;
 
public class ColumnSensingFlatXMLDataSetLoader extends AbstractDataSetLoader {
    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        try (InputStream inputStream = resource.getInputStream()) {
            ReplacementDataSet test = new ReplacementDataSet(builder.build(inputStream));
			test.addReplacementObject("NULL", null);
			return test;
        }
    }
}