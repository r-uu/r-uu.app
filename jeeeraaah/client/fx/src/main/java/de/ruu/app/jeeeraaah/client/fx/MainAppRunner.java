package de.ruu.app.jeeeraaah.client.fx;

import de.ruu.lib.fx.comp.FXCAppRunner;
import de.ruu.lib.fx.comp.FXCAppStartedEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Java FX Component Application Runner {@link MainAppRunner}
 * <p>
 * generated by {@code de.ruu.lib.gen.java.fx.comp.GeneratorFXCAppRunner} at 2024.10.11 17:28:37:127
 */
@Slf4j
public class MainAppRunner extends FXCAppRunner
{
	public static void main(String[] args) throws ClassNotFoundException
	{
		log.debug("starting MainAppRunner.class.getName()");

		Module addReadsReceiver   = MainAppRunner.class.getModule();
		Module moduleToBeReceived = MainAppRunner.class.getClassLoader().getUnnamedModule();

		log.debug("setting add-reads vm option for module {} to {}", addReadsReceiver.getName(), moduleToBeReceived.getName());
		addReadsReceiver.addReads(moduleToBeReceived);
		log.debug("set     add-reads vm option for module {} to {}", addReadsReceiver.getName(), moduleToBeReceived.getName());

		// force class to be loaded (and it's static initialiser to be executed)
		// add-reads module of FXCAppStartedEvent to ALL-UNNAMED
		Class<?> clazz = Class.forName(FXCAppStartedEvent.class.getName());
		log.debug("loaded {}", clazz.getName());

		FXCAppRunner.run(MainApp.class, args);
		log.debug("finished MainAppRunner.class.getName()");
	}
}