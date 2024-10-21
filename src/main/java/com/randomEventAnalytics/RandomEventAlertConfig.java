package rea.randomEventAlert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("higuys")
public interface RandomEventAlertConfig extends Config {
    @ConfigItem(
            keyName = "announcementVolume",
            name = "Announcement volume",
            description = "Adjust how loud the audio announcements are played!",
            position = 0
    )
    default int announcementVolume() {
        return 100;
    }
}
