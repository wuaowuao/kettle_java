package test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMetaDataCombi;

import java.net.URISyntaxException;
import java.util.List;

public class KettleUtils {

    public static void main(String[] args) throws KettleException, URISyntaxException {
        System.out.println("Hello World!");
        KettleEnvironment.init();
        String path = "/Users/zhoukewei/Documents/Untitled.ktr";
        TransMeta tm = new TransMeta(path);
        Trans trans = new Trans(tm);

        trans.prepareExecution(null);
        System.out.println("Kettle Recording...");

// 记录导入的每行数据
//        final List<RowMetaAndData> rows = new ArrayList<RowMetaAndData>();
//        RowListener rowListener = new RowAdapter() {
//            @Override
//            public void rowWrittenEvent(RowMetaInterface rowMeta, Object[] row) throws KettleStepException {
//                //rows.add(new RowMetaAndData(rowMeta, row));
//            }
//        };
        List<StepMetaDataCombi> steps = trans.getSteps();
        String stepname = steps.get(steps.size() - 1).stepname;
        StepInterface stepInterface = trans.findRunThread(stepname);
//        stepInterface.addRowListener(rowListener);
// 启动并等待执行完成
        trans.startThreads();
        trans.waitUntilFinished();
        System.out.println("Kettle Processing...");

        KettleEnvironment.shutdown();
    }
}
