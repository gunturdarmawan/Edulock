package com.example.edulockapp.ui.lockapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edulockapp.R;
import com.example.edulockapp.adapter.AppAdapter;
import com.example.edulockapp.model.AppItem;
import com.example.edulockapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LockApp extends AppCompatActivity {

    LinearLayout layout_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockapp);

        initToolbar();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view_app);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapter appAdapter = new AppAdapter(this, getAllApps());
        recyclerView.setAdapter(appAdapter);

        layout_permission = findViewById(R.id.layout_permission);
    }

    private List<AppItem> getAllApps() {

        List<AppItem> results = new ArrayList<>();

        PackageManager pk = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = pk.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList){
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            results.add(new AppItem(activityInfo.loadIcon(pk),activityInfo.loadLabel(pk).toString(),activityInfo.packageName));
        }

        return results;
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setTitle("App List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
            return true;
    }

    public void setPermission(View view) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    @Override
    protected void onResume() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            if(Utils.checkPermission(this)){
                layout_permission.setVisibility((View.GONE));
            } else {
                layout_permission.setVisibility(View.VISIBLE);
            }
        }
        super.onResume();
    }
}