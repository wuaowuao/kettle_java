package test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;

public class ExecuteJob {
    public static void main(String[] args) {
        try {
            KettleEnvironment.init();
            JobMeta jobMeta = new JobMeta("/Users/zhoukewei/Documents/Untitled123.kjb", null);

            Job job = new Job(null, jobMeta);
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() != 0) {
                System.out.println("Error");
            }
        } catch (KettleException e) {
            e.printStackTrace();
        }

    }

}
