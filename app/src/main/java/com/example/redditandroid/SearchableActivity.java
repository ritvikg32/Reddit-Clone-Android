package com.example.redditandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.redditandroid.models.PostData1Children;
import com.example.redditandroid.models.SearchSR;
import com.example.redditandroid.models.SubredditMineListing;
import com.example.redditandroid.mv.TabHomeViewModel;
import com.example.redditandroid.repository.TabHomeObserver;
import com.example.redditandroid.repository.TabHomeRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class SearchableActivity extends ListActivity implements TabHomeObserver {

    private TabHomeViewModel tabHomeMv = new TabHomeViewModel();
    private TabHomeRepository tabHomeRepository = new TabHomeRepository();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabHomeRepository.registerObserver(this);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }

    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    private void doSearch(String query){
        tabHomeMv.searchSubreddit(query);
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public Object OnSearchResultFetched(@NotNull SearchSR search, @NotNull Continuation<? super Unit> $completion) {
        if(search!=null){
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, search.getName());
            setListAdapter(adapter);
            getListAdapter().notifyAll();
        }
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Object PostsFetched(@NotNull ArrayList<PostData1Children> postList, @NotNull Continuation<? super Unit> $completion) {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Object SubredditInfoFetched(@NotNull SubredditMineListing subredditMineListing, @NotNull Continuation<? super Unit> $completion) {
        return null;
    }
}
