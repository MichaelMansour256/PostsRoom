package com.example.postswithroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView postsRecyclerView;
    private Button insertBtn,getBtn;
    private EditText titleEt,bodyEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postsRecyclerView=findViewById(R.id.posts_recyclerView);
        PostsAdapter postsAdapter=new PostsAdapter();
        postsRecyclerView.setAdapter(postsAdapter);

        insertBtn=findViewById(R.id.insertButton);
        getBtn=findViewById(R.id.getButton);
        titleEt=findViewById(R.id.editTexttitle);
        bodyEt=findViewById(R.id.editTextBody);

        final PostsDatabase postsDatabase=PostsDatabase.getInstance(this);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postsDatabase.PostsDao().insertPost(new Post(1,titleEt.getEditableText().toString(),bodyEt.getEditableText().toString()))
                        .subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@org.jetbrains.annotations.NotNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@org.jetbrains.annotations.NotNull Throwable e) {

                    }
                });
            }
        });
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postsDatabase.PostsDao().getPosts()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<Post>>() {
                            @Override
                            public void onSubscribe(@NotNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NotNull List<Post> posts) {
                                postsAdapter.setList(posts);
                                postsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(@NotNull Throwable e) {

                            }
                        });
            }
        });

    }
}