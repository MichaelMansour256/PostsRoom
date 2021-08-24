package com.example.postswithroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Post.class,version = 1 ,exportSchema = false)
public abstract class PostsDatabase extends RoomDatabase {
    private static PostsDatabase instance;
    public abstract PostsDao PostsDao();

    public static synchronized PostsDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),PostsDatabase.class,"posts_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }

}
