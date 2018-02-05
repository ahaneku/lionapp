package ng.edu.unnportal.com.lionapp;

import ng.edu.unn.UnnParser.UnnParser.UnnFeed;

import java.util.List;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.NetworkPolicy;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;


public class DetailActivity extends AppCompatActivity{

    public static final String FEED = "feed";

    private static TextView title;
    private static ImageView image;
    private static TextView content;
    private static TextView date;


    private ViewPager vPager;

    private static List<UnnFeed> unnFeed;



    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.feed_detail);

        Toolbar detailBar = (Toolbar) findViewById(R.id.detailToolbar);
        setSupportActionBar(detailBar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        DetailFragmentAdapter dfa = new DetailFragmentAdapter(getSupportFragmentManager());


                //retrives list of unnFeeds
        Feed uf = getIntent().getParcelableExtra(DetailActivity.FEED);
        unnFeed = uf.getUnnFeed();


        CollapsingToolbarLayout ctbl = (CollapsingToolbarLayout) findViewById(R.id.colapseView);
        ctbl.setTitleEnabled(false);


        image = (ImageView) findViewById(R.id.detailImage);
        vPager = (ViewPager) findViewById(R.id.detail_pager);
        vPager.setAdapter(dfa);
        vPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                Picasso.with(getBaseContext()).load(unnFeed.get(position).getImgLink()).
                        networkPolicy(NetworkPolicy.OFFLINE)
                        .centerCrop().into(image);
            }

        });



    }



    public class DetailFragmentAdapter extends FragmentStatePagerAdapter {



        public DetailFragmentAdapter(FragmentManager fm){
            super(fm);

        }


        @Override
        public Fragment getItem(int position){

            DetailFragment details = new DetailFragment();

            Bundle args = new Bundle();
            args.putString(LionApp.TITLE, unnFeed.get(position).getTitle());
            args.putString(LionApp.CONTENT, unnFeed.get(position).getContent());
            args.putString(LionApp.DATE, unnFeed.get(position).getDate());

            details.setArguments(args);

            return details;
        }



        @Override
        public int getCount(){
           return unnFeed.size();
        }

    }


    //fragment to display feed details
    public static class DetailFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state){

            View detailFragment = inflater.inflate(R.layout.detail_fragment, parent, false);


            title = (TextView) detailFragment.findViewById(R.id.detailTitle);
            content = (TextView) detailFragment.findViewById(R.id.detailContent);
            content.setMovementMethod(LinkMovementMethod.getInstance());
            date = (TextView) detailFragment.findViewById(R.id.detailDate);


            return detailFragment;

        }



        @Override
        public void onStart(){
            super.onStart();

            Bundle args = getArguments();

            Spanned span = Html.fromHtml(args.getString(LionApp.CONTENT));
            title.setText(args.getString(LionApp.TITLE));
            content.setText(span);
            date.setText(args.getString(LionApp.DATE));

        }


    }


}
