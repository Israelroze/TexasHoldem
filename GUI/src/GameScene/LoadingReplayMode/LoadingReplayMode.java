package GameScene.LoadingReplayMode;

import API.InterfaceAPI;
import javafx.concurrent.Task;

public class LoadingReplayMode extends Task<Boolean> {

    private InterfaceAPI model;

    @Override
    protected Boolean call() throws Exception {
        updateProgress(0,3);
        updateMessage("Start Loading");
        updateProgress(1,3);
        model.ReverseHandToStart();
        updateProgress(3,3);
        updateMessage("Loading Finished");
        return true;
    }

    public LoadingReplayMode(InterfaceAPI model )
    {
        this.model = model;
    }

}
