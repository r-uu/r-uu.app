module de.ruu.app.demo.client.executable
{
	exports de.ruu.app.demo.client.executable;

	requires static lombok;
	requires org.slf4j;

  requires javafx.controls;
	requires de.ruu.app.demo.client;
	requires de.ruu.lib.fx.comp;
}