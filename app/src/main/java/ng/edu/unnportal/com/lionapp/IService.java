package ng.edu.unnportal.com.lionapp;

import ng.edu.unn.UnnParser.UnnParser;
import ng.edu.unn.UnnParser.UnnParser.UnnFeed;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

import android.support.v4.content.LocalBroadcastManager;

import ng.edu.unn.UnnParser.UnnParser;


public class IService extends IntentService{

    public static final String UPDATE_ACTION = "ng.edu.unnportal.com.lionapp.UI_UPDATE";
    public static final String EXTRA_UNN_FEEDS = "unn_feeds_extra";
    public static final String EXTRA_UNN_ERROR = "unn_feed_error";
    public static final String EXTRA_UPDATE_STATE = "update_state";
    public static final String EXTRA_FROM = "ng.edu.unnportal.FROM_URL";
    public static final String FROM = "get_from";

    private UnnParser unnParser;
    private List<UnnFeed> unnFeed;
    private Database db;
    private Thread addFeedsTask = null;

    private Intent updateIntent = new Intent(IService.UPDATE_ACTION);

    private LocalBroadcastManager lbm = null;


    public IService(){
        super("IService");
        db = Database.getInstance(getBaseContext());

    }


    public void onHandleIntent(Intent intent)   {

            //task for unn news retrival

        String from = intent.getStringExtra(IService.FROM);



                if (from.equals(LionApp.GET_FROM_URL)) {

                  //  if(!LionApp.downloadState_getFeeds){

                      //  LionApp.downloadState_getFeeds = true;

                    try {
                        unnParser = UnnParser.getInstance();
                        unnFeed = unnParser.getFeeds();

                    }catch(NullPointerException npe){

                        upDateUI(null);
                        onDestroy();
                    }

                  //  }else{
                      //  this.onDestroy();
                   // }


                  //  if(unnFeed != null){


                        addFeedsTask = new Thread(new Runnable() {


                            @Override
                            public void run() {

                                boolean state = db.addFeeds(unnFeed);

                                if (state)
                                    addFeedsTask = null;

                            }

                        });

                        addFeedsTask.start();


                        updateIntent.putExtra(IService.EXTRA_FROM, LionApp.GET_FROM_URL);
                        upDateUI(unnFeed);
                      // LionApp.downloadState_getFeeds = false;

                  //  }else{



                       // LionApp.downloadState_getFeeds = false;

                  //  }

                } else if (from.equals(LionApp.GET_FROM_DB)) {

                    updateIntent.putExtra(IService.EXTRA_FROM, LionApp.GET_FROM_DB);
                    unnFeed = db.getFeeds();

                    upDateUI(unnFeed);


                }



            }


        public void upDateUI(List<UnnFeed> unnF){



            if(unnF != null){

                Feed uf = new Feed(unnF);

                updateIntent.putExtra(IService.EXTRA_UPDATE_STATE, true);
                updateIntent.putExtra(IService.EXTRA_UNN_FEEDS, uf);
                lbm = LocalBroadcastManager.getInstance(getBaseContext());
                lbm.sendBroadcast(updateIntent);


            }else if(unnF == null){

                updateIntent.putExtra(IService.EXTRA_UPDATE_STATE, false);
                updateIntent.putExtra(IService.EXTRA_UNN_ERROR, getResources().getString(R.string.null_caught));
                lbm = LocalBroadcastManager.getInstance(getBaseContext());
                lbm.sendBroadcast(updateIntent);

            }

            lbm = null;
        }

     }

