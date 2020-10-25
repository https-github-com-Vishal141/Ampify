package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * @author dean
 */
public abstract class AbstractView {
    protected final MediaModel mediaModel;
    protected final Node viewNode;

    public AbstractView(MediaModel mediaModel) {
        this.mediaModel = mediaModel;
        this.viewNode = initView();
    }

    public Node getViewNode() {
        return viewNode;
    }

    public void setNextHandler(EventHandler<ActionEvent> nextHandler) {
    }

    protected abstract Node initView();
}
