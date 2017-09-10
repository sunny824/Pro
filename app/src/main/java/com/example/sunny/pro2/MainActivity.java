package com.example.sunny.pro2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.sunny.pro2.R.layout.content_main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ExpandableRecyclerViewAdapter recycleViewAdapter;
    private RecyclerView keywordRview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this,"on Create",Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this,"1111",Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "FloatingActionButton click......", Toast.LENGTH_LONG).show();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                android.content.Intent intent = new android.content.Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Toast.makeText(this,"222",Toast.LENGTH_SHORT).show();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //ArrayList<String> keylist = new ArrayList<String>();
        //keylist.add("骨折");
        HashMap<String,ArrayList<String>> map = new java.util.HashMap<String,ArrayList<String>>();
        ArrayList  a1= new ArrayList<String>();
        a1.add("骨癌");
        a1.add("骨折");
        a1.add("腰稚受損");
        a1.add("11111");

        //map.put("骨科部", a1);

        ArrayList  b1= new ArrayList<String>();
        b1.add("人際關係不良");
        b1.add("幻聽");
        b1.add("心情低洛");
        //map.put("精神部", b1);


        ArrayList<ParentItem> myDataset = new ArrayList<ParentItem>();
           ArrayList<String> keyList = new ArrayList<String>();
           keyList.add("骨折");
           keyList.add("骨腫瘤");
           myDataset.add(new ParentItem("骨科部關鍵字", a1));
           myDataset.add(new ParentItem("精神部關鍵字", b1));

        keywordRview = (RecyclerView) findViewById(R.id.keyWordList);


        recycleViewAdapter = new ExpandableRecyclerViewAdapter(myDataset) ;
        //keywordRview.setLayoutManager();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        keywordRview.setLayoutManager(layoutManager);
        keywordRview.setAdapter(recycleViewAdapter);
        //keywordRview.set
        //= new ExpandableRecyclerViewAdapter();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bmi) {
            android.content.Intent intent = new android.content.Intent(this, BmiActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_item) {
            android.content.Intent intent = new android.content.Intent(this, TestActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add) {
            android.content.Intent intent = new android.content.Intent(this, AddActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_list) {
            android.content.Intent intent = new android.content.Intent(this, ListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_drug) {
            android.content.Intent intent = new android.content.Intent(this, DrugActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_kw) {
            android.content.Intent intent = new android.content.Intent(this, KeywordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_as) {
            android.content.Intent intent = new android.content.Intent(this, AsyncActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ParentItem extends ExpandableGroup {
        public ParentItem(String title, List<String> items) {
            super(title, items);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);


            //sectnoTextView = (TextView) v.findViewById(R.id.textSect);
            //rawTextView = (TextView) v.findViewById(R.id.textRaw);
        }
    }
    public class ParentViewHolder extends GroupViewHolder {
        private TextView parentTitle;
        public ParentViewHolder(View itemView) {
            super(itemView);
            parentTitle = (TextView) itemView.findViewById(R.id.topic);
            //parentTitle.setBackgroundColor(Color.parseColor("#ff4000"));//sunnyTest why 有綠色？
        }
        public void setParentTitle(ExpandableGroup group) {
            parentTitle.setText(group.getTitle());

        }
    }
    public class ChildViewHolder extends com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder {
        private TextView keywordView;
        public ChildViewHolder(View itemView) {
            super(itemView);
            keywordView = (TextView) itemView.findViewById(R.id.menuName);
        }
        public void onBind(String keyword) {

            keywordView.setText(keyword);
        }
    }


    public class ExpandableRecyclerViewAdapter extends com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter<ParentViewHolder, ChildViewHolder> {

        public ExpandableRecyclerViewAdapter(List<? extends ExpandableGroup> groups) {
            super(groups);
        }
        @Override
        public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.r1, parent, false);

            return new ParentViewHolder(view);
        }

        @Override
        public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.r2, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                private SimpleDateFormat ezDateFormat = new SimpleDateFormat("yyyyMMdd");
                @Override
                public void onClick(View view) {
                    //TextView keywordView = (TextView) view.findViewById(R.id.menuName);

                    //SearchView queryMainSearchView = (SearchView) ExpertKeywordMainActivity.this.findViewById(R.id.queryMainSearchView);
                    Toast.makeText(view.getContext(), "test:"+ view.getContext() , Toast.LENGTH_SHORT).show();
                    //queryMainSearchView.setQuery(keywordView.getText(), true);
                }
            });
            return new ChildViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
            final String str = (String) group.getItems().get(childIndex);
            //Toast.makeText(this,"on Create",Toast.LENGTH_SHORT).show();
            holder.onBind(childIndex+"."+str);
        }

        @Override
        public void onBindGroupViewHolder(ParentViewHolder holder, int flatPosition, ExpandableGroup group) {
            holder.setParentTitle(group);
        }
    }
}
