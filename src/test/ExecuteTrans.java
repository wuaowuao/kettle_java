package test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class ExecuteTrans {
    public static void main(String[] args) {
        try {
            KettleEnvironment.init();
            TransMeta transMeta = new TransMeta("/Users/zhoukewei/Documents/Untitled.ktr");
            Trans trans = new Trans(transMeta);
            trans.prepareExecution(null);
            trans.startThreads();
            trans.waitUntilFinished();
            if (trans.getErrors() != 0) {
                System.out.println("Error");
            }
        } catch (KettleException e) {
            e.printStackTrace();
        }

    }
}

