package test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class KettleTest {
    public static void main(String[] args) {
        String filename = "/wang/work/study/Kettle/sample/excel_trans.ktr";
        try {
            KettleEnvironment.init();
            EnvUtil.environmentInit();
            TransMeta transMeta = new TransMeta(filename);
            Trans trans = new Trans(transMeta);
            trans.execute(null); // You can pass arguments instead of null.
            trans.waitUntilFinished();
            if ( trans.getErrors() > 0 )
            {
                throw new RuntimeException( "There were errors during transformation execution." );
            }
        }
        catch (KettleException e ) {
            // TODO Put your exception-handling code here.
            System.out.println(filename);
            System.out.println(e);
        }
    }
}