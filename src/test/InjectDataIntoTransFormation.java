package test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.RowProducer;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.RowAdapter;
import org.pentaho.di.trans.step.RowListener;
import org.pentaho.di.trans.step.StepInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InjectDataIntoTransFormation {
    public static void main(String[] args) {
        String fileName = "";
        String stepName = "";
        try {
            KettleEnvironment.init();
            TransMeta transMeta = new TransMeta(fileName);
            Result result = new Result();
            result.setRows(createRows());
            transMeta.setPreviousResult(result);

            Trans trans = new Trans(transMeta);
            trans.prepareExecution(null);
            final List<RowMetaAndData> rows = new ArrayList<>();
            RowListener rowListener = new RowAdapter(){
                @Override
                public void rowWrittenEvent(RowMetaInterface rowMeta, Object[] row) throws KettleStepException {
                    rows.add(new RowMetaAndData(rowMeta, row));
                }
            };
            StepInterface stepInterface = trans.findRunThread(stepName);
            stepInterface.addRowListener(rowListener);

            RowProducer rowProducer = trans.addRowProducer("Injector", 0);
            trans.startThreads();
            for (RowMetaAndData row : createRows()) {
                rowProducer.putRow(row.getRowMeta(), row.getData());
            }
            rowProducer.finished();
            trans.startThreads();
            trans.waitUntilFinished();


            if (trans.getErrors() != 0) {
                System.out.println("Error");
            } else {
                System.out.println("we got back" + rows.size());
            }
        } catch (KettleException e) {
            e.printStackTrace();
        }

    }

    private static List<RowMetaAndData> createRows() {
        List<RowMetaAndData> list = new ArrayList<>();
        RowMetaAndData one = new RowMetaAndData();
        one.addValue("date", ValueMetaInterface.TYPE_DATE, new Date());
        list.add(one);
        return list;
    }
}