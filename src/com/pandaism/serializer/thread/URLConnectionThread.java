package com.pandaism.serializer.thread;

import com.pandaism.serializer.controller.units.fleetmind.DVR;
import javafx.concurrent.Task;
import org.apache.poi.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class URLConnectionThread<T> extends Task<HashMap<String, byte[]>> {
    private T unit;

    public URLConnectionThread(T unit) {
        this.unit = unit;
    }

    @Override
    public HashMap<String, byte[]> call() throws Exception {
        HashMap<String, byte[]> map = new HashMap<>();

        if(unit instanceof DVR) {
            map.put("Monitor", openConnection(((DVR)this.unit).getMonitor(), 206));
            map.put("CPU", openConnection(((DVR)this.unit).getCpu_serial(), 206));
            map.put("SIM", openConnection(((DVR)this.unit).getSim(), 206));
            map.put("RFID", openConnection(((DVR)this.unit).getRfid(), 206));
        }

        return map;
    }

    private byte[] openConnection(String input, int width) throws IOException {
        URLConnection connection = new URL("https://www.barcodesinc.com/generator/image.php?code=" + input + "&style=197&type=C128B&width=" + width + "&height=50&xres=1&font=3").openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        InputStream monitorStream = connection.getInputStream();
        return IOUtils.toByteArray(monitorStream);
    }
}
