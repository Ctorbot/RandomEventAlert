package com.randomEventAlert;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Player;
import net.runelite.api.events.InteractingChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.util.concurrent.ScheduledExecutorService;
import okhttp3.OkHttpClient;

@Slf4j
@PluginDescriptor(name = "Random Event Alert")
public class RandomEventAlertPlugin extends Plugin
{
	private static final int RANDOM_EVENT_TIMEOUT = 150;
	private int lastEventTick = -RANDOM_EVENT_TIMEOUT;
	@Inject
	private Client client;
	@Inject
	private SoundEngine soundEngine;
    @Inject
    private ScheduledExecutorService executor;
    @Inject
    private OkHttpClient okHttpClient;
	
	@Provides
	RandomEventAlertConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(RandomEventAlertConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		executor.submit(() -> {
			SoundFileManager.ensureDownloadDirectoryExists();
			SoundFileManager.downloadAllMissingSounds(okHttpClient);
		});
	}

	@Subscribe
	public void onInteractingChanged(InteractingChanged event)
	{
		Actor source = event.getSource();
		Actor target = event.getTarget();
		Player player = client.getLocalPlayer();
		if (player == null || target != player || player.getInteracting() == source || !(source instanceof NPC) || !getEventNpcIds().contains(((NPC) source).getId()))
		{
			return;
		}

		if (client.getTickCount() - lastEventTick > RANDOM_EVENT_TIMEOUT)
		{
			lastEventTick = client.getTickCount();
			handleRandomEvent((NPC) source);
		}
	}

	private void handleRandomEvent(NPC npc)
	{
		//Play audio here
        soundEngine.playClip(Sound.HI_GUYS_MAIKERU_HERE);
	}

    public static final List<Integer> NPCS = new ArrayList<Integer>()
    {
        {
            add(NpcID.BEE_KEEPER_6747);
            add(NpcID.CAPT_ARNAV);
            add(NpcID.DRUNKEN_DWARF);
            add(NpcID.FLIPPA_6744);
            add(NpcID.GILES);
            add(NpcID.GILES_5441);
            add(NpcID.MILES);
            add(NpcID.MILES_5440);
            add(NpcID.NILES);
            add(NpcID.NILES_5439);
            add(NpcID.PILLORY_GUARD);
            add(NpcID.POSTIE_PETE_6738);
            add(NpcID.RICK_TURPENTINE);
            add(NpcID.RICK_TURPENTINE_376);
            add(NpcID.SERGEANT_DAMIEN_6743);
            add(NpcID.FREAKY_FORESTER_6748);
            add(NpcID.FROG_5429);
            add(NpcID.GENIE);
            add(NpcID.GENIE_327);
            add(NpcID.DR_JEKYLL);
            add(NpcID.DR_JEKYLL_314);
            add(NpcID.EVIL_BOB);
            add(NpcID.EVIL_BOB_6754);
            add(NpcID.LEO_6746);
            add(NpcID.MYSTERIOUS_OLD_MAN_6750);
            add(NpcID.MYSTERIOUS_OLD_MAN_6751);
            add(NpcID.MYSTERIOUS_OLD_MAN_6752);
            add(NpcID.MYSTERIOUS_OLD_MAN_6753);
            add(NpcID.QUIZ_MASTER_6755);
            add(NpcID.DUNCE_6749);
            add(NpcID.SANDWICH_LADY);
            add(NpcID.COUNT_CHECK_12551);
            add(NpcID.COUNT_CHECK_12552);
        }
    };

    public static List<Integer> getEventNpcIds()
    {
        return new ArrayList<>(NPCS);
    }
}
