
public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		//*// Run Dummy App
		runDummyApp();
		/*/// Run Main App
		runMainApp();
		//*/
    }
	
	
	public static void runMainApp() {
		Iteration1AppController App = new Iteration1AppController();
		App.start();
		App.run();
		App.shutdown();
	}
	
	
	public static void runDummyApp() {
		//Can run code here when doing R&D
		DummyAppController App = new DummyAppController();
		App.start();
		App.run();
		App.shutdown();
	}
}
