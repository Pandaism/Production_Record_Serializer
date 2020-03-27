package com.pandaism.serializer.thread;

import com.pandaism.serializer.controller.units.fleetmind.DVR;
import javafx.concurrent.Task;
import org.apache.poi.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class URLConnectionThread<T> extends Task<Void> {
    private T unit;

    public URLConnectionThread(T unit) {
        this.unit = unit;
    }

    @Override
    public Void call() throws Exception {
        if(this.unit instanceof DVR) {
            DVR unit = (DVR) this.unit;
            unit.setMonitorBytes(openConnection(unit.getMonitor(), 219));
            unit.setCpuBytes(openConnection(unit.getCpu_serial(), 206));
            unit.setSimBytes(openConnection(unit.getSim(), 310));
            if(!unit.getRfid().isEmpty()) {
                unit.setRfidBytes(openConnection(unit.getRfid(), 167));
            }
        }

        return null;
    }

    private byte[] openConnection(String input, int width) throws IOException {
        URLConnection connection = new URL("https://www.barcodesinc.com/generator/image.php?code=" + input + "&style=197&type=C128B&width=" + width + "&height=50&xres=1&font=3").openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        InputStream monitorStream = connection.getInputStream();
        return IOUtils.toByteArray(monitorStream);
    }
}
