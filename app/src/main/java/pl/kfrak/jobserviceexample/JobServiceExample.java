package pl.kfrak.jobserviceexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

/**
 * Created by RENT on 2017-08-11.
 */

public class JobServiceExample extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //Odpalamy tu AsyncTask np.
        Toast.makeText(this, "onStartJob", Toast.LENGTH_SHORT).show();

        //True - jesli service odpali asyncTask
        //False - jesli nie ma więcej pracy dla tego joba
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(this, "STOP JOB", Toast.LENGTH_SHORT).show();
        return false;
    }
}
