package pl.kfrak.jobserviceexample;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    JobScheduler jobScheduler;
    private Chronometer chronometer;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startJobButton = (Button) findViewById(R.id.activity_main_start_job);
        Button canselJobButton = (Button) findViewById(R.id.activity_main_cancel_jobs);
        chronometer = (Chronometer) findViewById(R.id.activity_main_chronometer);

        //zbieramy sobie mechanizm zarzadzajacy jobami; mowimy do JobS: "odpal job service", taki sposób komunikacji z
        //komponentami
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

    }

    public void startJob(View view){
        //jobScheduler service zarzadzajacy (usluga systemowa) jobami
        //chronemeter - odmierza czas
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        //componentName potrzebne jako drugi parametr do JobInfo.Buildera
        ComponentName componentName = new ComponentName(this, JobServiceExample.class);
        //info o jobie który chcemy wykonać
        JobInfo.Builder builder = new JobInfo.Builder(997, componentName)
                .setRequiresCharging(true);
//                .setPeriodic(1000);
//                //dla Androidów w wersji 7+ .setPeriodic (long milisek, long flex milisek)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            builder.setMinimumLatency(4000);
        } else
            builder.setPeriodic(10000);

        JobInfo jobInfo = builder.build();
        //"wrzuć do kolejki", zeschedulować joba
        jobScheduler.schedule(jobInfo);
    }

    public void stopJob(View view){
        chronometer.stop();
//        jobScheduler.cancelAll();
        //lub:
        List<JobInfo> allJobs = jobScheduler.getAllPendingJobs();
        for(JobInfo jobInfo : allJobs){
            Log.d(TAG, String.format("Cancel %d", jobInfo.getId()));
            //jobInfo to tylko informacja o servisie, musimy odwołać się do jobSchedulera
            //jobInfo.stop
            jobScheduler.cancel(jobInfo.getId());
        }
    }
}
