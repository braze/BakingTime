package udacity.example.com.bakingtime;

import java.util.ArrayList;

import udacity.example.com.bakingtime.model.Bake;

public interface OnTaskCompleted {
    void onTaskCompleted(ArrayList<Bake> list);

}
