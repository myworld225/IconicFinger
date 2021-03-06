package customdialog;

//import customdialog.DialogController.Delta;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Stage;
/**
 * 해당 노드에 Draggable 속성을 부여하는 클래스
 * @author Root
 * 
 */
public class DraggableFactory {
	
	private static class Delta {
    double x, y;
	}
	//해당 스테이지와 최상위 노드를 받아서 draggable로 만든다.
	public static void makeDraggable(Stage stage, Node dragable) {
		// Stage stage = (Stage) cancel.getScene().getWindow();

		final Delta dragDelta = new Delta();
		dragable.setOnMousePressed(mouseEvent -> {
			// record a delta distance for the drag and drop operation.
			dragDelta.x = stage.getX() - mouseEvent.getScreenX();
			dragDelta.y = stage.getY() - mouseEvent.getScreenY();
			dragable.setCursor(Cursor.HAND);
		});

		final BooleanProperty inDrag = new SimpleBooleanProperty(false);

		dragable.setOnMouseReleased(mouseEvent -> {
			dragable.setCursor(Cursor.DEFAULT);

			if (inDrag.get()) {
//				stage.hide();
			}

			inDrag.set(false);
		});
		dragable.setOnMouseDragged(mouseEvent -> {
			stage.setX(mouseEvent.getScreenX() + dragDelta.x);
			stage.setY(mouseEvent.getScreenY() + dragDelta.y);

			inDrag.set(true);
		});
	}
}
