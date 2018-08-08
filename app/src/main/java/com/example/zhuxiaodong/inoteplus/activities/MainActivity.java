package com.example.zhuxiaodong.inoteplus.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhuxiaodong.inoteplus.R;
import com.example.zhuxiaodong.inoteplus.database.NoteDatabaseHelper;
import com.example.zhuxiaodong.inoteplus.entities.ItemBean;
import com.example.zhuxiaodong.inoteplus.widgets.DragLayout;
import com.example.zhuxiaodong.inoteplus.widgets.ItemDataUtils;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private DragLayout dragLayout;
    private ListView listView;
    private ImageView ivIcon, ivBottom;
    private QuickAdapter<ItemBean> quickAdapter;
    private Context mContext;
    protected NoteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        dbHelper = NoteDatabaseHelper.getInstance(this);

        setStatusBar();
        initDragLayout();
        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        FullScreencall();
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        FullScreencall();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void initDragLayout() {
        dragLayout = (DragLayout) findViewById(R.id.dl);
        dragLayout.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
            }

            @Override
            public void onClose() {
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(ivIcon, 1 - percent);
            }
        });
    }

    private void initView() {
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);

        listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(quickAdapter=new QuickAdapter<ItemBean>(this,R.layout.item_left_layout, ItemDataUtils.getItemBeans()) {
            @Override
            protected void convert(BaseAdapterHelper helper, ItemBean item) {
                helper.setImageResource(R.id.item_img,item.getImg())
                        .setText(R.id.item_tv,item.getTitle());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Toast.makeText(MainActivity.this,"Click Item "+position,Toast.LENGTH_SHORT).show();
                switch(position) {
                    case 0:
                        startActivity(new Intent(mContext, PersonalPageActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, FavoriteNoteActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, AlbumDisplayActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, FileExploerActivity.class));
                    default:
                        break;
                }
            }
        });
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dragLayout.open();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent = new Intent(this, NoteEditingActivity.class);
                intent.putExtra("editType", "new_note");
                startActivity(intent);
                break;
        }
    }
}
