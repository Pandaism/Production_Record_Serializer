package com.pandaism.serializer.thread;

import com.pandaism.serializer.controller.units.Singular;
import com.pandaism.serializer.controller.units.fleetmind.DVR;
import com.pandaism.serializer.controller.units.fleetmind.Tablets;
import org.apache.poi.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Used to manage the grabbing barcode data from external website
 *
 * @param <T> unit
 */
public class URLConnectionThread<T> implements Runnable {
    private T unit;
    private HttpURLConnection connection;

    public URLConnectionThread(T unit) {
        this.unit = unit;
    }

    private byte[] openConnection(String input, int width) throws IOException {
        this.connection = (HttpURLConnection) new URL("https://www.barcodesinc.com/generator/image.php?code=" + input + "&style=197&type=C128B&width=" + width + "&height=50&xres=1&font=3").openConnection();
        this.connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        InputStream monitorStream = this.connection.getInputStream();
        return IOUtils.toByteArray(monitorStream);
    }

    @Override
    public void run() {
        try {
            if (this.unit instanceof DVR) {
                DVR unit = (DVR) this.unit;
                unit.setMonitorBytes(openConnection(unit.getMonitor(), 219));
                unit.setSimBytes(openConnection(unit.getSim(), 310));
                if (!unit.getRfid().isEmpty()) {
                    unit.setRfidBytes(openConnection(unit.getRfid(), 167));
                }
            }

            if (this.unit instanceof Tablets) {
                Tablets unit = (Tablets) this.unit;
                unit.setSimBytes(openConnection(unit.getSim(), 310));
                if (!unit.getDocking_station().isEmpty()) {
                    unit.setDocking_stationBytes(openConnection(unit.getDocking_station(), 206));
                }
            }

            if(this.unit instanceof Singular) {
                Singular unit = (Singular) this.unit;
                unit.setCpuBytes(openConnection(unit.getCpu_serial(), 206));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
