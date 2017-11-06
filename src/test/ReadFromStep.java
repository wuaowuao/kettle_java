package test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.RowAdapter;
import org.pentaho.di.trans.step.RowListener;
import org.pentaho.di.trans.step.StepInterface;

import java.util.ArrayList;
import java.util.List;

public class ReadFromStep {
    public static void main(String[] args) {
        String fileName = "";
        String stepName = "";
        try {
            KettleEnvironment.init();
            TransMeta transMeta = new TransMeta(fileName);
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

            trans.startThreads();
            trans.waitUntilFinished();

            if (trans.getErrors() != 0) {
                System.out.println("Error");
            } else {
                System.out.println("we read" + rows.size() + "rows from step" + stepName);
            }
        } catch (KettleException e) {
            e.printStackTrace();
        }

    }
}
