package net.dreamer.why;

import net.dreamer.why.item.WhyItemRegistry;
import net.dreamer.why.util.WhyGameruleRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Why implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Why?");
	public static final String MOD_ID = "why";


	@Override
	public void onInitialize() {
		LOGGER.info("WHY???");



		WhyItemRegistry.register();
		WhyGameruleRegistry.register();
	}
}
