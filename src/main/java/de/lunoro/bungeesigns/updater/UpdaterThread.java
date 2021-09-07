package de.lunoro.bungeesigns.updater;

import de.lunoro.bungeesigns.BungeeSigns;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.config.ConfigContainer;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class UpdaterThread extends Thread {

    @Getter
    private final static UpdaterThread instance = new UpdaterThread();

    private final int updateTimeInSeconds;
    private final List<String> servernameList;
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final DataOutputStream dataOutputStream;

    private UpdaterThread() {
        updateTimeInSeconds = ConfigContainer.getInstance().getFile("config").getFileConfiguration().getInt("updateCounter") * 1000;
        servernameList = BungeeSignContainer.getInstance().getServerNameList();
        byteArrayOutputStream = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    }

    @Override
    public void run() {
        while (BungeeSigns.getInstance().isEnabled()) {
            sendUpdate();
            try {
                UpdaterThread.sleep(updateTimeInSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendUpdate() {
        try {
            update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() throws IOException {
        for (String servername : servernameList) {
            dataOutputStream.writeUTF("PlayerCount");
            dataOutputStream.writeUTF(servername);
        }
        Bukkit.getServer().sendPluginMessage(BungeeSigns.getInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}
