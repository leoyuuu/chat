import me.leoyuu.util.Logger;
import org.junit.Test;

public class TestLogger {

    @Test
    public void testLog(){
        Logger.INSTANCE.setPrintLevel(Logger.Level.Debug);
        Logger.INSTANCE.d("d", null);
        Logger.INSTANCE.i("callback", null);
        Logger.INSTANCE.w("w", new Exception("warning exception"));
        Logger.INSTANCE.e("e", null);
    }
}
